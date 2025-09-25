package com.jsp.controller;

import com.jsp.entity.Device;
import com.jsp.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing devices.
 */
@RestController
@RequestMapping("/device")
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    /**
     * Fetches all devices.
     *
     * @return list of devices
     */
    @GetMapping("/getAllDevice")
    public ResponseEntity<List<Device>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    /**
     * Saves a new device.
     *
     * @param device device details
     * @return success or error response
     */
    @PostMapping("/saveDevice")
    public ResponseEntity<String> addNewDevice(@RequestBody @Valid Device device) {
        try {
            deviceService.saveDevice(device);
            log.info("Device saved successfully: {}", device);
            return ResponseEntity.status(HttpStatus.CREATED).body("Device record saved successfully");
        } catch (Exception e) {
            log.error("Error saving device", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid device data");
        }
    }

    /**
     * Fetches a device by ID.
     *
     * @param id device ID
     * @return device record or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable int id) {
        return deviceService.getDeviceById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    /**
     * Deletes a device by ID.
     *
     * @param id device ID
     * @return success or not found response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeviceById(@PathVariable int id) {
        return deviceService.deleteDeviceById(id)
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).body("Device deleted successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device not found");
    }

    /**
     * Updates a device by ID.
     *
     * @param id           device ID
     * @param updateDevice updated device details
     * @return success or not found response
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateDeviceById(@PathVariable int id, @RequestBody @Valid Device updateDevice) {
        return deviceService.updateDeviceById(id, updateDevice)
                .map(device -> ResponseEntity.ok("Device updated successfully"))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device not found"));
    }
}
