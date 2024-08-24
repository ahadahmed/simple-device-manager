package com.ahad.devicemanager.controller;

import com.ahad.devicemanager.Device;
import com.ahad.devicemanager.DeviceBrand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DeviceManagerTest {


    private DeviceDao deviceDao;
    private DeviceManager deviceManager;
    @BeforeEach
    void init() {
        deviceDao = mock();
        deviceManager = new DeviceManager(deviceDao);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/devices.csv", numLinesToSkip = 1)
    void createDevice(String deviceId, String deviceName, DeviceBrand deviceBrand) throws DuplicateDeviceException {
        Device device = new Device(UUID.fromString(deviceId), deviceName, deviceBrand);
        this.deviceManager.createDevice(device);
        verify(deviceDao).createDevice((device));
    }
}