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

import java.util.List;

/**
 *  Simple api response data model for sentiment analysis
 **/
public final class SentimentResponse extends Response {

    private final List<Sentiment> sentiment;
    
    /**
     * constructor for {@code SentimentResponse}
     * @param requestId request id
     * @param sentiment list of sentiment analysis results
     */
    public SentimentResponse(String requestId,
                             List<Sentiment> sentiment) {
        super(requestId);
        this.sentiment = sentiment;
    }

    /**
     * get the list of sentiment analysis results
     * @return the list of sentiment analysis results
     */
    public List<Sentiment> getSentiment() {
        return sentiment;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sentiment != null ? sentiment.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code SentimentResponse}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SentimentResponse)) {
            return false;
        }

        SentimentResponse that = (SentimentResponse) o;
        return super.equals(o)
                && sentiment != null ? sentiment.equals(that.getSentiment()) : that.sentiment == null;
    }
}
