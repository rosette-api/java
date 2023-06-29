/*
 * Copyright 2023 Basis Technology Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.basistech.rosette.apimodel;

import java.util.EnumSet;

public enum EventsNegationOption {
    IGNORE("Ignore"),
    BOTH("Both"),
    ONLY_POSITIVE("Only positive"),
    ONLY_NEGATIVE("Only negative");

    private final String label;

    EventsNegationOption(String label) {
        this.label = label;
    }

    public static EventsNegationOption forValue(String value) {
        for (EventsNegationOption negationOption : EnumSet.allOf(EventsNegationOption.class)) {
            if (negationOption.toString().equalsIgnoreCase(value)) {
                return negationOption;
            }
        }
        throw new IllegalArgumentException("invalid events negation option: " + value);
    }

    @Override
    public String toString() {
        return label;
    }
}
