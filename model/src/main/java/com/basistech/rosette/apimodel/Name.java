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

import com.basistech.rosette.annotations.JacksonMixin;
import com.basistech.util.ISO15924;
import com.basistech.util.LanguageCode;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * Class that represents a name.
 */
@Value
@Builder
@JacksonMixin
public class Name {

    /**
     * @return text of the name
     */
    @NotNull
    private final String text;

    /**
     * @return entity type of the name
     */
    private final String entityType;

    /**
     * @return script of the name, {@link ISO15924}
     */
    private final ISO15924 script;

    /**
     * @return language of the name, {@link LanguageCode}
     */
    private final LanguageCode language;
}
