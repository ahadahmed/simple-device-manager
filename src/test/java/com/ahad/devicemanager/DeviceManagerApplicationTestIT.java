package com.ahad.devicemanager;

import com.ahad.devicemanager.controller.ApiResponse;
import com.ahad.devicemanager.domain.Device;
import com.ahad.devicemanager.domain.DeviceBrand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DeviceManagerApplicationTestIT {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;


    private final String URI = "/api/devices";

    private static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("nodevice").withDatabaseName("devicedb")
            .withExposedPorts(5432, 5432)
            .withPassword("nosecret");
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @BeforeAll
    static void beforeAll() {
        postgresContainer.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/devices.csv", numLinesToSkip = 1)
    void returnDevicesAfterSuccessfullyPersisted(String deviceId, String deviceName, DeviceBrand deviceBrand) throws Exception {
        Device device = new Device(UUID.fromString(deviceId), deviceName, deviceBrand);
        //Create devices
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post(URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(device)))
                .andExpect(status().isCreated())
                .andReturn();
        String apiResonseString = mvcResult.getResponse().getContentAsString();
        ApiResponse apiResponse = mapper.readValue(apiResonseString, ApiResponse.class);

        assertNotNull(apiResponse.getContent());
        assertEquals(apiResponse.getCode(), 201);

        // get devices
        mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get(URI)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        apiResonseString = mvcResult.getResponse().getContentAsString();
        apiResponse = mapper.readValue(apiResonseString, ApiResponse.class);
        assertNotNull(apiResponse.getContent());
        assertEquals(apiResponse.getCode(), 200);

    }

    @Test
    void updateDevice() throws Exception {
        UUID deviceId = UUID.randomUUID();
        Device device = new Device(deviceId, "deviceName", DeviceBrand.APPLE);
        //Create devices
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post(URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(device)))
                .andExpect(status().isCreated())
                .andReturn();
        String apiResonseString = mvcResult.getResponse().getContentAsString();
        ApiResponse apiResponse = mapper.readValue(apiResonseString, ApiResponse.class);

        assertNotNull(apiResponse.getContent());
        assertEquals(apiResponse.getCode(), 201);

        // update device
        device.setDeviceName("Samsung Galaxy");
        device.setDeviceBrand(DeviceBrand.SAMSUNG);
        mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.put(URI+"/"+deviceId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(device)))
                .andExpect(status().isAccepted())
                .andReturn();
        apiResonseString = mvcResult.getResponse().getContentAsString();
        apiResponse = mapper.readValue(apiResonseString, ApiResponse.class);

        Device updateDevice = mapper.convertValue(apiResponse.getContent(), Device.class);
        assertNotNull(apiResponse.getContent());
        assertEquals( 202, apiResponse.getCode());
        assertEquals(device.getDeviceName(), updateDevice.getDeviceName());
        assertEquals(device.getDeviceBrand(), updateDevice.getDeviceBrand());



    }

}
