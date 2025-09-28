package com.jsp.service;

import com.jsp.dto.UserRequestDto;
import com.jsp.entity.User;
import com.jsp.reposetory.InventoryReposetory;
import com.jsp.reposetory.UserReposetory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Implementation of the {@link UserService} interface.
 * <p>
 * This service provides CRUD operations for {@link User} entities
 * and includes additional validation such as checking if a user
 * has assigned inventory before deletion.
 * </p>
 *
 * <p>Uses Spring's {@link Service} annotation to mark it as a service bean
 * and Lombok's {@link Slf4j} for logging.</p>
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserReposetory userRepository;
    private final InventoryReposetory inventoryRepository;

    /**
     * Constructor for dependency injection.
     *
     * @param userRepository      repository for managing {@link User} entities
     * @param inventoryRepository repository for managing inventory assignments
     */
    public UserServiceImpl(UserReposetory userRepository, InventoryReposetory inventoryRepository) {
        this.userRepository = userRepository;
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Saves a {@link User} into the database.
     *
     * @param user the user to save
     * @return the saved {@link User}
     */
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of all {@link User} entities
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user
     * @return an {@link Optional} containing the {@link User} if found,
     *         otherwise an empty Optional
     */
    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    /**
     * Deletes a user by ID if they have no assigned inventory.
     *
     * @param id the ID of the user to delete
     * @return a success message if the user is deleted
     * @throws NoSuchElementException if the user does not exist
     * @throws IllegalStateException  if the user still has assigned inventory
     */
    @Override
    public String deleteUserById(int id) {
        return userRepository.findById(id)
                .map(user -> {
                    if (inventoryRepository.existsByUser(user)) {
                        log.warn("Attempted to delete user {} but they still have assigned inventory", id);
                        throw new IllegalStateException("Cannot delete user with assigned inventory");
                    }
                    userRepository.delete(user);
                    log.info("User with ID {} deleted successfully", id);
                    return "User deleted successfully";
                })
                .orElseThrow(() -> {
                    log.warn("User with ID {} not found for deletion", id);
                    return new NoSuchElementException("User not found");
                });
    }

    /**
     * Updates a user's details by ID.
     *
     * @param id           the ID of the user to update
     * @param updatedUser  a {@link User} containing the updated values
     * @return an {@link Optional} with the updated {@link User} if found,
     *         otherwise an empty Optional
     */
    @Override
    public Optional<User> updateUserById(int id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setGmail(updatedUser.getGmail());
            existingUser.setPassword(updatedUser.getPassword());
            userRepository.save(existingUser);
            return Optional.of(existingUser);
        }
        return Optional.empty();
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param gmail the email of the user
     * @return an {@link Optional} containing the {@link User} if found,
     *         otherwise an empty Optional
     */
    @Override
    public Optional<User> getUserByEmail(String gmail) {
        return userRepository.findByGmail(gmail);
    }

    /**
     * Creates a new user from a {@link UserRequestDto}.
     * <p>
     * Currently, the password is saved as plain text.
     * Consider applying a hashing mechanism like BCrypt for security.
     * </p>
     *
     * @param request the {@link UserRequestDto} containing user details
     * @return the created {@link User}
     */
    @Override
    public User createUser(UserRequestDto request) {
        User user = new User();
        user.setName(request.getName());
        user.setGmail(request.getGmail());
        user.setPassword(request.getPassword());
        return userRepository.save(user);
    }
}
