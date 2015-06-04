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
 * Sentiment analyzer sentiment
 */
public final class Sentiment {

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
        if (!(o instanceof Sentiment)) {
            return false;
        }

        Sentiment that = (Sentiment) o;
        return label != null ? label.equals(that.getLabel()) : that.label == null
                && confidence == that.getConfidence()
                && explanations != null ? explanations.equals(that.getExplanations()) : that.explanations == null;
    }
}
