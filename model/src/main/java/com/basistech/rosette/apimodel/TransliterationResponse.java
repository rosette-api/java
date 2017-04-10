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
 * Simple API response data model for RCT.
 */
public final class TransliterationResponse extends Response {
    private final String transliteration;

    /**
     * Constructs a transliteration response.
     * @param transliteration a transliteration string
     */
    public TransliterationResponse(final String transliteration) {
        this.transliteration = transliteration;
    }

    /**
     * Returns the transliteration.
     * @return the transliteration
     */
    public String getTransliteration() {
        return transliteration;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(transliteration, ((TransliterationResponse) obj).transliteration);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(transliteration);
    }
}
