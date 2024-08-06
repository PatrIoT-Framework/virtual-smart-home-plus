package io.patriotframework.virtualsmarthomeplus.house.devices;


import io.patriot_framework.generator.device.impl.basicActuators.BasicActuator;

public class DeviceMock extends Device {

    public DeviceMock(String label) {
        super(label);
        device = new BasicActuator(label);
    }

    public DeviceMock(Device origDevice, String newLabel) {
        super(origDevice, newLabel);
        device = new BasicActuator(newLabel);
    }

    @Override
    public Device createWithSameAttributes(String newLabel) {
        return null;
    }

    @Override
    public boolean hasSameAttributes(Device device) throws IllegalArgumentException {
        return false;
    }
}
