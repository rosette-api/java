<?php
/**
 * ReturnObject encapsulates the response from the Rosette API
 *
 * @copyright 2014-2015 Basis Technology Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * @license http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 **/
namespace rosette\api;

/**
 * Class ReturnObject
 * @package rosette\api
 */
class ReturnObject
{
    /**
     * Decoded JSON message body
     * @var mixed
     */
    public $jsonDecoded;
    /**
     * Original JSON
     * @var
     */
    public $json;
    /**
     * Response status code
     * @var
     */
    public $statusCode;

    /**
     * Constructor
     *
     * @param $response
     */
    public function __construct($response)
    {
        $this->jsonDecoded = [];
        if (strlen($response->body) > 3
            && mb_strpos($response->body, "\x1f" . "\x8b" . "\x08", 0, "US-ASCII") === 0) {
            $responseData = zlib_decode($response->body, ZLIB_ENCODING_DEFLATE);
            $this->json = $responseData;
            $this->jsonDecoded = json_decode($responseData, true);
        } else {
            $this->json = $response->body;
            $this->jsonDecoded = json_decode($response->body, true);
        }
        $this->statusCode = $response->status_code;
    }

    /**
     * Magic method to return the stringized form of ReturnObject
     * @return mixed
     */
    public function __toString()
    {
        return $this->json;
    }
}
