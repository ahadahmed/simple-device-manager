package com.ahad.devicemanager.controller;

import com.ahad.devicemanager.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceManager {

    private DeviceDao deviceDao;

    public DeviceManager(DeviceDao deviceDao) {

        this.deviceDao = deviceDao;
    }


    public Device createDevice(Device device) throws DuplicateDeviceException {
        Device createdDevice = this.deviceDao.createDevice(device);
        return createdDevice;
    }

    public Device updateDevice(UUID deviceId, Device device) throws DeviceNotFoundException {
        Device deviceById = this.findDeviceById(deviceId);

        deviceById.setDeviceBrand(device.getDeviceBrand());
        deviceById.setDeviceName(device.getDeviceName());
        return this.deviceDao.updateDevice(deviceById);
    }


    public Device findDeviceById(UUID deviceId) throws DeviceNotFoundException {
        Optional<Device> device = Optional.ofNullable(this.deviceDao.getDevice(deviceId));
        return device.orElseThrow(() -> new DeviceNotFoundException("Device not found with ID: " + deviceId));
    }

    public Page<Device> findAllDevices(Pageable pageable) {
        return this.deviceDao.devices(pageable);
    }
}
