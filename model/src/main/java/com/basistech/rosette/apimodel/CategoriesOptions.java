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
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Value;

/**
 * Categorization options 
 */
@Value
@Builder
@JacksonMixin
public class CategoriesOptions extends Options {

    /**
     * @deprecated
     * Users should use {@link singleLabel} to return only one result
     * or {@link scoreThreshold} to filter results based on raw score
     * @return number of categories
     */
    @Deprecated
    @Min(1)
    private final Integer numCategories;

    /**
     * Single label mode will return only the highest scoring category, regardless of score
     * If singleLabel is false, every category whose score exceeds the default
     * (or specified) {@link scoreThreshold} value will be returned
     * @return whether or not we are in single label mode
     */
    private final Boolean singleLabel;

    /**
     * threshold against the category's raw score, whose value
     * can be any real number.
     * @return the score threshold
     */
    private final Float scoreThreshold;
}
