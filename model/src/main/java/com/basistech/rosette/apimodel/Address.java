/*
 * Copyright 2022 Basis Technology Corp.
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

/**
 * @deprecated
 * Class that represents an address
 */
@Value
@Builder
@Deprecated
public class Address implements IAddress {

    /**
     * @return the address house
     */
    String house;

    /**
     * @return the address house number
     */
    String houseNumber;

    /**
     * @return the address road
     */
    String road;

    /**
     * @return the address unit
     */
    String unit;

    /**
     * @return the address level
     */
    String level;

    /**
     * @return the address staircase
     */
    String staircase;

    /**
     * @return the address entrance
     */
    String entrance;

    /**
     * @return the address suburb
     */
    String suburb;

    /**
     * @return the address city district
     */
    String cityDistrict;

    /**
     * @return the address city
     */
    String city;

    /**
     * @return the address island
     */
    String island;

    /**
     * @return the address state district
     */
    String stateDistrict;

    /**
     * @return the address state
     */
    String state;

    /**
     * @return the address country region
     */
    String countryRegion;

    /**
     * @return the address country
     */
    String country;

    /**
     * @return the address world region
     */
    String worldRegion;

    /**
     * @return the address postal code
     */
    String postCode;

    /**
     * @return the address P.O. Box
     */
    String poBox;

    @Override
    public boolean fielded() {
        return true;
    }

}
