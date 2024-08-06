package io.patriotframework.virtualsmarthomeplus.house.devices;

import io.patriotframework.virtualsmarthomeplus.DTOs.RGBLightDTO;
import io.patriotframework.virtualsmarthomeplus.Mapper.DTOMapper;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.RGBLight;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.awt.*;

import static io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.RGBLight.OFF;
import static io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.RGBLight.ON;
import static org.junit.jupiter.api.Assertions.*;

public class RGBLightTest {

    private final ModelMapper modelMapper = new ModelMapper();
    private final RGBLight rgb1 = new RGBLight("rgb1");
    private final RGBLight rgb2 = new RGBLight("rgb2");

    @Test
    public void constructorTest() {
        RGBLightDTO rgbLightDTO = new RGBLightDTO();
        rgbLightDTO.setLabel("rgb");
        assertDoesNotThrow(() -> {
            RGBLight rgb = new RGBLight("rgb3");
            rgb.switchOff();
        });
        assertThrows(IllegalArgumentException.class, () -> new RGBLight(""));
        assertThrows(IllegalArgumentException.class, () -> new RGBLight(null));
    }

    @Test
    public void switchOnTest() {
        rgb1.setEnabled(true);
        assertFalse(rgb1.getStatus().equals(ON));
        rgb1.switchOn();
        assertTrue(rgb1.getStatus().equals(ON));
        rgb1.switchOn();
        rgb1.switchOff();
        rgb1.switchOn();
        assertTrue(rgb1.getStatus().equals(ON));

        rgb2.setEnabled(true);
        assertFalse(rgb2.getStatus().equals(ON));
        rgb2.switchOn();
        assertTrue(rgb2.getStatus().equals(ON));
        rgb2.switchOn();
        rgb2.switchOff();
        rgb2.switchOn();
        assertTrue(rgb2.getStatus().equals(ON));
    }

    @Test
    public void switchOffTest() {
        rgb1.setEnabled(true);
        assertFalse(rgb1.getStatus().equals(ON));
        rgb1.switchOff();
        assertFalse(rgb1.getStatus().equals(ON));
        rgb1.switchOff();
        rgb1.switchOn();
        rgb1.switchOff();
        assertFalse(rgb1.getStatus().equals(ON));

        rgb2.setEnabled(true);
        assertFalse(rgb2.getStatus().equals(ON));
        rgb2.switchOff();
        assertFalse(rgb2.getStatus().equals(ON));
        rgb2.switchOff();
        rgb2.switchOn();
        rgb2.switchOff();
        assertFalse(rgb2.getStatus().equals(ON));
    }

    @Test
    public void getRGB() {
        Color color = new Color(0, 0, 0);
        assertEquals(rgb1.getRGB(), color);
    }

    @Test
    public void getRed() {
        assertEquals(rgb1.getRed(), 0);
    }

    @Test
    public void getGreen() {
        assertEquals(rgb1.getGreen(), 0);
    }

    @Test
    public void getBlue() {
        assertEquals(rgb1.getBlue(), 0);
    }

    @Test
    public void setRGB() {
        rgb1.setRGB(5, 4, 3);
        Color color = new Color(5, 4, 3);
        assertEquals(rgb1.getRGB(), color);
    }

    @Test
    public void setRed() {
        rgb1.setRed(4);
        Color color = new Color(4, 0, 0);
        assertEquals(rgb1.getRGB(), color);
    }

    @Test
    public void setGreen() {
        rgb1.setGreen(4);
        Color color = new Color(0, 4, 0);
        assertEquals(rgb1.getRGB(), color);
    }

    @Test
    public void setBlue() {
        rgb1.setBlue(4);
        Color color = new Color(0, 0, 4);
        assertEquals(rgb1.getRGB(), color);
    }

    @Test
    public void createWithSameAttributes() {
        rgb1.setEnabled(true);
        RGBLight rgbLight3 = rgb1.createWithSameAttributes("rgb3");
        assertTrue(rgbLight3.hasSameAttributes(rgb1));
    }

    @Test

    public void testHasSameAttributes() {
        DeviceMock device1 = new DeviceMock("door1");
        assertThrows(IllegalArgumentException.class, () -> rgb1.hasSameAttributes(device1));

        assertThrows(IllegalArgumentException.class, () -> rgb1.hasSameAttributes(null));

        assertTrue(rgb1.hasSameAttributes(rgb2));
        rgb1.setRed(36);
        assertFalse(rgb1.hasSameAttributes(rgb2));
        rgb1.setRed(rgb2.getRed());
        assertTrue(rgb1.hasSameAttributes(rgb2));
    }

}
