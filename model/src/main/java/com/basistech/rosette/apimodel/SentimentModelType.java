/*
 * Copyright 2017 Basis Technology Corp.
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

/**
 * A model type for sentiment analysis.
 */
public enum SentimentModelType {
    SVM("svm"),
    DNN("dnn"),
    /**/;

    private final String label;

    SentimentModelType(final String label) {
        this.label = label;
    }

    /**
     * Gets this model type's label.
     * @return this model type's label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Checks if a value is a valid {@code SentimentModelType}
     * @param value an input value
     * @return the {@code SentimentModelType} corresponding to the input value
     * @throws IllegalArgumentException if the input string is invalid
     */
    public static SentimentModelType forValue(final String value) throws IllegalArgumentException {
        for (final SentimentModelType sentimentModelType : EnumSet.allOf(SentimentModelType.class)) {
            if (sentimentModelType.label.equalsIgnoreCase(value)) {
                return sentimentModelType;
            }
        }
        throw new IllegalArgumentException("invalid sentiment model type: " + value);
    }
}
