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

/**
 * Response data model for comparison of two names.
 */
public final class NameSimilarityResponse extends Response {

    private final Double score;

    /**
     * Constructor for {@code NameMatchingResponse}
     * @param score the similarity score.
     */
    public NameSimilarityResponse(Double score) {
        this.score = score;
    }

    /**
     * Gets the name similarity result
     * @return name similarity result
     */
    public Double getScore() {
        return score;
    }

    @Override
    public int hashCode() {
        return score != null ? score.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NameSimilarityResponse)) {
            return false;
        }

        NameSimilarityResponse that = (NameSimilarityResponse) o;
        return score != null ? score.equals(that.getScore()) : that.score == null;
    }
}
