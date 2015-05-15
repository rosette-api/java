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
 * Unit of input
 */
public enum InputUnit {
    DOC("doc"),
    SENTENCE("sentence"),
    WORDS("words"),
    QUERY("query");

    private String label;

    /**
     * constructor for {@code InputUnit} which sets a label for reference
     */
    InputUnit(String label) {
        this.label = label;
    }

    /**
     * get the label
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * get the label
     * @return the label
     */
    public String toValue() {
        return label;
    }
}
