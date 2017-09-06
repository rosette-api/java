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

import java.util.List;

/**
 * Language region detection result
 */
public final class RegionDetectionResult {

    private final String region;
    private final List<LanguageDetectionResult> languages;

    /**
     * Constructor for {@code LanguageRegionResult}
     * @param region the text of the language region
     * @param languages list of languages detected for this region
     */
    public RegionDetectionResult(
            String region,
            List<LanguageDetectionResult> languages
    ) {
        this.region = region;
        this.languages = languages;
    }

    /**
     * get the region text
     * @return the confidence
     */
    public String getRegion() {
        return region;
    }

    /**
     * get the detected languages
     * @return the language
     */
    public List<LanguageDetectionResult> getLanguages() {
        return languages;
    }

    @Override
    public int hashCode() {
        int result;
        result = region != null ? region.hashCode() : 0;
        result = 31 * result + (languages != null ? languages.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code LanguageRegionResult}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RegionDetectionResult)) {
            return false;
        }

        RegionDetectionResult that = (RegionDetectionResult) o;
        return region != null ? region.equals(that.getRegion()) : that.region == null
                && languages != null ? languages.equals(that.getLanguages()) : that.languages == null;
    }
}
