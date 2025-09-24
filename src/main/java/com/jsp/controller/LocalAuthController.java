package com.jsp.controller;

import com.jsp.dto.LoginRequestDto;
import com.jsp.dto.UserRequestDto;
import com.jsp.entity.User;
import com.jsp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@Slf4j
public class LocalAuthController {

    private final UserService userService;

    public LocalAuthController(UserService userService) {
        this.userService = userService;
    }


    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            String gmail = loginRequest.getGmail();
            String password = loginRequest.getPassword();

            Optional<User> user = userService.getUserByEmail(gmail);
            if (user.isPresent() && user.get().getPassword().equals(password)) {
                log.info("Login successful for {}", gmail);
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login successful");
                response.put("user", user.get());
                return ResponseEntity.ok(response);
            } else {
                log.warn("Invalid login attempt for {}", gmail);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }

        } catch (NullPointerException e){
            log.error("Login failed due to null input", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email and password must be provided");
        }
    }

    // Create new user
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto request) {
        if (userService.getUserByEmail(request.getGmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User with this email already exists");
        }

        User user = userService.createUser(request);
        log.info("New user created: {}", user.getGmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
