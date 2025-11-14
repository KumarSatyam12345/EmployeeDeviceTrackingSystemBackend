package com.jsp.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Represents error details in the application.
 * Contains error code, message, timestamp, and field-specific errors.
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Errors {
    /**
     * The error code associated with the error.
     */
    private String code;

    /**
     * The error message describing the error.
     */
    private String message;

    /**
     * The timestamp when the error occurred.
     */
    private Timestamp timestamp;

    /**
     * A map of field-specific errors, where the key is the field name and the value is the error message.
     */
    private Map<String, String> fieldErrors;

}