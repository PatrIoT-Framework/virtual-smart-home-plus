package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * DTO for Door device
 */
@Getter
@Setter
public class DoorDTO extends DeviceDTO {

    /**
     * In response to request this attribute has value
     * {@link io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door#CLOSED} or
     * {@link io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door#OPENED}.
     */
    private String status;

    public DoorDTO() {
        this.setDeviceType("Door");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final DoorDTO doorDTO)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getDeviceType(), doorDTO.getDeviceType())
                && Objects.equals(getStatus(), doorDTO.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDeviceType(), getStatus());
    }
}
