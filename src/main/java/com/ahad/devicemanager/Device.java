package com.ahad.devicemanager;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "device_id", unique = true, nullable = false)
//    @Embedded
    private UUID deviceId;

    @Column(name = "device_name", nullable = false)
    private String deviceName;
    @Column(name = "device_brand", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeviceBrand deviceBrand;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "deleted")
    private Boolean deleted = false;

    public Device() {}

    public Device(Long id, UUID deviceId, String deviceName, DeviceBrand deviceBrand, LocalDateTime creationDate) {
        this.id = id;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceBrand = deviceBrand;
        this.creationDate = creationDate;
    }


    public UUID getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public DeviceBrand getDeviceBrand() {
        return deviceBrand;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceBrand(DeviceBrand deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", deviceId=" + deviceId +
                ", deviceName='" + deviceName + '\'' +
                ", deviceBrand=" + deviceBrand +
                ", creationDate=" + creationDate +
                '}';
    }
}
