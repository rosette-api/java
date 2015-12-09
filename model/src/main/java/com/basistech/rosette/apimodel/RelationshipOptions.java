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

/**
 * Relationship extraction options
 */
public final class RelationshipOptions {

    private AccuracyMode accuracyMode;

    /**
     * constructor for {@code RelationshipOptions}
     * @param accuracyMode   accuracyMode to use for relationship extraction
     */
    public RelationshipOptions(
            AccuracyMode accuracyMode) {
        this.accuracyMode = accuracyMode;
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

    @Override
    public int hashCode() {
        return accuracyMode != null ? accuracyMode.hashCode() : 0;
    }

    /**
     * if the param is a {@code RelationshipOptions}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RelationshipOptions)) {
            return false;
        }

        RelationshipOptions that = (RelationshipOptions) o;
        return accuracyMode != null ? accuracyMode.equals(that.getAccuracyMode()) : that.accuracyMode == null;
    }
}
