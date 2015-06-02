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

import java.util.Map;
import java.util.Set;

/**
 *  API response data model for RLI support info
 */
public final class LanguageInfoResponse extends Response {
    private final Map<String, Set<String>> supportedLanguages;
    private final Map<String, Set<String>> supportedScripts;
    
    /**
     * constructor for {@code LanguageInfoResponse}
     * @param requestId request id
     * @param supportedLanguages list of supported languages
     * @param supportedScripts list of supported scripts
     */
    public LanguageInfoResponse(String requestId,
                                Map<String, Set<String>> supportedLanguages,
                                Map<String, Set<String>> supportedScripts) {
        super(requestId);
        this.supportedLanguages = supportedLanguages;
        this.supportedScripts = supportedScripts;
    }

    /**
     * get the list of supported languages 
     * @return the list of supported languages
     */
    public Map<String, Set<String>> getSupportedLanguages() {
        return supportedLanguages;
    }

    /**
     * get the list of supported scripts
     * @return the list of supported scripts
     */
    public Map<String, Set<String>> getSupportedScripts() {
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
        if (o instanceof LanguageInfoResponse) {
            LanguageInfoResponse that = (LanguageInfoResponse) o;
            return super.equals(o)
                    && supportedLanguages != null ? supportedLanguages.equals(that.getSupportedLanguages()) : that.supportedLanguages == null
                    && supportedScripts != null ? supportedScripts.equals(that.getSupportedScripts()) : that.supportedScripts == null;
        } else {
            return false;
        }
    }
}
