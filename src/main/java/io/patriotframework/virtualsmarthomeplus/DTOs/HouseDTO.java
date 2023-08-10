package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

/**
 * DTO for whole house
 */
@Getter
@Setter
@NoArgsConstructor
public class HouseDTO {
    /**
     * contains all devices present in the house
     */
    @NotEmpty
    private List<? extends DeviceDTO> devices;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final HouseDTO houseDTO)) return false;
        return Objects.equals(getDevices(), houseDTO.getDevices());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDevices());
    }
}
