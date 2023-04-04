package io.patriotframework.virtualsmarthomeplus;

import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import org.apache.commons.lang3.NotImplementedException;

public class MockDevice extends Device {

     public MockDevice(String label) {
         super(label);
     }

    public Device createWithSameState(String newLabel) {
        return new MockDevice("newLabel");
    }

    public boolean hasSameAttributes(Device device) throws IllegalArgumentException {
        throw new NotImplementedException("Not implemented yet.");
    }
    public Fireplace createWithSameAttributes(String newLabel) {
        throw new NotImplementedException("Not implemented yet.");
    }
}
