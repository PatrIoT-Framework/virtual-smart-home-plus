package io.patriotframework.virtualsmarthomeplus.Mappers;

import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.DoorDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.FireplaceDTO;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.HashMap;


@Component
public class DeviceMapper {
    private final ModelMapper modelMapper;
    private static HashMap<Class<?extends Device>, Class<?extends DeviceDTO>>classToDto;
    static {
        classToDto = new HashMap<>();
        classToDto.put(Fireplace.class, FireplaceDTO.class);
        classToDto.put(Door.class, DoorDTO.class);
    }

    public DeviceMapper (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

//        Custom mappings can be added for each device e.g.
//        TypeMap<Fireplace, FireplaceDTO> fireplaceTypeMap = this.modelMapper.createTypeMap(Fireplace.class, FireplaceDTO.class);
//        fireplaceTypeMap.addMappings(mapper -> mapper.skip(FireplaceDTO::setStatus));
    }

    public DeviceDTO map(Device device) {
        if(!classToDto.containsKey(device.getClass())) {
            throw new DeviceMappingNotSupportedException(
                    String.format("Device %s is not supported by DeviceMapper", Device.class));
        }
        return modelMapper.map(device, classToDto.get(device.getClass()));
    }
}
