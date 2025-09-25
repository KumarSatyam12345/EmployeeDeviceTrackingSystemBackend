package com.jsp.reposetory;

import com.jsp.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Device} entities.
 * Provides basic CRUD operations via {@link JpaRepository}.
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
}
