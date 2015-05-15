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

package com.basistech.rosette.model;

import java.util.List;

/**
 * Sentiment analyzer sentiment
 */
public class Sentiment {

    private String label;
    private double confidence;
    private List<String> explanations;

    public Sentiment() { }

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
}
