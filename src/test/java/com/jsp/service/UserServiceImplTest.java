package com.jsp.service;

import com.jsp.entity.User;
import com.jsp.reposetory.InventoryReposetory;
import com.jsp.reposetory.UserReposetory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserReposetory userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private InventoryReposetory inventoryRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserReposetory.class);
        inventoryRepository = mock(InventoryReposetory.class);
        userService = new UserServiceImpl(userRepository, inventoryRepository);
    }

    private User sampleUser() {
        return User.builder()
                .uid(1)
                .name("John Doe")
                .gmail("john@example.com")
                .password("pass123")
                .build();
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
        when(inventoryRepository.existsByUser(user)).thenReturn(false);

        String result = userService.deleteUserById(1);

        assertEquals("User deleted successfully", result);
        verify(userRepository).delete(user);
    }

    @Test
    void testDeleteUserByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        NoSuchElementException exception =
                assertThrows(NoSuchElementException.class, () -> userService.deleteUserById(1));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, never()).delete(any());
    }

    @Test
    void testDeleteUserWithInventory() {
        User user = sampleUser();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(inventoryRepository.existsByUser(user)).thenReturn(true);

        IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> userService.deleteUserById(1));

        assertEquals("Cannot delete user with assigned inventory", exception.getMessage());
        verify(userRepository, never()).delete(any());
    }


    @Test
    void testUpdateUserByIdInvalidName() {
        User existingUser = sampleUser();
        User updatedUser = sampleUser();
        updatedUser.setName(""); // invalid

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updateUserById(1, updatedUser));

        assertEquals("User name cannot be empty", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testUpdateUserByIdSuccess() {
        User existingUser = sampleUser();
        User updatedUser = sampleUser();
        updatedUser.setName("Updated Name");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUserById(1, updatedUser);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(userRepository).save(existingUser);
    }

    @Test
    void testUpdateUserByIdNotFound() {
        User updatedUser = sampleUser();
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> userService.updateUserById(1, updatedUser));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, never()).save(any());
    }
}
