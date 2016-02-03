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
import java.util.Objects;

/**
 * Simple API response data model returned by MorphologyRequest
 *
  */
public final class MorphologyResponse extends Response {
    private final List<String> tokens;
    private final List<String> posTags;
    private final List<String> lemmas;
    private final List<List<String>> compoundComponents;
    private final List<List<String>> hanReadings;

    /**
     * Construct response.
     * @param tokens tokens, or {@code null}.
     * @param posTags posTags, 1-1 with tokens, or {@code null}.
     * @param lemmas lemmas, 1-1 with tokens, or {@code null}.
     * @param compoundComponents    compoundComponents, 1-1 with tokens, or {@code null}.
     * @param hanReadings hanReadings, 1-1 with tokens, or {@code null}.
     */
    public MorphologyResponse(List<String> tokens, List<String> posTags, List<String> lemmas, List<List<String>> compoundComponents, List<List<String>> hanReadings) {
        this.tokens = tokens;
        this.posTags = posTags;
        this.lemmas = lemmas;
        this.compoundComponents = compoundComponents;
        this.hanReadings = hanReadings;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public List<String> getPosTags() {
        return posTags;
    }

    public List<String> getLemmas() {
        return lemmas;
    }

    public List<List<String>> getCompoundComponents() {
        return compoundComponents;
    }

    public List<List<String>> getHanReadings() {
        return hanReadings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MorphologyResponse that = (MorphologyResponse) o;
        return Objects.equals(tokens, that.tokens)
                &&
                Objects.equals(posTags, that.posTags)
                &&
                Objects.equals(lemmas, that.lemmas)
                &&
                Objects.equals(compoundComponents, that.compoundComponents)
                &&
                Objects.equals(hanReadings, that.hanReadings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokens, posTags, lemmas, compoundComponents, hanReadings);
    }
}
