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

import java.util.Objects;

/**
 * Keyphrases found in a document.
 */
public final class Keyphrase {
    private final String phrase;
    private final Double salience;

    /**
     * constructor for {@code Keyphrase}
     * @param phrase text of the keyphrase
     * @param salience keyphrase salience
     */
    public Keyphrase(String phrase, Double salience) {
        this.phrase = phrase;
        this.salience = salience;
    }

    /**
     * get the keyphrase text
     * @return the keyphrase text
     */
    public String getPhrase() {
        return phrase;
    }

    /**
     * get the keyphrase salience
     * @return the keyphrase salience
     */
    public Double getSalience() {
        return salience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Keyphrase that = (Keyphrase) o;
        return Objects.equals(phrase, that.phrase)
                && Objects.equals(salience, that.salience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phrase, salience);
    }
}