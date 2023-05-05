package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DoorDTO extends DeviceDTO {
    private String deviceType = "Door";
    private String status;
}
