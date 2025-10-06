package com.jsp.service;

import com.jsp.dto.LoginRequestDto;
import com.jsp.dto.UserRequestDto;
import com.jsp.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LocalAuthServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private LocalAuthService controller;


    private User sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleUser = new User();
        sampleUser.setGmail("test@example.com");
        sampleUser.setPassword("password123");
    }

    @Test
    void testLoginSuccess() {
        LoginRequestDto request = new LoginRequestDto();
        request.setGmail("test@example.com");
        request.setPassword("password123");

        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(sampleUser));

        ResponseEntity<?> response = controller.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).getUserByEmail("test@example.com");
    }

    @Test
    void testLoginInvalidPassword() {
        LoginRequestDto request = new LoginRequestDto();
        request.setGmail("test@example.com");
        request.setPassword("wrongpassword");

        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(sampleUser));

        ResponseEntity<?> response = controller.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(userService, times(1)).getUserByEmail("test@example.com");
    }

    @Test
    void testLoginUserNotFound() {
        LoginRequestDto request = new LoginRequestDto();
        request.setGmail("unknown@example.com");
        request.setPassword("password123");

        when(userService.getUserByEmail("unknown@example.com")).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(userService, times(1)).getUserByEmail("unknown@example.com");
    }


    @Test
    void testCreateUserSuccess() {
        UserRequestDto request = new UserRequestDto();
        request.setGmail("newuser@example.com");
        request.setPassword("password123");

        when(userService.getUserByEmail("newuser@example.com")).thenReturn(Optional.empty());
        when(userService.createUser(request)).thenReturn(sampleUser);

        ResponseEntity<?> response = controller.createUser(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).getUserByEmail("newuser@example.com");
        verify(userService, times(1)).createUser(request);
    }

    @Test
    void testCreateUserConflict() {
        UserRequestDto request = new UserRequestDto();
        request.setGmail("test@example.com");
        request.setPassword("password123");

        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(sampleUser));

        ResponseEntity<?> response = controller.createUser(request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(userService, times(1)).getUserByEmail("test@example.com");
        verify(userService, never()).createUser(any());
    }
}
