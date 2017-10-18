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

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

/**
 * An entity mention found in a document.
 * The /entities endpoint returns a collection of entity mentions.
 */
@SuppressWarnings("PMD")
@Getter @EqualsAndHashCode
public class EntityMention {

    /**
     * in-document entity chain id
     * @return the id
     */
    @Deprecated
    private final Integer indocChainId;

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
    private final Double salience;

    /**
     * constructor for {@code EntityMention}
     * @param indocChainId in-document entity chain id
     * @param type entity type
     * @param mention mention text
     * @param normalized normalized mention text
     * @param count mention count
     * @param entityId if the entity was linked, the ID from the knowledge base.
     * @param confidence entity confidence
     */
    @Deprecated
    public EntityMention(
            Integer indocChainId,
            String type,
            String mention,
            String normalized,
            Integer count,
            String entityId,
            Double confidence
    ) {
        this(type, mention, normalized, count, null, entityId, confidence, null);
    }

    /**
     * constructor for {@code EntityMention}
     * @param type entity type
     * @param mention mention text
     * @param normalized normalized mention text
     * @param entityId if the entity was linked, the ID from the knowledge base.
     * @param count mention count
     * @param confidence entity confidence
     */
    @Deprecated
    public EntityMention(
            String type,
            String mention,
            String normalized,
            String entityId,
            Integer count,
            Double confidence
    ) {
        this(type, mention, normalized, count, null, entityId, confidence, null);
    }

    /**
     * constructor for {@code EntityMention}
     * @param type entity type
     * @param mention mention text
     * @param normalized normalized mention text
     * @param entityId if the entity was linked, the ID from the knowledge base.
     * @param count mention count
     * @param mentionOffsets list of mention offsets
     * @param confidence entity extraction confidence score
     */
    public EntityMention(
            String type,
            String mention,
            String normalized,
            Integer count,
            List<MentionOffsets> mentionOffsets,
            String entityId,
            Double confidence,
            Double salience
    ) {
        this.indocChainId = null;
        this.type = type;
        this.mention = mention;
        this.normalized = normalized;
        this.entityId = entityId;
        this.count = count;
        this.mentionOffsets = mentionOffsets;
        this.confidence = confidence;
        this.salience = salience;
    }

    /**
     * constructor for {@code EntityMention}
     * @param type entity type
     * @param mention mention text
     * @param normalized normalized mention text
     * @param entityId if the entity was linked, the ID from the knowledge base.
     * @param count mention count
     * @param confidence entity confidence
     * @param salience entity salience
     */
    public EntityMention(
            String type,
            String mention,
            String normalized,
            String entityId,
            Integer count,
            Double confidence,
            Double salience
    ) {
        this(type, mention, normalized, count, null, entityId, confidence, salience);
    }
}
