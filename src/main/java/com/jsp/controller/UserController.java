package com.jsp.controller;

import com.jsp.dto.LoginRequestDto;
import com.jsp.dto.UserRequestDto;
import com.jsp.entity.User;
import com.jsp.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@Slf4j
public class UserController {

    private final UserService userService;

    // Constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/SaveUser")
    public ResponseEntity<String> saveUser(@RequestBody @Valid User user) {
        if (user == null) {
            log.info("Received null user data");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please insert the data");
        }
        userService.saveUser(user);
        log.info("User saved successfully: {}", user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User record saved successfully!");
    }

    @GetMapping("/getAllUser")
    public List<User> getAllUser() {
        log.info("Fetching all users");
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        log.info("Fetching user with ID: {}", id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable int id) {
        Map<String, String> response = new HashMap<>();
        try {
            String message = userService.deleteUserById(id);
            response.put("message", message);
            return ResponseEntity.ok(response);

        } catch (NoSuchElementException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (IllegalStateException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody @Valid User updatedUser) {
        Optional<User> updated = userService.updateUserById(id, updatedUser);
        return updated.map(user -> ResponseEntity.ok("User record updated"))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    }

    // ✅ Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
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
    }

    // ✅ Create new user
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
