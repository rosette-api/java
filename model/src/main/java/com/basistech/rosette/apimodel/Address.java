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

import java.util.Map;

import lombok.Builder;
import lombok.Value;

/**
 * Class that represents an address
 */
@Value
@Builder
public class Address {

    private static final String EMPTY_STRING = "";

    /**
     * @return the address house
     */
    private final String house;

    /**
     * @return the address house number
     */
    private final String houseNumber;

    /**
     * @return the address road
     */
    private final String road;

    /**
     * @return the address unit
     */
    private final String unit;

    /**
     * @return the address level
     */
    private final String level;

    /**
     * @return the address staircase
     */
    private final String staircase;

    /**
     * @return the address entrance
     */
    private final String entrance;

    /**
     * @return the address suburb
     */
    private final String suburb;

    /**
     * @return the address city district
     */
    private final String cityDistrict;

    /**
     * @return the address city
     */
    private final String city;

    /**
     * @return the address island
     */
    private final String island;

    /**
     * @return the address state district
     */
    private final String stateDistrict;

    /**
     * @return the address state
     */
    private final String state;

    /**
     * @return the address country region
     */
    private final String countryRegion;

    /**
     * @return the address country
     */
    private final String country;

    /**
     * @return the address world region
     */
    private final String worldRegion;

    /**
     * @return the address postal code
     */
    private final String postCode;

    /**
     * @return the address P.O. Box
     */
    private final String poBox;

    /**
     * @return the extra properties of the address
     */
    private final Map<String, String> extra;

    /**
     * @return the address uid
     */
    private final String uid;
}
