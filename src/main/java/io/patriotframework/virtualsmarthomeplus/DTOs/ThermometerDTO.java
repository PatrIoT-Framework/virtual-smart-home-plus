package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for thermometer device
 */
@Getter @Setter @NoArgsConstructor
public class ThermometerDTO extends DeviceDTO {
    /**
     * specifies device type by string value "Thermometer"
     */
    public final String deviceType = "Thermometer";
    /**
     * specifies temperature measurement unit
     */
    public String unit;
    /**
     * value of actual temperature
     */
    public Float temperature;
}
