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

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (entityType != null ? entityType.hashCode() : 0);
        result = 31 * result + (script != null ? script.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code Name}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Name) {
            Name that = (Name) o;
            return text != null ? text.equals(that.getText()) : that.text == null
                    && entityType != null ? entityType.equals(that.getEntityType()) : that.entityType == null
                    && script != null ? script.equals(that.getScript()) : that.script == null
                    && language != null ? language.equals(that.getScript()) : that.language == null;
        } else {
            return false;
        }
    }
}
