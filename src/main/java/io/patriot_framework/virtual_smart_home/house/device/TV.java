/*
 * Copyright 2022 Patriot project
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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * !!! Created just for House tests!!!
 * Representation of tv device
 */
public class TV extends Actuator {
    public TV(@JsonProperty("label") String label) {
        super(label);
    }

    public TV createSimilar(String label) throws IllegalDeviceArgumentException {
        final TV tv = new TV(label);
        tv.setEnabled(this.isEnabled());
        return tv;
    }
}
