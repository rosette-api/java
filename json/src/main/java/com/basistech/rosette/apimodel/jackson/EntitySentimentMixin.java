/*
* Copyright 2016 Basis Technology Corp.
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

package com.basistech.rosette.apimodel.jackson;

import com.basistech.rosette.apimodel.Label;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class EntitySentimentMixin extends BaseMixin {
    @JsonCreator
    public EntitySentimentMixin(
            @JsonProperty("type") String type,
            @JsonProperty("mention") String mention,
            @JsonProperty("normalized") String normalized,
            @JsonProperty("count") Integer count,
            @JsonProperty("entityId") String entityId,
            @JsonProperty("confidence") Double confidence,
            @JsonProperty("salience") Double salience,
            @JsonProperty("linkingConfidence") Double linkingConfidence,
            @JsonProperty("sentiment") Label sentiment) {
        //
    }
}
