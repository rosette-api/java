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
import lombok.Builder;
import lombok.Value;

/**
 * EntityMention extraction options
 */
@Value
@Builder
@JacksonMixin
public class EntitiesOptions extends Options {
    /**
     * Default options
     */
    public static final EntitiesOptions DEFAULT = EntitiesOptions.builder()
            .calculateConfidence(false)
            .calculateSalience(false)
            .linkEntities(true)
            .modelType("statistical")
            .enableStructuredRegion(false)
            .build();

    /**
     * @return the calculateConfidence flag.
     */
    Boolean calculateConfidence;

    /**
     * @return the calculateSalience flag.
     */
    Boolean calculateSalience;

    /**
     * @return the linkEntities flag.
     */
    Boolean linkEntities;

    /**
     * @return the linkMentionMode mode.
     */
    String linkMentionMode;

    /**
     * @return the modelType to use.
     */
    String modelType;

    /**
     * @since 1.14.0 (19.08)
     * @return the includeDBpediaType flag.
     */
    Boolean includeDBpediaTypes;

    /**
     * @return the includePermID flag.
     */
    Boolean includePermID;

    /**
     * @return the RTS workspace id.
     */
    String rtsDecoder;

    /**
     * @return case sensitivity of model to use. Can be one of caseSensitive, caseInsensitive or automatic.
     */
    String caseSensitivity;

    /**
     * @return the enableStructuredRegion flag.
     */
    Boolean enableStructuredRegion;

    /**
     * @return the structuredRegionProcessingType flag. Can be one of none, nerModel, nameClassifier
     */
    String structuredRegionProcessingType;

    /**
     * @return the regexCurrencySplit flag.
     * If enabled, will cause MONEY regular expression entity extractions to be split into two:
     * CURRENCY:AMT and CURRENCY:TYPE
     */
    Boolean regexCurrencySplit;

    /**
     * @return useIndocServer flag.  If true, REX will make request to indoc-coref-server and merge
     * results with existing entities.
     * default false
     */
    Boolean useIndocServer;
}
