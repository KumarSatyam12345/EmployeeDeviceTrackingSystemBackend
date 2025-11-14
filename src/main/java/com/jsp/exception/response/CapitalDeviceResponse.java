package com.jsp.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.Errors;

import java.util.List;


/**
 * Represents a capital device response in the application.
 * Contains status, total count and error details.
 *
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CapitalDeviceResponse {

    /**
     * The status of response - to support existing APIs.
     */
    private String status;

    /**
     * The count of items processed - to support existing APIs.
     */
    private Integer totalCount;

    /**
     * The errors associated with the response - to support existing APIs.
     */
    private List<Errors> errors;
}