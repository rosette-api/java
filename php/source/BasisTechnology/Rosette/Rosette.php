<?php
namespace BasisTechnology\Rosette;

    /**
     * This data and information is proprietary to, and a valuable trade secret
     * of, Basis Technology Corp.  It is given in confidence by Basis Technology
     * and may only be used as permitted under the license agreement under which
     * it has been distributed, and in no other way.
     *
     * @copyright (c) 2015 Basis Technology Corporation All rights reserved.
     *
     * The technical data and information provided herein are provided with
     * `limited rights', and the computer software provided herein is provided
     * with `restricted rights' as those terms are defined in DAR and ASPR
     * 7-104.9(a).
     *
     */
    //require_once 'RosetteException.php';
    //require_once 'ReturnObject.php';
    //require_once 'RosetteParameters.php';
    //require_once 'RNTParameters.php';

    // Include the HTTP helper library and register the methods
    require_once dirname(__DIR__).'/../../vendor/rmccue/requests/library/Requests.php';
    require_once 'RosetteParameters.php';
    require_once 'CustomException.php';
    require_once 'ReturnObject.php';
    require_once 'RosetteException.php';
    require_once 'RNTParameters.php';

    \Requests::register_autoloader();

    define('_ACCEPTABLE_SERVER_VERSION', "0.5");
    define('N_RETRIES', 3);

    $GZIP_SIGNATURE = implode('', array_map("chr", [0x1f, 0x8b, 0x08]));


    /**
     * Class API
     *
     * Rosette Python Client Binding API; representation of a Rosette server. Call instance methods upon this object to communicate with particular Rosette server endpoints.
     *
     * usage example: $api = new API($service_url, $user_key)
     * @see _construct()
     *
     * Aside from Ping() and Info(), most of the methods require the construction of either a RosetteParameters object or
     * an RNTParameters object.  These provide the content data that will be processed by the service.
     *
     */
    class API
    {
        private $user_key;
        private $service_url;
        private $debug;
        private $useMultiPart;
        private $version_checked;
        private $subUrl;

        /**
         * Create an L{API} object.
         *
         * @param string $service_url
         * @param user_key : (Optional; required for servers requiring authentication.) An authentication string to be sent
         * as user_key with all requests.  The default Rosette server requires authentication to the server.
         *
         */
        public function __construct($service_url="http://api.rosette.com/rest/v1", $user_key = null)
        {
            $this->user_key = $user_key;
            $this->service_url = $service_url;
            $this->debug = false;
            $this->useMultiPart = false;
            $this->version_checked = false;
            $this->subUrl = null;
        }

        /**
         * @param boolean $version_checked
         */
        public function setVersionChecked($version_checked)
        {
            $this->version_checked = $version_checked;
        }

        /**
         * @return null
         */
        public function getUserKey()
        {
            return $this->user_key;
        }

        /**
         * @return string
         */
        public function getServiceUrl()
        {
            return $this->service_url;
        }

        /**
         * @return boolean
         */
        public function isUseMultiPart()
        {
            return $this->useMultiPart;
        }

        /**
         * @param boolean $useMultiPart
         */
        public function setUseMultiPart($useMultiPart)
        {
            $this->useMultiPart = $useMultiPart;
        }

        /**
         * @return mixed
         * @throws RosetteException
         */
        public function Ping()
        {
            $url = $this->service_url.'/ping';
            $headers = ['Accept' => 'application/json', 'Content-Type' => 'application/json'];
            if ($this->user_key) {
                $headers['user_key'] = $this->user_key;
            }
            $resultObject = GetHttp($url, $headers);
            return $this->finishResult($resultObject, 'ping');
        }

        /**
         * @param $resultObject
         * @param $action
         * @return mixed
         * @throws RosetteException
         */
        private function finishResult($resultObject, $action)
        {
            $msg = null;

            if ($resultObject->statusCode == 200) {
                return $resultObject->jsonDecoded;
            }
            else {
                if (strpos($resultObject, 'message') !== false) {
                    $msg = $resultObject->jsonDecoded['message'];
                }

                $complaint_url = $this->subUrl == null ? "Top level info" : $action . ' ' . $this->subUrl;
                if (strpos($resultObject, 'code') !== false) {
                    $serverCode = $resultObject->jsonDecoded['code'];
                    if ($msg == null) {
                        $msg = $serverCode;
                    }
                }
                else {
                    $serverCode = RosetteException::$UNKNOWN_ERROR;
                    if ($msg == null) {
                        $msg = 'unknown error';
                    }
                }
                //echo "FinishResult\n";

                throw new RosetteException($complaint_url.' : failed to communicate with Rosette: '.$msg, is_numeric($serverCode) ? $serverCode : RosetteException::$UNKNOWN_ERROR);
            }
        }

        /**
         * @return mixed
         */
        public function LanguageInfo()
        {
            $url = $this->service_url.'/language/info';
            $headers = ['Accept' => 'application/json', 'Content-Type' => 'application/json'];
            if ($this->user_key) {
                $headers['user_key'] = $this->user_key;
            }
            $resultObject = GetHttp($url, $headers);
            return $this->finishResult($resultObject, 'language-info');
        }

        /**
         * @param $params
         * @return mixed
         * @throws RosetteException
         */
        public function Language($params)
        {
            return $this->operate($params, 'language');
        }

        /**
         * @param $parameters
         * @param $subUrl
         * @return mixed
         * @throws RosetteException
         */
        private function operate(RosetteParamsSetBase $parameters, $subUrl)
        {
            $this->subUrl = $subUrl;
            $this->CheckVersion();
            if ($this->useMultiPart && $parameters['contentType'] != RosetteConstants::$DataFormat['SIMPLE']) {
                throw new RosetteException(sprintf("MultiPart requires contentType SIMPLE: %s", $parameters['contentType']), RosetteException::$INCOMPATIBLE);
            }
            $url = $this->service_url.'/'.$this->subUrl;
            $paramsToSerialize = $parameters->Serializable();

            $headers = ['Accept' => 'application/json', 'Accept-Encoding' => 'gzip'];
            if ($this->user_key) {
                $headers['user_key'] = $this->user_key;
            }
            $headers['Content-Type'] = 'application/json';

            $resultObject = PostHttp($url, $headers, $paramsToSerialize);

            return $this->finishResult($resultObject, "operate");
        }

        /**
         * @param $versionToCheck
         * @return bool
         * @throws RosetteException
         */
        public function CheckVersion($versionToCheck = null)
        {
            if (!$this->version_checked) {
                if (!$versionToCheck) {
                    $versionToCheck = _ACCEPTABLE_SERVER_VERSION;
                }
                $result = $this->Info();
                $version = substr($result['version'], 0, 3);
                if ($version != $versionToCheck) {
                    throw new RosetteException("The server version is not " . strval($versionToCheck), RosetteException::$BAD_SERVER_VERSION);
                }
                else {
                    $this->version_checked = TRUE;
                }
            }
            return $this->version_checked;
        }

        /**
         * @return mixed
         * @throws RosetteException
         */
        public function Info()
        {
            $url = $this->service_url.'/info';

            $headers = ['Accept' => 'application/json', 'Content-Type' => 'application/json'];
            if ($this->user_key) {
                $headers['user_key'] = $this->user_key;
            }
            $resultObject = GetHttp($url, $headers);

            return $this->FinishResult($resultObject, 'info');
        }

        /**
         * @param $params
         * @return mixed
         * @throws RosetteException
         */
        public function Sentences($params)
        {
            return $this->operate($params, "sentences");
        }

        /**
         * @param $params
         * @return mixed
         * @throws RosetteException
         */
        public function Tokens($params) {
            return $this->operate($params, "tokens");
        }

        /**
         * @param $params
         * @param null $facet
         * @return mixed
         * @throws RosetteException
         */
        public function Morphology($params, $facet = null)
        {
            if (!$facet) {
                $facet = RosetteConstants::$MorphologyOutput['COMPLETE'];
            }
            return $this->operate($params, "morphology/".$facet);
        }

        /**
         * @param $params
         * @param $linked
         * @return mixed
         * @throws RosetteException
         */
        public function Entities($params, $linked = null)
        {
            return $linked ? $this->operate($params, "entities/linked") : $this->operate($params, "entities");
        }

        /**
         * @param $params
         * @return mixed
         * @throws RosetteException
         */
        public function Categories($params)
        {
            return $this->operate($params, "categories");
        }

        /**
         * @param $params
         * @return mixed
         * @throws RosetteException
         */
        public function Sentiment($params)
        {
            return $this->operate($params, "sentiment");
        }

        /**
         * @param $rntParams
         * @return mixed
         * @throws RosetteException
         */
        public function TranslatedName($rntParams)
        {
            return $this->operate($rntParams, "translated-name");
        }

    }

    /**
     * function RetryingRequest
     *
     * Encapsulates the GET/POST and retries N_RETRIES
     *
     * @param $op
     * @param $url
     * @param $headers
     * @param string $data
     * @return null|\Requests_Response object or RosetteException
     * @throws RosetteException
     * @internal param $op : operation
     * @internal param $url : target URL
     * @internal param $headers : header data
     * @internal param data $optional : submission data
     */
    function RetryingRequest($op, $url, $headers, $data = "")
    {
        $response = null;
        for ($range = 0; $range < N_RETRIES; $range++) {
            if ($op == "GET") {
                $response = \Requests::get($url, $headers);
            }
            else {
                $response = \Requests::post($url, $headers, $data);
            }
            if ($response->status_code < 500) {
                return $response;
            }
        }
        $message = null;
        $code = 'unknown error';
        if ($response != null) {
            try {
                $json = json_decode($response->body);
                if (strpos($json, "message") === true) {
                    $message = $json["message"];
                }
                if (strpos($json, "code") === true) {
                    $code = $json["code"];
                }
            }
            catch (\Exception $e) {
                // pass
            }
        }
        if ($message == null) {
            $message = sprintf("A retryable network operation has not succeeded after %d attempts", N_RETRIES);
        }
        throw new RosetteException($message.' ['.$url.']', $code);
    }

    /**
     * Standard GET helper
     *
     * @param url: target URL
     * @param headers: header data
     *
     * @return ReturnObject
     */
    function GetHttp($url, $headers)
    {
        $response = RetryingRequest("GET", $url, $headers);
        return new ReturnObject($response);
    }

    /**
     * Standard POST helper
     *
     * @param url: target URL
     * @param headers: header data
     * @param data: submission data
     *
     * @return ReturnObject
     */
    function PostHttp($url, $headers, $data)
    {
        //echo $url."\n";
        $data = $data == null ? "" : json_encode($data);

        $response = RetryingRequest("POST", $url, $headers, $data);

        return new ReturnObject($response);
    }


