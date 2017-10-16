/*
* Copyright 2014 Basis Technology Corp.
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

import java.util.Objects;

/**
 * Sentiment options.
 */
public final class SentimentOptions extends Options {
    public static final SentimentOptions DEGAULT_OPTIONS = new SentimentOptions(false, false);
    private Boolean calculateEntityConfidence;
    private Boolean calculateEntitySalience;

    /**
     * Constructor for {@code SentimentOptions}
     *
     * @param calculateEntityConfidence return confidence score for the entities.
     */
    protected SentimentOptions(Boolean calculateEntityConfidence, Boolean calculateEntitySalience) {
        this.calculateEntityConfidence = calculateEntityConfidence;
        this.calculateEntitySalience = calculateEntitySalience;
    }

    /**
     * @return the calculateEntityConfidence flag.
     */
    public Boolean getCalculateEntityConfidence() {
        return calculateEntityConfidence;
    }

    /**
     * @return the calculateEntitySalience flag.
     */
    public Boolean getCalculateEntitySalience() {
        return calculateEntitySalience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SentimentOptions that = (SentimentOptions)o;
        return Objects.equals(this.calculateEntityConfidence, that.calculateEntityConfidence)
                && Objects.equals(this.calculateEntitySalience, that.calculateEntitySalience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calculateEntityConfidence, calculateEntitySalience);
    }

    public static class Builder {
        private Boolean calculateEntityConfidence;
        private Boolean calculateEntitySalience;

        /**
         * DocumentRequest calculate entity confidence score. If the value is {@code true}, then the endpoint will
         * return confidence scores. If {@code false} or {@code null}, not.
         * @param calculateEntityConfidence whether to get entity confidence score.
         * @return this.
         */
        public Builder calculateEntityConfidence(Boolean calculateEntityConfidence) {
            this.calculateEntityConfidence = calculateEntityConfidence;
            return this;
        }

        /**
         * DocumentRequest calculate entity salience score. If the value is {@code true}, then the endpoint will
         * return salience scores. If {@code false} or {@code null}, not.
         * @param calculateEntitySalience whether to get entity salience score.
         * @return this.
         */
        public Builder calculateEntitySalience(Boolean calculateEntitySalience) {
            this.calculateEntitySalience = calculateEntitySalience;
            return this;
        }

        public SentimentOptions build() {
            return new SentimentOptions(calculateEntityConfidence, calculateEntitySalience);
        }
    }
}
