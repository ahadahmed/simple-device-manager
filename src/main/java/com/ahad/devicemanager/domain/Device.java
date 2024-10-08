package com.ahad.devicemanager.domain;


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

    protected Device() {}

    public Device(UUID deviceId, String deviceName, DeviceBrand deviceBrand) {

        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceBrand = deviceBrand;
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

    public Boolean getDeleted() {
        return deleted;
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
