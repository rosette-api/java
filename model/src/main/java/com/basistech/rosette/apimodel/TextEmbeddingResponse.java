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
 * Simple api response data model for text embedding
 */
public final class TextEmbeddingResponse extends Response {

    private final List<Double> embedding;

    /**
     * constructor for {@code TextEmbeddingResponse}
     * @param embedding list of embedding
     */
    public TextEmbeddingResponse(List<Double> embedding) {
        this.embedding = embedding;
    }

    /**
     * get the list of embedding
     * @return the list of embedding
     */
    public List<Double> getEmbedding() {
        return embedding;
    }

    @Override
    public int hashCode() {
        return embedding != null ? embedding.hashCode() : 0;
    }

    /**
     * if the param is a {@code TextEmbeddingResponse}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TextEmbeddingResponse)) {
            return false;
        }

        TextEmbeddingResponse that = (TextEmbeddingResponse) o;
        return embedding != null ? embedding.equals(that.getEmbedding()) : that.embedding == null;
    }
}
