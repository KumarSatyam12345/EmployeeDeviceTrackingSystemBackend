package com.jsp.controller;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please insert the data");
        }
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User record saved successfully!");
    }

    @GetMapping("/getAllUser")
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
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
}
