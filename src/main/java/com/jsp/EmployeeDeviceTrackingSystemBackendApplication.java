package com.jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.jsp.*")
@EntityScan(basePackages = "com.jsp.entity")
public class EmployeeDeviceTrackingSystemBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeDeviceTrackingSystemBackendApplication.class, args);
    } 
}
