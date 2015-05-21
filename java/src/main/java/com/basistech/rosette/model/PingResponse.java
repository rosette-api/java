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
 * Response ping data
 */
public class PingResponse extends Response {
    private String message;
    private long time;

    public PingResponse() { }

    /**
     * constructor for {@code PingResponse} 
     * @param message ping response message
     * @param time ping response timestamp
     */
    public PingResponse(String message, long time) {
        this.message = message;
        this.time = time;
    }

    /**
     * get the ping response message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * get the ping response timestamp
     * @return the ping response timestamp
     */
    public long getTime() {
        return time;
    }

    /**
     * set the ping response message 
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * set the ping response timestamp
     * @param time the ping response timestamp
     */
    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (int) (time ^ (time >>> 32));
        return result;
    }

    /**
     * if the param is a {@code PingResponse}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof PingResponse) {
            PingResponse that = (PingResponse) o;
            return super.equals(o)
                    && message != null ? message.equals(that.getMessage()) : message == that.getMessage()
                    && time == that.getTime();
        } else {
            return false;
        }
    }
}
