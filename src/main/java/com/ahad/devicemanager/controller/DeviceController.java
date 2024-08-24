package com.ahad.devicemanager.controller;

import com.ahad.devicemanager.Device;
import com.ahad.devicemanager.DeviceBrand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    private DeviceManager deviceManager;

    public DeviceController(DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }


    @PostMapping
    public ResponseEntity<ApiResponse> addDevice(@RequestBody Device device) throws DuplicateDeviceException {
        ApiResponse apiResponse;

        Device aNewDevice = this.deviceManager.createDevice(device);
        apiResponse = createApiResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.name(), aNewDevice);


        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


    @GetMapping("/{deviceId}")
    public ResponseEntity<ApiResponse> deviceOf(@PathVariable UUID deviceId) throws DeviceNotFoundException {
        ApiResponse apiResponse;

        Device deviceById = this.deviceManager.deviceOf(deviceId);

        apiResponse = createApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), deviceById);


        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> devices(@PageableDefault(size = 5) Pageable pageable, PagedResourcesAssembler pagedResourcesAssembler) {
        ApiResponse apiResponse;
        Page<Device> devices = this.deviceManager.devices(pageable);

        apiResponse = createApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), pagedResourcesAssembler.toModel(devices));


        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<ApiResponse> devicesOf(@PathVariable DeviceBrand brand, @PageableDefault(size = 5) Pageable pageable, PagedResourcesAssembler pagedResourcesAssembler) {
        ApiResponse apiResponse;
        Page<Device> devices = this.deviceManager.devicesOf(brand, pageable);
        apiResponse = createApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), pagedResourcesAssembler.toModel(devices));


        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{deviceId}")
    public ResponseEntity<ApiResponse> updateDevice(@PathVariable UUID deviceId, @RequestBody Device device) throws DeviceNotFoundException {
        ApiResponse apiResponse;

        logger.info("device info for deviceId {} - {}", deviceId, device);
        device = this.deviceManager.updateDevice(deviceId, device);

        apiResponse = createApiResponse(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(), device);


        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<ApiResponse> removeDevice(@PathVariable UUID deviceId) throws DeviceNotFoundException {
        ApiResponse apiResponse;

        logger.info("DELETE device info {}", deviceId);
        Device device = this.deviceManager.removeDevice(deviceId);

        apiResponse = createApiResponse(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(), device);


        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }


    private ApiResponse createApiResponse(int status, String message, Object content) {
        ApiResponse apiResponse = new ApiResponse(status, message, content);
        return apiResponse;
    }
}
