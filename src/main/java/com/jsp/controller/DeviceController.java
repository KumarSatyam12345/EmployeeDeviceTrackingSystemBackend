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
 * REST controller for managing devices.
 * Provides endpoints for CRUD operations.
 */
@RestController
@RequestMapping("/device")
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @PostMapping
    public ResponseEntity<String> addNewDevice(@RequestBody @Valid Device device) {
        try {
            deviceService.saveDevice(device);
            return ResponseEntity.status(HttpStatus.CREATED).body("Device record saved successfully");
        } catch (Exception e) {
            log.error("Error saving device", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid device data");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable int id) {
        return deviceService.getDeviceById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeviceById(@PathVariable int id) {
        return deviceService.deleteDeviceById(id)
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).body("Device deleted successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device not found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDeviceById(@PathVariable int id, @RequestBody @Valid Device updateDevice) {
        return deviceService.updateDeviceById(id, updateDevice)
                .map(device -> ResponseEntity.ok("Device updated successfully"))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device not found"));
    }
}
