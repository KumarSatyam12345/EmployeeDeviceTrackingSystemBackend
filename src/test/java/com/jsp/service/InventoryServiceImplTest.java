package com.jsp.service;

import com.jsp.entity.Device;
import com.jsp.entity.Inventory;
import com.jsp.entity.User;
import com.jsp.reposetory.InventoryReposetory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceImplTest {

    private InventoryReposetory inventoryReposetory;
    private InventoryServiceImpl inventoryService;

    @BeforeEach
    void setUp() {
        inventoryReposetory = mock(InventoryReposetory.class);
        inventoryService = new InventoryServiceImpl(inventoryReposetory);
    }

    private Inventory sampleInventory() {
        Inventory inv = new Inventory();
        inv.setId(1);
        inv.setDateOfIssue(LocalDate.now());
        inv.setReturnDate(LocalDate.now().plusDays(5));
        inv.setUser(new User());
        inv.setDevice(new Device());
        return inv;
    }

    @Test
    void testGetAllInventory() {
        when(inventoryReposetory.findAll()).thenReturn(Arrays.asList(sampleInventory(), sampleInventory()));
        List<Inventory> list = inventoryService.getAllInventory();
        assertEquals(2, list.size());
        verify(inventoryReposetory).findAll();
    }

    @Test
    void testSaveInventory() {
        Inventory inv = sampleInventory();
        when(inventoryReposetory.save(inv)).thenReturn(inv);
        Inventory saved = inventoryService.saveInventory(inv);
        assertNotNull(saved);
        verify(inventoryReposetory).save(inv);
    }

    @Test
    void testGetInventoryByIdFound() {
        Inventory inv = sampleInventory();
        when(inventoryReposetory.findById(1)).thenReturn(Optional.of(inv));
        Optional<Inventory> result = inventoryService.getInventoryById(1);
        assertTrue(result.isPresent());
    }

    @Test
    void testGetInventoryByIdNotFound() {
        when(inventoryReposetory.findById(1)).thenReturn(Optional.empty());
        Optional<Inventory> result = inventoryService.getInventoryById(1);
        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteInventoryByIdFound() {
        Inventory inv = sampleInventory();
        when(inventoryReposetory.findById(1)).thenReturn(Optional.of(inv));
        boolean deleted = inventoryService.deleteInventoryById(1);
        assertTrue(deleted);
        verify(inventoryReposetory).delete(inv);
    }

    @Test
    void testDeleteInventoryByIdNotFound() {
        when(inventoryReposetory.findById(1)).thenReturn(Optional.empty());
        boolean deleted = inventoryService.deleteInventoryById(1);
        assertFalse(deleted);
        verify(inventoryReposetory, never()).delete(any());
    }

    @Test
    void testUpdateInventoryByIdFound() {
        Inventory existing = sampleInventory();
        Inventory update = sampleInventory();
        update.setDateOfIssue(LocalDate.now().minusDays(2));

        when(inventoryReposetory.findById(1)).thenReturn(Optional.of(existing));
        when(inventoryReposetory.save(existing)).thenReturn(existing);

        Optional<Inventory> result = inventoryService.updateInventoryById(1, update);

        assertTrue(result.isPresent());
        assertEquals(update.getDateOfIssue(), existing.getDateOfIssue());
        verify(inventoryReposetory).save(existing);
    }

    @Test
    void testUpdateInventoryByIdNotFound() {
        Inventory update = sampleInventory();
        when(inventoryReposetory.findById(1)).thenReturn(Optional.empty());
        Optional<Inventory> result = inventoryService.updateInventoryById(1, update);
        assertFalse(result.isPresent());
        verify(inventoryReposetory, never()).save(any());
    }
}
