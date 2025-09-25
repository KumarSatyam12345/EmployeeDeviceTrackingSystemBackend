package com.jsp.reposetory;

import com.jsp.entity.Inventory;
import com.jsp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Inventory} entities.
 * Provides CRUD operations via {@link JpaRepository}.
 * Includes a method to check if an inventory exists for a specific user.
 */
@Repository
public interface InventoryReposetory extends JpaRepository<Inventory, Integer> {

    /**
     * Checks if an inventory record exists for the given user.
     *
     * @param user the user to check
     * @return true if an inventory exists for the user, false otherwise
     */
    boolean existsByUser(User user);
}
