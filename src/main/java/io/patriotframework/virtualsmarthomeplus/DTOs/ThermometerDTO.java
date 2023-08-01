package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * DTO for thermometer device
 */
@Getter
@Setter
@NoArgsConstructor
public class ThermometerDTO extends DeviceDTO {
    /**
     * specifies device type by string value "Thermometer"
     */
    private final String deviceType = "Thermometer";
    /**
     * specifies temperature measurement unit
     */
    private String unit;
    /**
     * value of actual temperature
     */
    private Float temperature;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final ThermometerDTO that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getDeviceType(), that.getDeviceType())
                && Objects.equals(getUnit(), that.getUnit())
                && Objects.equals(getTemperature(), that.getTemperature());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDeviceType(), getUnit(), getTemperature());
    }
}
