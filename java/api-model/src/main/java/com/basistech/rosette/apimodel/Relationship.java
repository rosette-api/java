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
 * Relationship extracted by the relationship extractor
 */
public final class Relationship {

    // TODO: WS-496 will complete this
    private final String description; // placeholder

    /**
     * constructor for {@code Relationship}
     * @param description relationship text
     */
    public Relationship(
            String description
    ) {
        this.description = description;
    }

    /**
     * get the relationship description
     * @return the relationship description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        int result;
        result = description != null ? description.hashCode() : 0;
        return result;
    }

    /**
     * if the param is a {@code Relationship}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Relationship)) {
            return false;
        }

        Relationship that = (Relationship) o;
        return description != null ? description.equals(that.getDescription()) : that.description == null;
    }
}
