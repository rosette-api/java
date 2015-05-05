<?php
namespace BasisTechnology\Rosette;

/**
 * This data and information is proprietary to, and a valuable trade secret
 *  of, Basis Technology Corp.  It is given in confidence by Basis Technology
 * and may only be used as permitted under the license agreement under which
 * it has been distributed, and in no other way.
 *
 * @copyright (c) 2015 Basis Technology Corporation All rights reserved.
 *
 * The technical data and information provided herein are provided with
 * `limited rights', and the computer software provided herein is provided
 * with `restricted rights' as those terms are defined in DAR and ASPR
 * 7-104.9(a).
 */

    /**
    * class ReturnObject
    *
    * Encapsulates the Request response object by providing
    * public property jsonDecoded: decoded json data
    * public property json: response body
    * public property statusCode: response status_code
    * The class itself resolves to the decoded json data
    */
    class ReturnObject
    {
        public $jsonDecoded;
        public $json;
        public $statusCode;

        /**
        * @param $response
        */
        public function __construct($response) {
            $this->jsonDecoded = [];
            if (strlen($response->body) > 3 && substr($response->body, 0, 3) == $GLOBALS['GZIP_SIGNATURE']) {
                $responseData = gzuncompress($response->body);
                $this->json = $responseData;
                $this->jsonDecoded = json_decode($responseData, true);
            }
            else {
                $this->json = $response->body;
                $this->jsonDecoded = json_decode($response->body, true);
            }
            $this->statusCode = $response->status_code;
        }

        public function __toString() {
            return $this->json;
        }
    }