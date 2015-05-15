/******************************************************************************
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2015 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
 ******************************************************************************/

package com.basistech.rosette.model;

/**
 * Entity Resolver entity
 */
public class LinkedEntity {
    private String entityId;
    private int indocChainId;
    private String mention;
    private double confidence;

    public LinkedEntity() { }

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
}
