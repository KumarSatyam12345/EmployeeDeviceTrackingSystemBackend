package com.jsp.reposetory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.entity.Inventory;
@Repository
public interface InventoryReposetory extends JpaRepository<Inventory, Integer>{
}
