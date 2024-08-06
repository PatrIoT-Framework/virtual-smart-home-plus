package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * DTO for thermometer device
 */
@Getter
@Setter
public class ThermometerDTO extends DeviceDTO {

    /**
     * specifies temperature measurement unit
     */
    private String unit;
    /**
     * value of actual temperature
     */
    private Float temperature;

    public ThermometerDTO() {
        this.setDeviceType("Thermometer");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final ThermometerDTO that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getDeviceType(), that.getDeviceType())
                && Objects.equals(getUnit(), that.getUnit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDeviceType(), getUnit(), getTemperature());
    }
}
