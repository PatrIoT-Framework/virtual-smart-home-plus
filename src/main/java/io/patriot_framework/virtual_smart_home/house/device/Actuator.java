package io.patriot_framework.virtual_smart_home.house.device;

public class Actuator extends Device implements Switchable {

    private boolean enabled = false;

    public Actuator(String label) {
        super(label);
    }

    @Override
    public void switchIt(boolean on) {
        enabled = on;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
