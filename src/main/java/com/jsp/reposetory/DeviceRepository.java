package com.jsp.reposetory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.entity.Device;
@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer>{
	
}
