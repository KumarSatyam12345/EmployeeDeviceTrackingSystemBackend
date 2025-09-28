package com.jsp.service;

import com.jsp.entity.Inventory;
import com.jsp.reposetory.InventoryReposetory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link InventoryService}.
 * <p>
 * Handles business logic for managing {@link Inventory} entities,
 * including CRUD operations such as fetching, saving,
 * updating, and deleting inventory records.
 * </p>
 */
@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryReposetory inventoryReposetory;

    /**
     * Constructs {@code InventoryServiceImpl} with required repository.
     *
     * @param inventoryReposetory repository for inventory persistence
     */
    @Autowired
    public InventoryServiceImpl(InventoryReposetory inventoryReposetory) {
        this.inventoryReposetory = inventoryReposetory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Inventory> getAllInventory() {
        log.info("Fetching all Inventory");
        return inventoryReposetory.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Inventory saveInventory(Inventory inventory) {
        log.info("Saving inventory: {}", inventory);
        return inventoryReposetory.save(inventory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Inventory> getInventoryById(int id) {
        log.info("Fetching inventory with ID: {}", id);
        return inventoryReposetory.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteInventoryById(int id) {
        return inventoryReposetory.findById(id).map(inventory -> {
            log.info("Deleting inventory with ID: {}", id);
            inventoryReposetory.delete(inventory);
            return true;
        }).orElse(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Inventory> updateInventoryById(int id, Inventory inventory) {
        return inventoryReposetory.findById(id).map(existing -> {
            log.info("Updating inventory with ID: {}", id);
            existing.setDateOfIssue(inventory.getDateOfIssue());
            existing.setReturnDate(inventory.getReturnDate());
            existing.setUser(inventory.getUser());
            existing.setDevice(inventory.getDevice());
            return inventoryReposetory.save(existing);
        });
    }
}
