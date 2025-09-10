package com.jsp.service;

import com.jsp.entity.Device;
import com.jsp.reposetory.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link DeviceService}.
 * Handles business logic for managing devices.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Override
    public List<Device> getAllDevices() {
        log.info("Fetching all devices");
        return deviceRepository.findAll();
    }

    @Override
    public Device saveDevice(Device device) {
        log.info("Saving device: {}", device);
        return deviceRepository.save(device);
    }

    @Override
    public Optional<Device> getDeviceById(int id) {
        log.info("Fetching device by ID: {}", id);
        return deviceRepository.findById(id);
    }

    @Override
    public boolean deleteDeviceById(int id) {
        return deviceRepository.findById(id).map(device -> {
            log.info("Deleting device with ID: {}", id);
            deviceRepository.delete(device);
            return true;
        }).orElse(false);
    }

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
