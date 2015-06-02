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

/**
 * Token's part of speech
 */
public class PartOfSpeech {
    private final String text;
    private final String pos;

    /**
     * constructor for {@code PartOfSpeech}
     * @param text text
     * @param pos part of speech
     */
    public PartOfSpeech(String text, String pos) {
        this.text = text;
        this.pos = pos;
    }

    /**
     * get the text 
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * get the part of speech
     * @return the part of speech
     */
    public String getPos() {
        return pos;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (pos != null ? pos.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code PartOfSpeech}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof PartOfSpeech) {
            PartOfSpeech that = (PartOfSpeech) o;
            return text != null ? text.equals(that.getText()) : that.text == null
                    && pos != null ? pos.equals(that.getPos()) : that.pos == null;
        } else {
            return false;
        }
    }
}
