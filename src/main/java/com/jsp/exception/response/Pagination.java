package com.jsp.exception.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents pagination details in the application.
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
    /**
     * The size of each page
     */
    private int pageSize;

    /**
     * The current page number
     */
    private int currentPage;

    /**
     * The total number of items across all pages
     */
    private long totalItems;

    /**
     * The total number of pages available
     */
    private long totalPages;


}