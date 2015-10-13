<?php

/**
 * Api.
 *
 * Primary class for interfacing with the Rosette API
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

// autoload classes in the package
set_include_path(get_include_path() . PATH_SEPARATOR . dirname(__DIR__));
spl_autoload_register(function ($class) {
    $class = preg_replace('/.+\\\\/', '', $class);
    require_once $class . '.php';
});

/**
 * Class API.
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
 */
class Api
{
    /**
     * Compatible server version.
     *
     * @var string
     */
    private static $compatible_version = '0.5';
    /**
     * User key (required for Rosette API).
     *
     * @var null|string
     */
    private $user_key;
    /**
     * URL of the Rosette API (or test server).
     *
     * @var string
     */
    private $service_url;
    /**
     * HTTP headers for Rosette API.
     *
     * @var array
     */
    private $headers;
    /**
     * MultiPart status.
     *
     * @var bool
     */
    private $useMultiPart;
    /**
     * True if the version has already been checked.  Saves round trips.
     *
     * @var bool
     */
    private $version_checked;
    /**
     * Endpoint for the operation.
     *
     * @var null|string
     */
    private $subUrl;
    /**
     * Max timeout (seconds).
     *
     * @var
     */
    private $timeout;
    /**
     * Max retries before failing.
     *
     * @var
     */
    private $numRetries;
    /**
     * Last response code.
     *
     * @var
     */
    private $response_code;

    /**
     * Returns response code.
     *
     * @return mixed
     */
    public function getResponseCode()
    {
        return $this->response_code;
    }

    /**
     * Sets the response code.
     *
     * @param mixed $response_code
     */
    public function setResponseCode($response_code)
    {
        $this->response_code = $response_code;
    }

    /**
     * Returns the max timeout value (seconds).
     *
     * @return mixed
     */
    public function getTimeout()
    {
        return $this->timeout;
    }

    /**
     * Sets the max timeout value (seconds).
     *
     * @param mixed $timeout
     */
    public function setTimeout($timeout)
    {
        $this->timeout = $timeout;
    }

    /**
     * Skips version check with server, not recommended.
     */
    public function skipVersionCheck()
    {
        $this->version_checked = true;
    }

    /**
     * Create an L{API} object.
     *
     * @param string $service_url URL of the Api API
     * @param string $user_key    An authentication string to be sent as user_key with
     *                            all requests.
     */
    public function __construct($user_key, $service_url = 'https://api.rosette.com/rest/v1')
    {
        $this->user_key = $user_key;
        $this->service_url = $service_url;
        $this->debug = false;
        $this->useMultiPart = false;
        $this->version_checked = false;
        $this->subUrl = null;
        $this->timeout = 300;
        $this->numRetries = 1;

        $this->headers = array('user_key' => $user_key,
                          'Content-Type' => 'application/json',
                          'Accept' => 'application/json',
                          'Accept-Encoding' => 'gzip', );
    }

    /**
     * Creates the Request option based on current settings.
     */
    private function getOptions()
    {
        $options = array('timeout' => $this->timeout);

        return $options;
    }

    /**
     * Setter to set version_checked.
     *
     * @param bool $version_checked
     */
    public function setVersionChecked($version_checked)
    {
        $this->version_checked = $version_checked;
    }

    /**
     * Enables debug (more verbose output).
     *
     * @param bool $debug
     */
    public function setDebug($debug)
    {
        $this->debug = $debug;
    }

    /**
     * Getter for the user_key.
     *
     * @return null|string
     */
    public function getUserKey()
    {
        return $this->user_key;
    }

    /**
     * Getter for the service_url.
     *
     * @return string
     */
    public function getServiceUrl()
    {
        return $this->service_url;
    }

    /**
     * Getter for MultiPart.
     *
     * @return bool
     */
    public function isUseMultiPart()
    {
        return $this->useMultiPart;
    }

    /**
     * Setter for MultiPart.
     *
     * @param bool $useMultiPart
     */
    public function setUseMultiPart($useMultiPart)
    {
        $this->useMultiPart = $useMultiPart;
    }

    /**
     * Setter for numRetries.
     *
     * @param int numRetries
     */
    public function setNumRetries($numRetries)
    {
        $this->numRetries = $numRetries;
    }

    /**
     * Processes the response, returning either the decoded Json or throwing an exception.
     *
     * @param $resultObject
     * @param $action
     *
     * @return mixed
     *
     * @throws RosetteException
     */
    private function finishResult($resultObject, $action)
    {
        $msg = null;

        if ($this->getResponseCode() === 200) {
            return $resultObject;
        } else {
            if (array_key_exists('message', $resultObject)) {
                $msg = $resultObject['message'];
            }
            $complaint_url = $this->subUrl === null ? 'Top level info' : $action . ' ' . $this->subUrl;
            if (array_key_exists('code', $resultObject)) {
                $serverCode = $resultObject['code'];
                if ($msg === null) {
                    $msg = $serverCode;
                }
            } else {
                $serverCode = RosetteException::$BAD_REQUEST_FORMAT;
                if ($msg === null) {
                    $msg = 'unknown error';
                }
            }

            throw new RosetteException(
                $complaint_url . '
                : failed to communicate with Api: ' . $msg,
                is_numeric($serverCode) ? $serverCode : RosetteException::$BAD_REQUEST_FORMAT
            );
        }
    }

