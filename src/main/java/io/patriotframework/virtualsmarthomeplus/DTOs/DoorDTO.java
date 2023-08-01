package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * DTO for Door device
 */
@Getter
@Setter
@NoArgsConstructor
public class DoorDTO extends DeviceDTO {
    /**
     * specifies device type by string value "Door"
     */
    private final String deviceType = "Door";
    /**
     * In response to request this attribute has value
     * {@link io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door#CLOSED} or
     * {@link io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door#OPENED}.
     */
    private String status;

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
