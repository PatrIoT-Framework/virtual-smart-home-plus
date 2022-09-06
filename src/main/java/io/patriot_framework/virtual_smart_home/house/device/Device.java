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

package io.patriot_framework.virtual_smart_home.house.device;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import java.util.Objects;

/**
 * Main representation of the device used in {@code House}
 */
public abstract class Device {

    public static final Logger LOGGER = LogManager.getLogger();
    @ApiModelProperty(accessMode = AccessMode.READ_WRITE)
    private final String label;

    public Device(String label) throws IllegalDeviceArgumentException {
        if (label == null) {
            throw new IllegalDeviceArgumentException("Label of the device can't be null");
        }
        this.label = label;
        LOGGER.debug(String.format("Created new device with label \"%s\"", label));
    }

    /**
     * Returns copy of this object with specified label.
     *
     * @param label label of the new fireplace
     * @return new fireplace with given label, other attributes will be same as in this object
     * @throws IllegalDeviceArgumentException if label is null
     */
    public abstract Device createSimilar(String label) throws IllegalDeviceArgumentException;

    /**
     * Getter for device label
     *
     * @return string containing device label
     */
    public String getLabel() {
        return label;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.label);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Device other = (Device) obj;
        return Objects.equals(this.label, other.label);
    }
}
