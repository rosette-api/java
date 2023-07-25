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

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum EventsNegationOption {
    IGNORE("Ignore"),
    BOTH("Both"),
    ONLY_POSITIVE("Only positive"),
    ONLY_NEGATIVE("Only negative");

    private static final Map<String, EventsNegationOption> STRING_KEYS;

    static {
        STRING_KEYS = Arrays.stream(EventsNegationOption.values())
                .collect(Collectors.toMap(
                    value -> value.toString().toLowerCase(),
                    Function.identity()
                ));
    }

    private final String label;

    EventsNegationOption(String label) {
        this.label = label;
    }

    public static EventsNegationOption forValue(String value) {
        return STRING_KEYS.get(value.toLowerCase());
    }

    @Override
    public String toString() {
        return label;
    }
}
