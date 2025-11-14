package com.jsp.exception;


import lombok.Getter;
import lombok.ToString;

import java.util.Map;

/**
 * Exception thrown when a resource cannot be processed.
 * This exception extends {@link RuntimeException} and can be used to indicate
 * that a particular resource has invalid fields that prevent it from being processed.
 *
 */
@Getter
@ToString
public class UnprocessableResourceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    // Map containing invalid fields and their corresponding error messages
    private final Map<String, String> invalidFields;

    /**
     * Constructs a new UnprocessableResourceException with the specified detail message
     * and invalid fields.
     *
     * @param message       the detail message
     * @param invalidFields a map containing invalid fields and their corresponding error messages
     */
    public UnprocessableResourceException(String message, Map<String, String> invalidFields) {
        super(message);
        this.invalidFields = invalidFields;
    }

    /**
     * Constructs a new UnprocessableResourceException with the specified detail message,
     * cause, and invalid fields.
     *
     * @param message       the detail message
     * @param cause         the cause of the exception
     * @param invalidFields a map containing invalid fields and their corresponding error messages
     */
    public UnprocessableResourceException(String message, Throwable cause,
                                          Map<String, String> invalidFields) {
        super(message, cause);
        this.invalidFields = invalidFields;
    }
}

