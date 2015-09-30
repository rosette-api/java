/******************************************************************************
 ** Copyright (c) 2014-2015 Basis Technology Corporation.
 **
 ** Licensed under the Apache License, Version 2.0 (the "License");
 ** you may not use this file except in compliance with the License.
 ** You may obtain a copy of the License at
 **
 **     http://www.apache.org/licenses/LICENSE-2.0
 **
 ** Unless required by applicable law or agreed to in writing, software
 ** distributed under the License is distributed on an "AS IS" BASIS,
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ** See the License for the specific language governing permissions and
 ** limitations under the License.
 ******************************************************************************/

package com.basistech.rosette.apimodel;

import java.util.EnumSet;

/**
 * Rosette API relationship accuracy mode
 */
public enum AccuracyMode {
    PRECISION("precision"),
    RECALL("recall");

    private String label;

    /**
     * constructor for {@code AccuracyMode} which sets a label for reference
     */
    AccuracyMode(String label) {
        this.label = label;
    }

    /**
     * get the label
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * set the label
     * @param label the label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * checks if value is a valid {@code AccuracyMode} enum
     * @param value input value
     * @return {@code AccuracyMode corresponding to input value}
     * @throws IllegalArgumentException
     */
    public static AccuracyMode forValue(String value) throws IllegalArgumentException {
        for (AccuracyMode model : EnumSet.allOf(AccuracyMode.class)) {
            if (model.getLabel().equalsIgnoreCase(value)) {
                return model;
            }
        }
        throw new IllegalArgumentException("invalid relationship accuracy mode: " + value);
    }

    /**
     * get the label
     * @return the label
     */
    public String toValue() {
        return label;
    }
}

