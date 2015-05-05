package com.basistech.rosette.model;

/******************************************************************************
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2014 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
 ******************************************************************************/

public class ExtractedEntity {
    private int indocChainId;
    private String type;
    private String mention;
    private String normalized;
    private int count;
    private double confidence;

    public ExtractedEntity() {
    }

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

    public int getIndocChainId() {
        return indocChainId;
    }

    public String getType() {
        return type;
    }

    public String getMention() {
        return mention;
    }

    public String getNormalized() {
        return normalized;
    }

    public int getCount() {
        return count;
    }

    public double getConfidence() {
        return confidence;
    }
}
