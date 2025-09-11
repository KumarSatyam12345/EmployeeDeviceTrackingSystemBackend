package com.jsp.service;

import com.jsp.entity.Inventory;
import com.jsp.reposetory.InventoryReposetory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryReposetory inventoryReposetory;

    @Autowired
    public InventoryServiceImpl(InventoryReposetory inventoryReposetory) {
        this.inventoryReposetory = inventoryReposetory;
    }

    @Override
    public List<Inventory> getAllInventory() {
        log.info("Fetching all Inventory");
        return inventoryReposetory.findAll();
    }

    @Override
    public Inventory saveInventory(Inventory inventory) {
        return inventoryReposetory.save(inventory);
    }

    @Override
    public Optional<Inventory> getInventoryById(int id) {
        return inventoryReposetory.findById(id);
    }

    @Override
    public boolean deleteInventoryById(int id) {
        Optional<Inventory> inventoryOptional = inventoryReposetory.findById(id);
        if (inventoryOptional.isPresent()) {
            inventoryReposetory.delete(inventoryOptional.get());
            return true;
        }
        return false;
    }

    @Override
    public Optional<Inventory> updateInventoryById(int id, Inventory inventory) {
        Optional<Inventory> existingInventory = inventoryReposetory.findById(id);
        if (existingInventory.isPresent()) {
            Inventory inv = existingInventory.get();
            inv.setDateOfIssue(inventory.getDateOfIssue());
            inv.setReturnDate(inventory.getReturnDate());
            inv.setUser(inventory.getUser());
            inv.setDevice(inventory.getDevice());
            inventoryReposetory.save(inv);
            return Optional.of(inv);
        }
        return Optional.empty();
    }
}
