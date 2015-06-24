<?php
/**
 * Api
 *
 * Primary class for interfacing with the Rosette API
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
 * Class API
 *
 * Api Python Client Binding API; representation of a Api server.
 * Call instance methods upon this object to communicate with particular
 * Api server endpoints.
 * Aside from ping() and info(), most of the methods require the construction
 * of either a DocumentParameters object or an NameTranslationParameters object.  These
 * provide the content data that will be processed by the service.
 *
 * usage example: $api = new API($service_url, $user_key)
 *
 * @see _construct()
 *
 */

class Api
{
    /**
     * User key (required for Rosette API)
     * @var null|string
     */
    private $user_key;
    /**
     * URL of the Rosette API (or test server)
     * @var string
     */
    private $service_url;
    /**
     * MultiPart status
     * @var bool
     */
    private $useMultiPart;
    /**
     * True if the version has already been checked.  Saves round trips.
     * @var bool
     */
    private $version_checked;
    /**
     * Endpoint for the operation
     * @var null|string
     */
    private $subUrl;
    /**
     * Max timeout (seconds)
     * @var
     */
    private $timeout;

    /**
     * Returns the max timeout value (seconds)
     * @return mixed
     */
    public function getTimeout()
    {
        return $this->timeout;
    }

    /**
     * Sets the max timeout value (seconds)
     * @param mixed $timeout
     */
    public function setTimeout($timeout)
    {
        $this->timeout = $timeout;
    }

    /**
     * Create an L{API} object.
     *
     * @param string $service_url URL of the Api API
     * @param string $user_key  An authentication string to be sent as user_key with
     * all requests.
     */
    public function __construct($user_key, $service_url = "https://api.rosette.com/rest/v1")
    {
        spl_autoload_extensions('.php');
        spl_autoload_register();

        $this->user_key = $user_key;
        $this->service_url = $service_url;
        $this->debug = false;
        $this->useMultiPart = false;
        $this->version_checked = false;
        $this->subUrl = null;
        $this->timeout = 300;
    }

    /**
     * Creates the Request option based on current settings
     */
    private function getOptions()
    {
        $options = ['timeout' => $this->timeout];
        return $options;
    }

    /**
     * Setter to set version_checked
     * @param boolean $version_checked
     */
    public function setVersionChecked($version_checked)
    {
        $this->version_checked = $version_checked;
    }

    /**
     * Getter for the user_key
     * @return null|string
     */
    public function getUserKey()
    {
        return $this->user_key;
    }

    /**
     * Getter for the service_url
     * @return string
     */
    public function getServiceUrl()
    {
        return $this->service_url;
    }

    /**
     * Getter for MultiPart
     * @return boolean
     */
    public function isUseMultiPart()
    {
        return $this->useMultiPart;
    }

    /**
     * Setter for MultiPart
     * @param boolean $useMultiPart
     */
    public function setUseMultiPart($useMultiPart)
    {
        $this->useMultiPart = $useMultiPart;
    }

    /**
     * Calls the Ping endpoint
     * @return mixed
     * @throws \Rosette\Api\RosetteException
     */
    public function ping()
    {
        $url = $this->service_url.'/ping';
        $headers = ['Accept' => 'application/json', 'Content-Type' => 'application/json'];
        if ($this->user_key) {
            $headers['user_key'] = $this->user_key;
        }
        $resultObject = $this->getHttp($url, $headers, $this->getOptions());
        return $this->finishResult($resultObject, 'ping');
    }

    /**
     * Processes the response, returning either the decoded Json or throwing an exception
     * @param $resultObject
     * @param $action
     * @return mixed
     * @throws RosetteException
     */
    private function finishResult($resultObject, $action)
    {
        $msg = null;

        if ($resultObject['response_code'] == 200) {
            return $resultObject;
        } else {
            if (array_key_exists('message', $resultObject)) {
            //if (strpos($resultObject, 'message') !== false) {
                $msg = $resultObject['message'];
            }
            $complaint_url = $this->subUrl == null ? "Top level info" : $action . ' ' . $this->subUrl;
            if (array_key_exists('code', $resultObject)) {
            //if (strpos($resultObject, 'code') !== false) {
                $serverCode = $resultObject['code'];
                if ($msg == null) {
                    $msg = $serverCode;
                }
            } else {
                $serverCode = RosetteException::$BAD_REQUEST_FORMAT;
                if ($msg == null) {
                    $msg = 'unknown error';
                }
            }
            //echo "FinishResult\n";

            throw new RosetteException(
                $complaint_url.'
                : failed to communicate with Api: '.$msg,
                is_numeric($serverCode) ? $serverCode : RosetteException::$BAD_REQUEST_FORMAT
            );
        }
    }

