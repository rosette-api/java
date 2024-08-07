/*
* Copyright 2022 Basis Technology Corp.
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
import lombok.Builder;
import lombok.Value;

import jakarta.validation.constraints.NotNull;

/**
 * Class that represents a name.
 */
@Value
@Builder
public class Name {

    /**
     * @return text of the name
     */
    @NotNull
    String text;

    /**
     * @return entity type of the name
     */
    String entityType;

    /**
     * @return script of the name, {@link ISO15924}
     */
    ISO15924 script;

    /**
     * @return language of the name, {@link LanguageCode}
     */
    LanguageCode language;


    /**
     * @return gender of the name, {@link Gender}
     */
    Gender gender;


    /**
     * Default constructor for lombok (gender is optional)
     *
     * @param name
     * @param entityType
     * @param script
     * @param language
     */
    public Name(String name, String entityType, ISO15924 script, LanguageCode language) {
        this.text = name;
        this.entityType = entityType;
        this.script = script;
        this.language = language;
        this.gender = null;
    }

    /**
     * Default constructor for lombok with gender
     *
     * @param name
     * @param entityType
     * @param script
     * @param language
     * @param gender
     */
    public Name(String name, String entityType, ISO15924 script, LanguageCode language, Gender gender) {
        this.text = name;
        this.entityType = entityType;
        this.script = script;
        this.language = language;
        this.gender = gender;
    }

    /**
     * Constructor for {@code Name} with default entityType PERSON, unknown script and unknown language
     * This allows Jackson to use the single arg constructor to deserialize a short-hand value
     * like {"name": "Mike"} instead of the full fledged {"name": {"text": "Mike"}}
     * @param name a name
     */
    public Name(String name) {
        this.text = name;
        this.entityType = "PERSON";
        this.script = ISO15924.Zyyy;
        this.language = LanguageCode.UNKNOWN;
        this.gender = null;
    }
}
