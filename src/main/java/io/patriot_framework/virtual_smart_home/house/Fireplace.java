package io.patriot_framework.virtual_smart_home.house;

import io.patriot_framework.virtual_smart_home.house.device.Actuator;

public class Fireplace extends Actuator {

    public Fireplace(String label) {
        super(label);
    }

    public void fireUp() {
        this.switchIt(true);
    }

    public void extinguish() {
        this.switchIt(false);
    }
}
