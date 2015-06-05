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
 * Data from informational request such as version, build, and support info
 */
public final class ConstantsResponse extends Response {

    private final String version;
    private final String build;
    private final Object support;

    /**
     * Constructor for {@code ConstantsResponse}
     * @param requestId request id
     * @param version Rosette API endpoint version
     * @param build Rosette API endpoint build
     * @param support support (reserved for future feature)
     */
    public ConstantsResponse(String requestId, String version, String build, Object support) {
        super(requestId);
        this.version = version;
        this.build = build;
        this.support = support;
    }

    /**
     * get the Rosette API endpoint version
     * @return the Rosette API endpoint version
     */
    public String getVersion() {
        return version;
    }

    /**
     * get the Rosette API endpoint build
     * @return the Rosette API endpoint build
     */
    public String getBuild() {
        return build;
    }

    /**
     * get support 
     * @return support (reserved for future feature)
     */
    public Object getSupport() {
        return support;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (build != null ? build.hashCode() : 0);
        result = 31 * result + (support != null ? support.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code ConstantsResponse}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ConstantsResponse)) {
            return false;
        }

        ConstantsResponse that = (ConstantsResponse) o;
        return super.equals(o)
                && version != null ? version.equals(that.getVersion()) : that.version == null
                && build != null ? build.equals(that.getBuild()) : that.build == null
                && support != null ? support.equals(that.getSupport()) : that.support == null;
    }
}
