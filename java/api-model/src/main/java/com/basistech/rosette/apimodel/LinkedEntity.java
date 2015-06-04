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
 * Entity linked against a knowledge base (default is Wikipedia)
 */
public final class LinkedEntity {

    private final String entityId;
    private final int indocChainId;
    private final String mention;
    private final double confidence;

    /**
     * constructor for {@code LinkedEntity}
     * @param entityId resolved entity id
     * @param indocChainId in-document chain id
     * @param mention mention text
     * @param confidence confidence
     */
    public LinkedEntity(
            String entityId,
            int indocChainId,
            String mention,
            double confidence) {
        this.entityId = entityId;
        this.indocChainId = indocChainId;
        this.mention = mention;
        this.confidence = confidence;
    }

    /**
     * get the resolved entity id 
     * @return the entity id
     */
    public String getEntityId() {
        return entityId;
    }

    /**
     * get the in-document chain id 
     * @return the in-document chain id
     */
    public int getIndocChainId() {
        return indocChainId;
    }

    /**
     * get the mention text 
     * @return the mention text
     */
    public String getMention() {
        return mention;
    }

    /**
     * get the confidence
     * @return the confidence
     */
    public double getConfidence() {
        return confidence;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = entityId != null ? entityId.hashCode() : 0;
        result = 31 * result + indocChainId;
        result = 31 * result + (mention != null ? mention.hashCode() : 0);
        temp = Double.doubleToLongBits(confidence);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * if the param is a {@code LinkedEntity}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LinkedEntity)) {
            return false;
        }

        LinkedEntity that = (LinkedEntity) o;
        return entityId != null ? entityId.equals(that.getEntityId()) : that.entityId == null
                && indocChainId == that.getIndocChainId()
                && mention != null ? mention.equals(that.getMention()) : that.mention == null
                && confidence == that.getConfidence();
    }
}
