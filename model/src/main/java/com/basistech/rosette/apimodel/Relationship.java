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

import java.util.List;
import java.util.Set;

/**
 * Relationship extracted by the relationship extractor
 */
@Value
@Builder
public class Relationship {

    /**
     * @return the relationship predicate
     */
    private final String predicate;

    /**
     * @return the relationship predicate'sID
     */
    private final String predicateId;

    /**
     * @return the first arg
     */
    private final String arg1;

    /**
     * @return the first arg's ID
     */
    private final String arg1Id;

    /**
     * @return the second arg
     */
    private final String arg2;

    /**
     * @return the second arg's ID
     */
    private final String arg2Id;

    /**
     * @return the third arg
     */
    private final String arg3;

    /**
     * @return the third arg's ID
     */
    private final String arg3Id;

    /**
     * @return a list of adjuncts
     */
    private final List<String> adjuncts;

    /**
     * @return a list of locatives
     */
    private final List<String> locatives;

    /**
     * @return a list of temporals
     */
    private final List<String> temporals;

    /**
     * @return modalities
     */
    private final Set<String> modalities;

    /**
     * @return the confidence (0.0-1.0)
     */
    private final Double confidence;
}
