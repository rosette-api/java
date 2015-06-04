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
 * Response ping data
 */
public final class PingResponse extends Response {

    private final String message;
    private final long time;

    /**
     * constructor for {@code PingResponse} 
     * @param message ping response message
     * @param time ping response timestamp
     */
    public PingResponse(String message, long time) {
        super(null);
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
        if (!(o instanceof PingResponse)) {
            return false;
        }

        PingResponse that = (PingResponse) o;
        return super.equals(o)
                && message != null ? message.equals(that.getMessage()) : that.message == null
                && time == that.getTime();
    }
}
