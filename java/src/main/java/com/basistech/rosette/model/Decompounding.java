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

import java.util.List;

/**
 * Decompounded components of a compound word
 */
public class Decompounding {
    private String text;
    private List<String> compoundComponents;
    
    public Decompounding() { }

    /**
     * Constructor for {@code Decompounding}
     * @param text compound word
     * @param compoundComponents list of compound components
     */
    public Decompounding(
            String text,
            List<String> compoundComponents
    ) {
        this.text = text;
        this.compoundComponents = compoundComponents;
    }

    /**
     * get the compound word
     * @return the compound word
     */
    public String getText() {
        return text;
    }

    /**
     * get the list of compound components
     * @return the list of compound components
     */
    public List<String> getCompoundComponents() {
        return compoundComponents;
    }
}
