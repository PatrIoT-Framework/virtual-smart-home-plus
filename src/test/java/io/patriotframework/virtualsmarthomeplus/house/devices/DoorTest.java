package io.patriotframework.virtualsmarthomeplus.house.devices;

import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoorTest {
    Door door1 = new Door("door1");
    Door door2 = new Door("door2");

    @Test
    public void getStatus() {
        ReflectionTestUtils.setField(door1, "opened", false);
        assertEquals(Door.CLOSED, door1.getStatus());
        assertEquals(Door.CLOSED, door1.getStatus());
        assertSame(false, ReflectionTestUtils.getField(door1, "opened"));
        ReflectionTestUtils.setField(door1, "opened", true);
        assertEquals(Door.OPENED, door1.getStatus());
        assertEquals(Door.OPENED, door1.getStatus());
        assertSame(true, ReflectionTestUtils.getField(door1, "opened"));
    }

    @Test
    public void open() {
        assertEquals(Door.CLOSED, door1.getStatus());
        door1.open();
        assertEquals(Door.OPENED, door1.getStatus());
        door1.open();
        assertEquals(Door.OPENED, door1.getStatus());
        door1.open();
        door1.open();
        assertEquals(Door.OPENED, door1.getStatus());
    }

    @Test
    public void close() {
        door1.setEnabled(false);
        door1.close();
        assertEquals(Door.CLOSED, door1.getStatus());
        door1.close();
        assertEquals(Door.CLOSED, door1.getStatus());
        door1.close();
        door1.close();
        assertEquals(Door.CLOSED, door1.getStatus());
    }

    @Test
    public void testHasSameAttributes() {
        assertTrue(door1.hasSameAttributes(door2));
        door1.setEnabled(true);
        assertFalse(door1.hasSameAttributes(door2));
        door2.setEnabled(true);
        assertTrue(door1.hasSameAttributes(door2));
    }

    @Test
    public void testCreateWithSameAttributes() {
        door1.setEnabled(true);
        assertFalse(door1.hasSameAttributes(door2));
        door2 = door1.createWithSameAttributes("door2");
        assertTrue(door1.hasSameAttributes(door2));
    }
}
