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
     * @return the modelType to use.
     */
    private final String modelType;
}
