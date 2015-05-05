package com.basistech.rosette.model;

/******************************************************************************
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2014 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
 ******************************************************************************/

import com.basistech.rosette.api.RosetteAPIParameterException;

/**
 * Class that represents the data from a RaaS client name translation request
 */
public class NameTranslationRequest {

    private String name;
    private String entityType;
    private ISO15924 sourceScript;
    private String sourceLanguageOfOrigin;
    private String sourceLanguageOfUse;
    private String targetLanguage;
    private ISO15924 targetScript;
    private TransliterationScheme targetScheme;

    public NameTranslationRequest(String name,
                                  String entityType,
                                  ISO15924 sourceScript,
                                  String sourceLanguageOfOrigin,
                                  String sourceLanguageOfUse,
                                  String targetLanguage,
                                  ISO15924 targetScript,
                                  TransliterationScheme targetScheme) throws RosetteAPIParameterException {
        this.name = name;
        this.entityType = entityType;
        this.sourceScript = sourceScript;
        this.sourceLanguageOfOrigin = sourceLanguageOfOrigin;
        this.sourceLanguageOfUse = sourceLanguageOfUse;
        this.targetLanguage = targetLanguage;
        this.targetScript = targetScript;
        this.targetScheme = targetScheme;
        if (this.name == null || "".equals(this.name)) {
            throw new RosetteAPIParameterException(new ErrorResponse(null, "missingParameter", "Required parameter \"name\" not supplied"));
        }
        if (this.targetLanguage == null || "".equals(this.targetLanguage)) {
            throw new RosetteAPIParameterException(new ErrorResponse(null, "missingParameter", "Required parameter \"targetLanguage\" not supplied"));
        }
    }

    public String getName() {
        return name;
    }

    public String getEntityType() {
        return entityType;
    }

    public ISO15924 getSourceScript() {
        return sourceScript;
    }

    public String getSourceLanguageOfOrigin() {
        return sourceLanguageOfOrigin;
    }

    public String getSourceLanguageOfUse() {
        return sourceLanguageOfUse;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public ISO15924 getTargetScript() {
        return targetScript;
    }

    public TransliterationScheme getTargetScheme() {
        return targetScheme;
    }
}
