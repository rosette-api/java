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

/**
 * Language detection result
 */
public final class LanguageDetectionResult {

    private final LanguageCode language;
    private final double confidence;
    
    /**
     * Constructor for {@code LanguageDetectionResult}
     * @param language detected language
     * @param confidence detection confidence
     */
    public LanguageDetectionResult(
            LanguageCode language,
            double confidence
    ) {
        this.language = language;
        this.confidence = confidence;
    }

    /**
     * get the detected language 
     * @return the language
     */
    public LanguageCode getLanguage() {
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
        if (!(o instanceof LanguageDetectionResult)) {
            return false;
        }

        LanguageDetectionResult that = (LanguageDetectionResult) o;
        return language != null ? language.equals(that.getLanguage()) : that.language == null
                && confidence == that.getConfidence();
    }
}
