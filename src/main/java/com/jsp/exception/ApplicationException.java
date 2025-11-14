package com.jsp.exception;

import lombok.Getter;
import lombok.ToString;

import java.io.Serial;

/**
 * Exception thrown for application-specific errors.
 * <p>
 * This exception extends {@link RuntimeException} and can be used to indicate
 * that a particular operation has failed due to an application-specific reason.
 * It supports optional additional arguments for more context.
 * </p>
 *
 *
 */
@Getter
@ToString
public class ApplicationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ApplicationException with the specified detail message.
     *
     * @param message the detail message
     */
    public ApplicationException(String message) {
        super(message);
    }

    /**
     * Constructs a new ApplicationException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}

