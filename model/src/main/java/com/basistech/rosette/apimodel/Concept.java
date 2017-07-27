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

import java.util.Objects;

/**
 * Concepts found in a document.
 */
public final class Concept {
    private final String phrase;
    private final Double salience;
    private final String conceptId;

    /**
     * constructor for {@code Concept}
     * @param phrase text of the concept
     * @param salience concept salience
     * @param conceptId the id of the concept
     */
    public Concept(String phrase, Double salience, String conceptId) {
        this.phrase = phrase;
        this.salience = salience;
        this.conceptId = conceptId;
    }

    /**
     * get the concept text
     * @return the concept text
     */
    public String getPhrase() {
        return phrase;
    }

    /**
     * get the concept salience
     * @return the concept salience
     */
    public Double getSalience() {
        return salience;
    }

    /**
     * get the concept id
     * @return the concept id
     */
    public String getConceptId() {
        return conceptId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Concept that = (Concept) o;
        return Objects.equals(phrase, that.phrase)
                && Objects.equals(salience, that.salience)
                && Objects.equals(conceptId, that.conceptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phrase, salience, conceptId);
    }
}