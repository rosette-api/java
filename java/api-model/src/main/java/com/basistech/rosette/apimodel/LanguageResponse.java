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
 * Simple api response data model for Rli 
 */
public final class LanguageResponse extends Response {

    private final List<LanguageDetectionResult> languageDetections;

    /**
     * constructor for {@code LanguageResponse}
     * @param requestId request id
     * @param languageDetections list of detected languages
     */
    public LanguageResponse(String requestId,
                            List<LanguageDetectionResult> languageDetections) {
        super(requestId);
        this.languageDetections = languageDetections;
    }

    /**
     * get the list of detected languages
     * @return the list of detected languages
     */
    public List<LanguageDetectionResult> getLanguageDetections() {
        return languageDetections;
    }

    @Override
    public int hashCode() {
        return languageDetections != null ? languageDetections.hashCode() : 0;
    }

    /**
     * if the param is a {@code CategoriesResponse}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LanguageResponse)) {
            return false;
        }

        LanguageResponse that = (LanguageResponse) o;
        return super.equals(o)
                && languageDetections != null ? languageDetections.equals(that.getLanguageDetections()) : that.languageDetections == null;
    }
}
