package com.jsp.controller;

import com.jsp.entity.Inventory;
import com.jsp.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
@CrossOrigin("*")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/getAllRepo")
    public List<Inventory> getAllData() {
        return inventoryService.getAllInventory();
    }

    @PostMapping("/saveRepo")
    public ResponseEntity<String> addInventory(@RequestBody @Valid Inventory inventory) {
        try {
            if (inventory == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please insert the data");
            }
            inventoryService.saveInventory(inventory);
            return ResponseEntity.status(HttpStatus.CREATED).body("Data submitted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("duplicate entry");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryDataById(@PathVariable int id) {
        Optional<Inventory> optional = inventoryService.getInventoryById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDataById(@PathVariable int id) {
        boolean deleted = inventoryService.deleteInventoryById(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Data deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecordById(@PathVariable int id, @RequestBody @Valid Inventory inventory) {
        Optional<Inventory> updated = inventoryService.updateInventoryById(id, inventory);
        if (updated.isPresent()) {
            return ResponseEntity.ok("Data updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inventory with ID " + id + " not found");
    }
}
