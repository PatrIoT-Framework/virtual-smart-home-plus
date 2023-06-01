package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Base class for all devices
 */
@Getter @Setter @NoArgsConstructor
public class DeviceDTO {
    /**
     * Each device has unique label. Label represents id of the device.
     */
    @NotEmpty
    public String label;

    /**
     * True if device is enabled
     */
    @NotNull
    public Boolean enabled;
}
