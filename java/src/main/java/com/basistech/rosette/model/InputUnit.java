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

import java.util.EnumSet;

/**
 * Unit of input
 */
public enum InputUnit {
    //CHECKSTYLE:OFF
    doc("doc"),
    sentence("sentence"),
    words("words"),
    query("query");
    //CHECKSTYLE:ON

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
     * set the label
     * @param label the label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * checks if value is a valid {@code InputUnit} enum
     * @param value input value
     * @return {@code InputUnit corresponding to input value}
     * @throws IllegalArgumentException
     */
    public static InputUnit forValue(String value) throws IllegalArgumentException {
        for (InputUnit unit : EnumSet.allOf(InputUnit.class)) {
            if (unit.getLabel().equalsIgnoreCase(value)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("invalid doc unit: " + value);
    }

    /**
     * get the label
     * @return the label
     */
    public String toValue() {
        return label;
    }
}
