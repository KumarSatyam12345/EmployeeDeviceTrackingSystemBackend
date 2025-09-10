package com.jsp.controller;

import com.jsp.entity.Device;
import com.jsp.service.DeviceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class DeviceControllerTest {

    private DeviceService deviceService;
    private DeviceController deviceController;

    @BeforeEach
    void setUp() {
        deviceService = mock(DeviceService.class);
        deviceController = new DeviceController(deviceService);
    }

    @Test
    void testGetAllDevices() {
        when(deviceService.getAllDevices()).thenReturn(Arrays.asList(new Device(), new Device()));
        ResponseEntity<List<Device>> response = deviceController.getAllDevices();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testAddNewDeviceSuccess() {
        Device device = new Device();
        when(deviceService.saveDevice(device)).thenReturn(device);

        ResponseEntity<String> response = deviceController.addNewDevice(device);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Device record saved successfully", response.getBody());
        verify(deviceService).saveDevice(device);
    }

    @Test
    void testAddNewDeviceFailure() {
        Device device = new Device();
        doThrow(new RuntimeException()).when(deviceService).saveDevice(device);

        ResponseEntity<String> response = deviceController.addNewDevice(device);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(deviceService).saveDevice(device);
    }

    @Test
    void testGetDeviceByIdFound() {
        Device device = new Device();
        when(deviceService.getDeviceById(1)).thenReturn(Optional.of(device));

        ResponseEntity<?> response = deviceController.getDeviceById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(device, response.getBody());
    }

    @Test
    void testGetDeviceByIdNotFound() {
        when(deviceService.getDeviceById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = deviceController.getDeviceById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteDeviceByIdFound() {
        when(deviceService.deleteDeviceById(1)).thenReturn(true);

        ResponseEntity<String> response = deviceController.deleteDeviceById(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Device deleted successfully", response.getBody());
    }

    @Test
    void testDeleteDeviceByIdNotFound() {
        when(deviceService.deleteDeviceById(1)).thenReturn(false);

        ResponseEntity<String> response = deviceController.deleteDeviceById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Device not found", response.getBody());
    }

    @Test
    void testUpdateDeviceByIdFound() {
        Device device = new Device();
        when(deviceService.updateDeviceById(1, device)).thenReturn(Optional.of(device));

        ResponseEntity<String> response = deviceController.updateDeviceById(1, device);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Device updated successfully", response.getBody());
    }

    @Test
    void testUpdateDeviceByIdNotFound() {
        Device device = new Device();
        when(deviceService.updateDeviceById(1, device)).thenReturn(Optional.empty());

        ResponseEntity<String> response = deviceController.updateDeviceById(1, device);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Device not found", response.getBody());
    }
}
