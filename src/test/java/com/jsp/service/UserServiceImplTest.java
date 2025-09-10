package com.jsp.service;

import com.jsp.entity.User;
import com.jsp.reposetory.UserReposetory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserReposetory userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserReposetory.class);
        userService = new UserServiceImpl(userRepository);
    }

    private User sampleUser() {
        User user = new User();
        user.setName("Satyam");
        user.setGmail("satyam@example.com");
        user.setPassword("pass123");
        return user;
    }

    @Test
    void testSaveUser() {
        User user = sampleUser();
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertEquals(user, savedUser);
        verify(userRepository).save(user);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(sampleUser(), sampleUser());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void testGetUserByIdFound() {
        User user = sampleUser();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(1);

        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteUserByIdFound() {
        User user = sampleUser();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        boolean deleted = userService.deleteUserById(1);

        assertTrue(deleted);
        verify(userRepository).delete(user);
    }

    @Test
    void testDeleteUserByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        boolean deleted = userService.deleteUserById(1);

        assertFalse(deleted);
        verify(userRepository, never()).delete(any());
    }

    @Test
    void testUpdateUserByIdFound() {
        User user = sampleUser();
        User updatedUser = sampleUser();
        updatedUser.setName("Updated Name");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        Optional<User> result = userService.updateUserById(1, updatedUser);

        assertTrue(result.isPresent());
        assertEquals("Updated Name", result.get().getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpdateUserByIdNotFound() {
        User updatedUser = sampleUser();
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Optional<User> result = userService.updateUserById(1, updatedUser);

        assertFalse(result.isPresent());
        verify(userRepository, never()).save(any());
    }
}
