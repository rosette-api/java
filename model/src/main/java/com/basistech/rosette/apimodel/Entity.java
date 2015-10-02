/******************************************************************************
 ** Copyright (c) 2014-2015 Basis Technology Corporation.
 **
 ** Licensed under the Apache License, Version 2.0 (the "License");
 ** you may not use this file except in compliance with the License.
 ** You may obtain a copy of the License at
 **
 **     http://www.apache.org/licenses/LICENSE-2.0
 **
 ** Unless required by applicable law or agreed to in writing, software
 ** distributed under the License is distributed on an "AS IS" BASIS,
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ** See the License for the specific language governing permissions and
 ** limitations under the License.
 ******************************************************************************/

package com.basistech.rosette.apimodel;

/**
 * Entity extracted by the entity extractor
 */
public final class Entity {

    private final int indocChainId;
    private final String type;
    private final String mention;
    private final String normalized;
    private final int count;
    private final Double confidence;

    /**
     * constructor for {@code Entity}
     * @param indocChainId in-document entity chain id
     * @param type entity type
     * @param mention mention text
     * @param normalized normalized mention text
     * @param count mention count
     * @param confidence confidence
     */
    public Entity(
            int indocChainId,
            String type,
            String mention,
            String normalized,
            int count,
            Double confidence
    ) {
        this.indocChainId = indocChainId;
        this.type = type;
        this.mention = mention;
        this.normalized = normalized;
        this.count = count;
        this.confidence = confidence;
    }

    /**
     * get the in-document entity chain id 
     * @return the id
     */
    public int getIndocChainId() {
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

    /**
     * get the confidence 
     * @return the confidence
     */
    public Double getConfidence() {
        return confidence;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = indocChainId;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (mention != null ? mention.hashCode() : 0);
        result = 31 * result + (normalized != null ? normalized.hashCode() : 0);
        result = 31 * result + count;
        temp = Double.doubleToLongBits(confidence);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
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
        return indocChainId == that.getIndocChainId()
                && type != null ? type.equals(that.getType()) : that.type == null
                && mention != null ? mention.equals(that.getMention()) : that.mention == null
                && normalized != null ? normalized.equals(that.getNormalized()) : that.normalized == null
                && count == that.getCount()
                && confidence.equals(that.getConfidence());
    }
}
