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
 * Relationship extraction options
 */
public final class RelationshipsOptions extends Options {

    private AccuracyMode accuracyMode;
    private Boolean discoveryMode;

    /**
     * Create a set of relationship extraction options with default values.
     * Note that {@code null} is used to represent defaults.
     */
    public RelationshipsOptions() {
        //
    }

    /**
     * constructor for {@code RelationshipOptions}
     * @param accuracyMode   accuracyMode to use for relationship extraction
     * @param discoveryMode  discoveryMode to use for relationship extraction
     */
    public RelationshipsOptions(AccuracyMode accuracyMode, Boolean discoveryMode) {
        this.accuracyMode = accuracyMode;
        this.discoveryMode = discoveryMode;
    }

    /**
     * get the accuracyMode to use for relationship extraction
     * @return the accuracyMode to use for relationship extraction
     */
    public AccuracyMode getAccuracyMode() {
        return accuracyMode;
    }

    /**
     * set the accuracyMode to use for relationship extraction
     * @param accuracyMode the accuracyMode to use for relationship extraction
     */
    public void setAccuracyMode(AccuracyMode accuracyMode) {
        this.accuracyMode = accuracyMode;
    }

    /**
     * @return the discoveryMode flag.
     */
    public Boolean getDiscoveryMode() {
        return discoveryMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RelationshipsOptions that = (RelationshipsOptions) o;
        return accuracyMode == that.accuracyMode
            && Objects.equals(discoveryMode, that.discoveryMode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accuracyMode, discoveryMode);
    }

    public static class Builder {
        private Boolean discoveryMode;
        private AccuracyMode accuracyMode;

        public Builder() {
            //
        }

        /**
         * DocumentRequest accuracy mode for the relationship endpoint.
         * @param accuracyMode {@link AccuracyMode} to use.
         * @return this.
         */
        public RelationshipsOptions.Builder accuracyMode(AccuracyMode accuracyMode) {
            this.accuracyMode = accuracyMode;
            return this;
        }


        /**
         * DocumentRequest discovery mode. If the value is {@code true}, then the the endpoint will open relationship
         * extraction results. If {@code false}, not. If {@code null}, the endpoint will perform default processing.
         * @param discoveryMode discovery mode
         * @return this.
         */
        public RelationshipsOptions.Builder discoveryMode(Boolean discoveryMode) {
            this.discoveryMode = discoveryMode;
            return this;
        }

        public RelationshipsOptions build() {
            return new RelationshipsOptions(accuracyMode, discoveryMode);
        }
    }

}
