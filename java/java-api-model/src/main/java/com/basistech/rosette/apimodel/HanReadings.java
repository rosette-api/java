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

import java.util.List;

/**
 * Han morphological analysis readings
 */
public final class HanReadings {

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
        if (!(o instanceof HanReadings)) {
            return false;
        }

        HanReadings that = (HanReadings) o;
        return text != null ? text.equals(that.getText()) : that.text == null
                && hanReadings != null ? hanReadings.equals(that.getHanReadings()) : that.hanReadings == null;
    }
}
