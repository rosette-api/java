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

package com.basistech.rosette.model;

/**
 * Data from constant tickets
 */
public class ConstantsResponse extends Response {

    //CHECKSTYLE:OFF
    public String version;
    public String build;
    public Object support;
    //CHECKSTYLE:ON
    
    public ConstantsResponse() { super(); }

    /**
     * Constructor for {@code ConstantsResponse}
     * @param requestId request id
     * @param version component version
     * @param build component build
     * @param support support
     */
    public ConstantsResponse(String requestId, String version, String build, Object support) {
        super(requestId);
        this.version = version;
        this.build = build;
        this.support = support;
    }

    /**
     * get the component version 
     * @return the component version
     */
    public String getVersion() {
        return version;
    }

    /**
     * get the component build 
     * @return the component build
     */
    public String getBuild() {
        return build;
    }

    /**
     * get support 
     * @return support
     */
    public Object getSupport() {
        return support;
    }

    /**
     * set the component version 
     * @param version the version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * set the component build 
     * @param build the build
     */
    public void setBuild(String build) {
        this.build = build;
    }

    /**
     * set support 
     * @param support support
     */
    public void setSupport(Object support) {
        this.support = support;
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
        if (o instanceof ConstantsResponse) {
            ConstantsResponse that = (ConstantsResponse) o;
            return super.equals(o)
                    && version != null ? version.equals(that.getVersion()) : version == that.getVersion()
                    && build != null ? build.equals(that.getBuild()) : build == that.getBuild()
                    && support != null ? support.equals(that.getSupport()) : support == that.getSupport();
        } else {
            return false;
        }
    }
}
