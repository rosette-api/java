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
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

/**
 * Simple api response data model for entity extraction
 */
@Getter
@EqualsAndHashCode
@Builder
public class TopicsResponse extends Response {

    /**
     * @return the list of extracted keyphrases
     */
    private final List<Keyphrase> keyphrases;

    /**
     * @return the list of extracted concepts
     */
    private final List<Concept> concepts;
}
