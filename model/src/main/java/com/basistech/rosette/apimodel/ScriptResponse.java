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

import java.util.List;

/**
 * Response data model for scripts 
 */
public final class ScriptResponse extends Response {

    private final List<String> scripts;

    /**
     * constructor for {@code ScriptResponse}
     * @param requestId request id
     * @param scripts list of scripts
     */
    public ScriptResponse(String requestId,
                          List<String> scripts) {
        super(requestId);
        this.scripts = scripts;
    }

    /**
     * get the list of scripts
     * @return the list of scripts
     */
    public List<String> getScripts() {
        return scripts;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (scripts != null ? scripts.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code ScriptResponse}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ScriptResponse)) {
            return false;
        }

        ScriptResponse that = (ScriptResponse) o;
        return super.equals(o)
                && scripts != null ? scripts.equals(that.getScripts()) : that.scripts == null;
    }
}