package com.jsp.reposetory;

import com.jsp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for {@link User} entities.
 * Provides CRUD operations via {@link JpaRepository}.
 * Includes a method to find a user by email.
 */
@Repository
public interface UserReposetory extends JpaRepository<User, Integer> {

    /**
     * Finds a user by their email.
     *
     * @param gmail the email of the user
     * @return an Optional containing the user if found, or empty if not
     */
    Optional<User> findByGmail(String gmail);
}
