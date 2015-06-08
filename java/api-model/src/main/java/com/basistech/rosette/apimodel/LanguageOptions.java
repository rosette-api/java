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

import java.util.Set;

/**
 * Languages detection options 
 */
public final class LanguageOptions {

    private Integer minValidChars;
    private Integer profileDepth;
    private Double ambiguityThreshold;
    private Double invalidityThreshold;
    private LanguageCode languageHint;
    private Double languageHintWeight;
    private String encodingHint;
    private Double encodingHintWeight;
    private Set<LanguageWeight> languageWeightAdjustments;
    
    /**
     * constructor for {@code LanguageOptions} 
     * @param minValidChars minimum number of valid characters required for identification
     * @param profileDepth number of n-grams to consider in detection
     * @param ambiguityThreshold number of n-gram distance ambiguity threshold
     * @param invalidityThreshold number of n-gram distance invalidity threshold
     * @param languageHint language hint used to resolve ambiguous results
     * @param languageHintWeight language hint weight used to resolve ambiguous results
     * @param encodingHint encoding hint used to resolve ambiguous results
     * @param encodingHintWeight encoding hint weight used to resolve ambiguous results
     * @param languageWeightAdjustments language weight adjustment
     */
    public LanguageOptions(
            Integer minValidChars,
            Integer profileDepth,
            Double ambiguityThreshold,
            Double invalidityThreshold,
            LanguageCode languageHint,
            Double languageHintWeight,
            String encodingHint,
            Double encodingHintWeight,
            Set<LanguageWeight> languageWeightAdjustments
    ) {
        this.minValidChars = minValidChars;
        this.profileDepth = profileDepth;
        this.ambiguityThreshold = ambiguityThreshold;
        this.invalidityThreshold = invalidityThreshold;
        this.languageHint = languageHint;
        this.languageHintWeight = languageHintWeight;
        this.encodingHint = encodingHint;
        this.encodingHintWeight = encodingHintWeight;
        this.languageWeightAdjustments = languageWeightAdjustments;
    }

    /**
     * get minimum number of valid characters needed for identification 
     * @return minimum number of valid characters
     */
    public Integer getMinValidChars() {
        return minValidChars;
    }

    /**
     * get the most frequent n-grams to consider in detection.
     + Smaller depth results in faster operation but lower detection accuracy.
     * @return number of n-grams
     */
    public Integer getProfileDepth() {
        return profileDepth;
    }

    /**
     * get the n-gram distance ambiguity threshold, value range [0-100].
     + Input profile distances closer to each other than this threshold are deemed ambiguous.
     * @return number of n-gram distance ambiguity threshold
     */
    public Double getAmbiguityThreshold() {
        return ambiguityThreshold;
    }

    /**
     * get the n-gram distance invalidity threshold, value range [0-100]. 
     * Input profile distance exceeding this threshold will be deemed invalid .
     * @return number of n-gram distance invalidity threshold
     */
    public Double getInvalidityThreshold() {
        return invalidityThreshold;
    }

    /**
     * get the language hint 
     * @return language hint
     */
    public LanguageCode getLanguageHint() {
        return languageHint;
    }

    /**
     * get the language hint weight used to help resolve ambiguous results. 
     + A value of N reduces the distance of correctly hinted ambiguous result by N%.
     + Weight Value range [1-99]. Value of 1 is the lightest hint, value of 99 the strongest. 
     * @return the language hint weight
     */
    public Double getLanguageHintWeight() {
        return languageHintWeight;
    }

    /**
     * get encoding hint used to help resolve ambiguous results
     * @return the encoding hint
     */
    public String getEncodingHint() {
        return encodingHint;
    }

    /**
     * get the encoding hint weight used to help resolve ambiguous results, value range [1-100].
     + A value of N reduces the distance of correctly hinted result by N%.
     + A value of 1 is the lightest hint. 
     + A value of 100 forces the detector to consider only the results with the hinted encoding. 
     * @return the encoding hint weight
     */
    public Double getEncodingHintWeight() {
        return encodingHintWeight;
    }

    /**
     * get the set of language weight adjustments
     * @return the set of language weight adjustments
     */
    public Set<LanguageWeight> getLanguageWeightAdjustments() {
        return languageWeightAdjustments;
    }

    /**
     * set minimum number of valid characters needed for identification 
     * @param minValidChars minimum number of valid characters
     */
    public void setMinValidChars(Integer minValidChars) {
        this.minValidChars = minValidChars;
    }

    /**
     * set the most frequent n-grams to consider in detection.
     + Smaller depth results in faster operation but lower detection accuracy. 
     * @param profileDepth number of n-grams
     */
    public void setProfileDepth(Integer profileDepth) {
        this.profileDepth = profileDepth;
    }

