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

package com.basistech.rosette.model;

import java.util.Map;
import java.util.Set;

/**
 *  API response data model for RLI support info
 */
public final class LanguageInfoResponse extends Response {
    private Map<String, Set<String>> supportedLanguages;
    private Map<String, Set<String>> supportedScripts;
    
    public LanguageInfoResponse() { super(); }

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
}
