package com.ahad.devicemanager.controller;

import com.ahad.devicemanager.Device;
import com.ahad.devicemanager.DeviceBrand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Device findByDeviceId(UUID deviceId);

    Page<Device> findAllByDeviceBrand(DeviceBrand brand, Pageable pageable);
    Page<Device> findAllByDeleted(Boolean deleted, Pageable pageable);

}
