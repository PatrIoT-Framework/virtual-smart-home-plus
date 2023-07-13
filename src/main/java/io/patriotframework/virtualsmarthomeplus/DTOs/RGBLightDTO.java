package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
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
    public Integer red;
    /**
     * this attribute contains intensity of green color in RGB
     */
    @Min(0)
    @Max(255)
    public Integer green;
    /**
     * this attribute contains intensity of blue color in RGB
     */
    @Min(0)
    @Max(255)
    public Integer blue;

    /**
     * construtor sets deviceType of device
     */
    public RGBLightDTO() {
        this.deviceType="RGBLight";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RGBLightDTO that = (RGBLightDTO) o;
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(red, that.red) && Objects.equals(green, that.green) && Objects.equals(blue, that.blue);
    }

}
