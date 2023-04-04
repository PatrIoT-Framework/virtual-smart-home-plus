package io.patriotframework.virtualsmarthomeplus.house.devices;

import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class FireplaceTest {
    Fireplace fireplace1 = new Fireplace("fireplace1");
    Fireplace fireplace2 = new Fireplace("fireplace2");

    @Test
    public void getStatus() {
        ReflectionTestUtils.setField(fireplace1, "onFire", false);
        assertEquals(Fireplace.EXTINGUISHED, fireplace1.getStatus());
        assertEquals(Fireplace.EXTINGUISHED, fireplace1.getStatus());
        assertSame(false, ReflectionTestUtils.getField(fireplace1, "onFire"));
        ReflectionTestUtils.setField(fireplace1, "onFire", true);
        assertEquals(Fireplace.ON_FIRE, fireplace1.getStatus());
        assertEquals(Fireplace.ON_FIRE, fireplace1.getStatus());
        assertSame(true, ReflectionTestUtils.getField(fireplace1, "onFire"));
    }

    @Test
    public void fireUp() {
        assertEquals(Fireplace.EXTINGUISHED, fireplace1.getStatus());
        fireplace1.fireUp();
        assertEquals(Fireplace.ON_FIRE, fireplace1.getStatus());
        fireplace1.fireUp();
        assertEquals(Fireplace.ON_FIRE, fireplace1.getStatus());
        fireplace1.fireUp();
        fireplace1.fireUp();
        assertEquals(Fireplace.ON_FIRE, fireplace1.getStatus());
    }

    @Test
    public void extinguish() {
        fireplace1.setEnabled(false);
        fireplace1.extinguish();
        assertEquals(Fireplace.EXTINGUISHED, fireplace1.getStatus());
        fireplace1.extinguish();
        assertEquals(Fireplace.EXTINGUISHED, fireplace1.getStatus());
        fireplace1.extinguish();
        fireplace1.extinguish();
        assertEquals(Fireplace.EXTINGUISHED, fireplace1.getStatus());
    }

    @Test
    public void testHasSameAttributes() {
        assertTrue(fireplace1.hasSameAttributes(fireplace2));
        fireplace1.setEnabled(true);
        assertFalse(fireplace1.hasSameAttributes(fireplace2));
        fireplace2.setEnabled(true);
        assertTrue(fireplace1.hasSameAttributes(fireplace2));
    }

    @Test
    public void testCreateWithSameAttributes() {
        fireplace1.setEnabled(true);
        assertFalse(fireplace1.hasSameAttributes(fireplace2));
        fireplace2 = fireplace1.createWithSameAttributes("fireplace2");
        assertTrue(fireplace1.hasSameAttributes(fireplace2));
    }
}
