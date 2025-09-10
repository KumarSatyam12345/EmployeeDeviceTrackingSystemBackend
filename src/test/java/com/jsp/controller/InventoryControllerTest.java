package com.jsp.controller;

import com.jsp.entity.Device;
import com.jsp.entity.Inventory;
import com.jsp.entity.User;
import com.jsp.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class InventoryControllerTest {

    private InventoryService inventoryService;
    private InventoryController inventoryController;

    @BeforeEach
    void setUp() {
        inventoryService = mock(InventoryService.class);
        inventoryController = new InventoryController(inventoryService);
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
    void testGetAllData() {
        when(inventoryService.getAllInventory()).thenReturn(Arrays.asList(sampleInventory(), sampleInventory()));
        List<Inventory> result = inventoryController.getAllData();
        assertEquals(2, result.size());
        verify(inventoryService).getAllInventory();
    }

    @Test
    void testAddInventorySuccess() {
        Inventory inv = sampleInventory();
        when(inventoryService.saveInventory(inv)).thenReturn(inv);

        ResponseEntity<String> response = inventoryController.addInventory(inv);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Data submitted successfully", response.getBody());
        verify(inventoryService).saveInventory(inv);
    }

    @Test
    void testAddInventoryNull() {
        ResponseEntity<String> response = inventoryController.addInventory(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Please insert the data", response.getBody());
    }

    @Test
    void testAddInventoryException() {
        Inventory inv = sampleInventory();
        doThrow(new RuntimeException()).when(inventoryService).saveInventory(inv);

        ResponseEntity<String> response = inventoryController.addInventory(inv);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("duplicate entry", response.getBody());
    }

    @Test
    void testGetInventoryDataByIdFound() {
        Inventory inv = sampleInventory();
        when(inventoryService.getInventoryById(1)).thenReturn(Optional.of(inv));

        ResponseEntity<Inventory> response = inventoryController.getInventoryDataById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(inv, response.getBody());
    }

    @Test
    void testGetInventoryDataByIdNotFound() {
        when(inventoryService.getInventoryById(1)).thenReturn(Optional.empty());

        ResponseEntity<Inventory> response = inventoryController.getInventoryDataById(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteDataByIdFound() {
        when(inventoryService.deleteInventoryById(1)).thenReturn(true);

        ResponseEntity<String> response = inventoryController.deleteDataById(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Data deleted successfully", response.getBody());
        verify(inventoryService).deleteInventoryById(1);
    }

    @Test
    void testDeleteDataByIdNotFound() {
        when(inventoryService.deleteInventoryById(1)).thenReturn(false);

        ResponseEntity<String> response = inventoryController.deleteDataById(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Data not found", response.getBody());
    }

    @Test
    void testUpdateRecordByIdFound() {
        Inventory inv = sampleInventory();
        when(inventoryService.updateInventoryById(1, inv)).thenReturn(Optional.of(inv));

        ResponseEntity<String> response = inventoryController.updateRecordById(1, inv);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Data updated successfully", response.getBody());
    }

    @Test
    void testUpdateRecordByIdNotFound() {
        Inventory inv = sampleInventory();
        when(inventoryService.updateInventoryById(1, inv)).thenReturn(Optional.empty());

        ResponseEntity<String> response = inventoryController.updateRecordById(1, inv);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Inventory with ID 1 not found", response.getBody());
    }
}
