package com.ahad.devicemanager.controller;

import com.ahad.devicemanager.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @PutMapping("/{deviceId}")
    public ResponseEntity<ApiResponse> updateDevice(@PathVariable UUID deviceId, @RequestBody Device device) throws DeviceNotFoundException {
        ApiResponse apiResponse;

        logger.info("device info for deviceId {} - {}", deviceId, device);
        device = this.deviceManager.updateDevice(deviceId, device);

        apiResponse = createApiResponse(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(), device);


        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }


    @GetMapping("/{deviceId}")
    public ResponseEntity<ApiResponse> deviceOf(@PathVariable UUID deviceId) throws DeviceNotFoundException {
        ApiResponse apiResponse;

        Device deviceById = this.deviceManager.findDeviceById(deviceId);

        apiResponse = createApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), deviceById);


        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> devices(@RequestParam(defaultValue = "0") int offset,
                                               @RequestParam(defaultValue = "20") int limit) {
        ApiResponse apiResponse;
        Pageable pageRequest = PageRequest.of(offset, limit);
        Page<Device> devices = this.deviceManager.findAllDevices(pageRequest);

        apiResponse = createApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), devices);


        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<ApiResponse> removeDevice(@PathVariable UUID deviceId) {
        ApiResponse apiResponse;

        logger.info("DELETE device info {}", deviceId);

        apiResponse = createApiResponse(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(), deviceId);


        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }


    private ApiResponse createApiResponse(int status, String message, Object content) {
        ApiResponse apiResponse = new ApiResponse(status, message, content);
        return apiResponse;
    }
}
