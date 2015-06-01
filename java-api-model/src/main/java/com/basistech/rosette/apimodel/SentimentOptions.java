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

/**
 * Sentiment options
 */
public final class SentimentOptions {
    private final String model;
    private final Boolean explain;

    /**
     * constructor for {@code SentimentOptions}
     * @param model   model to use for sentiment analysis
     * @param explain whether to return explanation strings for the sentiment results returned
     */
    public SentimentOptions(
            String model,
            Boolean explain) {
        this.model = model;
        this.explain = explain;
    }

    /**
     * get the model to use for sentiment analysis
     * @return the model to use for sentiment analysis
     */
    public String getModel() {
        return model;
    }

    /**
     * get whether to return explanation strings for the sentiment results returned
     * @return whether to return explanation strings for the sentiment results returned
     */
    public Boolean getExplain() {
        return explain;
    }

    @Override
    public int hashCode() {
        int result = model != null ? model.hashCode() : 0;
        result = 31 * result + (explain != null ? explain.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code SentimentOptions}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof SentimentOptions) {
            SentimentOptions that = (SentimentOptions) o;
            return model != null ? model.equals(that.getModel()) : model == that.getModel()
                    && explain != null ? explain.equals(that.getExplain()) : explain == that.getExplain();
        } else {
            return false;
        }
    }
}
