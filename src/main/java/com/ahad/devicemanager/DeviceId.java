package com.ahad.devicemanager;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class DeviceId implements Serializable {
    private UUID deviceId;

    public DeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    protected DeviceId() {

    }
}
