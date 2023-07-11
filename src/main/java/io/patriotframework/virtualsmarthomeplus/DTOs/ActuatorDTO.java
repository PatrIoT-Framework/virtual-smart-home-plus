package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ActuatorDTO extends DeviceDTO {
    /**
     * True if actuator is switched on
     */
    public Boolean switchedOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActuatorDTO that = (ActuatorDTO) o;
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(switchedOn, that.switchedOn);
    }

}
