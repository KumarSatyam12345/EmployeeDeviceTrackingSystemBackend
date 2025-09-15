package com.jsp.service;

import com.jsp.entity.User;
import com.jsp.reposetory.InventoryReposetory;
import com.jsp.reposetory.UserReposetory;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserReposetory userRepository;
    private final InventoryReposetory inventoryRepository;

    // Constructor injection
    public UserServiceImpl(UserReposetory userRepository, InventoryReposetory inventoryRepository) {
        this.userRepository = userRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

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
}
