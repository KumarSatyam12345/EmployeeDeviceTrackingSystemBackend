package com.jsp.dto;

import lombok.Data;

/**
 * DTO for creating a new user.
 * Contains name, email, and password.
 */
@Data
public class  UserRequestDto {
    private String name;
    private String gmail;
    private String password;
}
