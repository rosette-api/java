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
 * Language detection result
 */
public class LanguageDetectionResult {
    private final String language;
    private final double confidence;
    
    /**
     * Constructor for {@code LanguageDetectionResult}
     * @param language detected language
     * @param confidence detection confidence
     */
    public LanguageDetectionResult(
            String language,
            double confidence
    ) {
        this.language = language;
        this.confidence = confidence;
    }

    /**
     * get the detected language 
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * get the detection confidence
     * @return the confidence
     */
    public double getConfidence() {
        return confidence;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = language != null ? language.hashCode() : 0;
        temp = Double.doubleToLongBits(confidence);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * if the param is a {@code LanguageDetectionResult}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof LanguageDetectionResult) {
            LanguageDetectionResult that = (LanguageDetectionResult) o;
            return language != null ? language.equals(that.getLanguage()) : that.language == null
                    && confidence == that.getConfidence();
        } else {
            return false;
        }
    }
}
