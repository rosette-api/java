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
 * Sentiment options
 */
public final class SentimentOptions {

    private Boolean explain;

    /**
     * Create a set of sentiment analysis options with default values.
     * Note that {@code null} is used to represent defaults.
     */
    public SentimentOptions() {
        //
    }

    /**
     * constructor for {@code SentimentOptions}
     * @param explain whether to return explanation strings for the sentiment results returned
     */
    public SentimentOptions(Boolean explain) {
        this.explain = explain;
    }

    /**
     * get whether to return explanation strings for the sentiment results returned
     * @return whether to return explanation strings for the sentiment results returned
     */
    public Boolean getExplain() {
        return explain;
    }

    /**
     * set whether to return explanation strings for the sentiment results returned
     * @param explain whether to return explanation strings for the sentiment results returned
     */
    public void setExplain(Boolean explain) {
        this.explain = explain;
    }

    @Override
    public int hashCode() {
        return explain != null ? explain.hashCode() : 0;
    }

    /**
     * if the param is a {@code SentimentOptions}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SentimentOptions)) {
            return false;
        }

        SentimentOptions that = (SentimentOptions) o;
        return explain != null ? explain.equals(that.getExplain()) : that.explain == null;
    }
}
