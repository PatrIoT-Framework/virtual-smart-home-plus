package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class FireplaceDTO extends DeviceDTO {
    private String deviceType = "Fireplace";
    private String status;

}
