package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * DTO for Fireplace device
 */
@Getter
@Setter
@NoArgsConstructor
public class FireplaceDTO extends DeviceDTO {
    /**
     * specifies device ty by string value "Fireplace"
     */
    private final String deviceType = "Fireplace";
    /**
     * in response to request this attribute has value
     * {@link io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace#ON_FIRE} or
     * {@link io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace#EXTINGUISHED}.
     */
    private String status;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final FireplaceDTO that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getDeviceType(), that.getDeviceType())
                && Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDeviceType(), getStatus());
    }
}
