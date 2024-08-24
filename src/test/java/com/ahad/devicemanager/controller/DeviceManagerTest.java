package com.ahad.devicemanager.controller;

import com.ahad.devicemanager.Device;
import com.ahad.devicemanager.DeviceBrand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    void returnDeviceWhenFound() throws DeviceNotFoundException {
        UUID deviceId = UUID.randomUUID();
        Device device = new Device(deviceId, "deviceName", DeviceBrand.APPLE);
        when(deviceDao.getDevice(deviceId)).thenReturn(device);
        Device result = this.deviceManager.deviceOf(deviceId);
        assertNotNull(result);
        assertEquals(deviceId, result.getDeviceId());
    }

    @Test
    void throwExceptionWhenDeviceNotFound() {
        UUID deviceId = UUID.randomUUID();
        Device device = new Device(deviceId, "deviceName", DeviceBrand.APPLE);
        when(deviceDao.getDevice(deviceId)).thenReturn(null);

        assertThrows(DeviceNotFoundException.class, () -> this.deviceManager.deviceOf(deviceId));
    }
}