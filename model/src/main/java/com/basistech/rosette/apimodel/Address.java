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

import java.util.Collections;
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

    /**
     * Default constructor for lombok
     * @param house
     * @param houseNumber
     * @param road
     * @param unit
     * @param level
     * @param staircase
     * @param entrance
     * @param suburb
     * @param cityDistrict
     * @param city
     * @param island
     * @param stateDistrict
     * @param state
     * @param countryRegion
     * @param country
     * @param worldRegion
     * @param postCode
     * @param poBox
     * @param extra
     * @param uid
     */
    public Address(String house, String houseNumber, String road, String unit, String level, String staircase,
                String entrance, String suburb, String cityDistrict, String city, String island, String stateDistrict,
                String state, String countryRegion, String country, String worldRegion, String postCode, String poBox,
                Map<String, String> extra, String uid) {
        this.house = house;
        this.houseNumber = houseNumber;
        this.road = road;
        this.unit = unit;
        this.level = level;
        this.staircase = staircase;
        this.entrance = entrance;
        this.suburb = suburb;
        this.cityDistrict = cityDistrict;
        this.city = city;
        this.island = island;
        this.stateDistrict = stateDistrict;
        this.state = state;
        this.countryRegion = countryRegion;
        this.country = country;
        this.worldRegion = worldRegion;
        this.postCode = postCode;
        this.poBox = poBox;
        this.extra = extra == null ? Collections.<String, String>emptyMap() : Collections.unmodifiableMap(extra);
        this.uid = uid;
    }
}
