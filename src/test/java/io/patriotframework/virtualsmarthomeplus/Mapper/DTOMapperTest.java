package io.patriotframework.virtualsmarthomeplus.Mapper;

import io.patriotframework.virtualsmarthomeplus.DTOS.MockDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.*;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.DeviceMock;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.RGBLight;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Thermometer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door.CLOSED;
import static io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door.OPENED;
import static io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace.EXTINGUISHED;
import static io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace.ON_FIRE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DTOMapperTest {

    private final DTOMapper dtoMapper;

    public DTOMapperTest() {
        this.dtoMapper = new DTOMapper(new ModelMapper());
    }
    @Test
    public void unknownDeviceToDeviceDTO() {
        DeviceMock deviceMock = new DeviceMock("deviceMock");
        deviceMock.setEnabled(true);

        assertThrows(DeviceMappingNotSupportedException.class, () -> dtoMapper.map(deviceMock));
    }

    @Test
    public void thermometerToThermometerDTO() {
        Thermometer thermometer = new Thermometer("thermometer1");
        thermometer.setEnabled(true);
        thermometer.setUnit("F");

        ThermometerDTO thermometerDTO = new ThermometerDTO();
        thermometerDTO.setLabel("thermometer1");
        thermometerDTO.setEnabled(true);
        thermometerDTO.setUnit("F");

        assertEquals(dtoMapper.map(thermometer),thermometerDTO);
    }

    @Test
    public void rgbToRgbDTO(){
        RGBLight rgbLight = new RGBLight("rgb1");
        rgbLight.setEnabled(true);
        rgbLight.switchOn();
        rgbLight.setRed(20);
        rgbLight.setGreen(21);
        rgbLight.setBlue(22);

        RGBLightDTO rgbLightDTO = new RGBLightDTO();
        rgbLightDTO.setLabel("rgb1");
        rgbLightDTO.setEnabled(true);
        rgbLightDTO.setSwitchedOn(true);
        rgbLightDTO.setRed(20);
        rgbLightDTO.setGreen(21);
        rgbLightDTO.setBlue(22);

        assertEquals(dtoMapper.map(rgbLight),rgbLightDTO);

        rgbLight.switchOf();
        rgbLightDTO.setSwitchedOn(false);

        assertEquals(dtoMapper.map(rgbLight),rgbLightDTO);
    }

    @Test
    public void fireplaceToFireplaceDTO() {
        Fireplace fireplace = new Fireplace("fireplace1");
        fireplace.setEnabled(true);
        fireplace.fireUp();

        FireplaceDTO fireplaceDTO = new FireplaceDTO();
        fireplaceDTO.setLabel("fireplace1");
        fireplaceDTO.setStatus(ON_FIRE);
        fireplaceDTO.setEnabled(true);

        assertEquals(dtoMapper.map(fireplace),fireplaceDTO);

        fireplace.extinguish();
        fireplaceDTO.setStatus(EXTINGUISHED);

        assertEquals(dtoMapper.map(fireplace),fireplaceDTO);
    }

    @Test
    public void doorToDoorDTO() {
        Door door = new Door("door1");
        door.setEnabled(true);
        door.open();

        DoorDTO doorDTO = new DoorDTO();
        doorDTO.setLabel("door1");
        doorDTO.setEnabled(true);
        doorDTO.setStatus(OPENED);

        assertEquals(dtoMapper.map(door),doorDTO);

        door.close();
        doorDTO.setStatus(CLOSED);

        assertEquals(dtoMapper.map(door),doorDTO);
    }

    @Test
    public void classDTOToClass() {
        assertEquals(RGBLight.class,dtoMapper.mapDtoClassType(RGBLightDTO.class));
        assertEquals(Door.class,dtoMapper.mapDtoClassType(DoorDTO.class));
        assertEquals(Fireplace.class,dtoMapper.mapDtoClassType(FireplaceDTO.class));
        assertEquals(Thermometer.class,dtoMapper.mapDtoClassType(ThermometerDTO.class));

        assertThrows(DeviceMappingNotSupportedException.class, () -> dtoMapper.mapDtoClassType(MockDTO.class));
    }

    @Test
    public void classToClassDTO() {
        assertEquals(RGBLightDTO.class,dtoMapper.mapDeviceClassType(RGBLight.class));
        assertEquals(DoorDTO.class,dtoMapper.mapDeviceClassType(Door.class));
        assertEquals(FireplaceDTO.class,dtoMapper.mapDeviceClassType(Fireplace.class));
        assertEquals(ThermometerDTO.class,dtoMapper.mapDeviceClassType(Thermometer.class));

        assertThrows(DeviceMappingNotSupportedException.class, () -> dtoMapper.mapDeviceClassType(DeviceMock.class));
    }

    @AfterAll
    @Test
    public void houseToHouseDTO() {
        House house = new House();
        RGBLight rgbLight = new RGBLight("rgb1");
        house.addDevice(rgbLight);

        HouseDTO houseDTO = new HouseDTO();
        List<DeviceDTO> list = new ArrayList<>();
        houseDTO.setDevices(list);
        list.add(dtoMapper.map(rgbLight));
        houseDTO.setDevices(list);

        HouseDTO houseDTO1 = dtoMapper.map(house);
        assertEquals(houseDTO1,houseDTO);
    }

}
