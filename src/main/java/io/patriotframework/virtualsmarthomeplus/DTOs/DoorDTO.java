package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for Door device
 */
@Getter @Setter @NoArgsConstructor
public class DoorDTO extends DeviceDTO {
    /**
     * specifies device ty by string value "Door"
     */
    public final String deviceType = "Door";
    /**
     * In response to request this attribute has value
     * {@link io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door#CLOSED} or
     * {@link io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door#OPENED}.
     */
    public String status;
}
