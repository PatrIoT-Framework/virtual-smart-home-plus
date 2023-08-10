package io.patriotframework.virtualsmarthomeplus.house.devices;

import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Thermometer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ThermometerTest {
    private final Thermometer thermometer1 = new Thermometer("thermometer1", Thermometer.CELSIUS);
    private final Thermometer thermometer2 = new Thermometer("thermometer2", Thermometer.FAHRENHEIT);

    @Test
    public void constructorTest() {
        assertDoesNotThrow(() -> {
            new Thermometer("thermometer3");
        });
        assertDoesNotThrow(() -> {
            new Thermometer("thermometer4", Thermometer.FAHRENHEIT);
        });
        assertThrows(IllegalArgumentException.class, () -> new Thermometer(""));
        assertThrows(IllegalArgumentException.class, () -> new Thermometer(null));
    }

    @Test
    public void getUnit() {
        assertEquals(thermometer1.getUnit(), Thermometer.CELSIUS);
        assertEquals(thermometer2.getUnit(), Thermometer.FAHRENHEIT);
    }

    @Test
    public void getTemperature() {
        float temperature = thermometer1.getTemperature();
        assertTrue(temperature < 40 && temperature > 0);
    }

    @Test
    public void setUnit() {
        thermometer1.setUnit(Thermometer.FAHRENHEIT);
        assertEquals(thermometer1.getUnit(), Thermometer.FAHRENHEIT);
        thermometer2.setUnit(Thermometer.CELSIUS);
        assertEquals(thermometer2.getUnit(), Thermometer.CELSIUS);
    }

    @Test
    public void createWithSameAttributes() {
        Thermometer thermometer3 = (Thermometer) thermometer1.createWithSameAttributes("thermometer3");
        assertEquals(thermometer1.getUnit(), thermometer3.getUnit());
        assertNotEquals(thermometer3.getUnit(), thermometer2.getUnit());

        Thermometer thermometer4 = (Thermometer) thermometer2.createWithSameAttributes("thermometer4");
        assertNotEquals(thermometer1.getUnit(), thermometer4.getUnit());
        assertEquals(thermometer4.getUnit(), thermometer2.getUnit());
    }

    @Test
    public void hasSameAttributes() {
        DeviceMock device1 = new DeviceMock("door1");
        assertThrows(IllegalArgumentException.class, () -> thermometer1.hasSameAttributes(device1));

        assertThrows(IllegalArgumentException.class, () -> thermometer1.hasSameAttributes(null));

        assertFalse(thermometer1.hasSameAttributes(thermometer2));
        thermometer1.setUnit(Thermometer.FAHRENHEIT);
        assertTrue(thermometer1.hasSameAttributes(thermometer2));
    }
}
