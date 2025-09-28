package com.jsp.service;

import com.jsp.dto.UserRequestDto;
import com.jsp.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link User} entities.
 * Provides methods for CRUD operations and additional user-specific queries.
 */
public interface UserService {

    /**
     * Saves a new {@link User} entity into the database.
     *
     * @param user the user object to be saved
     * @return the saved {@link User} object with generated ID
     */
    User saveUser(User user);

    /**
     * Retrieves all users from the database.
     *
     * @return a list of all {@link User} entities
     */
    List<User> getAllUsers();

    /**
     * Finds a user by its unique ID.
     *
     * @param id the unique identifier of the user
     * @return an {@link Optional} containing the {@link User} if found,
     *         otherwise an empty Optional
     */
    Optional<User> getUserById(int id);

    /**
     * Deletes a user from the database by its ID.
     *
     * @param id the unique identifier of the user
     * @return a success message if deletion is completed
     */
    String deleteUserById(int id);

    /**
     * Updates the details of an existing user by its ID.
     *
     * @param id           the unique identifier of the user
     * @param updatedUser  the new user object containing updated values
     * @return an {@link Optional} containing the updated {@link User} if found,
     *         otherwise an empty Optional
     */
    Optional<User> updateUserById(int id, User updatedUser);

    /**
     * Finds a user by their email address.
     *
     * @param gmail the email address of the user
     * @return an {@link Optional} containing the {@link User} if found,
     *         otherwise an empty Optional
     */
    Optional<User> getUserByEmail(String gmail);

    /**
     * Creates a new user from a {@link UserRequestDto}.
     *
     * @param request the user request DTO containing user data
     * @return the created {@link User} entity
     */
    User createUser(UserRequestDto request);
}
