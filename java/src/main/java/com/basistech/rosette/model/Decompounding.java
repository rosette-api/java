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

    /**
     * set the compound word 
     * @param text the compound word
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * set the list of compound components 
     * @param compoundComponents the list of compound components
     */
    public void setCompoundComponents(List<String> compoundComponents) {
        this.compoundComponents = compoundComponents;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (compoundComponents != null ? compoundComponents.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code Decompounding}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Decompounding) {
            Decompounding that = (Decompounding) o;
            return text != null ? text.equals(that.getText()) : text == that.getText()
                    && compoundComponents != null ? compoundComponents.equals(that.getCompoundComponents()) : compoundComponents == that.getCompoundComponents();
        } else {
            return false;
        }
    }
}
