package com.jsp.service;

import com.jsp.entity.Device;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing devices.
 * Defines the contract for device operations.
 */
public interface DeviceService {

    /**
     * Get all devices from the database.
     *
     * @return list of devices
     */
    List<Device> getAllDevices();

    /**
     * Save a new device.
     *
     * @param device the device entity to be saved
     * @return the saved device
     */
    Device saveDevice(Device device);

    /**
     * Find a device by its ID.
     *
     * @param id the device ID
     * @return an Optional containing the device if found, or empty otherwise
     */
    Optional<Device> getDeviceById(int id);

    /**
     * Delete a device by its ID.
     *
     * @param id the device ID
     * @return true if deleted, false if not found
     */
    boolean deleteDeviceById(int id);

    /**
     * Update an existing device by its ID.
     *
     * @param id           the device ID
     * @param updateDevice the updated device object
     * @return an Optional containing the updated device, or empty if not found
     */
    Optional<Device> updateDeviceById(int id, Device updateDevice);
}
