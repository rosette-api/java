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
 * Decompounded components of a compound word
 */
public final class CompoundComponents {

    private final String text;
    private final List<String> compoundComponents;
    
    /**
     * Constructor for {@code CompoundComponents}
     * @param text compound word
     * @param compoundComponents list of compound components
     */
    public CompoundComponents(
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

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (compoundComponents != null ? compoundComponents.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code CompoundComponents}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CompoundComponents)) {
            return false;
        }

        CompoundComponents that = (CompoundComponents) o;
        return text != null ? text.equals(that.getText()) : that.text == null
                && compoundComponents != null ? compoundComponents.equals(that.getCompoundComponents()) : that.compoundComponents == null;
    }
}
