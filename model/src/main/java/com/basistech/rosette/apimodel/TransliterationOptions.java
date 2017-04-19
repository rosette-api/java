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

public final class TransliterationOptions extends Options {
    private Boolean reversed;

    /**
     * Constructs a set of transliteration options.
     * @param reversed whether to do reverse transliteration
     */
    public TransliterationOptions(final Boolean reversed) {
        this.reversed = reversed;
    }

    /**
     * Returns whether to do reverse transliteration.
     * @return whether to do reverse transliteration
     */
    public Boolean getReversed() {
        return reversed;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final TransliterationOptions that = (TransliterationOptions) obj;
        return Objects.equals(reversed, that.reversed);
    }

    @Override
        public int hashCode() {
        return Objects.hashCode(reversed);
    }
}
