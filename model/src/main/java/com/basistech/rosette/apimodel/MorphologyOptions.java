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

import com.basistech.util.PartOfSpeechTagSet;
import lombok.Builder;
import lombok.Value;

/**
 * Morphology options
 */
@Value
@Builder
public class MorphologyOptions extends Options {

    /**
     * @return whether the linguistics analysis should disambiguate results
     */
    private final Boolean disambiguate;

    /**
     * DocumentRequest query processing. Linguistics analysis may change its behavior
     * to reflect the fact that query input is often not in full sentences;
     * Typically, this disables disambiguation.
     * @return request query processing
     *         Linguistics analysis may change its behavior to reflect the fact that query input is often
     *         not in full sentences; Typically, this disables disambiguation.
     */
    private final Boolean query;

    /**
     * @return the tag set used when returning part of speech tags. A {@code null} value
     * indicates the default.
     */
    private final PartOfSpeechTagSet partOfSpeechTagSet;
}
