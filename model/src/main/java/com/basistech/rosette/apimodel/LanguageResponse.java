/*
* Copyright 2017 Basis Technology Corp.
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

import java.util.ArrayList;
import java.util.List;

/**
 * Simple api response data model for Rli 
 */
public final class LanguageResponse extends Response {

    private final List<LanguageDetectionResult> languageDetections;
    private final List<RegionDetectionResult> regionalDetections;

    /**
     * constructor for {@code LanguageResponse}
     * @param languageDetections list of detected whole-document languages
     */
    public LanguageResponse(List<LanguageDetectionResult> languageDetections) {
        this(languageDetections, new ArrayList<RegionDetectionResult>());
    }

    /**
     * constructor for {@code LanguageResponse}
     * @param languageDetections list of detected whole-document languages
     * @param regionalDetections list of detected language regions
     */
    public LanguageResponse(
            List<LanguageDetectionResult> languageDetections,
            List<RegionDetectionResult> regionalDetections
    ) {
        this.languageDetections = languageDetections;
        this.regionalDetections = regionalDetections;
    }

    /**
     * get the list of detected whole-document languages
     * @return the list of detected whole-document languages
     */
    public List<LanguageDetectionResult> getLanguageDetections() {
        return languageDetections;
    }

    /**
     * get the list of detected language regions
     * @return the list of detected language regions
     */
    public List<RegionDetectionResult> getRegionalDetections() {
        return regionalDetections;
    }

    @Override
    public int hashCode() {
        int result;
        result = languageDetections != null ? languageDetections.hashCode() : 0;
        result = 31 * result + (regionalDetections != null ? regionalDetections.hashCode() : 0);
        return result;
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
        return languageDetections != null ? languageDetections.equals(that.getLanguageDetections()) : that.languageDetections == null
                && regionalDetections != null ? regionalDetections.equals(that.getRegionalDetections()) : that.regionalDetections == null;
    }
}
