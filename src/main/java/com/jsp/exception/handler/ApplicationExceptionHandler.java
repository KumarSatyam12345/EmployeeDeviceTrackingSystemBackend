package com.jsp.exception.handler;


import com.jsp.exception.*;
import com.jsp.exception.response.CapitalDeviceResponse;
import com.jsp.exception.response.Errors;
import com.jsp.exception.response.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

/**
 * Global exception handler for handling {@link ApplicationException} and generic {@link Exception}.
 * Provides standardized error responses for REST controllers, including device-specific formatting.
 * <p>
 */
@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    private static final String CORRELATION_ID = "X-Correlation-Id";
    private final MessageSource errorCodeMessageSource;

    /**
     * Constructs the exception handler with the provided message source.
     *
     * @param errorCodeMessageSource the message source for error code localization
     */
    public ApplicationExceptionHandler(MessageSource errorCodeMessageSource) {
        this.errorCodeMessageSource = errorCodeMessageSource;
    }

    /**
     * Handles {@link ApplicationException} and builds an appropriate error response.
     *
     * @param ex      the application exception
     * @param locale  the locale for message localization
     * @param request the HTTP servlet request
     * @return a response entity containing the error response
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleException(ApplicationException ex, Locale locale, HttpServletRequest request) {
        log.error("Application operation failed", ex);
        String requestURI = request.getRequestURI();
        String correlationId = (request.getHeader(CORRELATION_ID) == null) ?
                (String) request.getAttribute(CORRELATION_ID) :
                request.getHeader(CORRELATION_ID);

        if (requestURI.contains(ExceptionConstants.DEVICE_URI)) {
            CapitalDeviceResponse response = CapitalDeviceResponse.builder()
                    .status(errorCodeMessageSource.getMessage("failure.status", null, locale))
                     .totalCount(0)
                    .errors(List.of((org.springframework.validation.Errors) Errors.builder()
                            .code(ExceptionConstants.GENERAL_ERROR_CODE)
                            .message(errorCodeMessageSource.getMessage("internal.server.error", null, locale)
                                    + " " + ex.getMessage()).build()))
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } else {
            Errors errors = Errors.builder()
                    .code(ExceptionConstants.ERROR_CODE_APPLICATION)
                    .message(errorCodeMessageSource.getMessage("cloud.internal.server.error", new Object[]{correlationId}, locale))
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            log.error("Error response: {}", errors);

            // Return an error response with the error code and message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder()
                    .errors(errors)
                    .build());
        }
    }
}
