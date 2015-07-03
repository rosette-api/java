<?php
/**
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

// It is better to use phpunit --bootstrap ./vendor/autoload.php than to play with
// the pathing.
require_once __DIR__ . '/../../../vendor/autoload.php';

/**
 * Class ApiTest
 * @package rosette\api
 */
class ApiTest extends \PHPUnit_Framework_TestCase
{
    private $userKey = null;
    private static $requestDir = __DIR__ . '/../../../../mock-data/request/';
    private static $responseDir = __DIR__ . '/../../../../mock-data/response/';

    /**
     * Find the correct response file from the mock-data directory
     * Used to replace the retryingRequest function for mocking
     * @param $filename
     * @return mixed|string
     */
    private function getMockedResponse($filename)
    {
        $response = file_get_contents(self::$responseDir . $filename . '.json');
        $response = json_decode($response, true);
        return $response;
    }

    /**
     * Replace the getResponseCode method in the API class for mocking purposes
     * @param $filename
     * @return int
     */
    private function getStatusCode($filename)
    {
        return intval(file_get_contents(self::$responseDir . $filename . '.status'));
    }

    /**
     * Mock the api so that everything still works except the retryingRequest and getResponseCode methods are overridden
     * setResponseCode and checkVersion are stubbed so they just return null
     * @param $userKey
     * @return mixed
     */
    private function setUpApi($userKey)
    {
        $api = $this->getMockBuilder('rosette\api\Api')
                    ->setConstructorArgs([$userKey])
                    ->setMethods(['retryingRequest', 'setResponseCode', 'getResponseCode', 'checkVersion'])
                    ->getMock();
        $api->expects($this->any())
            ->method('retryingRequest')
            ->will($this->returnValue($this->getMockedResponse($userKey)));
        $api->expects($this->any())
            ->method('getResponseCode')
            ->will($this->returnValue($this->getStatusCode($userKey)));
        return $api;
    }

    public function testInfo()
    {
        $expected = $this->getMockedResponse('info');
        $this->userKey = 'info';
        $api = $this->setUpApi($this->userKey);
        $result = $api->info();
        $this->assertSame($expected, $result);
    }

    public function testPing()
    {
        $expected = $this->getMockedResponse('ping');
        $this->userKey = 'ping';
        $api = $this->setUpApi($this->userKey);
        $result = $api->ping();
        $this->assertSame($expected, $result);
    }

    /**
     * Get the file body for a request given a partial file name
     * @param $filename
     * @return mixed
     */
    private function getRequest($filename)
    {
        $request = file_get_contents(self::$requestDir . $filename . '.json');
        return json_decode($request, true);
    }

    /**
     * Return an array of arrays to be passed to testLanguages
     * Each sub array is of the form [file name (after request/ and before .json), endpoint]
     * @return array
     */
    public function findFiles()
    {
        $pattern = "/.*\/request\/([\w\d]*-[\w\d]*-(.*))\.json/";
        $files = [];
        foreach (glob(self::$requestDir . "*.json") as $filename) {
            preg_match($pattern, $filename, $output_array);
            $files[] = [$output_array[1], $output_array[2]];
        }
        return $files;
    }


    /**
     * Test all endpoints (other than ping and info)
     * @dataProvider findFiles
     * @param $filename
     * @param $endpoint
     */
    public function testEndpoints($filename, $endpoint)
    {
        // Set user key as file name because a real user key is unnecessary for testing
        $this->userKey = $filename; // ex 'eng-sentence-language';
        // Set up a mocked api to test against
        $api = $this->setUpApi($this->userKey);
        $input = $this->getRequest($this->userKey);
        $expected = $this->getMockedResponse($this->userKey);
        if ($endpoint == 'matched-name') {
            $name1 = new Name(
                $input["name1"]["text"],
                $input["name1"]["entityType"],
                $input["name1"]["language"],
                $input["name1"]["script"]
            );
            $name2 = new Name(
                $input["name2"]["text"],
                $input["name2"]["entityType"],
                $input["name2"]["language"],
                $input["name2"]["script"]
            );
            $params = new NameMatchingParameters($name1, $name2);
        } else {
            if ($endpoint == 'translated-name') {
                $params = new NameTranslationParameters();
            } else {
                $params = new DocumentParameters();
            }
            // Fill in parameters object with data if it is not matched-name (because those parameters are formatted
            // differently and handled when the object is created.
            foreach (array_keys($input) as $key) {
                $params->params[$key] = $input[$key];
            }
        }

        // Find the correct call to make and call it.
        // If it does not throw an exception, check that it was not supposed to and if so check that it
        // returns the correct thing.
        // If it throws an exception, check that it was supposed to and if so pass otherwise fail test.
        try {
            $result = '';
            if ($endpoint === "categories") {
                $result = $api->categories($params);
            }
            if ($endpoint === "entities") {
                $result = $api->entities($params);
            }
            if ($endpoint === "entities_linked") {
                $result = $api->entities($params, true);
            }
            if ($endpoint === "language") {
                $result = $api->language($params);
            }
            if ($endpoint === "matched-name") {
                $result = $api->matchedName($params);
            }
            if ($endpoint === "morphology_complete") {
                $result = $api->morphology($params);
            }
            if ($endpoint === "sentiment") {
                $result = $api->sentiment($params);
            }
            if ($endpoint === "translated-name") {
                $result = $api->translatedName($params);
            }
            // If there is a "code" key, it means an exception should be thrown
            if (!array_key_exists("code", $expected)) {
                $this->assertSame($expected, $result);
            }
        } catch (RosetteException $exception) {
            $this->assertSame("unsupportedLanguage", $expected["code"]);
        }
    }
}
