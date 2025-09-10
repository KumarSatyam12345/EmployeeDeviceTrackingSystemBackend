package com.jsp.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jsp.entity.Inventory;
import com.jsp.reposetory.InventoryReposetory;

@RestController
@RequestMapping("/inventory")
@CrossOrigin("*")
public class InventoryController {

    @Autowired
    private InventoryReposetory inventoryReposetory;
    @GetMapping("/getAllRepo")
    public List<Inventory> getAllData() {
        return inventoryReposetory.findAll();
    }
    @PostMapping("/saveRepo")
    public ResponseEntity<String> addInventory(@RequestBody @Valid Inventory inventory) {
        try {
        	if (inventory == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please insert the data");
            }

            inventoryReposetory.save(inventory);
            return ResponseEntity.status(HttpStatus.CREATED).body("Data submitted successfully");
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("duplicate entry");
		}
    }
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryDataById(@PathVariable int id) {
        Optional<Inventory> optional = inventoryReposetory.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDataById(@PathVariable int id) {
        Optional<Inventory> optional = inventoryReposetory.findById(id);
        if (optional.isPresent()) {
            inventoryReposetory.delete(optional.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Data deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecordById(@PathVariable int id, @RequestBody @Valid Inventory inventory) {
        Optional<Inventory> optional = inventoryReposetory.findById(id);
        if (optional.isPresent()) {
            Inventory existingInventory = optional.get();
            existingInventory.setDateOfIssue(inventory.getDateOfIssue());
            existingInventory.setReturnDate(inventory.getReturnDate());
            existingInventory.setUser(inventory.getUser());
            existingInventory.setDevice(inventory.getDevice());
            inventoryReposetory.save(existingInventory);
            return ResponseEntity.ok("Data updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inventory with ID " + id + " not found");
    }
}
