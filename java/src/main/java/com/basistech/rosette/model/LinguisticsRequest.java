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
 * Class that represents the data from a RaaS client base linguistics request
 */
public class LinguisticsRequest extends Request {
    private LinguisticsOptions options;
    
    public LinguisticsRequest() { super(); }

    /**
     * constructor for {@code LinguisticsRequest}
     * @param language language code
     * @param content raw data
     * @param contentUri uri pointing to the data
     * @param contentType byte array of data
     * @param unit input unit code
     * @param options base linguistics options
     */
    public LinguisticsRequest(
            String language,
            String content,
            String contentUri,
            String contentType,
            InputUnit unit,
            LinguisticsOptions options
    ) {
        super(language, content, contentUri, contentType, unit);
        this.options = options;
    }

    /**
     * get the base linguistics options
     * @return the base linguistics options
     */
    public LinguisticsOptions getOptions() {
        return options;
    }
}