    /**
     * Internal operations processor for most of the endpoints.
     *
     * @param $parameters
     * @param $subUrl
     *
     * @return mixed
     *
     * @throws RosetteException
     */
    private function callEndpoint(RosetteParamsSetBase $parameters, $subUrl)
    {
        $this->subUrl = $subUrl;
        $this->checkVersion();
        if ($this->useMultiPart && $parameters['contentType'] !== RosetteConstants::$DataFormat['SIMPLE']) {
            throw new RosetteException(
                sprintf('MultiPart requires contentType SIMPLE: %s', $parameters['contentType']),
                RosetteException::$BAD_REQUEST_FORMAT
            );
        }
        $url = $this->service_url . '/' . $this->subUrl;
        if ($this->debug) {
            $url .= '?debug=true';
        }
        $resultObject = $this->postHttp($url, $this->headers, $parameters, $this->getOptions());

        return $this->finishResult($resultObject, 'callEndpoint');
    }

    /**
     * Checks the server version against the api (or provided )version.
     *
     * @param $versionToCheck
     *
     * @return bool
     *
     * @throws RosetteException
     */
    public function checkVersion($versionToCheck = null)
    {
        if (!$this->version_checked) {
            if (!$versionToCheck) {
                $versionToCheck = self::$compatible_version;
            }
            $result = $this->info();
            // compatibility with server side is at minor version level of semver
            preg_match('/(^[0-9]+\.[0-9]+)/', $result['version'], $matches);
            $version = $matches[1];
            if ($version !== $versionToCheck) {
                throw new RosetteException(
                    'The server version is not ' . strval($versionToCheck),
                    RosetteException::$INCOMPATIBLE_VERSION
                );
            } else {
                $this->version_checked = true;
            }
        }

        return $this->version_checked;
    }

    /**
     * function retryingRequest.
     *
     * Encapsulates the GET/POST and retries N_RETRIES
     *
     * @param $url
     * @param $context
     *
     * @return string
     *
     * @throws RosetteException
     *
     * @internal param $op : operation
     * @internal param $url : target URL
     * @internal param $headers : header data
     * @internal param data $optional : submission data
     */
    private function retryingRequest($url, $context)
    {
        $response = null;
        $message = null;
        $code = 'unknownError';
        for ($range = 0; $range <= $this->numRetries; ++$range) {
            $http_response_header = null;
            $response = file_get_contents($url, false, $context);
            $response_status = $this->getResponseStatusCode($http_response_header);
            $this->setResponseCode($response_status);
            if (strlen($response) > 3 && mb_strpos($response, "\x1f" . "\x8b" . "\x08", 0) === 0) {
                // a gzipped string starts with ID1(\x1f) ID2(\x8b) CM(\x08)
                // http://www.gzip.org/zlib/rfc-gzip.html#member-format
                $response = gzinflate(substr($response, 10, -8));
            }
            if ($this->getResponseCode() < 500) {
                return json_decode($response, true);
            }
            if ($response !== null) {
                try {
                    $json = json_decode($response, true);
                    if (array_key_exists('message', $json)) {
                        $message = $json['message'];
                    }
                    if (array_key_exists('code', $json)) {
                        $code = $json['code'];
                    }
                } catch (\Exception $e) {
                    // pass
                }
            }
        }
        if ($code === 'unknownError') {
            $message = sprintf('A retryable network operation has not succeeded after %d attempts', $this->numRetries);
        }
        throw new RosetteException($message . ' [' . $url . ']', $code);
    }

    /**
     * The response header that is returned by $http_response_header does not contain an explicit return code;
     * it is in the first array element. This method extracts that code.
     *
     * @param $header_str
     *
     * @return int
     *
     * @throws RosetteException
     */
    public function getResponseStatusCode($header_str)
    {
        // the first line of a HTTP response by spec is the status line that looks like:
        //     HTTP/1.1 200 OK
        // just need to regex out the status code
        $status_line = array_shift($header_str);
        if (preg_match('#^HTTP/1\.[0-9]+\s+([1-5][0-9][0-9])\s+#', $status_line, $out) === 1) {
            return intval($out[1]);
        } else {
            throw new RosetteException('Invalid HTTP response status line: ' . $status_line);
        }
    }

    /**
     * Creates the header string that is acceptable to file_get_contents.
     *
     * @param $headers
     *
     * @return string
     */
    private function headersAsString($headers)
    {
        return implode(
            "\r\n",
            array_map(
                function ($k, $v) {
                    return "$k: $v";
                },
                array_keys($headers),
                array_values($headers)
            )
        );
    }

    /**
     * Standard GET helper.
     *
     * @param $url
     * @param $headers
     * @param $options
     *
     * @return string : JSON string
     *
     * @throws RosetteException
     *
     * @internal param $url : target URL
     * @internal param $headers : header data
     */
    private function getHttp($url, $headers, $options)
    {
        $opts['http']['method'] = 'GET';
        $opts['http']['header'] = $this->headersAsString($headers);
        $opts['http'] = array_merge($opts['http'], $options);
        $context = stream_context_create($opts);

        $response = $this->retryingRequest($url, $context);

        return $response;
    }

