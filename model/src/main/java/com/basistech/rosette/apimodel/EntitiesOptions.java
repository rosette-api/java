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
 * EntityMention extraction options
 */
public final class EntitiesOptions extends Options {

    public static final EntitiesOptions DEFAULT_OPTIONS = new EntitiesOptions(false, false, true);
    private Boolean calculateConfidence;
    private Boolean calculateSalience;
    private Boolean linkEntities;

    /**
     * Constructor for {@code EntitiesOptions}
     *
     * @param calculateConfidence return confidence score for the extraction.
     * @param linkEntities perform entity linking in addition to extraction.
     */
    protected EntitiesOptions(Boolean calculateConfidence, Boolean calculateSalience, Boolean linkEntities) {
        this.calculateConfidence = calculateConfidence;
        this.calculateSalience = calculateSalience;
        this.linkEntities = linkEntities;
    }

    /**
     * @return the linkEntities flag.
     */
    public Boolean getLinkEntities() {
        return linkEntities;
    }

    /**
     * @return the calculatConfidence flag.
     */
    public Boolean getCalculateConfidence() {
        return calculateConfidence;
    }

    /**
     * @return the calculateSalience flag.
     */
    public Boolean getCalculateSalience() {
        return calculateSalience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EntitiesOptions that = (EntitiesOptions) o;
        return Objects.equals(linkEntities, that.linkEntities)
                && Objects.equals(calculateConfidence, that.calculateConfidence)
                && Objects.equals(calculateSalience, that.calculateSalience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calculateConfidence, calculateSalience, linkEntities);
    }

    public static class Builder {
        private Boolean calculateConfidence;
        private Boolean calculateSalience;
        private Boolean linkEntities;

        public Builder() {
            //
        }

        /**
         * DocumentRequest calculate confidence score. If the value is {@code true}, then the endpoint will
         * return confidence scores. If {@code false} or {@code null}, not.
         * @param calculateConfidence whether to get confidence score.
         * @return this.
         */
        public Builder calculateConfidence(Boolean calculateConfidence) {
            this.calculateConfidence = calculateConfidence;
            return this;
        }

        /**
         * DocumentRequest calculate salience score. If the value is {@code true}, then the endpoint will
         * return salience scores. If {@code false} or {@code null}, not.
         * @param calculateSalience whether to get salience score.
         * @return this.
         */
        public Builder calculateSalience(Boolean calculateSalience) {
            this.calculateSalience = calculateSalience;
            return this;
        }

        /**
         * DocumentRequest entity linking. If the value is {@code true}, then the the endpoint will link entities to the
         * knowledge base. If {@code false}, not. If {@code null}, the endpoint will perform default processing.
         * @param linkEntities whether to link.
         * @return this.
         */
        public Builder linkEntities(Boolean linkEntities) {
            this.linkEntities = linkEntities;
            return this;
        }

        public EntitiesOptions build() {
            return new EntitiesOptions(calculateConfidence, calculateSalience, linkEntities);
        }
    }
}
