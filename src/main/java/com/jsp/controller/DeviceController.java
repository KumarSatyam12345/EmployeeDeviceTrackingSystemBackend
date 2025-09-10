package com.jsp.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.entity.Device;
import com.jsp.reposetory.DeviceRepository;

@RestController
@RequestMapping("/device")
@CrossOrigin("*")
@Slf4j
public class DeviceController {
    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/getAllDevice")
    public List<Device> getAllDevice() {
        List<Device> s = deviceRepository.findAll();
        log.info(s.toString());

        return s;
    }

    @PostMapping("/saveDevice")
    public ResponseEntity<String> addNewDevice(@RequestBody @Valid Device device) {
        try {
            log.info("Received device: {}", device);
            System.out.println(device.getDName());
            System.out.println(device.getDModel());
            System.out.println(device.getDId());
            if (device != null) {
                deviceRepository.save(device);
                return new ResponseEntity<>("Device record saved successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Please insert the data", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Error saving device", e);
            return new ResponseEntity<>("Please insert valid characters; escape characters are not allowed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public Optional<Device> getDeviceById(@PathVariable int id) {
        Optional<Device> d = deviceRepository.findById(id);
        if (d.isPresent())
            return d;
        else
            return null;
    }

    @DeleteMapping("/{id}")
    public String deletDeviceById(@PathVariable int id) {
        Optional<Device> d = deviceRepository.findById(id);
        if (d.isPresent()) {
            deviceRepository.delete(d.get());
            return "data deleted successfully";
        } else
            return "data not found";
    }

    @PutMapping("/{id}")
    public String updateDeviceByID(@PathVariable int id, @RequestBody @Valid Device updateDevice) {
        Optional<Device> optional = deviceRepository.findById(id);
        if (optional.isPresent()) {
            Device d1 = optional.get();
            d1.setDModel(updateDevice.getDModel());
            d1.setDName(updateDevice.getDName());
            deviceRepository.save(d1);
            return "Data inserted";
        }
        return "Data not found";
    }
}
