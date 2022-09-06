/*
 * Copyright 2021 Patriot project
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package io.patriot_framework.virtual_smart_home.house;

import io.patriot_framework.virtual_smart_home.house.device.Device;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import io.patriot_framework.virtual_smart_home.house.device.DifferentDeviceException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.management.openmbean.KeyAlreadyExistsException;

/**
 * House class is responsible for management of {@code Device} used in the Virtual Smart Home
 */
public final class House {

    public static final Logger LOGGER = LogManager.getLogger();
    private String houseName;
    private Map<String, Device> devices = new ConcurrentHashMap<>();

    public House(String houseName) throws IllegalArgumentException {
        if (houseName == null) {
            throw new IllegalArgumentException("houseName can't be null");
        }
        this.houseName = houseName;
        LOGGER.debug(String.format("Created new house with name \"%s\"",this.houseName));
    }

    /**
     * Method for addition of devices to house object
     *
     * @param label string representing device name
     * @param device actual instance of device
     * @throws IllegalArgumentException if one of the parameters is null
     * @throws KeyAlreadyExistsException if house already contains device with given label
     */
    public void addDevice(String label, Device device) throws IllegalArgumentException, KeyAlreadyExistsException {
        if (label == null) {
            throw new IllegalArgumentException("Label of the device can't be null");
        }
        if (device == null) {
            throw new IllegalArgumentException("Device parameter can't be null");
        }
        final Device origDevice = devices.putIfAbsent(label, device);
        if (origDevice != null) {
            throw new KeyAlreadyExistsException(String.format("Device with label: %s is already exists", label));
        }
        LOGGER.debug(String.format("Device %s with label %s added to house %s", device, label, this.houseName));
    }

    /**
     * Method used to retrieve single device with certain label
     *
     * @param label label of desired device
     * @return instance of device with given label
     * @throws IllegalArgumentException if parameter is null
     * @throws NoSuchElementException if device with given label isn't in the House
     */
    public Device getDevice(String label) throws IllegalArgumentException, NoSuchElementException {
        if (label == null) {
            throw new IllegalArgumentException("Label of the device can't be null");
        }
        final Device device = devices.get(label);
        if (device == null) {
            throw new NoSuchElementException(String.format("Device with label: %s is not present in the house", label));
        } else {
            return device;
        }
    }

    /**
     * Method used to replace device with certain label
     *
     * @param label label of device to be replaced
     * @param device instance of new device with given label
     * @throws IllegalArgumentException if one of the parameters is null
     * @throws NoSuchElementException if house doesn't contains device with given label
     * @throws io.patriot_framework.virtual_smart_home.house.device.DifferentDeviceException if
     * updating with different device
     */
    public void updateDevice(String label, Device device) throws IllegalArgumentException, NoSuchElementException {
        if (label == null) {
            throw new IllegalArgumentException("Label of the device can't be null");
        }
        if (device == null) {
            throw new IllegalArgumentException("Device parameter can't be null");
        }

        final Device origDevice = devices.get(label);
        if (origDevice == null) {
            throw new NoSuchElementException(String.format("Device with label: %s is not present in the house", label));
        }
        if (!origDevice.equals(device)) {
            throw new DifferentDeviceException("Updating with different device");
        }

        devices.put(label, device);
        LOGGER.debug(String.format("At house:%s device with label:%s updated. "
                    + "(Device:%s replaced by:%s", houseName, label, origDevice, device));
    }

    /**
     * Method used to remove device from house object
     *
     * @param label label of device to be removed
     * @throws IllegalArgumentException if label is null
     * @throws NoSuchElementException if house doesn't contains device with given label
     */
    public void removeDevice(String label) throws IllegalArgumentException, NoSuchElementException {
        if (label == null) {
            throw new IllegalArgumentException("Label of the device can't be null");
        }
        final Device origDevice = devices.remove(label);
        if (origDevice == null) {
            throw new NoSuchElementException(String.format("Device with label: %s is not present in the house", label));
        } else {
            LOGGER.debug(String.format("At house:%s device:%s with label:%s removed.", houseName, origDevice, label));
        }
    }

    /**
     * Method used to retrieve devices of certain type stored in house
     *
     * @param type type of desired devices
     * @return Map of devices of requested type with their labels
     */
    public Map<String, Device> getDevicesOfType(Class<? extends Device> type) {
        return devices.entrySet().stream()
                .filter(entry -> type.isInstance(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Getter for house name
     *
     * @return string containing house name
     */
    public String getHouseName() {
        return houseName;
    }

    /**
     * Setter for house name
     *
     * @param houseName string with house name
     * @throws IllegalArgumentException if houseName is null
     */
    public void setHouseName(String houseName) throws IllegalArgumentException { // TODO: Remove?
        if (houseName == null) {
            throw new IllegalArgumentException("houseName can't be null");
        }
        this.houseName = houseName;
        LOGGER.debug(String.format("House with name:%s renamed to:%s", this.houseName, houseName));
    }

    /**
     * Method used to retrieve all devices stored in house
     *
     * @return Map of devices and their labels
     */
    public Map<String, Device> getDevices() {
        return Collections.unmodifiableMap(devices);
    }

    /**
     * Method used to setup or reset all the devices in house
     *
     * @param devices map of devices and their labels
     * @throws IllegalArgumentException if map of devices is null
     */
    public void setDevices(Map<String, Device> devices) throws IllegalArgumentException { // TODO: ?
        if (devices == null) {
            throw new IllegalArgumentException("Methd parameter devices can't be null");
        }
        this.devices = devices;
        LOGGER.debug(String.format("At house:%s devices:%s set instead of devices:%s",
                houseName, devices, this.devices));
    }
}
