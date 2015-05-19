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
 * of either a RosetteParameters object or an RNTParameters object.  These
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
     * Create an L{API} object.
     *
     * @param string $service_url URL of the Api API
     * @param string $user_key    (Optional; required for servers requiring
     * authentication.) An authentication string to be sent as user_key with
     * all requests.  The default Api server requires authentication to
     * the server.
     */
    public function __construct($service_url = "http://api.rosette.com/rest/v1", $user_key = null)
    {
        spl_autoload_extensions('.php');
        spl_autoload_register();

        $this->user_key = $user_key;
        $this->service_url = $service_url;
        $this->debug = false;
        $this->useMultiPart = false;
        $this->version_checked = false;
        $this->subUrl = null;
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
        $resultObject = GetHttp($url, $headers);
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

        if ($resultObject->statusCode == 200) {
            return $resultObject->jsonDecoded;
        } else {
            if (strpos($resultObject, 'message') !== false) {
                $msg = $resultObject->jsonDecoded['message'];
            }
            $complaint_url = $this->subUrl == null ? "Top level info" : $action . ' ' . $this->subUrl;
            if (strpos($resultObject, 'code') !== false) {
                $serverCode = $resultObject->jsonDecoded['code'];
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
        $resultObject = GetHttp($url, $headers);
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

        $resultObject = PostHttp($url, $headers, $paramsToSerialize);

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
        $resultObject = GetHttp($url, $headers);

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
     * @param $rntParams
     * @return mixed
     * @throws RosetteException
     */
    public function translatedName($rntParams)
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
    $numberRetries = 3;
    $response = null;
    for ($range = 0; $range < $numberRetries; $range++) {
        if ($op == "GET") {
            $response = \Requests::get($url, $headers);
        } else {
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
        } catch (\Exception $e) {
            // pass
        }
    }
    if ($message == null) {
        $message = sprintf("A retryable network operation has not succeeded after %d attempts", $numberRetries);
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
