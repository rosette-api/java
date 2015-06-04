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
 * model for categorization
 */
public final class Category {

    private final String label;
    private final double confidence;
    private final List<String> explanations;
    
    /**
     * Constructor for {@code Category}
     * @param label label for contextual category
     * @param confidence confidence score (0.0-1.0)
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
     * get the confidence score (0.0-1.0)
     * @return the confidence score
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
        if (!(o instanceof Category)) {
            return false;
        }

        Category that = (Category) o;
        return label != null ? label.equals(that.label) : that.label == null
                && confidence == that.confidence
                && explanations != null ? explanations.equals(that.getExplanations()) : that.explanations == null;
    }
}
