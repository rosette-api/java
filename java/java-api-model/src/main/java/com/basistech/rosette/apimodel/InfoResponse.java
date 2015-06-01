/******************************************************************************
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2015 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
 ******************************************************************************/

package com.basistech.rosette.apimodel;

/**
 * Rosette API information 
 */
public class InfoResponse extends Response {
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
        if (o instanceof InfoResponse) {
            InfoResponse that = (InfoResponse) o;
            return super.equals(o)
                    && name != null ? name.equals(that.getName()) : name == that.getName()
                    && version != null ? version.equals(that.getVersion()) : version == that.getVersion()
                    && buildNumber != null ? buildNumber.equals(that.getBuildNumber()) : buildNumber == that.getBuildNumber()
                    && buildTime != null ? buildTime.equals(that.getBuildTime()) : buildTime == that.getBuildTime();
        } else {
            return false;
        }
    }
}
