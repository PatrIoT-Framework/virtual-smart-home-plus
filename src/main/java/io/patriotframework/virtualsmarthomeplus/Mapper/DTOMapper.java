package io.patriotframework.virtualsmarthomeplus.Mapper;

import io.patriotframework.virtualsmarthomeplus.DTOs.ThermometerDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.HouseDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.RGBLightDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.DoorDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.FireplaceDTO;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.RGBLight;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Thermometer;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Class responsible for mapping model objects to DTOs
 */
@Service
public class DTOMapper {
    private final HashMap<Class<? extends Device>, Class<? extends DeviceDTO>> classToDtoMap = new HashMap<>();
    private final HashMap<Class<? extends DeviceDTO>, Class<? extends Device>> dtoToClassMap = new HashMap<>();


    private final ModelMapper modelMapper;

    /**
     * Constructs DTOMapper for all instances of the House interface
     *
     * @param modelMapper instance of ModelMapper
     */
    public DTOMapper(ModelMapper modelMapper) {
        classToDtoMap.put(Device.class, DeviceDTO.class);
        classToDtoMap.put(Fireplace.class, FireplaceDTO.class);
        classToDtoMap.put(Door.class, DoorDTO.class);
        classToDtoMap.put(Thermometer.class, ThermometerDTO.class);
        classToDtoMap.put(RGBLight.class, RGBLightDTO.class);

        dtoToClassMap.put(DeviceDTO.class, Device.class);
        dtoToClassMap.put(FireplaceDTO.class, Fireplace.class);
        dtoToClassMap.put(DoorDTO.class, Door.class);
        dtoToClassMap.put(ThermometerDTO.class, Thermometer.class);
        dtoToClassMap.put(RGBLightDTO.class, RGBLight.class);

        this.modelMapper = modelMapper;

        final TypeMap<FireplaceDTO, Fireplace> fireplaceTypeMap = this.modelMapper
                .createTypeMap(FireplaceDTO.class, Fireplace.class);
        fireplaceTypeMap.setProvider(request -> {
            final FireplaceDTO fireplaceDTO = (FireplaceDTO) request.getSource();
            return new Fireplace(fireplaceDTO.getLabel());
        });

        final TypeMap<DoorDTO, Door> doorTypeMap = this.modelMapper.createTypeMap(DoorDTO.class, Door.class);
        doorTypeMap.setProvider(request -> {
            final DoorDTO doorDTO = (DoorDTO) request.getSource();
            return new Door(doorDTO.getLabel());
        });

        final TypeMap<RGBLightDTO, RGBLight> rgbLightTypeMap = this.modelMapper.createTypeMap(
                RGBLightDTO.class,
                RGBLight.class
        );
        rgbLightTypeMap.setProvider(request -> {
            final RGBLightDTO rgbLightDTO = (RGBLightDTO) request.getSource();
            return new RGBLight(rgbLightDTO.getLabel());
        });

        final TypeMap<ThermometerDTO, Thermometer> thermometerTypeMap = this.modelMapper.createTypeMap(
                ThermometerDTO.class,
                Thermometer.class
        );
        thermometerTypeMap.setProvider(request -> {
            final ThermometerDTO thermometerDTO = (ThermometerDTO) request.getSource();
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
        final HouseDTO dto = modelMapper.map(house, HouseDTO.class);
        dto.setDevices((house.getDevicesOfType(Device.class))
                .stream()
                .map(this::map)
                .collect(Collectors.toList()));
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
        if (!classToDtoMap.containsKey(device.getClass())) {
            throw new DeviceMappingNotSupportedException(
                    String.format("Device %s is not supported by DeviceMapper", device.getClass()));
        }
        return modelMapper.map(device, classToDtoMap.get(device.getClass()));
    }

    /**
     * Returns device from DTO
     *
     * @param dto dto to be mapped
     * @return device of class which corresponds to type of device in DTO
     * @throws DeviceMappingNotSupportedException if class of DTO is unknown for the mapper
     */
    public Device map(DeviceDTO dto) throws DeviceMappingNotSupportedException {
        if (!this.dtoToClassMap.containsKey(dto.getClass())) {
            throw new DeviceMappingNotSupportedException(
                    String.format("DeviceDTO %s is not supported by DeviceMapper", dto.getClass()));
        }
        return modelMapper.map(dto, this.dtoToClassMap.get(dto.getClass()));
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
        final Class<? extends Device> result = this.dtoToClassMap.get(dto);
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
        final Class<? extends DeviceDTO> result = classToDtoMap.get(device);
        if (result == null) {
            throw new DeviceMappingNotSupportedException(
                    String.format("Device %s is not supported by DeviceMapper", device));
        }
        return result;
    }
}
