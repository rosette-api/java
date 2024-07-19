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

import jakarta.validation.Valid;
import java.util.List;

/**
 * Extracted entity
 */
@Value
@Builder
@JacksonMixin
public class Entity {

    /**
     * @return the entity type
     */
    String type;

    /**
     * @return the entity mention text
     */
    String mention;

    /**
     * @return the normalized entity mention text
     */
    String normalized;

    /**
     * @return the entity mention count
     */
    Integer count;

    /**
     * @return the list of offsets for all mentions
     */
    @Valid
    List<MentionOffsets> mentionOffsets;

    /**
     * @return the ID of this entity. If this entity was linked to a knowledge base,
     * the resulting string will begin with 'Q'. If it was not linked to a knowledge base,
     * it will begin with a 'T'. 'T' identifiers represent intra-document co-references.
     */
    String entityId;

    /**
     * @return the confidence score for the entity (0.0-1.0)
     */
    Double confidence;

    /**
     * @return the confidence score for linking the entity to the knowledge base (0.0-1.0)
     */
    Double linkingConfidence;

    /**
     * @return the salience score for the entity (0.0|1.0)
     */
    Double salience;

    /**
     * @return the sentiment information.
     */
    Label sentiment;

    /**
     * @since 1.14.0 (19.08)
     * @return the DBpediaTypes
     */
    List<String> dbpediaTypes;

    /**
     * @return the PermID
     */
    String permId;
}
