/******************************************************************************
 * * This data and information is proprietary to, and a valuable trade secret
 * * of, Basis Technology Corp.  It is given in confidence by Basis Technology
 * * and may only be used as permitted under the license agreement under which
 * * it has been distributed, and in no other way.
 * *
 * * Copyright (c) 2015 Basis Technology Corporation All rights reserved.
 * *
 * * The technical data and information provided herein are provided with
 * * `limited rights', and the computer software provided herein is provided
 * * with `restricted rights' as those terms are defined in DAR and ASPR
 * * 7-104.9(a).
 ******************************************************************************/

package com.basistech.rosette.apimodel;

/**
 * Per-entity sentiment info.
 */
public class EntitySentiment {
    private final String entity;
    private final String type;
    private final String qid;
    private final Label label;

    public EntitySentiment(String entity, String type, String qid, Label label) {
        this.entity = entity;
        this.type = type;
        this.qid = qid;
        this.label = label;
    }

    public String getEntity() {
        return entity;
    }

    public String getType() {
        return type;
    }

    public String getQid() {
        return qid;
    }

    public Label getLabel() {
        return label;
    }
}
