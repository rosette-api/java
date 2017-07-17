/*
* Copyright 2017 Basis Technology Corp.
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
import java.util.Objects;

/**
 * Simple api response data model for entity extraction
 */
public final class TopicsResponse extends Response {

    private final List<Keyphrase> keyphrases;
    private final List<Concept> concepts;

    /**
     * Constructor for {@code TopicsResponse}
     * @param keyphrases a list of extracted keyphrases
     * @param concepts a list of extracted concepts
     */
    public TopicsResponse(List<Keyphrase> keyphrases, List<Concept> concepts) {
        this.keyphrases = keyphrases;
        this.concepts = concepts;
    }

    /**
     * get the list of extracted keyphrases
     * @return the list of extracted keyphrases
     */
    public List<Keyphrase> getKeyphrases() {
        return keyphrases;
    }

    /**
     * get the list of extracted concepts
     * @return the list of extracted concepts
     */
    public List<Concept> getConcepts() {
        return concepts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyphrases, concepts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TopicsResponse that = (TopicsResponse) o;
        return Objects.equals(keyphrases, that.keyphrases)
                && Objects.equals(concepts, that.concepts);
    }
}
