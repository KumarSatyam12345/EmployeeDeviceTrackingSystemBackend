package com.jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * Entry point for the Employee Device Tracking System Backend application.
 * Running this class will start the embedded web server (Tomcat by default),
 * initialize the Spring application context, and deploy the application.
 *
 */
@SpringBootApplication
public class EmployeeDeviceTrackingSystemBackendApplication {

    /**
     * The main method, serving as the entry point of the Spring Boot application.
     *
     * @param args command-line arguments passed during application startup
     */
    public static void main(String[] args) {
        SpringApplication.run(EmployeeDeviceTrackingSystemBackendApplication.class, args);
    }
}
