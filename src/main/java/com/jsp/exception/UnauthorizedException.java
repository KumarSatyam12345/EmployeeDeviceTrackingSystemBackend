package com.jsp.exception;

import java.io.Serial;

/**
 * Exception thrown when an unauthorized action is attempted.
 * This exception extends {@link RuntimeException} and can be used to indicate
 * that a particular operation is not authorized.
 *
 */
public class UnauthorizedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new UnauthorizedException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
