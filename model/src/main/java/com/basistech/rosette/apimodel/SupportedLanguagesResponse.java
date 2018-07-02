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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

/**
 * Supported langauges/scripts for a given endpoint
 */
@Getter
@EqualsAndHashCode
@Builder
@JacksonMixin
public class SupportedLanguagesResponse extends Response {
    /**
     * @return the set of supported languages/scripts
     */
    private final Set<SupportedLanguage> supportedLanguages;
}
