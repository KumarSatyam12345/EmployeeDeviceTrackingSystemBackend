package com.jsp.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a response in the application.
 * Contains data, total count, execution time, and error details.
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObject {
    /**
     * The data returned in the response.
     */
    private Object data;

    /**
     * The pagination details of items.
     */
    private Pagination pagination;

    /**
     * The error details associated with the response.
     */
    private Errors errors;

}