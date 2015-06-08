/******************************************************************************
 ** Copyright (c) 2014-2015 Basis Technology Corporation.
 **
 ** Licensed under the Apache License, Version 2.0 (the "License");
 ** you may not use this file except in compliance with the License.
 ** You may obtain a copy of the License at
 **
 **     http://www.apache.org/licenses/LICENSE-2.0
 **
 ** Unless required by applicable law or agreed to in writing, software
 ** distributed under the License is distributed on an "AS IS" BASIS,
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ** See the License for the specific language governing permissions and
 ** limitations under the License.
 ******************************************************************************/

package com.basistech.rosette.apimodel;

/**
 * Token's part of speech
 */
public final class PartOfSpeech {

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
        if (!(o instanceof PartOfSpeech)) {
            return false;
        }

        PartOfSpeech that = (PartOfSpeech) o;
        return text != null ? text.equals(that.getText()) : that.text == null
                && pos != null ? pos.equals(that.getPos()) : that.pos == null;
    }
}
