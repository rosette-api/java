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

import java.util.List;

/**
 * Sentiment Analysis and Categorization return labels.
 * For Sentiment, these are items like 'positive' or 'negative';
 * for Categorization, 'sports' or 'news'.
 */
public final class Label {

    private final String label;
    private final Double confidence;
    private final List<String> explanations;
    
    /**
     * Constructor for {@code Category}
     * @param label label for contextual category
     * @param confidence confidence score (0.0-1.0)
     * @param explanations list of input text elements
     */
    public Label(String label, Double confidence, List<String> explanations) {
        this.label = label;
        this.confidence = confidence;
        this.explanations = explanations;
    }

    /**
     * @return the label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return the confidence score  (0.0-1.0)
     */
    public Double getConfidence() {
        return confidence;
    }

    /**
     * get the list of explanation strings contributing to this label.
     * @return the list of strings.
     */
    public List<String> getExplanations() {
        return explanations;
    }

    @Override
    public int hashCode() {
        int result;
        result = label != null ? label.hashCode() : 0;
        result = 31 * result + (confidence != null ? confidence.hashCode() : 0);
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
        if (!(o instanceof Label)) {
            return false;
        }

        Label that = (Label) o;
        return label != null ? label.equals(that.label) : that.label == null
                && confidence.equals(that.confidence)
                && explanations != null ? explanations.equals(that.getExplanations()) : that.explanations == null;
    }
}
