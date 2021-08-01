package io.patriot_framework.virtual_smart_home.house;

import io.patriot_framework.virtual_smart_home.house.device.Fireplace;

public class HouseBean {

    private House house;

    private HouseBean(House house) {
        this.house = house;
    }

    public void fireplaceOn() {
        ((Fireplace) (house.getDevice("fireplace"))).fireUp();
    }
}
