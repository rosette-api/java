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

import java.util.Map;

import lombok.Builder;
import lombok.Value;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Request object for name-similarity.
 *
 * This class carries the two names to compare.
 */
@Value
public class NameSimilarityRequest extends Request {

    /**
     * @return first name
     */
    @NotNull
    @Valid
    Name name1;

    /**
     * @return second name
     */
    @NotNull
    @Valid
    Name name2;

    /**
     * @return parameters to use
     */
    Map<String, String> parameters;

    @Builder     // workaround for inheritance https://github.com/rzwitserloot/lombok/issues/853
    public NameSimilarityRequest(String profileId,
                                 Name name1,
                                 Name name2,
                                 Map<String, String> parameters) {
        super(profileId);
        this.name1 = name1;
        this.name2 = name2;
        this.parameters = parameters;
    }
}
