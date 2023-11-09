package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
/**
 * Base class for actuator devices
 */
@Getter
@Setter
@NoArgsConstructor
public class ActuatorDTO extends DeviceDTO {
    /**
     * True if actuator is switched on
     */
    private Boolean switchedOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final ActuatorDTO that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getSwitchedOn(), that.getSwitchedOn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSwitchedOn());
    }
}
