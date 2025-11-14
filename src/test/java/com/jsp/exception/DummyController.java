package com.jsp.exception;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
public class DummyController {

    @PostMapping("/saveDevice")
    public String save(@Valid @RequestBody DummyRequest request) {
        return "OK";
    }
}
