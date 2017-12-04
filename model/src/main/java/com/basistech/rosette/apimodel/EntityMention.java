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

import java.util.List;
import java.util.Objects;

/**
 * An entity mention found in a document.
 * The /entities endpoint returns a collection of entity mentions.
 */
public final class EntityMention {

    private final Integer indocChainId;
    private final String type;
    private final String mention;
    private final String normalized;
    private final Integer count;
    private final List<MentionOffsets> mentionOffsets;
    private final String entityId;
    private final Double confidence;
    private final Double salience;
    private final Double linkingConfidence;

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
        this.indocChainId = indocChainId;
        this.type = type;
        this.mention = mention;
        this.normalized = normalized;
        this.count = count;
        this.mentionOffsets = null;
        this.entityId = entityId;
        this.confidence = confidence;
        this.salience = null;
        this.linkingConfidence = null;
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
    public EntityMention(
            String type,
            String mention,
            String normalized,
            String entityId,
            Integer count,
            Double confidence
    ) {
        this.indocChainId = null;
        this.type = type;
        this.mention = mention;
        this.normalized = normalized;
        this.mentionOffsets = null;
        this.entityId = entityId;
        this.count = count;
        this.confidence = confidence;
        this.salience = null;
        this.linkingConfidence = null;
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
        this.indocChainId = null;
        this.type = type;
        this.mention = mention;
        this.normalized = normalized;
        this.mentionOffsets = null;
        this.entityId = entityId;
        this.count = count;
        this.confidence = confidence;
        this.salience = salience;
        this.linkingConfidence = null;
    }

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
        List<MentionOffsets> mentionOffsets,
        String entityId,
        Double confidence,
        Double salience,
        Double linkingConfidence
    ) {
        this.indocChainId = indocChainId;
        this.type = type;
        this.mention = mention;
        this.normalized = normalized;
        this.count = count;
        this.mentionOffsets = mentionOffsets;
        this.entityId = entityId;
        this.confidence = confidence;
        this.salience = salience;
        this.linkingConfidence = linkingConfidence;
    }

    /**
     * get the in-document entity chain id
     * @return the id
     */
    @Deprecated
    public Integer getIndocChainId() {
        return indocChainId;
    }

    /**
     * get the entity type
     * @return the entity type
     */
    public String getType() {
        return type;
    }

    /**
     * get the mention text
     * @return the mention text
     */
    public String getMention() {
        return mention;
    }

    /**
     * get the normalized mention text
     * @return the normalized mention text
     */
    public String getNormalized() {
        return normalized;
    }

    /**
     * get the mention count
     * @return the mention count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * get the offsets for all mentions of an entity
     * @return offsets for all mentions of an entity
     */
    public List<MentionOffsets> getMentionOffsets() {
        return mentionOffsets;
    }

    /**
     * get the entity knowledge base ID.
     * @return the ID of this entity. If this entity was linked to a knowledge base,
     * the resulting string will begin with 'Q'. If it was not linked to a knowledge base,
     * it will begin with a 'T'. 'T' identifiers represent intra-document co-references.
     */
    public String getEntityId() {
        return entityId;
    }

    /**
     * get the confidence score for the entity.
     * @return the confidence score  (0.0-1.0)
     */
    public Double getConfidence() {
        return confidence;
    }

    /**
     * get the salience score for the entity.
     * @return the salience score  (0.0 or 1.0)
     */
    public Double getSalience() {
        return salience;
    }

    /**
     * get the linking confidence score for the entity.
     * @return the linking confidence score  (0.0-1.0)
     */
    public Double getLinkingConfidence() {
        return linkingConfidence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EntityMention that = (EntityMention) o;
        return Objects.equals(indocChainId, that.indocChainId)
                && Objects.equals(type, that.type)
                && Objects.equals(mention, that.mention)
                && Objects.equals(normalized, that.normalized)
                && Objects.equals(count, that.count)
                && Objects.equals(mentionOffsets, that.mentionOffsets)
                && Objects.equals(entityId, that.entityId)
                && Objects.equals(confidence, that.confidence)
                && Objects.equals(salience, that.salience)
                && Objects.equals(linkingConfidence, that.linkingConfidence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indocChainId, type, mention, normalized, count, mentionOffsets, entityId, confidence, salience, linkingConfidence);
    }
}
