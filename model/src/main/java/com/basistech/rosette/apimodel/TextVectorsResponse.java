/*
* Copyright 2014 Basis Technology Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.basistech.rosette.apimodel;

import java.util.List;

/** 
 * Simple api response data model for text vector
 */
public final class TextVectorsResponse extends Response {

    private final List<Double> vectors;

    /**
     * constructor for {@code TextVectorsResponse}
     * @param vectors list of vectors
     */
    public TextVectorsResponse(List<Double> vectors) {
        this.vectors = vectors;
    }

    /**
     * get the list of vectors
     * @return the list of vectors
     */
    public List<Double> getVectors() {
        return vectors;
    }

    @Override
    public int hashCode() {
        return vectors != null ? vectors.hashCode() : 0;
    }

    /**
     * if the param is a {@code TextVectorsResponse}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TextVectorsResponse)) {
            return false;
        }

        TextVectorsResponse that = (TextVectorsResponse) o;
        return vectors != null ? vectors.equals(that.getVectors()) : that.vectors == null;
    }
}
