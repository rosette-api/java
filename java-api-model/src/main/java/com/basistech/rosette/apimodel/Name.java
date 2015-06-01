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
 * Class that represents a name.
 */
public class Name {

    private final String text;
    private final String entityType;
    private final String script;
    private final String language;

    /**
     * Constructor for {@code Name}
     * @param name a name
     * @param entityType entity type of the name
     * @param script script of the name
     * @param language language of the name
     */
    public Name(String name,
                String entityType,
                String script,
                String language) {
        this.text = name;
        this.entityType = entityType;
        this.script = script;
        this.language = language;
    }

    /**
     * @param name a name
     */
    public Name(String name) {
        this.text = name;
        this.entityType = "PERSON";
        this.script = ISO15924.Zyyy;
        this.language = LanguageCode.UNKNOWN;
    }

    /**
     * Gets the name
     * @return Text of the name
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the entity type of the name
     * @return entity type of the name
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * Gets the script of the name, {@see ISO15924}
     * @return script of the name
     */
    public String getScript() {
        return script;
    }

    /**
     * Gets the language of the name, {@see LanguageCode}
     * @return language of the name
     */
    public String getLanguage() {
        return language;
    }
}
