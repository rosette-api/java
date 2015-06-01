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
 * Class that represents a name matcher request.
 *
 * Request to match 2 names.
 */
public class NameMatcherRequest {

    private final Name name1;
    private final Name name2;

    /**
     * Constructor for {@code NameMatcherRequest}
     * @param name1 First name to be matched against second name
     * @param name2 Second name to be matched against first name
     */
    public NameMatcherRequest(Name name1,
                              Name name2) {
        this.name1 = name1;
        this.name2 = name2;
    }

    /**
     * Gets the first name
     * @return first name
     */
    public Name getName1() {
        return name1;
    }

    /**
     * Gets the second name
     * @return second name
     */
    public Name getName2() {
        return name2;
    }
}
