package com.jsp.controller;

import com.jsp.dto.LoginRequestDto;
import com.jsp.dto.UserRequestDto;
import com.jsp.service.LocalAuthService;
import com.jsp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling user login and account creation.
 */
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@Slf4j
@AllArgsConstructor
public class LocalAuthController {

    private final UserService userService;
    private final LocalAuthService localAuthService;

    /**
     * Authenticates a user with email and password.
     *
     * @param loginRequest login credentials
     * @return success response with user or error status
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDto loginRequest) {
        return localAuthService.login(loginRequest);
    }

    /**
     * Registers a new user.
     *
     * @param request user details
     * @return created user or conflict error
     */
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody UserRequestDto request) {
        return localAuthService.createUser(request);
    }

}