    /**
     * Standard POST helper.
     *
     * @param $url
     * @param $headers
     * @param $data
     * @param $options
     *
     * @return string : JSON string
     *
     * @throws RosetteException
     *
     * @internal param $url : target URL
     * @internal param $headers : header data
     * @internal param $data : submission data
     */
    private function postHttp($url, $headers, $data, $options)
    {
        $opts['http']['method'] = 'POST';
        $opts['http']['header'] = $this->headersAsString($headers);
        $opts['http']['content'] = $data->serialize();
        $opts['http'] = array_merge($opts['http'], $options);
        $context = stream_context_create($opts);

        $response = $this->retryingRequest($url, $context);

        return $response;
    }

    /**
     * Calls the Ping endpoint.
     *
     * @return mixed
     *
     * @throws \Rosette\Api\RosetteException
     */
    public function ping()
    {
        $this->skipVersionCheck();
        $url = $this->service_url . '/ping';
        $resultObject = $this->getHttp($url, $this->headers, $this->getOptions());

        return $this->finishResult($resultObject, 'ping');
    }

    /**
     * Calls the info endpoint.
     *
     * @return mixed
     *
     * @throws RosetteException
     */
    public function info()
    {
        $this->skipVersionCheck();
        $url = $this->service_url . '/info';
        $resultObject = $this->getHttp($url, $this->headers, $this->getOptions());

        return $this->finishResult($resultObject, 'info');
    }

    /**
     * Calls the language/info endpoint.
     *
     * @return mixed
     */
    public function languageInfo()
    {
        $this->skipVersionCheck();
        $url = $this->service_url . '/language/info';
        $resultObject = $this->getHttp($url, $this->headers, $this->getOptions());

        return $this->finishResult($resultObject, 'language-info');
    }

    /**
     * Calls the language endpoint.
     *
     * @param $params
     *
     * @return mixed
     *
     * @throws RosetteException
     */
    public function language($params)
    {
        return $this->callEndpoint($params, 'language');
    }

    /**
     * Calls the sentences endpoint.
     *
     * @param $params
     *
     * @return mixed
     *
     * @throws RosetteException
     */
    public function sentences($params)
    {
        return $this->callEndpoint($params, 'sentences');
    }

    /**
     * Calls the tokens endpoint.
     *
     * @param $params
     *
     * @return mixed
     *
     * @throws RosetteException
     */
    public function tokens($params)
    {
        return $this->callEndpoint($params, 'tokens');
    }

    /**
     * Calls the morphology endpoint.
     *
     * @param $params
     * @param null $facet
     *
     * @return mixed
     *
     * @throws RosetteException
     */
    public function morphology($params, $facet = null)
    {
        if (!$facet) {
            $facet = RosetteConstants::$MorphologyOutput['COMPLETE'];
        }

        return $this->callEndpoint($params, 'morphology/' . $facet);
    }

    /**
     * Calls the entities endpoint.
     *
     * @param $params
     * @param $linked
     *
     * @return mixed
     *
     * @throws RosetteException
     */
    public function entities($params, $linked = false)
    {
        return $linked ? $this->callEndpoint($params, 'entities/linked') : $this->callEndpoint($params, 'entities');
    }

    /**
     * Calls the categories endpoint.
     *
     * @param $params
     *
     * @return mixed
     *
     * @throws RosetteException
     */
    public function categories($params)
    {
        return $this->callEndpoint($params, 'categories');
    }

    /**
     * Calls the sentiment endpoint.
     *
     * @param $params
     *
     * @return mixed
     *
     * @throws RosetteException
     */
    public function sentiment($params)
    {
        return $this->callEndpoint($params, 'sentiment');
    }

    /**
     * Calls the name translation endpoint.
     *
     * @param $nameTranslationParams
     *
     * @return mixed
     *
     * @throws RosetteException
     */
    public function translatedName($nameTranslationParams)
    {
        return $this->callEndpoint($nameTranslationParams, 'translated-name');
    }

    /**
     * Calls the name matching endpoint.
     *
     * @param $nameMatchingParams
     *
     * @return mixed
     *
     * @throws RosetteException
     */
    public function matchedName($nameMatchingParams)
    {
        return $this->callEndpoint($nameMatchingParams, 'matched-name');
    }

    /**
     * Calls the relationships/info endpoint.
     *
     * @return mixed
     */
    public function relationshipsInfo()
    {
        $this->skipVersionCheck();
        $url = $this->service_url . '/relationships/info';
        $resultObject = $this->getHttp($url, $this->headers, $this->getOptions());

        return $this->finishResult($resultObject, 'relationships-info');
    }

    /**
     * Calls the relationships endpoint.
     *
     * @param $params
     *
     * @return mixed
     *
     * @throws RosetteException
     */
    public function relationships($params)
    {
        return $this->callEndpoint($params, 'relationships');
    }
}
