/*
* Copyright 2014 Basis Technology Corp.
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
    private final String entityId;
    private final Double confidence;

    /**
     * constructor for {@code EntityMention}
     * @param indocChainId in-document entity chain id
     * @param type entity type
     * @param mention mention text
     * @param normalized normalized mention text
     * @param count mention count
     * @param entityId if the entity was linked, the ID from the knowledge base.
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
        this.entityId = entityId;
        this.confidence = confidence;
    }

    /**
     * constructor for {@code EntityMention}
     * @param type entity type
     * @param mention mention text
     * @param normalized normalized mention text
     * @param entityId if the entity was linked, the ID from the knowledge base.
     * @param count mention count
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
        this.entityId = entityId;
        this.count = count;
        this.confidence = confidence;
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
                && Objects.equals(entityId, that.entityId)
                && Objects.equals(confidence, that.confidence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indocChainId, type, mention, normalized, count, entityId, confidence);
    }
}
