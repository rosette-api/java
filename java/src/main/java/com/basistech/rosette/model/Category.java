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
 * model for categorization
 */
public class Category {
    private String label;
    private double confidence;
    private List<String> explanations;
    
    public Category() { }

    /**
     * Constructor for {@code Category}
     * @param label label for contextual category
     * @param confidence confidence (0-1)
     * @param explanations list of input text elements
     */
    public Category(String label, double confidence, List<String> explanations) {
        this.label = label;
        this.confidence = confidence;
        this.explanations = explanations;
    }

    /**
     * get the label for contextual category
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * get the confidence (0-1)
     * @return the confidence
     */
    public double getConfidence() {
        return confidence;
    }

    /**
     * get the list of input text elements contributing to identification of this category
     * @return the list of text elements
     */
    public List<String> getExplanations() {
        return explanations;
    }
}
