/******************************************************************************
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2015 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
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
        if (o instanceof SentimentResponse) {
            SentimentResponse that = (SentimentResponse) o;
            return super.equals(o)
                    && sentiment != null ? sentiment.equals(that.getSentiment()) : that.sentiment == null;
        } else {
            return false;
        }
    }
}
