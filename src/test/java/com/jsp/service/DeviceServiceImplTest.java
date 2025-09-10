package com.jsp.service;

import com.jsp.entity.Device;
import com.jsp.reposetory.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceServiceImplTest {

    private DeviceRepository deviceRepository;
    private DeviceService deviceService;

    @BeforeEach
    void setUp() {
        deviceRepository = mock(DeviceRepository.class);
        deviceService = new DeviceServiceImpl(deviceRepository);
    }

    @Test
    void testGetAllDevices() {
        when(deviceRepository.findAll()).thenReturn(Arrays.asList(new Device(), new Device()));
        List<Device> devices = deviceService.getAllDevices();
        assertEquals(2, devices.size());
        verify(deviceRepository).findAll();
    }

    @Test
    void testSaveDevice() {
        Device device = new Device();
        when(deviceRepository.save(device)).thenReturn(device);
        Device saved = deviceService.saveDevice(device);
        assertNotNull(saved);
        verify(deviceRepository).save(device);
    }

    @Test
    void testGetDeviceByIdFound() {
        Device device = new Device();
        when(deviceRepository.findById(1)).thenReturn(Optional.of(device));
        Optional<Device> result = deviceService.getDeviceById(1);
        assertTrue(result.isPresent());
    }

    @Test
    void testGetDeviceByIdNotFound() {
        when(deviceRepository.findById(1)).thenReturn(Optional.empty());
        Optional<Device> result = deviceService.getDeviceById(1);
        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteDeviceByIdFound() {
        Device device = new Device();
        when(deviceRepository.findById(1)).thenReturn(Optional.of(device));
        boolean deleted = deviceService.deleteDeviceById(1);
        assertTrue(deleted);
        verify(deviceRepository).delete(device);
    }

    @Test
    void testDeleteDeviceByIdNotFound() {
        when(deviceRepository.findById(1)).thenReturn(Optional.empty());
        boolean deleted = deviceService.deleteDeviceById(1);
        assertFalse(deleted);
        verify(deviceRepository, never()).delete(any());
    }

    @Test
    void testUpdateDeviceByIdFound() {
        Device existing = new Device();
        existing.setDModel("Old");
        existing.setDName("OldName");

        Device update = new Device();
        update.setDModel("New");
        update.setDName("NewName");

        when(deviceRepository.findById(1)).thenReturn(Optional.of(existing));
        when(deviceRepository.save(existing)).thenReturn(existing);

        Optional<Device> result = deviceService.updateDeviceById(1, update);

        assertTrue(result.isPresent());
        assertEquals("New", existing.getDModel());
        assertEquals("NewName", existing.getDName());
        verify(deviceRepository).save(existing);
    }

    @Test
    void testUpdateDeviceByIdNotFound() {
        Device update = new Device();
        when(deviceRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Device> result = deviceService.updateDeviceById(1, update);

        assertFalse(result.isPresent());
        verify(deviceRepository, never()).save(any());
    }
}
