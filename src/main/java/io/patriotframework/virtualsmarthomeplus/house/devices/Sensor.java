package io.patriotframework.virtualsmarthomeplus.house.devices;

public abstract class Sensor extends Device {

    public Sensor(String label) {
        super(label);
    }

    public Sensor(Device origDevice, String newLabel) {
        super(origDevice, newLabel);
    }
}
