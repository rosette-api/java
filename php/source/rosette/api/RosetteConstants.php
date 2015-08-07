<?php

/**
 * Container for the Rosette Constants.
 *
 * @copyright 2014-2015 Basis Technology Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * @license http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 **/
namespace rosette\api;

/**
 * Class RosetteConstants.
 */
class RosetteConstants
{
    /**
     * Accepted data formats.
     *
     * @var array
     */
    public static $DataFormat = array(
        'SIMPLE' => '',
        'JSON' => 'application/json',
        'HTML' => 'text/html',
        'XHTML' => 'application/xhtml+xml',
        'UNSPECIFIED' => 'application/octet-stream',
    );

    /**
     * Accepted Input Units.
     *
     * @var array
     */
    public static $InputUnit = array(
        'DOC' => 'doc',
        'SENTENCE' => 'sentence',
    );

    /**
     * Accepted Morphology Output types.
     *
     * @var array
     */
    public static $MorphologyOutput = array(
        'LEMMAS' => 'lemmas',
        'PARTS_OF_SPEECH' => 'parts-of-speech',
        'COMPOUND_COMPONENTS' => 'compound-components',
        'HAN_READINGS' => 'han-readings',
        'COMPLETE' => 'complete',
    );
}
