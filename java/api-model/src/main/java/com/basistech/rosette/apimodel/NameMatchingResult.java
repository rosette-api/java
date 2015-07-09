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

import java.util.Objects;

/**
 * Name matcher result
 */
public final class NameMatchingResult {

    private final Double score;

    /**
     * Constructor for {@code NameMatchingResult}
     * @param score score of matching 2 names
     */
    public NameMatchingResult(
            Double score) {
        this.score = score;
    }

    /**
     * Gets the score of matching 2 names
     * @return score
     */
    public Double getScore() {
        return score;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(score);
        return (int) (temp ^ (temp >>> 32));
    }

    /**
     * if the param is a {@code NameMatchingResult}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NameMatchingResult)) {
            return false;
        }

        NameMatchingResult that = (NameMatchingResult) o;
        return Objects.equals(score, that.getScore());
    }
}
