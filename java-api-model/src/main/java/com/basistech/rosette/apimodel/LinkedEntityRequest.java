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
 * Class that represents the data from a RaaS client entity resolution request
 */
public class LinkedEntityRequest extends Request {

    /**
     * constructor for {@code LinkedEntityRequest}
     * @param language language code
     * @param content raw data
     * @param contentUri uri pointing to the data
     * @param contentType byte array of data
     * @param unit input unit code
     */
    public LinkedEntityRequest(
            String language,
            String content,
            String contentUri,
            String contentType,
            InputUnit unit
    ) {
        super(language, content, contentUri, contentType, unit);
    }

    public LinkedEntityRequest() {
        super(null, null, null, null, null);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * if the param is a {@code LinguisticsRequest}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof LinkedEntityRequest) {
            return super.equals(o);
        } else {
            return false;
        }
    }
}
