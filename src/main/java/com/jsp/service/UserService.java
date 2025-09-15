package com.jsp.service;

import com.jsp.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(int id);

    String deleteUserById(int id);

    Optional<User> updateUserById(int id, User updatedUser);
}
