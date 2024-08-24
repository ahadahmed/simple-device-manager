package com.ahad.devicemanager.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Device findByDeviceId(UUID deviceId);

    Page<Device> findAllByDeviceBrandAndDeleted( DeviceBrand brand, Boolean deleted, Pageable pageable);
    Page<Device> findAllByDeleted(Boolean deleted, Pageable pageable);

}
