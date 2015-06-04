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
 * Rosette API information 
 */
public final class InfoResponse extends Response {

    private final String name;
    private final String version;
    private final String buildNumber;
    private final String buildTime;

    /**
     * constructor for {@code InfoResponse} 
     * @param name name
     * @param version version
     * @param buildNumber build number
     * @param buildTime build time
     */
    public InfoResponse(String name, String version, String buildNumber, String buildTime) {
        super(null);
        this.name = name;
        this.version = version;
        this.buildNumber = buildNumber;
        this.buildTime = buildTime;
    }

    /**
     * get the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * get the version 
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * get the build number 
     * @return the build number
     */
    public String getBuildNumber() {
        return buildNumber;
    }

    /**
     * get the build time 
     * @return the build time
     */
    public String getBuildTime() {
        return buildTime;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (buildNumber != null ? buildNumber.hashCode() : 0);
        result = 31 * result + (buildTime != null ? buildTime.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code InfoResponse}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof InfoResponse)) {
            return false;
        }

        InfoResponse that = (InfoResponse) o;
        return super.equals(o)
                && name != null ? name.equals(that.getName()) : that.name == null
                && version != null ? version.equals(that.getVersion()) : that.version == null
                && buildNumber != null ? buildNumber.equals(that.getBuildNumber()) : that.buildNumber == null
                && buildTime != null ? buildTime.equals(that.getBuildTime()) : that.buildTime == null;
    }
}
