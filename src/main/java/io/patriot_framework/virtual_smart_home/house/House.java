package io.patriot_framework.virtual_smart_home.house;

import io.patriot_framework.virtual_smart_home.house.device.Device;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class House {

    private String houseName;
    private Map<String, Device> devices = new ConcurrentHashMap<>();

    public House(String houseName) {
        this.houseName = houseName;
    }

    public void addDevice(String label, Device device) {
        devices.put(label, device);
    }

    public Device getDevice(String label) {
        return devices.get(label);
    }

    public void removeDevice(String label){
        devices.remove(label);
    }

    public void setDevices(Map<String, Device> devices) {
        this.devices = devices;
    }

    public <T> List<T> getDevicesOfType(Class<T> type) {
        return devices.values().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }
}
