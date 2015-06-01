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
 * Sentiment analyzer sentiment
 */
public class Sentiment {

    private final String label;
    private final double confidence;
    private final List<String> explanations;

    /**
     * constructor for {@code Sentiment}
     * @param label sentiment label
     * @param confidence confidence
     * @param explanations list of explanations
     */
    public Sentiment(
            String label,
            double confidence,
            List<String> explanations) {
        this.label = label;
        this.confidence = confidence;
        this.explanations = explanations;
    }

    /**
     * get the sentiment label 
     * @return the sentiment label
     */
    public String getLabel() {
        return label;
    }

    /**
     * get the confidence 
     * @return the confidence
     */
    public double getConfidence() {
        return confidence;
    }

    /**
     * get the list of explanations contributing to determination of sentiment
     * @return the list of explanations
     */
    public List<String> getExplanations() {
        return explanations;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = label != null ? label.hashCode() : 0;
        temp = Double.doubleToLongBits(confidence);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (explanations != null ? explanations.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code Sentiment}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Sentiment) {
            Sentiment that = (Sentiment) o;
            return label != null ? label.equals(that.getLabel()) : label == that.getLabel()
                    && confidence == that.getConfidence()
                    && explanations != null ? explanations.equals(that.getExplanations()) : explanations == that.getExplanations();
        } else {
            return false;
        }
    }
}
