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
 * Rosette API sentiment model
 */
public enum SentimentModel {
    REVIEW("review"),
    SHORT_STRING("short-string");

    private String label;

    /**
     * constructor for {@code SentimentModel} which sets a label for reference
     */
    SentimentModel(String label) {
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
     * checks if value is a valid {@code SentimentModel} enum
     * @param value input value
     * @return {@code SentimentModel corresponding to input value}
     * @throws IllegalArgumentException
     */
    public static SentimentModel forValue(String value) throws IllegalArgumentException {
        for (SentimentModel model : EnumSet.allOf(SentimentModel.class)) {
            if (model.getLabel().equalsIgnoreCase(value)) {
                return model;
            }
        }
        throw new IllegalArgumentException("invalid sentiment model: " + value);
    }

    /**
     * get the label
     * @return the label
     */
    public String toValue() {
        return label;
    }
}

