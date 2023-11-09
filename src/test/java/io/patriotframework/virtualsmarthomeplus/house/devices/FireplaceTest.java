package io.patriotframework.virtualsmarthomeplus.house.devices;

import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import org.junit.jupiter.api.Test;

import static io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door.CLOSED;
import static io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door.OPENED;
import static io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace.EXTINGUISHED;
import static io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace.ON_FIRE;
import static org.junit.jupiter.api.Assertions.*;

public class FireplaceTest {
    Fireplace fireplace1 = new Fireplace("fireplace1");
    Fireplace fireplace2 = new Fireplace("fireplace2");

    @Test
    public void constructorTest() {

        Fireplace device1 = new Fireplace("fireplacex");
        assertDoesNotThrow(() -> {
            Fireplace device2 = new Fireplace("fireplacexy");
        });
        assertThrows(IllegalArgumentException.class, () -> new Fireplace(""));
        assertThrows(IllegalArgumentException.class, () -> new Fireplace(null));
    }

    @Test
    public void fireUpExtinguishTest() {
        fireplace1.setEnabled(true);
        assertEquals(fireplace1.getStatus(),EXTINGUISHED);
        fireplace1.fireUp();
        assertEquals(fireplace1.getStatus(),ON_FIRE);
        fireplace1.extinguish();
        assertEquals(fireplace1.getStatus(),EXTINGUISHED);
        fireplace1.fireUp();
        fireplace1.fireUp();
        assertEquals(fireplace1.getStatus(),ON_FIRE);

        fireplace1.extinguish();
        fireplace1.extinguish();
        assertEquals(fireplace1.getStatus(),EXTINGUISHED);
    }

    @Test
    public void createWithSameAttributes() {
        fireplace1.setEnabled(true);
        Fireplace fireplace3 = fireplace1.createWithSameAttributes("rgb3");
        assertEquals(fireplace3.isEnabled(), fireplace1.isEnabled());
        assertTrue(fireplace3.hasSameAttributes(fireplace1));
    }

    @Test
    public void testHasSameAttributes() {
        DeviceMock device1 = new DeviceMock("fireplace1");
        assertThrows(IllegalArgumentException.class, () -> fireplace1.hasSameAttributes(device1));

        assertThrows(IllegalArgumentException.class, () -> fireplace1.hasSameAttributes(null));

        assertTrue(fireplace1.hasSameAttributes(fireplace2));
        fireplace1.fireUp();
        assertFalse(fireplace1.hasSameAttributes(fireplace2));
        fireplace1.extinguish();
        assertTrue(fireplace1.hasSameAttributes(fireplace2));
    }
}
