package io.patriotframework.virtualsmarthomeplus.devices;

import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.RGBLight;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class RGBLightTest {

    RGBLight rgb1 = new RGBLight("rgb1");
    RGBLight rgb2 = new RGBLight("rgb2");

    @Test
    public void constructorTest() {
        assertDoesNotThrow(() -> {
            new RGBLight("rgb3");
        });
        assertThrows(IllegalArgumentException.class, () -> new RGBLight(""));
        assertThrows(IllegalArgumentException.class, () -> new RGBLight(null));
    }

    @Test
    public void switchOn() {
        rgb1.setEnabled(true);
        assertFalse(rgb1.isOn());
        rgb1.switchOn();
        assertTrue(rgb1.isOn());
        rgb1.switchOn();
        rgb1.switchOf();
        rgb1.switchOn();
        assertTrue(rgb1.isOn());

        rgb2.setEnabled(true);
        assertFalse(rgb2.isOn());
        rgb2.switchOn();
        assertTrue(rgb2.isOn());
        rgb2.switchOn();
        rgb2.switchOf();
        rgb2.switchOn();
        assertTrue(rgb2.isOn());
    }

    @Test
    public void switchOf() {
        rgb1.setEnabled(true);
        assertFalse(rgb1.isOn());
        rgb1.switchOf();
        assertFalse(rgb1.isOn());
        rgb1.switchOf();
        rgb1.switchOn();
        rgb1.switchOf();
        assertFalse(rgb1.isOn());

        rgb2.setEnabled(true);
        assertFalse(rgb2.isOn());
        rgb2.switchOf();
        assertFalse(rgb2.isOn());
        rgb2.switchOf();
        rgb2.switchOn();
        rgb2.switchOf();
        assertFalse(rgb2.isOn());
    }

    @Test
    public void getRGB()
    {
        Color color= new Color(0,0,0);
        assertEquals(rgb1.getRGB(),color);
    }
    @Test
    public  void getRed()
    {
        assertEquals(rgb1.getRed(),0);
    }
    @Test
    public  void getGreen()
    {
        assertEquals(rgb1.getGreen(),0);
    }
    @Test
    public  void getBlue()
    {
        assertEquals(rgb1.getBlue(),0);
    }

    @Test
    public void setRGB() {
        rgb1.setRGB(5,4,3);
        Color color = new Color(5,4,3);
        assertEquals(rgb1.getRGB(),color);
    }
    @Test
    public void setRed() {
        rgb1.setRed(4);
        Color color = new Color(4,0,0);
        assertEquals(rgb1.getRGB(),color);
    }
    @Test
    public void setGreen() {
        rgb1.setGreen(4);
        Color color = new Color(0,4,0);
        assertEquals(rgb1.getRGB(),color);
    }
    @Test
    public void setBlue() {
        rgb1.setBlue(4);
        Color color = new Color(0,0,4);
        assertEquals(rgb1.getRGB(),color);
    }

    @Test
    public void createWithSameAttributes(){
        rgb1.setEnabled(true);
        RGBLight rgbLight3 = rgb1.createWithSameAttributes("rgb3");
        assertEquals(rgbLight3.isEnabled(),rgb1.isEnabled());
        assertTrue(rgbLight3.hasSameAttributes(rgb1));
    }

    @Test

    public void testHasSameAttributes() {
        DeviceMock device1 = new DeviceMock("door1");
        assertThrows(IllegalArgumentException.class, () -> rgb1.hasSameAttributes(device1));

        assertThrows(IllegalArgumentException.class,() -> rgb1.hasSameAttributes(null));

        assertTrue(rgb1.hasSameAttributes(rgb2));
        rgb1.setRed(36);
        assertFalse(rgb1.hasSameAttributes(rgb2));
        rgb1.setRed(rgb2.getRed());
        assertTrue(rgb1.hasSameAttributes(rgb2));
    }

}
