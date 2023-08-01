package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

/**
 * Base class for all devices
 */
@Getter
@Setter
@NoArgsConstructor
public class DeviceDTO {
    /**
     * Each device has unique label. Label represents id of the device.
     */
    @NotEmpty
    private String label;
    /**
     * Type of the device
     */
    private String deviceType;
    /**
     * True if device is enabled
     */
    private Boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final DeviceDTO deviceDTO)) return false;
        return Objects.equals(getLabel(), deviceDTO.getLabel())
                && Objects.equals(getDeviceType(), deviceDTO.getDeviceType())
                && Objects.equals(getEnabled(), deviceDTO.getEnabled());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabel(), getDeviceType(), getEnabled());
    }
}
