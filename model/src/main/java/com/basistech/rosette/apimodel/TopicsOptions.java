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

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

/**
 * Options for topics requests
 */
@Value
@Builder
public class TopicsOptions extends Options {

    /**
     * @return concept salience threshold (0.0-1.0)
     */
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private final Double conceptSalienceThreshold;

    /**
     * @return key phrase salience threshold (0.0-1.0)
     */
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private final Double keyphraseSalienceThreshold;
}
