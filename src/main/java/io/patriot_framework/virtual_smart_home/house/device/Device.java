package io.patriot_framework.virtual_smart_home.house.device;

public abstract class Device {

    private final String label;

    public Device(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