    /**
     * Calls the language/info endpoint
     * @return mixed
     */
    public function languageInfo()
    {
        $url = $this->service_url.'/language/info';
        $headers = ['Accept' => 'application/json', 'Content-Type' => 'application/json'];
        if ($this->user_key) {
            $headers['user_key'] = $this->user_key;
        }
        $resultObject = $this->getHttp($url, $headers, $this->getOptions());
        return $this->finishResult($resultObject, 'language-info');
    }

    /**
     * Calls the language endpoint
     * @param $params
     * @return mixed
     * @throws RosetteException
     */
    public function language($params)
    {
        return $this->operate($params, 'language');
    }

    /**
     * Internal operations processor for most of the endpoints
     * @param $parameters
     * @param $subUrl
     * @return mixed
     * @throws RosetteException
     */
    private function operate(RosetteParamsSetBase $parameters, $subUrl)
    {
        $this->subUrl = $subUrl;
        $this->checkVersion();
        if ($this->useMultiPart && $parameters['contentType'] != RosetteConstants::$DataFormat['SIMPLE']) {
            throw new RosetteException(
                sprintf("MultiPart requires contentType SIMPLE: %s", $parameters['contentType']),
                RosetteException::$BAD_REQUEST_FORMAT
            );
        }
        $url = $this->service_url.'/'.$this->subUrl;
        $paramsToSerialize = $parameters->serializable();

        $headers = ['Accept' => 'application/json', 'Accept-Encoding' => 'gzip'];
        if ($this->user_key) {
            $headers['user_key'] = $this->user_key;
        }
        $headers['Content-Type'] = 'application/json';

        $resultObject = $this->postHttp($url, $headers, $paramsToSerialize, $this->getOptions());

        return $this->finishResult($resultObject, "operate");
    }

    /**
     * Checks the server version against the api (or provided )version
     * @param $versionToCheck
     * @return bool
     * @throws RosetteException
     */
    public function checkVersion($versionToCheck = null)
    {
        if (!$this->version_checked) {
            if (!$versionToCheck) {
                $versionToCheck = '0.5';
            }
            $result = $this->info();
            $version = substr($result['version'], 0, 3);
            if ($version != $versionToCheck) {
                throw new RosetteException(
                    "The server version is not " . strval($versionToCheck),
                    RosetteException::$BAD_SERVER_VERSION
                );
            } else {
                $this->version_checked = true;
            }
        }
        return $this->version_checked;
    }

    /**
     * Calls the info endpoint
     * @return mixed
     * @throws RosetteException
     */
    public function info()
    {
        $url = $this->service_url.'/info';

        $headers = ['Accept' => 'application/json', 'Content-Type' => 'application/json'];
        if ($this->user_key) {
            $headers['user_key'] = $this->user_key;
        }
        $resultObject = $this->getHttp($url, $headers, $this->getOptions());

        return $this->FinishResult($resultObject, 'info');
    }

    /**
     * Calls the sentences endpoint
     * @param $params
     * @return mixed
     * @throws RosetteException
     */
    public function sentences($params)
    {
        return $this->operate($params, "sentences");
    }

    /**
     * Calls the tokens endpoint
     * @param $params
     * @return mixed
     * @throws RosetteException
     */
    public function tokens($params)
    {
        return $this->operate($params, "tokens");
    }

    /**
     * Calls the morphology endpoint
     * @param $params
     * @param null $facet
     * @return mixed
     * @throws RosetteException
     */
    public function morphology($params, $facet = null)
    {
        if (!$facet) {
            $facet = RosetteConstants::$MorphologyOutput['COMPLETE'];
        }
        return $this->operate($params, "morphology/".$facet);
    }

    /**
     * Calls the entities endpoint
     * @param $params
     * @param $linked
     * @return mixed
     * @throws RosetteException
     */
    public function entities($params, $linked = null)
    {
        return $linked ? $this->operate($params, "entities/linked") : $this->operate($params, "entities");
    }

    /**
     * Calls the categories endpoint
     * @param $params
     * @return mixed
     * @throws RosetteException
     */
    public function categories($params)
    {
        return $this->operate($params, "categories");
    }

    /**
     * Calls the sentiment endpoint
     * @param $params
     * @return mixed
     * @throws RosetteException
     */
    public function sentiment($params)
    {
        return $this->operate($params, "sentiment");
    }

