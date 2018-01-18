/*
* Copyright 2017 Basis Technology Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.basistech.rosette.apimodel;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Request object for name-deduplication.
 *
 * This class carries the list of names to dedupe as well as the score threshold.
 */
@Value
public final class NameDeduplicationRequest extends Request {

    /**
     * @return the list of names
     */
    @NotNull
    private final  List<Name> names;

    /**
     * @return the threshold
     */
    private final Double threshold;

    @Builder     // workaround for inheritance https://github.com/rzwitserloot/lombok/issues/853
    public NameDeduplicationRequest(String profileId,
                                     List<Name> names,
                                     Double threshold) {
        super(profileId);
        this.names = names;
        this.threshold = threshold;
    }
}
