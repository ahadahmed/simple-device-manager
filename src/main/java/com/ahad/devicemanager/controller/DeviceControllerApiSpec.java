package com.ahad.devicemanager.controller;

import com.ahad.devicemanager.domain.Device;
import com.ahad.devicemanager.domain.DeviceBrand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Device APIs", description = "REST APIs for Creating Devices, Get Device info")
public interface DeviceControllerApiSpec {

    @Operation(summary = "Create Device", description = "Create a new Device")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Device created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Device.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "A Device already exist with the same ID, Could not create", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DuplicateDeviceException.class))})
    })
    @PostMapping
    ResponseEntity<ApiResponse> addDevice(@RequestBody Device device) throws DuplicateDeviceException;


    @Operation(summary = "Get a device details")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Device Found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Device.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Device is not found with the ID", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DeviceNotFoundException.class))})
    })
    @GetMapping("/{deviceId}")
    ResponseEntity<ApiResponse> deviceOf(@PathVariable UUID deviceId) throws DeviceNotFoundException;

    @Operation(summary = "Get Devices")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Devices Found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Device.class))}),
    })
    @GetMapping()
    ResponseEntity<ApiResponse> devices(@RequestParam(required = false) @PageableDefault(size = 5) Pageable pageable, PagedResourcesAssembler pagedResourcesAssembler);

    @Operation(summary = "Get Devices of a brand")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Devices found of a specific brand", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Device.class))}),
    })
    @GetMapping("/brand/{brand}")
    ResponseEntity<ApiResponse> devicesOf(@PathVariable DeviceBrand brand, @PageableDefault(size = 5) Pageable pageable, PagedResourcesAssembler pagedResourcesAssembler);

    @Operation(summary = "Update Device Info", description = "Update an Device Info")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Device Updated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Device.class))}),
    })
    @PutMapping("/{deviceId}")
    ResponseEntity<ApiResponse> updateDevice(@PathVariable UUID deviceId, @RequestBody Device device) throws DeviceNotFoundException;

    @Operation(summary = "Remove Device", description = "Remove a Device Info")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Device Removed", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Device.class))}),
    })
    @DeleteMapping("/{deviceId}")
    ResponseEntity<ApiResponse> removeDevice(@PathVariable UUID deviceId) throws DeviceNotFoundException;
}
