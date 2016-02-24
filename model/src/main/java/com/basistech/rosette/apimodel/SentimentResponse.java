/*
* Copyright 2016 Basis Technology Corp.
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

import java.util.Collection;
import java.util.Objects;

/**
 *  Information returned from the sentiment endpoint.
 **/
public final class SentimentResponse extends Response {
    private final Label document;
    private final Collection<EntitySentiment> entities;

    /**
     *
     * @param document The sentiment information for the entire document.
     * @param entities The sentiment information for the entities detected in the document.
     */
    public SentimentResponse(Label document, Collection<EntitySentiment> entities) {
        this.document = document;
        this.entities = entities;
    }

    /**
     * @return the whole-document sentiment.
     */
    public Label getDocument() {
        return document;
    }

    /**
     * @return the information for individual entities.
     */
    public Collection<EntitySentiment> getEntities() {
        return entities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SentimentResponse that = (SentimentResponse) o;
        return Objects.equals(document, that.document)
                && Objects.equals(entities, that.entities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(document, entities);
    }
}
