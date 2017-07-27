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


package com.basistech.rosette.apimodel;

import java.util.Objects;

/**
 * Per-entity sentiment info.
 */
public final class EntitySentiment {
    private final String type;
    private final String mention;
    private final String normalized;
    private final Integer count;
    private final String entityId;
    private final Double confidence;
    private final Label sentiment;

    /**
     * constructor for {@code EntitySentiment}
     * @param type entity type
     * @param mention mention text
     * @param normalized normalized mention text
     * @param count mention count
     * @param entityId if the entity was linked, the ID from the knowledge base.
     * @param confidence entity confidence.
     * @param sentiment the sentiment information.
     */
    public EntitySentiment(String type,
                           String mention,
                           String normalized,
                           Integer count,
                           String entityId,
                           Double confidence,
                           Label sentiment) {
        this.type = type;
        this.mention = mention;
        this.normalized = normalized;
        this.count = count;
        this.entityId = entityId;
        this.confidence = confidence;
        this.sentiment = sentiment;
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
     * get the entity confidence
     * @return the entity confidence
     */
    public Double getConfidence() {
        return confidence;
    }

    /**
     * @return the sentiment information.
     */
    public Label getSentiment() {
        return sentiment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EntitySentiment that = (EntitySentiment) o;
        return Objects.equals(type, that.type)
                && Objects.equals(mention, that.mention)
                && Objects.equals(normalized, that.normalized)
                && Objects.equals(count, that.count)
                && Objects.equals(entityId, that.entityId)
                && Objects.equals(confidence, that.confidence)
                && Objects.equals(sentiment, that.sentiment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, mention, normalized, count, entityId, confidence, sentiment);
    }
}
