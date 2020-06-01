/*
 * Copyright 2020 Basis Technology Corp.
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

package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonTypeName("FieldedAddress")
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class FieldedAddressMixin {

    @JsonCreator
    protected FieldedAddressMixin(@JsonProperty("house") String house,
                           @JsonProperty("houseNumber") String houseNumber,
                           @JsonProperty("road") String road,
                           @JsonProperty("unit") String unit,
                           @JsonProperty("level") String level,
                           @JsonProperty("staircase") String staircase,
                           @JsonProperty("entrance") String entrance,
                           @JsonProperty("suburb") String suburb,
                           @JsonProperty("cityDistrict") String cityDistrict,
                           @JsonProperty("city") String city,
                           @JsonProperty("island") String island,
                           @JsonProperty("stateDistrict") String stateDistrict,
                           @JsonProperty("state") String state,
                           @JsonProperty("countryRegion") String countryRegion,
                           @JsonProperty("country") String country,
                           @JsonProperty("worldRegion") String worldRegion,
                           @JsonProperty("postCode") String postCode,
                           @JsonProperty("poBox") String poBox) {
        //
    }

    @JsonPOJOBuilder(withPrefix = "")
    abstract class FieldedAddressBuilderMixin {
    }
}
