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
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final ActuatorDTO that)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getStatus());
    }
}
