package com.jsp.controller;

import com.jsp.entity.Inventory;
import com.jsp.service.InventoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing inventory records.
 */
@Slf4j
@RestController
@RequestMapping("/inventory")
@CrossOrigin("*")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Fetches all inventory records.
     *
     * @return list of inventory items
     */
    @GetMapping("/getAllRepo")
    public List<Inventory> getAllData() {
        log.info("Fetching all inventory data");
        return inventoryService.getAllInventory();
    }

    /**
     * Saves a new inventory record.
     *
     * @param inventory inventory details
     * @return success or error response
     */
    @PostMapping("/saveRepo")
    public ResponseEntity<String> addInventory(@RequestBody @Valid Inventory inventory) {
        try {
            if (inventory == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please insert the data");
            }
            inventoryService.saveInventory(inventory);
            log.info("Inventory saved successfully: {}", inventory);
            return ResponseEntity.status(HttpStatus.CREATED).body("Data submitted successfully");
        } catch (Exception e) {
            log.info("Error saving inventory: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("duplicate entry");
        }
    }

    /**
     * Fetches inventory by ID.
     *
     * @param id inventory ID
     * @return inventory record or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryDataById(@PathVariable int id) {
        Optional<Inventory> optional = inventoryService.getInventoryById(id);
        log.info("Fetching inventory with ID: {}", id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    /**
     * Deletes inventory by ID.
     *
     * @param id inventory ID
     * @return success or not found response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDataById(@PathVariable int id) {
        boolean deleted = inventoryService.deleteInventoryById(id);
        if (deleted) {
            log.info("Deleted inventory with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Data deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
    }

    /**
     * Updates inventory by ID.
     *
     * @param id inventory ID
     * @param inventory updated inventory data
     * @return success message or not found response
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecordById(@PathVariable int id, @RequestBody @Valid Inventory inventory) {
        Optional<Inventory> updated = inventoryService.updateInventoryById(id, inventory);
        if (updated.isPresent()) {
            log.info("Updated inventory with ID: {}", id);
            return ResponseEntity.ok("Data updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inventory with ID " + id + " not found");
    }
}
