package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

/**
 * DTO for RGBLight device
 */
@Getter
@Setter
public class RGBLightDTO extends ActuatorDTO {
    /**
     * this attribute contains intensity of red color in RGB
     */
    @Min(0)
    @Max(255)
    private Integer red;
    /**
     * this attribute contains intensity of green color in RGB
     */
    @Min(0)
    @Max(255)
    private Integer green;
    /**
     * this attribute contains intensity of blue color in RGB
     */
    @Min(0)
    @Max(255)
    private Integer blue;

    /**
     * constructor sets deviceType of device
     */
    public RGBLightDTO() {
        this.setDeviceType("RGBLight");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final RGBLightDTO that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getRed(), that.getRed())
                && Objects.equals(getGreen(), that.getGreen())
                && Objects.equals(getBlue(), that.getBlue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRed(), getGreen(), getBlue());
    }
}