    /**
     * set the n-gram distance ambiguity threshold, value range [0-100].
     + Input profile distances closer to each other than this threshold are deemed ambiguous.
     * @param ambiguityThreshold number of n-grams distance ambiguity threshold
     */
    public void setAmbiguityThreshold(Double ambiguityThreshold) {
        if (ambiguityThreshold >= 0 && ambiguityThreshold <= 100) {
            this.ambiguityThreshold = ambiguityThreshold;
        } else {
            throw new IllegalArgumentException("ambiguity threshold value range 0-100");
        }
    }

    /**
     * get the n-gram distance invalidity threshold, value range [0-100].
     * Input profile distance exceeding this threshold will be deemed invalid.
     * @param invalidityThreshold number of n-grams distance invalidity threshold
     */
    public void setInvalidityThreshold(Double invalidityThreshold) {
        if (invalidityThreshold >= 0 && invalidityThreshold <= 100) {
            this.invalidityThreshold = invalidityThreshold;
        } else {
            throw new IllegalArgumentException("invalidity threshold value range 0-100");
        }
    }

    /**
     * set the language hint 
     * @param languageHint the language hint
     */
    public void setLanguageHint(LanguageCode languageHint) {
        this.languageHint = languageHint;
    }

    /**
     * set the language hint weight used to help resolve ambiguous results. 
     + A value of N reduces the distance of correctly hinted ambiguous result by N%.
     + Weight Value range [1-99]. Value of 1 is the lightest hint, value of 99 the strongest. 
     * @param languageHintWeight
     */
    public void setLanguageHintWeight(Double languageHintWeight) {
        if (languageHintWeight >= 1 && languageHintWeight <= 99) {
            this.languageHintWeight = languageHintWeight;
        } else {
            throw new IllegalArgumentException("language hint weight value range 1-99");
        }
    }

    /**
     * set encoding hint used to help resolve ambiguous results
     * @param encodingHint the encoding hint
     */
    public void setEncodingHint(String encodingHint) {
        this.encodingHint = encodingHint;
    }

    /**
     * set the encoding hint weight used to help resolve ambiguous results, value range [1-100].
     + A value of N reduces the distance of correctly hinted result by N%.
     + A value of 1 is the lightest hint. 
     + A value of 100 forces the detector to consider only the results with the hinted encoding.
     * @param encodingHintWeight the encoding hint weight
     */
    public void setEncodingHintWeight(Double encodingHintWeight) {
        if (encodingHintWeight >= 1 && encodingHintWeight <= 100) {
            this.encodingHintWeight = encodingHintWeight;
        } else {
            throw new IllegalArgumentException("encoding hint weight range 1-100");
        }
    }

    /**
     * set the set of language weight adjustments 
     * @param languageWeightAdjustments the set of language weight adjustments
     */
    public void setLanguageWeightAdjustments(Set<LanguageWeight> languageWeightAdjustments) {
        this.languageWeightAdjustments = languageWeightAdjustments;
    }

    @Override
    public int hashCode() {
        int result = minValidChars != null ? minValidChars.hashCode() : 0;
        result = 31 * result + (profileDepth != null ? profileDepth.hashCode() : 0);
        result = 31 * result + (ambiguityThreshold != null ? ambiguityThreshold.hashCode() : 0);
        result = 31 * result + (invalidityThreshold != null ? invalidityThreshold.hashCode() : 0);
        result = 31 * result + (languageHint != null ? languageHint.hashCode() : 0);
        result = 31 * result + (languageHintWeight != null ? languageHintWeight.hashCode() : 0);
        result = 31 * result + (encodingHint != null ? encodingHint.hashCode() : 0);
        result = 31 * result + (encodingHintWeight != null ? encodingHintWeight.hashCode() : 0);
        result = 31 * result + (languageWeightAdjustments != null ? languageWeightAdjustments.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code LanguageOptions}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LanguageOptions)) {
            return false;
        }

        LanguageOptions that = (LanguageOptions) o;
        return minValidChars != null ? minValidChars.equals(that.getMinValidChars()) : that.minValidChars == null
                && profileDepth != null ? profileDepth.equals(that.getProfileDepth()) : that.profileDepth == null
                && ambiguityThreshold != null ? ambiguityThreshold.equals(that.getAmbiguityThreshold()) : that.ambiguityThreshold == null
                && invalidityThreshold != null ? invalidityThreshold.equals(that.getInvalidityThreshold()) : that.invalidityThreshold == null
                && languageHint != null ? languageHint.equals(that.getLanguageHint()) : that.languageHint == null
                && languageHintWeight != null ? languageHintWeight.equals(that.getLanguageHintWeight()) : that.languageHintWeight == null
                && encodingHint != null ? encodingHint.equals(that.getEncodingHint()) : that.encodingHint == null
                && encodingHintWeight != null ? encodingHintWeight.equals(that.getEncodingHintWeight()) : that.encodingHintWeight == null
                && languageWeightAdjustments != null ? languageWeightAdjustments.equals(that.getLanguageWeightAdjustments()) : that.languageWeightAdjustments == null;
    }
}
