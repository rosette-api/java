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

import java.util.List;
import java.util.Objects;

/**
 * Response data model for the deduplication of a list of names
 */
public final class NameDeduplicationResponse extends Response {

    private final List<String> results;

    /**
     * Constructor for {@code NameMatchingResponse}
     * @param results the results from deduplication as a list of cluster IDs.
     */
    public NameDeduplicationResponse(List<String> results) {
        this.results = results;
    }

    /**
     * Gets the name deduplication results
     * @return name deduplication results
     */
    public List<String> getResults() {
        return results;
    }

    @Override
    public int hashCode() {
        return Objects.hash(results);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NameDeduplicationResponse that = (NameDeduplicationResponse) o;
        return Objects.equals(results, that.results);
    }
}
