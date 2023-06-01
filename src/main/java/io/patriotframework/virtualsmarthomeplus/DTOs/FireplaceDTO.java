package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for Fireplace device
 */
@Getter @Setter @NoArgsConstructor
public class FireplaceDTO extends DeviceDTO {
    /**
     * specifies device ty by string value "Fireplace"
     */
    public final String deviceType = "Fireplace";
    /**
     * in response to request this attribute has value
     * {@link io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace#ON_FIRE} or
     * {@link io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace#EXTINGUISHED}.
     */
    public String status;

}
