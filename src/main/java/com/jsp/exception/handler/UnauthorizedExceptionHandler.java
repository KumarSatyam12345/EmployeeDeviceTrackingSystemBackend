package com.jsp.exception.handler;

import com.jsp.exception.ExceptionConstants;
import com.jsp.exception.UnauthorizedException;
import com.jsp.exception.response.Errors;
import com.jsp.exception.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.util.Locale;

/**
 * Global exception handler for handling {@link UnauthorizedException}.
 * This class is annotated with {@link RestControllerAdvice} to handle exceptions
 * across the whole application in one global handling component.
 *
 */
@RestControllerAdvice
@Slf4j
public class UnauthorizedExceptionHandler {

    private final MessageSource errorCodeMessageSource;

    /**
     * Constructs a new UnauthorizedExceptionHandler with the specified MessageSource.
     *
     * @param errorCodeMessageSource the MessageSource to use for error message localization
     */
    public UnauthorizedExceptionHandler(MessageSource errorCodeMessageSource) {
        this.errorCodeMessageSource = errorCodeMessageSource;
    }

    /**
     * Handles {@link UnauthorizedException} and returns a standardized error response.
     *
     * @param ex     the exception to handle
     * @param locale the locale to use for error message localization
     * @return a {@link ResponseEntity} containing the error response
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleException(UnauthorizedException ex, Locale locale) {
        log.error("Unauthorized operation", ex);

        Errors errors = Errors.builder()
                .code(ExceptionConstants.ERROR_CODE_UNAUTHORIZED)
                .message(errorCodeMessageSource.getMessage(
                        ex.getMessage(), null, locale))
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        log.error("Error response: {}", errors);

        // Return an error response with the error code and message
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder()
                .errors(errors)
                .build());
    }
}
