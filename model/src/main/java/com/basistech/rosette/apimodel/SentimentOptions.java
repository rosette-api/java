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
public final class SentimentOptions {

    private Integer explanationCount;

    /**
     * Create a set of sentiment analysis options with default values.
     */
    public SentimentOptions() {
        //
    }

    /**
     * @return the maximum number of explanation tokens to return.
     */
    public Integer getExplanationCount() {
        return explanationCount;
    }

    /**
     * Set the maximum number of explanation strings to return. The default is none.
     * @param explanationCount the number of strings.
     */
    public void setExplanationCount(Integer explanationCount) {
        this.explanationCount = explanationCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SentimentOptions that = (SentimentOptions) o;
        return Objects.equals(explanationCount, that.explanationCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(explanationCount);
    }
}
