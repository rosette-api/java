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

/**
 * Simple api response data model for name translation
 */
public final class NameTranslationResponse extends Response {

    private final NameTranslationResult result;
    
    /**
     * constructor for {@code NameTranslationResponse}
     * @param requestId request id
     * @param result name translation result
     */
    public NameTranslationResponse(String requestId,
                                   NameTranslationResult result) {
        super(requestId);
        this.result = result;
    }

    /**
     * get the name translation result
     * @return the name translation result
     */
    public NameTranslationResult getResult() {
        return result;
    }

    @Override
    public int hashCode() {
        return result != null ? result.hashCode() : 0;
    }

    /**
     * if the param is a {@code NameTranslationResponse}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NameTranslationResponse)) {
            return false;
        }

        NameTranslationResponse that = (NameTranslationResponse) o;
        return super.equals(o)
                && result != null ? result.equals(that.getResult()) : that.result == null;
    }
}