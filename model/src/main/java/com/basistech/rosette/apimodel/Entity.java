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

/**
 * An entity mention found in a document.
 * The /entities endpoint returns a collection of entities. Each
 * entity is uniquely identified by its representation in the text
 * (the {@code }mention}_ and its entity ID from the knowledge base.
 * If there is no entity id available, mentions with the same string
 * are distinguished by type. If there are multiple occurrences of the same
 * string in the document, they are combined, and counted with the {@code count} field.
 */
public final class Entity {

    private final Integer indocChainId;
    private final String type;
    private final String mention;
    private final String normalized;
    private final int count;
    private final String entityId;

    /**
     * constructor for {@code Entity}
     * @param indocChainId in-document entity chain id
     * @param type entity type
     * @param mention mention text
     * @param normalized normalized mention text
     * @param count mention count
     * @param entityId if the entity was linked, the ID from the knowledge base.
     */
    @Deprecated
    public Entity(
            Integer indocChainId,
            String type,
            String mention,
            String normalized,
            int count,
            String entityId
    ) {
        this.indocChainId = indocChainId;
        this.type = type;
        this.mention = mention;
        this.normalized = normalized;
        this.count = count;
        this.entityId = entityId;
    }

    /**
     * constructor for {@code Entity}
     * @param type entity type
     * @param mention mention text
     * @param normalized normalized mention text
     * @param count mention count
     * @param entityId if the entity was linked, the ID from the knowledge base.
     */
    public Entity(
            String type,
            String mention,
            String normalized,
            int count,
            String entityId
    ) {
        this.indocChainId = null;
        this.type = type;
        this.mention = mention;
        this.normalized = normalized;
        this.count = count;
        this.entityId = entityId;
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
    public int getCount() {
        return count;
    }

    public String getEntityId() {
        return entityId;
    }

    @Override
    public int hashCode() {
        int result;
        result = indocChainId != null ? indocChainId.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (mention != null ? mention.hashCode() : 0);
        result = 31 * result + (normalized != null ? normalized.hashCode() : 0);
        result = 31 * result + count;
        result = 31 * result + (entityId != null ? entityId.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code Entity}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Entity)) {
            return false;
        }

        Entity that = (Entity) o;
        return indocChainId != null ? indocChainId.equals(that.getIndocChainId()) : that.indocChainId == null
                && type != null ? type.equals(that.getType()) : that.type == null
                && mention != null ? mention.equals(that.getMention()) : that.mention == null
                && normalized != null ? normalized.equals(that.getNormalized()) : that.normalized == null
                && entityId != null ? entityId.equals(that.entityId) : that.entityId == null
                && count == that.getCount();
    }
}
