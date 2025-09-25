package com.jsp.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Filter for logging HTTP requests and responses.
 * <p>
 * Logs request/response bodies, HTTP method, URI, status, and processing time.
 * Sensitive fields like passwords are masked, and long bodies are truncated.
 * </p>
 */
@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
    private static final int MAX_BODY_LENGTH = 500; // max chars to log

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Skip logging for OPTIONS requests
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            String requestBody = new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
            String responseBody = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);

            requestBody = maskSensitiveData(requestBody);
            responseBody = maskSensitiveData(responseBody);

            if (requestBody.length() > MAX_BODY_LENGTH) {
                requestBody = requestBody.substring(0, MAX_BODY_LENGTH) + "...[truncated]";
            }
            if (responseBody.length() > MAX_BODY_LENGTH) {
                responseBody = responseBody.substring(0, MAX_BODY_LENGTH) + "...[truncated]";
            }

            if (!requestBody.isBlank() || !responseBody.isBlank()) {
                log.info("""
                        API Log:
                        Method   : {}
                        Status   : {}
                        URI      : {}
                        Request  : {}
                        Response : {}
                        Time(ms) : {}
                        """,
                        request.getMethod(),
                        response.getStatus(),
                        request.getRequestURI(),
                        requestBody,
                        responseBody,
                        duration
                );
            } else {
                log.info("API {} {} Status={} Time={}ms",
                        request.getMethod(),
                        request.getRequestURI(),
                        response.getStatus(),
                        duration);
            }

            responseWrapper.copyBodyToResponse();
        }
    }

    /**
     * Masks sensitive fields like passwords in JSON bodies.
     *
     * @param body the original request or response body
     * @return body with sensitive fields masked
     */
    private String maskSensitiveData(String body) {
        if (body == null || body.isBlank()) return body;
        return body.replaceAll("(?i)\"password\"\\s*:\\s*\"[^\"]*\"", "\"password\":\"****\"");
    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return true;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return true;
    }
}
