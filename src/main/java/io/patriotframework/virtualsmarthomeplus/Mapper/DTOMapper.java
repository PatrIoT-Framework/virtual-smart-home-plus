package io.patriotframework.virtualsmarthomeplus.Mapper;

import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.DoorDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.FireplaceDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.HouseDTO;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Class responsible for mapping model objects to DTOs
 */
@Component
public class DTOMapper {
    private final ModelMapper modelMapper;

    private static final HashMap<Class<?extends Device>, Class<?extends DeviceDTO>>classToDto;
    static {
        classToDto = new HashMap<>();
        classToDto.put(Device.class, DeviceDTO.class);
        classToDto.put(Fireplace.class, FireplaceDTO.class);
        classToDto.put(Door.class, DoorDTO.class);
    }

    private static final HashMap<Class<?extends DeviceDTO>, Class<?extends Device>> dtoToClass;
    static {
        dtoToClass = new HashMap<>();
        dtoToClass.put(DeviceDTO.class, Device.class);
        dtoToClass.put(FireplaceDTO.class, Fireplace.class);
        dtoToClass.put(DoorDTO.class, Door.class);
    }

    /**
     * Constructs DTOMapper for all instances of the House interface
     * @param modelMapper instance of ModelMapper
     */
    public DTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        TypeMap<FireplaceDTO, Fireplace> fireplaceTypeMap = this.modelMapper
                .createTypeMap(FireplaceDTO.class, Fireplace.class);
        fireplaceTypeMap.setProvider(request -> {
            FireplaceDTO fireplaceDTO = (FireplaceDTO) request.getSource();
            return new Fireplace(fireplaceDTO.getLabel());
        });

        TypeMap<DoorDTO, Door> doorTypeMap = this.modelMapper.createTypeMap(DoorDTO.class, Door.class);
        doorTypeMap.setProvider(request -> {
            DoorDTO doorDTO = (DoorDTO) request.getSource();
            return new Door(doorDTO.getLabel());
        });
    }

    /**
     * Returns DTO of House
     * @param house house to be mapped
     * @return DTO of house
     */
    public HouseDTO map (House house) {
        HouseDTO dto = modelMapper.map(house, HouseDTO.class);
        dto.setDevices(house.getDevicesOfType(DeviceDTO.class).values().stream().toList());
        return dto;
    }

    /**
     * Returns DTO of device.
     * @param device device to be mapped
     * @return DTO of device
     * @throws DeviceMappingNotSupportedException if class of DTO is unknown for the mapper
     */
    public DeviceDTO map(Device device) throws DeviceMappingNotSupportedException {
        if(!classToDto.containsKey(device.getClass())) {
            throw new DeviceMappingNotSupportedException(
                    String.format("Device %s is not supported by DeviceMapper", device.getClass()));
        }
        return modelMapper.map(device, classToDto.get(device.getClass()));
    }

    /**
     * Returns device from DTO
     * @param dto dto to be mapped
     * @return device of class which corresponds to type of device in DTO
     * @throws DeviceMappingNotSupportedException if class of DTO is unknown for the mapper
     */
    public Device map(DeviceDTO dto) throws DeviceMappingNotSupportedException {
        if(!dtoToClass.containsKey(dto.getClass())) {
            throw new DeviceMappingNotSupportedException(
                    String.format("DeviceDTO %s is not supported by DeviceMapper", dto.getClass()));
        }
        return modelMapper.map(dto, dtoToClass.get(dto.getClass()));
    }

    /**
     * Returns class of the device corresponding to DTO class;
     * @param dto class of the dto
     * @return class of the corresponding device
     * @throws DeviceMappingNotSupportedException if class of DTO is unknown for the mapper
     */
    public Class<?extends Device> mapDtoClassType(Class<? extends DeviceDTO> dto)
            throws DeviceMappingNotSupportedException {
        Class<?extends Device> result = dtoToClass.get(dto);
        if(result == null) {
            throw new DeviceMappingNotSupportedException(
                    String.format("DeviceDTO %s is not supported by DeviceMapper", dto));
        }
        return result;
    }

    /**
     * Returns class of the DTO corresponding to device class;
     * @param device class of the device
     * @return class of the corresponding DTO
     * @throws DeviceMappingNotSupportedException if class of device is unknown for the mapper
     */
    public Class<?extends DeviceDTO> mapDeviceClassType(Class<? extends Device> device) {
        Class<?extends DeviceDTO> result = classToDto.get(device);
        if(result == null) {
            throw new DeviceMappingNotSupportedException(
                    String.format("Device %s is not supported by DeviceMapper", device));
        }
        return result;
    }
}
