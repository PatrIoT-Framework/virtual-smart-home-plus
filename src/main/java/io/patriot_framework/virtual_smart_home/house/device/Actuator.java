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

/**
 * Simple Device type with ability to manipulate state (enabled/disabled) of {@code Device}
 */
public abstract class Actuator extends Device {

    public static final Logger LOGGER = LogManager.getLogger();
    private boolean enabled = false;

    public Actuator(String label) {
        super(label);
    }

    /**
     * Returns state of actuator
     *
     * @return true if actuator is running, false if actuator is disabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets state of the actuator
     *
     * @param enabled boolean parameter determining if actuator is running or if it's disabled
     */
    public void setEnabled(boolean enabled) {
        LOGGER.debug(String.format("Actuator with label:%s set from state enabled:%s to state enabled:%s",
                this.getLabel(), this.enabled, enabled));
        this.enabled = enabled;
    }
}
