package com.jsp.service;

import com.jsp.entity.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    List<Inventory> getAllInventory();

    Inventory saveInventory(Inventory inventory);

    Optional<Inventory> getInventoryById(int id);

    boolean deleteInventoryById(int id);

    Optional<Inventory> updateInventoryById(int id, Inventory inventory);
}
