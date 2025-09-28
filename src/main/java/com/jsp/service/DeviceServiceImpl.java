package com.jsp.service;

import com.jsp.entity.Device;
import com.jsp.reposetory.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing {@link Device} entities.
 * <p>
 * Provides business logic for CRUD operations by delegating to
 * {@link DeviceRepository}.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    /**
     * Retrieves all devices from the database.
     *
     * @return a list of all devices
     */
    @Override
    public List<Device> getAllDevices() {
        log.info("Fetching all devices");
        return deviceRepository.findAll();
    }

    /**
     * Saves a new device to the database.
     *
     * @param device the device entity to save
     * @return the saved device entity
     */
    @Override
    public Device saveDevice(Device device) {
        log.info("Saving device: {}", device);
        return deviceRepository.save(device);
    }

    /**
     * Fetches a device by its ID.
     *
     * @param id the ID of the device
     * @return an {@link Optional} containing the device if found, otherwise empty
     */
    @Override
    public Optional<Device> getDeviceById(int id) {
        log.info("Fetching device by ID: {}", id);
        return deviceRepository.findById(id);
    }

    /**
     * Deletes a device by its ID.
     *
     * @param id the ID of the device
     * @return true if the device was deleted, false if not found
     */
    @Override
    public boolean deleteDeviceById(int id) {
        return deviceRepository.findById(id).map(device -> {
            log.info("Deleting device with ID: {}", id);
            deviceRepository.delete(device);
            return true;
        }).orElse(false);
    }

    /**
     * Updates an existing device by its ID.
     *
     * @param id           the ID of the device to update
     * @param updateDevice the device data to update with
     * @return an {@link Optional} containing the updated device, or empty if not found
     */
    @Override
    public Optional<Device> updateDeviceById(int id, Device updateDevice) {
        return deviceRepository.findById(id).map(existing -> {
            log.info("Updating device with ID: {}", id);
            existing.setDModel(updateDevice.getDModel());
            existing.setDName(updateDevice.getDName());
            return deviceRepository.save(existing);
        });
    }
}
