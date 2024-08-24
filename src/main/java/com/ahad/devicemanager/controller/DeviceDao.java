package com.ahad.devicemanager.controller;

import com.ahad.devicemanager.Device;
import com.ahad.devicemanager.DeviceBrand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class DeviceDao {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private DeviceRepository deviceRepository;

    public DeviceDao(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device createDevice(Device device) throws DuplicateDeviceException {
        Device savedDevice = null;
        try {
            savedDevice = deviceRepository.save(device);
        } catch (DataIntegrityViolationException e) {
            logger.warn(e.getMessage());
            throw new DuplicateDeviceException("duplicate device: " + device.getDeviceId());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return savedDevice;
    }

    public Device getDevice(UUID deviceId) {
        Device byDeviceId = deviceRepository.findByDeviceId(deviceId);
        return byDeviceId;
    }

    public Device updateDevice(Device device) {
        Device updatedDevice = deviceRepository.save(device);
        return updatedDevice;

    }

    public Page<Device> devices(Pageable pageable) {
        return deviceRepository.findAllByDeleted(false, pageable);
    }

    public Page<Device> devices(DeviceBrand brand, Pageable pageable) {
        return deviceRepository.findAllByDeviceBrandAndDeleted(false, brand, pageable);

    }
}
