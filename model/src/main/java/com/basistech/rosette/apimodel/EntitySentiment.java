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

/**
 * Per-entity sentiment info.
 */
@Deprecated
@Value
@Builder
public class EntitySentiment {

    /**
     * @return the entity type
     */
    private final String type;

    /**
     * @return the entity mention text
     */
    private final String mention;

    /**
     * @return the normalized entity mention text
     */
    private final String normalized;

    /**
     * @return the entity mention count
     */
    private final Integer count;

    /**
     * @return the list of offsets for all mentions
     */
    private final List<MentionOffsets> mentionOffsets;

    /**
     * @return the ID of this entity. If this entity was linked to a knowledge base,
     * the resulting string will begin with 'Q'. If it was not linked to a knowledge base,
     * it will begin with a 'T'. 'T' identifiers represent intra-document co-references.
     */
    private final String entityId;

    /**
     * @return the confidence score for the entity (0.0-1.0)
     */
    private final Double confidence;

    /**
     * @return the confidence score for linking the entity to the knowledge base (0.0-1.0)
     */
    private final Double linkingConfidence;

    /**
     * @return the salience score for the entity (0.0|1.0)
     */
    private final Double salience;

    /**
     * @return the sentiment information.
     */
    private final Label sentiment;

}
