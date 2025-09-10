package com.jsp.controller;

import com.jsp.entity.User;
import com.jsp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserService userService;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class); // Mock service
        userController = new UserController(userService); // Constructor injection
    }

    @Test
    void testGetAllUser() {
        User user1 = new User();
        User user2 = new User();
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        List<User> result = userController.getAllUser();

        assertEquals(2, result.size());
        verify(userService).getAllUsers(); // Verify service method called
    }

    @Test
    void testSaveUser() {
        User user = new User();
        when(userService.saveUser(user)).thenReturn(user);

        var response = userController.saveUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User record saved successfully!", response.getBody());
        verify(userService).saveUser(user);
    }

    @Test
    void testGetUserByIdFound() {
        User user = new User();
        when(userService.getUserById(1)).thenReturn(Optional.of(user));

        var response = userController.getUserById(1);

        assertEquals(user, response.getBody());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userService.getUserById(1)).thenReturn(Optional.empty());

        var response = userController.getUserById(1);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteUserFound() {
        when(userService.deleteUserById(1)).thenReturn(true);

        var response = userController.deleteUser(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Data is deleted", response.getBody());
    }

    @Test
    void testDeleteUserNotFound() {
        when(userService.deleteUserById(1)).thenReturn(false);

        var response = userController.deleteUser(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void testUpdateUserFound() {
        User updatedUser = new User();
        User savedUser = new User();
        when(userService.updateUserById(1, updatedUser)).thenReturn(Optional.of(savedUser));

        var response = userController.updateUser(1, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User record updated", response.getBody());
    }

    @Test
    void testUpdateUserNotFound() {
        User updatedUser = new User();
        when(userService.updateUserById(1, updatedUser)).thenReturn(Optional.empty());

        var response = userController.updateUser(1, updatedUser);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }
}
