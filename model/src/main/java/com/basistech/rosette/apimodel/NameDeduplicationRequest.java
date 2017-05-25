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

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * Request object for name-deduplication.
 *
 * This class carries the list of names to dedupe as well as the score threshold.
 */
public final class NameDeduplicationRequest extends Request {

    @NotNull
    private List<Name> names;
    private Double threshold;

    /**
     * Constructor for {@code NameMatchingRequest}
     * @param names List of names to be deduplicated
     * @param threshold score threshold used to tune the strictness of the clustering algorithm. Can be null for default threshold.
     */
    public NameDeduplicationRequest(List<Name> names,
                                    Double threshold) {
        this.names = names;
        this.threshold = threshold;
    }

    /**
     * Gets the names
     * @return the names
     */
    public List<Name> getNames() {
        return names;
    }

    /**
     * Gets the threshold
     * @return the threshold
     */
    public Double getThreshold() {
        return threshold;
    }

    /**
     * Sets the names
     * @param names the names.
     */
    public void setNames(List<Name> names) {
        this.names = names;
    }

    /**
     * Sets the threshold
     * @param threshold the threshold.
     */
    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    @Override
    public int hashCode() {
        return Objects.hash(names, threshold);
    }

    /**
     * if the param is a {@code NameSimilarityRequest}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NameDeduplicationRequest that = (NameDeduplicationRequest) o;

        boolean sameThresh = (that.threshold == null || threshold == null) ? that.threshold == threshold
                                                                           : Double.compare(that.threshold, threshold) == 0;

        return sameThresh && Objects.equals(names, that.names);
    }
}
