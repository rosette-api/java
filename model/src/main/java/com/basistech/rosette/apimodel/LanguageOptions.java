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

import com.basistech.rosette.annotations.JacksonMixin;
import com.basistech.rosette.util.EncodingCode;
import com.basistech.util.LanguageCode;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.util.Set;

/**
 * Languages detection options 
 */
@Value
@Builder
@JacksonMixin
public class LanguageOptions extends Options {

    /**
     * @return whether to detect language regions
     */
    Boolean multilingual;

    /**
     * @return minimum number of valid characters
     */
    @Min(1)
    Integer minValidChars;

    /**
     * @return the most frequent n-grams to consider in detection, smaller depth results in faster operation but
     * lower detection accuracy
     */
    @Min(1)
    Integer profileDepth;

    /**
     * @return number of n-gram distance ambiguity threshold (0.0-100.0), input profile distances closer to each
     * other than this threshold are deemed ambiguous
     */
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    Double ambiguityThreshold;

    /**
     * @return number of n-gram distance invalidity threshold (0.0-100.0), input profile distance exceeding this
     * threshold will be deemed invalid
     */
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    Double invalidityThreshold;

    /**
     * @return language hint
     */
    LanguageCode languageHint;

    /**
     * @return the language hint weight (1.0-99.0) used to help resolve ambiguous results.
     *         A value of N reduces the distance of correctly hinted ambiguous result by N%.
     *         Value of 1.0 is the lightest hint, value of 99.0 the strongest.
     */
    @DecimalMin("1.0")
    @DecimalMax("99.0")
    Double languageHintWeight;

    /**
     * @return the encoding hint used to help resolve ambiguous results
     */
    EncodingCode encodingHint;

    /**
     * @return the encoding hint weight (1.0-100.0) used to help resolve ambiguous results.
     *         A value of N reduces the distance of correctly hinted result by N%.
     *         A value of 100 forces the detector to consider only the results with the hinted encoding.
     */
    @DecimalMin("1.0")
    @DecimalMax("100.0")
    Double encodingHintWeight;

    /**
     * @return languageWeightAdjustments the set of language weight adjustments
     */
    @Valid
    Set<LanguageWeight> languageWeightAdjustments;

    /**
     * @return whether to distinguish between North Korean and South Korean
     */
    Boolean koreanDialects;

}
