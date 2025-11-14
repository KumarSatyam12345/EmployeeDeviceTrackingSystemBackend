package com.jsp.exception.handler;

import com.jsp.exception.ExceptionConstants;
import com.jsp.exception.UnauthorizedException;
import com.jsp.exception.UnprocessableResourceException;
import com.jsp.exception.response.Errors;
import com.jsp.exception.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Global exception handler for handling {@link UnauthorizedException}.
 * This class is annotated with {@link RestControllerAdvice} to handle exceptions
 * across the whole application in one global handling component.
 *
 */
@RestControllerAdvice
@Slf4j
public class UnprocessableResourceExceptionHandler {

    private final MessageSource errorCodeMessageSource;

    /**
     * Constructs a new UnauthorizedExceptionHandler with the specified MessageSource.
     *
     * @param errorCodeMessageSource the MessageSource to use for error message localization
     */
    public UnprocessableResourceExceptionHandler(MessageSource errorCodeMessageSource) {
        this.errorCodeMessageSource = errorCodeMessageSource;
    }

    /**
     * Handles {@link UnauthorizedException} and returns a standardized error response.
     *
     * @param ex     the exception to handle
     * @param locale the locale to use for error message localization
     * @return a {@link ResponseEntity} containing the error response
     */
    @ExceptionHandler(UnprocessableResourceException.class)
    public ResponseEntity<Object> handleException(UnprocessableResourceException ex, Locale locale) {
        log.error("Unprocessable resource", ex);
        Map<String, String> invalidFields = ex.getInvalidFields();

        // Converts map value to message resource
        if (invalidFields != null) {
            invalidFields = new HashMap<>(invalidFields); // Creates a new amp to avoid immutable map
            invalidFields.replaceAll(convertKeyToMessage(locale));
        }

        Errors errors = Errors.builder()
                .code(ExceptionConstants.ERROR_CODE_UNPROCESSABLE)
                .message(errorCodeMessageSource.getMessage(
                        ExceptionConstants.ERROR_CODE_UNPROCESSABLE, null, locale))
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .fieldErrors(invalidFields)
                .build();
        log.error("Error response: {}", errors);

        // Builds and returns the response entity with the validation error details
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResponseObject.builder()
                .errors(errors)
                .build());
    }

    private BiFunction<String, String, String> convertKeyToMessage(Locale locale) {
        return (key, value) -> errorCodeMessageSource.getMessage(value, null, locale);
    }
}
