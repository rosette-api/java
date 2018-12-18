/******************************************************************************
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2018 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
 ******************************************************************************/

package com.basistech.rosette.apimodel;

import com.basistech.rosette.annotations.JacksonMixin;
import com.basistech.util.LanguageCode;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * Related terms options
 */
@Value
@Builder
@JacksonMixin
public class RelatedTermsOptions extends Options {

    /**
     * The List of languages to find related terms in
     * @return the list of specified result languages
     */
    private final List<LanguageCode> resultLanguages;

    /**
     * The number of related terms to return for each specified language
     * @return the number of related terms requested
     */
    private final Integer count;
}
