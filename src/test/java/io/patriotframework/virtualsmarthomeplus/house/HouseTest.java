package io.patriotframework.virtualsmarthomeplus.house;

import io.patriotframework.virtualsmarthomeplus.DTOs.RGBLightDTO;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.RGBLight;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.management.openmbean.KeyAlreadyExistsException;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class HouseTest {
    private final Door door1 = new Door("HouseDoor1");
    private final Door door2 = new Door("HouseDoor2");
    private final RGBLight rgbLight = new RGBLight("HouseRGB");
    private final House house = new House();

    @Test
    @Order(1)
    public void addAndGetDeviceTest() {
        house.addDevice(door1);
        assertEquals(house.getDevice("HouseDoor1"),door1);
    }

    @Test
    @Order(2)
    public void removeDeviceTest() {
        house.addDevice(door2);
        house.removeDevice("HouseDoor2");
        assertNull(house.getDevice("HouseDoor2"));
    }

    @Test
    @Order(3)
    public void getDevicesOfType() {
        house.addDevice(rgbLight);
        house.addDevice(door2);
        house.addDevice(door1);
        assertEquals(house.getDevicesOfType(Door.class).size(),2);
        assertEquals(house.getDevicesOfType(RGBLight.class).size(),1);
    }

    @Test
    @Order(4)
    public void getDeviceOfType() {
        house.addDevice(rgbLight);
        house.addDevice(door2);
        house.addDevice(door1);
        assertEquals(house.getDeviceOfType(Door.class,"HouseDoor1"),door1);
        assertEquals(house.getDeviceOfType(RGBLight.class,"HouseRGB"),rgbLight);
        assertNull(house.getDeviceOfType(Door.class, "HouseRGB"));
    }

    @Test
    public void deviceAlreadyExistsAddDevice() {
        house.addDevice(door1);
        assertThrows(KeyAlreadyExistsException.class, () -> house.addDevice(door1));
    }

    @Test
    public void nullLabelInputParam() {
        assertThrows(IllegalArgumentException.class, () -> house.removeDevice(null));
        assertThrows(IllegalArgumentException.class, () -> house.getDevice(null));
        assertThrows(IllegalArgumentException.class, () -> house.addDevice(null));
        assertThrows(IllegalArgumentException.class, () -> house.getDeviceOfType(Door.class,null));
    }

    @Test
    public void noSuchElementRemove() {
        assertThrows(NoSuchElementException.class, () -> house.removeDevice("HouseDoor4"));
    }
}
