package io.patriotframework.virtualsmarthomeplus.Mapper;

import io.patriotframework.virtualsmarthomeplus.DTOs.*;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.RGBLight;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Thermometer;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Class responsible for mapping model objects to DTOs
 */
@Component
public class DTOMapper {
    private static final HashMap<Class<? extends Device>, Class<? extends DeviceDTO>> classToDto;
    private static final HashMap<Class<? extends DeviceDTO>, Class<? extends Device>> dtoToClass;

    static {
        classToDto = new HashMap<>();
        classToDto.put(Device.class, DeviceDTO.class);
        classToDto.put(Fireplace.class, FireplaceDTO.class);
        classToDto.put(Door.class, DoorDTO.class);
        classToDto.put(Thermometer.class, ThermometerDTO.class);
        classToDto.put(RGBLight.class, RGBLightDTO.class);
    }

    static {
        dtoToClass = new HashMap<>();
        dtoToClass.put(DeviceDTO.class, Device.class);
        dtoToClass.put(FireplaceDTO.class, Fireplace.class);
        dtoToClass.put(DoorDTO.class, Door.class);
        dtoToClass.put(ThermometerDTO.class, Thermometer.class);
        dtoToClass.put(RGBLightDTO.class, RGBLight.class);

    }

    private final ModelMapper modelMapper;

    /**
     * Constructs DTOMapper for all instances of the House interface
     *
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

        TypeMap<RGBLightDTO, RGBLight> RGBLightTypeMap = this.modelMapper.createTypeMap(RGBLightDTO.class, RGBLight.class);
        RGBLightTypeMap.setProvider(request -> {
            RGBLightDTO rgbLightDTO = (RGBLightDTO) request.getSource();
            return new RGBLight(rgbLightDTO.getLabel());
        });

        TypeMap<ThermometerDTO, Thermometer> thermometerTypeMap = this.modelMapper.createTypeMap(ThermometerDTO.class, Thermometer.class);
        thermometerTypeMap.setProvider(request -> {
            ThermometerDTO thermometerDTO = (ThermometerDTO) request.getSource();
            return new Thermometer(thermometerDTO.getLabel());
        });
    }

    /**
     * Returns DTO of House
     *
     * @param house house to be mapped
     * @return DTO of house
     */
    public HouseDTO map(House house) {
        HouseDTO dto = modelMapper.map(house, HouseDTO.class);
        dto.setDevices(house.getDevicesOfType(DeviceDTO.class).values().stream().toList());
        return dto;
    }

    /**
     * Returns DTO of device.
     *
     * @param device device to be mapped
     * @return DTO of device
     * @throws DeviceMappingNotSupportedException if class of DTO is unknown for the mapper
     */
    public DeviceDTO map(Device device) throws DeviceMappingNotSupportedException {
        if (!classToDto.containsKey(device.getClass())) {
            throw new DeviceMappingNotSupportedException(
                    String.format("Device %s is not supported by DeviceMapper", device.getClass()));
        }
        return modelMapper.map(device, classToDto.get(device.getClass()));
    }

    /**
     * Returns device from DTO
     *
     * @param dto dto to be mapped
     * @return device of class which corresponds to type of device in DTO
     * @throws DeviceMappingNotSupportedException if class of DTO is unknown for the mapper
     */
    public Device map(DeviceDTO dto) throws DeviceMappingNotSupportedException {
        if (!dtoToClass.containsKey(dto.getClass())) {
            throw new DeviceMappingNotSupportedException(
                    String.format("DeviceDTO %s is not supported by DeviceMapper", dto.getClass()));
        }
        return modelMapper.map(dto, dtoToClass.get(dto.getClass()));
    }

    /**
     * Returns class of the device corresponding to DTO class;
     *
     * @param dto class of the dto
     * @return class of the corresponding device
     * @throws DeviceMappingNotSupportedException if class of DTO is unknown for the mapper
     */
    public Class<? extends Device> mapDtoClassType(Class<? extends DeviceDTO> dto)
            throws DeviceMappingNotSupportedException {
        Class<? extends Device> result = dtoToClass.get(dto);
        if (result == null) {
            throw new DeviceMappingNotSupportedException(
                    String.format("DeviceDTO %s is not supported by DeviceMapper", dto));
        }
        return result;
    }

    /**
     * Returns class of the DTO corresponding to device class;
     *
     * @param device class of the device
     * @return class of the corresponding DTO
     * @throws DeviceMappingNotSupportedException if class of device is unknown for the mapper
     */
    public Class<? extends DeviceDTO> mapDeviceClassType(Class<? extends Device> device) {
        Class<? extends DeviceDTO> result = classToDto.get(device);
        if (result == null) {
            throw new DeviceMappingNotSupportedException(
                    String.format("Device %s is not supported by DeviceMapper", device));
        }
        return result;
    }
}