    /**
     * Calls the name translation endpoint
     * @param $nameTranslationParams
     * @return mixed
     * @throws RosetteException
     */
    public function translatedName($nameTranslationParams)
    {
        return $this->operate($nameTranslationParams, "translated-name");
    }

    /**
     * Calls the name matching endpoint
     * @param $nameMatchingParams
     * @return mixed
     * @throws RosetteException
     */
    public function matchedName($nameMatchingParams)
    {
        return $this->operate($nameMatchingParams, "matched-name");
    }

    /**
     * function retryingRequest
     *
     * Encapsulates the GET/POST and retries N_RETRIES
     *
     * @param $url
     * @param $context
     * @return null|\Requests_Response object or RosetteException
     * @throws RosetteException
     * @internal param $op : operation
     * @internal param $url : target URL
     * @internal param $headers : header data
     * @internal param data $optional : submission data
     */
    private function retryingRequest($url, $context)
    {
        $numberRetries = 3;
        $response = null;
        for ($range = 0; $range < $numberRetries; $range++) {
            $response = file_get_contents($url, false, $context);
            $responseHeader = $this->parseHeaders($http_response_header);
            if ($responseHeader['response_code'] < 500) {
                $response = json_decode($response, true);
                $response['response_code'] = $responseHeader['response_code'];
                return $response;
            }
        }
        $message = null;
        $code = 'unknown error';
        if ($response != null) {
            try {
                $json = json_decode($response, true);
                if (array_key_exists('message', $json)) {
                    $message = $json["message"];
                }
                if (array_key_exists('code', $json)) {
                    $code = $json["code"];
                }
            } catch (\Exception $e) {
                // pass
            }
        }
        if ($message == null) {
            $message = sprintf("A retryable network operation has not succeeded after %d attempts", $numberRetries);
        }
        throw new RosetteException($message . ' [' . $url . ']', $code);
    }

    /**
     * Parses the header response and adds a response code
     * @param $headers
     * @return array
     */
    private function parseHeaders($headers)
    {
        $head = [];
        foreach ($headers as $k => $v) {
            $t = explode(':', $v, 2);
            if (isset($t[1])) {
                $head[trim($t[0])] = trim($t[1]);
            } else {
                $head[] = $v;
                if (preg_match("#HTTP/[0-9\.]+\s+([0-9]+)#", $v, $out)) {
                    $head['response_code'] = intval($out[1]);
                }
            }
        }
        return $head;
    }

    /**
     * @param $headers
     * @return string
     */
    private function headersAsString($headers)
    {
        $s = "";
        $first = true;
        foreach ($headers as $key => $value) {
            if (!$first) {
                $s = $s . "\r\n";
            }
            $s = $s . $key . ': ' . $value;
            $first = false;
        }

        return $s;
    }

    /**
     * Formats an array according to the required format
     * @param $data
     * @return string
     */
    private function arrayAsContent($data)
    {
        $s = "{";
        $first = true;
        foreach ($data as $key => $value) {
            if ($value != null) {
                if (!$first) {
                    $s = $s.',';
                }
                $s = $s.'"'.$key.'":"'.$value.'"';
                $first = false;
            }
        }
        $s = $s.'}';
        return $s;
    }


    /**
     * Standard GET helper
     *
     * @param $url
     * @param $headers
     * @param $options
     * @return ReturnObject
     * @throws RosetteException
     * @internal param $url : target URL
     * @internal param $headers : header data
     *
     */
    private function getHttp($url, $headers, $options)
    {
        $opts['http']['method'] = 'GET';
        $opts['http']['header'] = $this->headersAsString($headers);
        $opts['http'] = array_merge($opts['http'], $options);
        $context = stream_context_create($opts);
        //$response = file_get_contents($url, false, $context);

        //$responseCode = $this->parseHeaders($http_response_header)['response_code'];
        $response = $this->retryingRequest($url, $context);
        return $response;
    }

    /**
     * Standard POST helper
     *
     * @param $url
     * @param $headers
     * @param $data
     * @param $options
     * @return ReturnObject
     * @throws RosetteException
     * @internal param $url : target URL
     * @internal param $headers : header data
     * @internal param $data : submission data
     *
     */
    private function postHttp($url, $headers, $data, $options)
    {
        $opts['http']['method'] = 'POST';
        $opts['http']['header'] = $this->headersAsString($headers);
        $opts['http']['content'] = $this->arrayAsContent($data);
        $opts['http'] = array_merge($opts['http'], $options);
        $context = stream_context_create($opts);

        $response =$this->retryingRequest($url, $context);
        return new ReturnObject($response);
    }

}


