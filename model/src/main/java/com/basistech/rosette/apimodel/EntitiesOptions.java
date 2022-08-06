/*
* Copyright 2017-2019 Basis Technology Corp.
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
    private final Boolean calculateConfidence;

    /**
     * @return the calculateSalience flag.
     */
    private final Boolean calculateSalience;

    /**
     * @return the linkEntities flag.
     */
    private final Boolean linkEntities;

    /**
     * @return the linkExistingEntities flag.
     */
    private final Boolean linkExistingEntities;

    /**
     * @return the modelType to use.
     */
    private final String modelType;

    /**
     * @deprecated use includeDBpediaTypes instead.
     * @return the includeDBpediaType flag.
     */
    private final Boolean includeDBpediaType;

    /**
     * @since 1.14.0 (19.08)
     * @return the includeDBpediaType flag.
     */
    private final Boolean includeDBpediaTypes;

    /**
     * @return the includePermID flag.
     */
    private final Boolean includePermID;

    /**
     * @return the RTS workspace id.
     */
    private final String rtsDecoder;

    /**
     * @return case sensitivity of model to use. Can be one of caseSensitive, caseInsensitive or automatic.
     */
    private final String caseSensitivity;

    /**
     * @return the enableStructuredRegion flag.
     */
    private final Boolean enableStructuredRegion;

    /**
     * @return the structuredRegionProcessingType flag. Can be one of none, nerModel, nameClassifier
     */
    private final String structuredRegionProcessingType;
}
