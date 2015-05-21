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
     * get the list of text elements contributing to identification of this category
     * @return the list of text elements
     */
    public List<String> getExplanations() {
        return explanations;
    }

    /**
     * set the label for contextual category 
     * @param label the label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * set the confidence (0-1) 
     * @param confidence the confidence
     */
    public void setConfidence(double confidence) {
        if (confidence >= 0 && confidence <= 1) {
            this.confidence = confidence;
        } else {
            throw new IllegalArgumentException("confidence 0-1");
        }
    }

    /**
     * set the list of text elements contributing to identification of this category
     * @param explanations the list of text elements
     */
    public void setExplanations(List<String> explanations) {
        this.explanations = explanations;
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
     * if the param is a {@code Category}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Category) {
            Category that = (Category) o;
            return label != null ? label.equals(that.label) : that.label == null
                    && confidence == that.confidence
                    && explanations != null ? explanations.equals(that.getExplanations()) : that.explanations == null;
        } else {
            return false;
        }
    }
}
