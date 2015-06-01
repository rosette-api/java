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

package com.basistech.rosette.apimodel;

import java.util.List;

/**
 * Han morphological analysis readings
 */
public class HanReadings {
    private final String text;
    private final List<String> hanReadings;
    
    /**
     * Constructor for {@code HanReadings}
     * @param text text
     * @param hanReadings list of han readings
     */
    public HanReadings(
            String text,
            List<String> hanReadings
    ) {
        this.text = text;
        this.hanReadings = hanReadings;
    }

    /**
     * get the text 
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * get the list of han readings
     * @return the list of han readings
     */
    public List<String> getHanReadings() {
        return hanReadings;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (hanReadings != null ? hanReadings.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code HanReadings}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof HanReadings) {
            HanReadings that = (HanReadings) o;
            return text != null ? text.equals(that.getText()) : text == that.getText()
                    && hanReadings != null ? hanReadings.equals(that.getHanReadings()) : hanReadings == that.getHanReadings();
        } else {
            return false;
        }
    }
}
