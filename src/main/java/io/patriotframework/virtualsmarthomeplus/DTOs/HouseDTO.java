package io.patriotframework.virtualsmarthomeplus.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

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
}
