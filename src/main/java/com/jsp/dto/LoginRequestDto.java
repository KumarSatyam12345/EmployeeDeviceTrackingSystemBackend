package com.jsp.dto;

import lombok.Data;

/**
 * DTO for user login requests.
 * Contains email and password.
 */
@Data
public class LoginRequestDto {
    private String gmail;
    private String password;
}
