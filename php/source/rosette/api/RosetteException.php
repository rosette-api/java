<?php

/**
 * class RosetteException.
 *
 * Encapsulates the common exceptions that could occur.
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
 * Class RosetteException.
 */
class RosetteException extends CustomException
{
    /**
     * The general error returned from Rosette API, indicating unrecognized format.
     *
     * @var int
     */
    public static $BAD_REQUEST_FORMAT = 400; // server error
    /**
     * Internal error (api level) that indicates an incorrect datatype.
     *
     * @var int
     */
    public static $INVALID_DATATYPE = -3; // api error
    /**
     * Internal error that throws if the Rosette API server version does not match the Api version.
     *
     * @var int
     */
    public static $INCOMPATIBLE_VERSION = -6; // api error
    /**
     * Internal error indicating that the requested property does not exist.
     *
     * @var int
     */
    public static $INVALID_PROPERTY_NAME = -7;
}
