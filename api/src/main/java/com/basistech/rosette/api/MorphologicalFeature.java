/*
* Copyright 2014 Basis Technology Corp.
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

package com.basistech.rosette.api;

/**
 * Specify which feature you want Analytics API Morphology endpoint to return. Specify COMPLETE for every feature.
 */
public enum MorphologicalFeature {
    COMPLETE("complete"),
    LEMMAS("lemmas"),
    PARTS_OF_SPEECH("parts-of-speech"),
    COMPOUND_COMPONENTS("compound-components"),
    HAN_READINGS("han-readings");

    private String pathLabel;

    MorphologicalFeature(String pathLabel) {
        this.pathLabel = pathLabel;
    }

    @Override
    public String toString() {
        return pathLabel;
    }
}
