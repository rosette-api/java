/*
 * Copyright 2018 Basis Technology Corp.
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
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.jackson.Jacksonized;

/**
 * Supported language/script
 */
@Value
@Builder
@Jacksonized
@JacksonMixin
public class SupportedLanguage {
    /**
     * @return the language code
     */
    private final LanguageCode language;

    /**
     * @return the script code
     */
    private final ISO15924 script;

    /**
     * @return if this language is licensed or not
     */
    @NonFinal
    @Getter
    @Setter
    private Boolean licensed;
}
