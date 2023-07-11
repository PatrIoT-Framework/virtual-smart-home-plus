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
    public String label;

    public String deviceType;
    /**
     * True if device is enabled
     */
    public Boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!this.getDeviceType().equals(((DeviceDTO) o).getDeviceType())) return false;
        DeviceDTO deviceDTO = (DeviceDTO) o;
        return Objects.equals(label, deviceDTO.label) && Objects.equals(enabled, deviceDTO.enabled);
    }
}
