package com.jsp.reposetory;

import com.jsp.entity.Inventory;
import com.jsp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryReposetory extends JpaRepository<Inventory, Integer> {
    boolean existsByUser(User user);
}
