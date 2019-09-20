/*
 * Copyright 2019 Basis Technology Corp.
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


import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

/**
 * Request object for address-similarity.
 *
 * This class carries the two addresses to compare.
 */
@Value
public final class AddressSimilarityRequest extends Request {

    /**
     * @return first address
     */
    @NotNull
    private Address address1;

    /**
     * @return second address
     */
    @NotNull
    private Address address2;

    @Builder
    public AddressSimilarityRequest(String profileId, Address address1, Address address2) {
        super(profileId);
        this.address1 = address1;
        this.address2 = address2;
    }
}
