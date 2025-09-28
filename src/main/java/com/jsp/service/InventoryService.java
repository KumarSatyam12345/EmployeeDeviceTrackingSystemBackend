package com.jsp.service;

import com.jsp.entity.Inventory;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling operations related to {@link Inventory}.
 */
public interface InventoryService {

    /**
     * Fetch all inventory records.
     *
     * @return list of {@link Inventory}
     */
    List<Inventory> getAllInventory();

    /**
     * Save a new inventory record.
     *
     * @param inventory inventory entity to save
     * @return saved {@link Inventory}
     */
    Inventory saveInventory(Inventory inventory);

    /**
     * Get inventory record by ID.
     *
     * @param id inventory ID
     * @return optional {@link Inventory} if found
     */
    Optional<Inventory> getInventoryById(int id);

    /**
     * Delete inventory record by ID.
     *
     * @param id inventory ID
     * @return true if deleted, false if not found
     */
    boolean deleteInventoryById(int id);

    /**
     * Update inventory record by ID.
     *
     * @param id        inventory ID
     * @param inventory updated inventory data
     * @return optional {@link Inventory} with updated data if found
     */
    Optional<Inventory> updateInventoryById(int id, Inventory inventory);
}
