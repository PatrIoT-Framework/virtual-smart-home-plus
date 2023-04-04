package io.patriotframework.virtualsmarthomeplus.house.devices;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeviceTest {
    Device device1 = new MockDevice("device1");

    @Test
    public void getLabel() {
        assertEquals("device1", device1.getLabel());
        assertEquals("device1", device1.getLabel());
    }

    @Test
    public void getEnabled() {
        assertFalse(device1.isEnabled());
        assertFalse(device1.isEnabled());
        device1.setEnabled(false);
        assertFalse(device1.isEnabled());
        device1.setEnabled(true);
        assertTrue(device1.isEnabled());
        assertTrue(device1.isEnabled());
        device1.setEnabled(true);
        assertTrue(device1.isEnabled());
        device1.setEnabled(false);
        assertFalse(device1.isEnabled());
    }

    @Test
    public void devicesWithSameLabelEquals() {
        Device device2 = new MockDevice("device1");
        device1.setEnabled(true);
        device2.setEnabled(false);
        assertEquals(device1, device2);
    }

    @Test
    public void testCompareTo() {
        Device device = new MockDevice("device1");
        assertEquals(device1.getLabel().compareTo(device.getLabel()), device1.compareTo(device));
        device = new MockDevice("a");
        assertEquals(device1.getLabel().compareTo(device.getLabel()), device1.compareTo(device));
        device = new MockDevice("x");
        assertEquals(device1.getLabel().compareTo(device.getLabel()), device1.compareTo(device));
    }

    @Test
    public void testEquals() {
        Device device = new MockDevice("device1");
        assertEquals(device, device1);
        device.setEnabled(true);
        assertEquals(device, device1);
        device = new MockDevice("device2");
        assertNotEquals(device, device1);
    }

    @Test
    public void testHashCode() {
        Device device = new MockDevice("device1");
        assertEquals(device.hashCode(), device1.hashCode());
        device.setEnabled(true);
        assertEquals(device.hashCode(), device1.hashCode());
        device = new MockDevice("device2");
        assertNotEquals(device.hashCode(), device.hashCode());
    }

    static class MockDevice extends Device {
        MockDevice(String label) {
            super(label);
        }

        @Override
        public MockDevice createWithSameAttributes(String newLabel) {
            throw new NotImplementedException("This mock method shouldn't be called.");
        }

        @Override
        public boolean hasSameAttributes(Device device) throws IllegalArgumentException {
            throw new NotImplementedException("This mock method shouldn't be called.");
        }
    }
}
