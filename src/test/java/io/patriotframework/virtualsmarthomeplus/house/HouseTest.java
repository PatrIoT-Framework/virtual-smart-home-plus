package io.patriotframework.virtualsmarthomeplus.house;

import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import org.junit.jupiter.api.Test;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class HouseTest {
    House house = new House();
    Fireplace fireplace1 = new Fireplace("fireplace1");
    Fireplace fireplace2 = new Fireplace("fireplace2");
    Door door1 = new Door("door1");
    Door door2 = new Door("door2");

    @Test
    public void houseIsByDefaultEmpty() {
        HashMap<String, Device> devices = house.getDevicesOfType(Device.class);
        assertEquals(0, devices.size());
    }

    @Test
    public void addDeviceThrowsExceptions() {
        house.addDevice(fireplace1);
        assertThrows(IllegalArgumentException.class, () -> house.addDevice(null));
        assertThrows(KeyAlreadyExistsException.class, () -> house.addDevice(fireplace1));
    }

    @Test
    public void getDeviceThrowsExceptions() {
        assertThrows(IllegalArgumentException.class, () -> house.getDevice(null));
    }

    @Test
    public void updateDeviceThrowsExceptions() {
        assertThrows(IllegalArgumentException.class, () -> house.updateDevice(null));
        assertThrows(NoSuchElementException.class, () -> house.updateDevice(new Fireplace("non-existent")));
    }

    @Test
    public void removeDeviceThrowsExceptions() {
        assertThrows(IllegalArgumentException.class, () -> house.removeDevice(null));
        assertThrows(NoSuchElementException.class, () -> house.removeDevice("non-existent"));
    }

    @Test
    public void getDevicesOfTypeThrowsExceptions() {
        assertThrows(IllegalArgumentException.class, () -> house.getDevicesOfType(null));
    }

    @Test
    public void addOnEmptyHouse() {
        assertThrows(NoSuchElementException.class, () -> house.addDevice(new Fireplace("label")));
    }

    @Test
    public void updateOnEmptyHouse() {
        assertThrows(NoSuchElementException.class, () -> house.updateDevice(new Fireplace("label")));
    }

    @Test
    public void deleteOnEmptyHouse() {
        assertThrows(NoSuchElementException.class, () -> house.removeDevice("non-existent"));
    }

    @Test
    public void addAndGetDeviceWorks() {
        house.addDevice(fireplace1);
        Device device = house.getDevice(fireplace1.getLabel());
        assertEquals(fireplace1, device);
        house.addDevice(door1);
        house.addDevice(fireplace2);
        device = house.getDevice(fireplace1.getLabel());
        assertEquals(fireplace1, device);
    }

    @Test
    public void updateDeviceWorks() {
        fireplace1.setEnabled(false);
        Fireplace updatedFireplace = new Fireplace(fireplace1.getLabel());
        updatedFireplace.setEnabled(true);
        house.addDevice(fireplace1);
        house.updateDevice(updatedFireplace);
        Fireplace returnedFireplace = (Fireplace) house.getDevice(fireplace1.getLabel());
        assertEquals(updatedFireplace, returnedFireplace);
        assertTrue(updatedFireplace.hasSameAttributes(returnedFireplace));
    }

    @Test
    public void removeDeviceWorks() {
        house.addDevice(fireplace1);
        house.addDevice(fireplace2);
        house.addDevice(door1);
        assertEquals(fireplace1, house.getDevice(fireplace1.getLabel()));
        house.removeDevice(fireplace1.getLabel());
        assertThrows(NoSuchElementException.class, () -> house.getDevice(fireplace1.getLabel()));
        house.removeDevice(fireplace2.getLabel());
        house.removeDevice(door1.getLabel());
        assertNull(house.getDevicesOfType(Device.class));
    }

    @Test
    public void getDevicesOfTypeDoesNotMixTypes() {
        HashMap<String, Device> devices = house.getDevicesOfType(Device.class);
        assertEquals(0, devices.size());
        house.addDevice(fireplace1);
        devices = house.getDevicesOfType(Door.class);
        assertEquals(0, devices.size());
        devices = house.getDevicesOfType(Fireplace.class);
        assertEquals(1, devices.size());
        house.addDevice(fireplace2);
        house.addDevice(door1);
        devices = house.getDevicesOfType(Door.class);
        assertEquals(1, devices.size());
        assertEquals(2, devices.size());
    }

    @Test
    public void getDevicesOfTypeDoesNotCauseChange() {
        HashMap<String, Device> devices = house.getDevicesOfType(Device.class);
        assertEquals(0, devices.size());
        devices = house.getDevicesOfType(Device.class);
        assertEquals(0, devices.size());
        house.addDevice(fireplace1);
        devices = house.getDevicesOfType(Device.class);
        assertEquals(1, devices.size());
        devices = house.getDevicesOfType(Device.class);
        assertEquals(1, devices.size());
        house.addDevice(fireplace2);
        house.addDevice(door1);
        house.addDevice(door2);
        house.addDevice(fireplace1);
        devices = house.getDevicesOfType(Device.class);
        assertEquals(4, devices.size());
        devices = house.getDevicesOfType(Device.class);
        assertEquals(4, devices.size());
    }
}
