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
 * Class that represents the data from a RaaS sentiment analysis request
 */
public class SentimentRequest extends Request {
    private final SentimentOptions options;
    
    /**
     * constructor for {@code SentimentRequest}
     * @param language language code
     * @param content raw data
     * @param contentUri uri pointing to the data
     * @param contentType byte array of data
     * @param unit input unit code
     * @param options sentiment options
     */
    public SentimentRequest(
            String language,
            String content,
            String contentUri,
            String contentType,
            InputUnit unit,
            SentimentOptions options
    ) {
        super(language, content, contentUri, contentType, unit);
        this.options = options;
    }

    /**
     * get the sentiment options
     * @return the sentiment options
     */
    public SentimentOptions getOptions() {
        return options;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code SentimentRequest}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof SentimentRequest) {
            SentimentRequest that = (SentimentRequest) o;
            return super.equals(o)
                    && getOptions().equals(that.getOptions());
        } else {
            return false;
        }
    }
}
