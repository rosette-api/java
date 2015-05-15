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
 * entity extractor (REX) entity
 */
public class ExtractedEntity {
    private int indocChainId;
    private String type;
    private String mention;
    private String normalized;
    private int count;
    private double confidence;

    public ExtractedEntity() { }

    /**
     * constructor for {@code ExtractedEntity}
     * @param indocChainId in-document entity chain id
     * @param type entity type
     * @param mention mention text
     * @param normalized normalized mention text
     * @param count mention count
     * @param confidence confidence
     */
    public ExtractedEntity(
            int indocChainId,
            String type,
            String mention,
            String normalized,
            int count,
            double confidence
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
     * @return the mention text
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
    public double getConfidence() {
        return confidence;
    }
}
