package com.jsp.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RequestResponseLoggingFilterTest {

    private RequestResponseLoggingFilter filter;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        filter = new RequestResponseLoggingFilter();
        filterChain = mock(FilterChain.class);
    }

    @Test
    void testDoFilterInternal_logsRequestAndResponse() throws ServletException, IOException {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/api/test");
        request.setContent("{\"key\":\"value\"}".getBytes(StandardCharsets.UTF_8));

        MockHttpServletResponse response = new MockHttpServletResponse();

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        // simulate filterChain writing to response
        doAnswer(invocation -> {
            responseWrapper.getWriter().write("{\"success\":true}");
            return null;
        }).when(filterChain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));

        // when
        filter.doFilterInternal(requestWrapper, responseWrapper, filterChain);

        // then: response body should match
        String responseBody = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
        assertEquals("{\"success\":true}", responseBody);

        // copy body to actual MockHttpServletResponse
        responseWrapper.copyBodyToResponse();
        assertEquals("{\"success\":true}", response.getContentAsString());

        // verify filter chain executed once
        verify(filterChain, times(1)).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

    @Test
    void testDoFilterInternal_skipsOptionsRequest() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("OPTIONS");
        request.setRequestURI("/api/test");

        MockHttpServletResponse response = new MockHttpServletResponse();

        // Execute filter
        filter.doFilterInternal(request, response, filterChain);

        // Verify chain executed once with any request/response (because wrapper not applied for OPTIONS)
        verify(filterChain, times(1)).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

}
