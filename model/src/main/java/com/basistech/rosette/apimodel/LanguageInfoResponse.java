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

import com.basistech.util.ISO15924;
import com.basistech.util.LanguageCode;

import java.util.Map;
import java.util.Set;

/**
 *  API response data model for language identification support info
 */
public final class LanguageInfoResponse extends Response {

    private final Map<LanguageCode, Set<ISO15924>> supportedLanguages;
    private final Map<ISO15924, Set<LanguageCode>> supportedScripts;
    
    /**
     * constructor for {@code LanguageInfoResponse}
     * @param requestId request id
     * @param supportedLanguages list of supported languages
     * @param supportedScripts list of supported scripts
     */
    public LanguageInfoResponse(String requestId,
                                Map<LanguageCode, Set<ISO15924>> supportedLanguages,
                                Map<ISO15924, Set<LanguageCode>> supportedScripts) {
        super(requestId);
        this.supportedLanguages = supportedLanguages;
        this.supportedScripts = supportedScripts;
    }

    /**
     * @return a map. The keys are the supported languages, and the value
     * for each key is the scripts that are supported for that language.
     */
    public Map<LanguageCode, Set<ISO15924>> getSupportedLanguages() {
        return supportedLanguages;
    }

    /**
     * @return a map. The keys are the supported scripts, and the value
     * for each key is the languages that are supported for that script.
     */
    public Map<ISO15924, Set<LanguageCode>> getSupportedScripts() {
        return supportedScripts;
    }

    @Override
    public int hashCode() {
        int result = supportedLanguages != null ? supportedLanguages.hashCode() : 0;
        result = 31 * result + (supportedScripts != null ? supportedScripts.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code LanguageInfoResponse}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LanguageInfoResponse)) {
            return false;
        }

        LanguageInfoResponse that = (LanguageInfoResponse) o;
        return super.equals(o)
                && supportedLanguages != null ? supportedLanguages.equals(that.getSupportedLanguages()) : that.supportedLanguages == null
                && supportedScripts != null ? supportedScripts.equals(that.getSupportedScripts()) : that.supportedScripts == null;
    }
}
