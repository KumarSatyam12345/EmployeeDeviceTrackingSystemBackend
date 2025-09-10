# Employee Device Tracking System Backend

A Spring Boot backend application for managing employees, devices, and inventory assignments. This project provides REST APIs for CRUD operations and uses PostgreSQL as the database.

---

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Environment Variables](#environment-variables)
- [Contributing](#contributing)
- [License](#license)

---

## Features
- Manage **Employees**: add, update, delete, and fetch employee records.
- Manage **Devices**: track devices assigned to employees.
- Manage **Inventory**: track device issuance and return.
- Input validation with **custom annotations**.
- Logging with **Lombok @Slf4j**.
- Fully tested with **JUnit 5 and Mockito** for 90% coverage.

---

## Tech Stack
- Java 21
- Spring Boot 3.4
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok
- JUnit 5 + Mockito

---

## Getting Started

### Prerequisites
- Java 21
- Maven
- PostgreSQL

### Installation
1. Clone the repository:
```bash

## Getting Started
Follow these steps to set up and run the **Employee Device Tracking System Backend** locally.

### 1. Clone the repository
```bash
git clone <https://github.com/KumarSatyam12345/EmployeeDeviceTrackingSystemBackend.git>
cd EmployeeDeviceTrackingSystemBackend

### Set environment variables for database credentials (Windows):
set DB_USER=postgres
set DB_PASS=yourpassword

### Build the project:
mvn clean install

### Run the Spring Boot application:
mvn spring-boot:run

### Access the API
### 1.By default, the application runs on: http://localhost:8080
### 2. Use tools like Postman or curl to test the API endpoints.
