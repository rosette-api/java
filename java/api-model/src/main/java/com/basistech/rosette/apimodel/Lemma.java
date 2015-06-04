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
 * lemma (canonical form) of the word
 */
public final class Lemma {

    private final String text;
    private final String lemma;

    /**
     * constructor for {@code lemma}
     * @param text text
     * @param lemma lemma
     */
    public Lemma(
            String text, 
            String lemma
    ) {
        this.text = text;
        this.lemma = lemma;
    }

    /**
     * get the text 
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * get the lemma
     * @return the lemma
     */
    public String getLemma() {
        return lemma;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (lemma != null ? lemma.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code Lemma}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Lemma)) {
            return false;
        }

        Lemma that = (Lemma) o;
        return text != null ? text.equals(that.getText()) : that.text == null
                && lemma != null ? lemma.equals(that.getLemma()) : that.lemma == null;
    }
}