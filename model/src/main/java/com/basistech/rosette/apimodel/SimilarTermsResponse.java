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
import com.basistech.util.LanguageCode;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * simple api response format for similar terms
 */
@Getter
@EqualsAndHashCode
@Builder
@JacksonMixin
public class SimilarTermsResponse extends Response {

    /**
     * @return a mapping of language codes to similar terms
     */
    private final Map<LanguageCode, List<SimilarTerm>> similarTerms;
}
