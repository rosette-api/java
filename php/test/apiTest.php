<?php
/**
 * @license This data and information is proprietary to, and a valuable trade secret
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
use BasisTechnology\Rosette\API;
use BasisTechnology\Rosette\RNTParameters;
use BasisTechnology\Rosette\RosetteConstants;
use BasisTechnology\Rosette\RosetteParameters;

require dirname(__DIR__).'/source/BasisTechnology/Rosette/Rosette.php';
require dirname(__DIR__).'/vendor/autoload.php';

/**
 * The unit tests take advantage HTTP Mock for PHP to create a temporary php server at localhost:80.  Attempts to
 * run it on a different port failed and time limits prevented further investigation.  There were several edits made to
 * the HTTP Mock Server for PHP code to get it to work.
 *
 * Command line: from the root directory (source, test, etc are children) run, phpunit -v ./test/apiTest.php
 *
 * To run the test against a live server, edit the $testUrl and $userKey.  The default is the built-in server.
 */

static $XHTML = <<<EOD
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
    <html xmlns="http://www.w3.org/1999/xhtml">
    
    <head>
      <title>Precipitation Paradigms on the Iberian Peninsula</title>
    </head>
    
    <body>
       The reign in Spain falls mainly upon the planes.
    </body>
    
    </html>
EOD;
    
    
static $HAM_SENTENCE = "Yes, Ma'm! Green eggs and ham?  I am Sam;  I filter Spam.";

static $MORPHO_EXPECTED_POSES = ['NOUN', 'CM', 'NOUN', 'VBPRES', 'SENT', 'ADJ', 'NOUN', 'COORD', 'NOUN', 'SENT',
                         'PRONPERS', 'VBPRES', 'PROP', 'SENT', 'PRONPERS', 'VI', 'PROP', 'SENT'];
static $MORPHO_EXPECTED_LEMMAS = ['yes', ',', 'ma', 'be', '!', 'green', 'egg', 'and', 'ham', '?', 'I', 'be',
                          'Sam', ';', 'I', 'filter', 'Spam', '.'];
static $MORPHOX_EXPECTED_POSES = ['DET', 'NOUN', 'PREP', 'PROP', 'NOUN',  // last wrong
                          'ADV', 'PREP', 'DET', 'NOUN', 'SENT'];
static $CHINESE_HEAD_TAGS = ['GUESS', 'GUESS', 'GUESS', 'GUESS', 'GUESS', 'GUESS', 'GUESS', 'GUESS', 'GUESS',
                     'GUESS', 'GUESS', 'NC', 'V', 'V', 'E', 'NN', 'NC', 'A', 'NC', 'GUESS', 'GUESS',
                     'NN', 'NC', 'GUESS', 'D', 'GUESS', 'GUESS', 'NC', 'GUESS', 'A', 'NC', 'V', 'NC',
                     'W', 'W', 'NM', 'NC', 'GUESS', 'V', 'NC', 'W', 'A', 'GUESS', 'W', 'NC', 'GUESS',
                     'NC', 'GUESS', 'D', 'V', 'A', 'NC', 'NC', 'U', 'A', 'GUESS', 'NC', 'OC', 'NC', 'A',
                     'NC', 'D', 'W', 'W', 'V', 'GUESS', 'NN', 'NM', 'V', 'NC', 'NP', 'NC', 'A', 'OC'];


/**
 * Class APITest
 */
class APITest extends PHPUnit_Framework_TestCase
    {
        use \InterNations\Component\HttpMock\PhpUnit\HttpMockTrait;

        //public $testUrl = 'http://jugmaster.basistech.net/rest/v1';
        public $testUrl = 'http://localhost:80';
        public $userKey = null;
        //public $testUrl = 'https://api.rosette.com/rest/v1';
        //public $userKey = '7c3e0f9f51334a0793dde4be37cb22ce';
        public $hamParams;
        public $hamParamsU;
        public $dtHtmlParams;
        public $dtXHtmlParams;
        public $tagParams;
        public $uriParams;
        public $dir;

    /**
     * @param $get_post
     * @param $endpoint
     * @param $body
     */
    public function httpMock($get_post, $endpoint, $body)
        {
            $this->http->mock
                ->when()
                ->methodIs($get_post)
                ->pathIs($endpoint)
                ->then()
                ->body($body)
                ->end();
            $this->http->setUp();
        }

        public static function setUpBeforeClass()
        {
            static::setUpHttpMockBeforeClass('80', 'localhost');
        }

        public static function tearDownAfterClass()
        {
            static::tearDownHttpMockAfterClass();
        }
        
        public function setUp()
        {
            $this->setUpHttpMock();

            $rp = new RosetteParameters();
            $rp->params['content'] = $GLOBALS['HAM_SENTENCE'];
            $this->hamParams = $rp;
            
            $rp = new RosetteParameters();
            $rp->params['content'] = $GLOBALS['HAM_SENTENCE'];
            $rp->params['contentType'] = RosetteConstants::$DataFormat['UNSPECIFIED'];
            $this->hamParamsU = $rp;
            
            $rp = new RosetteParameters();
            $rp->params['content'] = $GLOBALS['HAM_SENTENCE'];
            $rp->params['contentType'] = RosetteConstants::$DataFormat['HTML'];
            $this->dtHtmlParams = $rp;
            
            $rp = new RosetteParameters();
            $rp->params['content'] = $GLOBALS['XHTML'];
            $rp->params['contentType'] = RosetteConstants::$DataFormat['XHTML'];
            $this->dtXHtmlParams = $rp;
            
            $rp = new RosetteParameters();
            $rp->params['content'] = "In the short story 'নষ্টনীড়', Rabindranath Tagore wrote,"
                ."\"Charu, have you read 'The Poison Tree' by Bankim Chandra Chatterjee?\".";
            $this->tagParams = $rp;
            
            $rp = new RosetteParameters();
            $rp->params['contentUri'] = 'http://www.basistech.com';
            $this->uriParams = $rp;
            
            $this->dir = dirname(__DIR__);
        }

        public function tearDown()
        {
            $this->tearDownHttpMock();
        }

        public function testSimpleRequest()
        {
            $this->httpMock('GET', '/foo', 'mocked body');

            $this->assertSame('mocked body', file_get_contents('http://localhost:80/foo'));
        }
        
        public function testAPIConstructor()
        {
            $api = new API();
            $this->assertEquals('http://api.rosette.com/rest/v1', $api->getServiceUrl());
            $api = new API('http://test.url.com', 'testKey');
            $this->assertEquals('testKey', $api->getUserKey());
            $this->assertEquals('http://test.url.com', $api->getServiceUrl());
        }
        
        public function testSetMultiPart() 
        {
            $api = new API($this->testUrl, $this->userKey);
            $multiPart = $api->isUseMultiPart();
            $api->setUseMultiPart(!$multiPart);
            $this->assertEquals(!$multiPart, $api->isUseMultiPart());
        }

        public function testCheckVersion_False()
        {
            $this->httpMock('GET', '/info','{"buildNumber": "cc30824d",  "name": "Rosette API",  "version": "0.5.0-SNAPSHOT",  "buildTime": "2015.04.30_17:00:41"}' );

            $api = new API($this->testUrl, $this->userKey);
            $this->setExpectedException('\BasisTechnology\Rosette\RosetteException', 'The server version is not 1.0');
            $api->CheckVersion('1.0');
        }

        public function testCheckVersion_True()
        {
            $this->httpMock('GET', '/info','{"buildNumber": "cc30824d",  "name": "Rosette API",  "version": "0.5.0-SNAPSHOT",  "buildTime": "2015.04.30_17:00:41"}' );

            $api = new API($this->testUrl, $this->userKey);
            $result = $api->CheckVersion();
            $this->assertTrue($result);
        }
        
        public function testInfo() 
        {
            $this->httpMock('GET', '/info','{"buildNumber": "cc30824d",  "name": "Rosette API",  "version": "0.5.0-SNAPSHOT",  "buildTime": "2015.04.30_17:00:41"}' );

            $api = new API($this->testUrl, $this->userKey);
            $result = $api->Info();

            $this->assertEquals('Rosette API', $result['name']);
        }
        
        public function testPing()
        {
            $this->httpMock('GET', '/ping', '{"message": "Rosette API at your service",  "time": 1430750475393}');

            $api = new API($this->testUrl, $this->userKey);
            $result = $api->Ping();

            $this->assertEquals('Rosette API at your service', $result['message']);
        }
        
        public function testLanguageInfo()
        {
            $this->httpMock('GET', '/language/info', '{
                    "requestId": "7d071890-afa2-432f-b1c6-d15238d1538d",
                    "supportedLanguages": {
                        "srp": [
                            "Cyrl",
                            "Latn"
                        ],
                        "jpn": [
                            "Jpan",
                            "Kana"
                        ]
                    },
                    "supportedScripts": {
                        "Beng": [
                            "ben"
                        ],
                        "Gujr": [
                            "guj"
                        ]
                    }
                }');

            $api = new API($this->testUrl, $this->userKey);
            // for testing, set version_checked to true, otherwise, the mock will fail because the endpoint "/info" does
            // not exist.
            $api->setVersionChecked(true);
            $result = $api->LanguageInfo();
            $this->assertNotEquals(0, count($result['supportedLanguages']));
        }
        
        public function testLanguage()
        {
            $this->httpMock('POST', '/language',
                '{
                      "requestId": "43939f9c-cb20-46c4-a61e-e5527c44b98c",
                      "languageDetections": [
                        {
                          "language": "eng",
                          "confidence": 0.38971654861172306
                        },
                        {
                          "language": "nld",
                          "confidence": 0.11256076405554828
                        },
                        {
                          "language": "fra",
                          "confidence": 0.07878603648280982
                        },
                        {
                          "language": "ita",
                          "confidence": 0.06586061662286635
                        },
                        {
                          "language": "tur",
                          "confidence": 0.048501824064942474
                        },
                        {
                          "language": "spa",
                          "confidence": 0.04773385574027546
                        },
                        {
                          "language": "por",
                          "confidence": 0.04399321305608022
                        },
                        {
                          "language": "deu",
                          "confidence": 0.04207307936844298
                        },
                        {
                          "language": "swe",
                          "confidence": 0.035767768437740266
                        },
                        {
                          "language": "ces",
                          "confidence": 0.03040476517460798
                        },
                        {
                          "language": "nob",
                          "confidence": 0.02329398189165085
                        },
                        {
                          "language": "fin",
                          "confidence": 0.022011995848263813
                        },
                        {
                          "language": "dan",
                          "confidence": 0.0209754856024916
                        },
                        {
                          "language": "ron",
                          "confidence": 0.01952603613015959
                        },
                        {
                          "language": "hun",
                          "confidence": 0.01879402891239734
                        }
                      ]
                }');

            $api = new API($this->testUrl, $this->userKey);
            // for testing, set version_checked to true, otherwise, the mock will fail because the endpoint "/info" does
            // not exist.
            $api->setVersionChecked(true);
            $result = $api->Language($this->hamParams);

            $this->assertNotEquals(0, count($result['languageDetections']));
        }
        
        public function testSentences()
        {
            $this->httpMock('POST', '/sentences',
                '{
                      "requestId": "c20f7bcd-6e9d-4186-8ae4-ea02f02ef00b",
                      "sentences": [
                        "Yes, Ma\'m! ",
                        "Green eggs and ham?  ",
                        "I am Sam;  I filter Spam."
                      ]
                }');
            $api = new API($this->testUrl, $this->userKey);
            // for testing, set version_checked to true, otherwise, the mock will fail because the endpoint "/info" does
            // not exist.
            $api->setVersionChecked(true);
            $result = $api->Sentences($this->hamParams);

            $this->assertNotNull($result['sentences']);
            $this->assertEquals(3, count($result['sentences']));
        }
        
        public function testTokens()
        {
            $this->httpMock('POST', '/tokens', '{
              "requestId": "576104ac-499f-4565-834d-9727280110c6",
              "tokens": [
                "Yes",
                ",",
                "Ma",
                "\'m",
                "!",
                "Green",
                "eggs",
                "and",
                "ham",
                "?",
                "I",
                "am",
                "Sam",
                ";",
                "I",
                "filter",
                "Spam",
                "."
              ]
            }');
            $api = new API($this->testUrl, $this->userKey);
            // for testing, set version_checked to true, otherwise, the mock will fail because the endpoint "/info" does
            // not exist.
            $api->setVersionChecked(true);
            $result = $api->Tokens($this->hamParams);

            $this->assertEquals(18, count($result['tokens']));
        }
        
        public function testMorphologyFile()
        {
            $this->httpMock('POST', '/morphology/parts-of-speech', '{
              "requestId": "157681cf-49dc-4e6c-bd47-2193929be3da",
              "posTags": [
                {
                  "text": "2015",
                  "pos": "GUESS"
                },
                {
                  "text": "-",
                  "pos": "GUESS"
                },
                {
                  "text": "02",
                  "pos": "GUESS"
                },
                {
                  "text": "-",
                  "pos": "GUESS"
                },
                {
                  "text": "28",
                  "pos": "GUESS"
                },
                {
                  "text": "09",
                  "pos": "GUESS"
                },
                {
                  "text": ":",
                  "pos": "GUESS"
                },
                {
                  "text": "34",
                  "pos": "GUESS"
                },
                {
                  "text": ":",
                  "pos": "GUESS"
                },
                {
                  "text": "00",
                  "pos": "GUESS"
                },
                {
                  "text": "新华",
                  "pos": "GUESS"
                },
                {
                  "text": "网",
                  "pos": "NC"
                },
                {
                  "text": "分享",
                  "pos": "V"
                },
                {
                  "text": "参与",
                  "pos": "V"
                },
                {
                  "text": "沉寂",
                  "pos": "E"
                },
                {
                  "text": "两",
                  "pos": "NN"
                },
                {
                  "text": "年",
                  "pos": "NC"
                },
                {
                  "text": "多",
                  "pos": "A"
                },
                {
                  "text": "后",
                  "pos": "NC"
                },
                {
                  "text": "，",
                  "pos": "GUESS"
                },
                {
                  "text": "“",
                  "pos": "GUESS"
                },
                {
                  "text": "二",
                  "pos": "NN"
                },
                {
                  "text": "张",
                  "pos": "NC"
                },
                {
                  "text": "”",
                  "pos": "GUESS"
                },
                {
                  "text": "再度",
                  "pos": "D"
                },
                {
                  "text": "开吵",
                  "pos": "GUESS"
                },
                {
                  "text": "，",
                  "pos": "GUESS"
                },
                {
                  "text": "张",
                  "pos": "NC"
                },
                {
                  "text": "艺谋",
                  "pos": "GUESS"
                },
                {
                  "text": "御用",
                  "pos": "A"
                },
                {
                  "text": "文学",
                  "pos": "NC"
                },
                {
                  "text": "策划",
                  "pos": "V"
                },
                {
                  "text": "周",
                  "pos": "NC"
                },
                {
                  "text": "晓",
                  "pos": "W"
                },
                {
                  "text": "枫",
                  "pos": "W"
                },
                {
                  "text": "出",
                  "pos": "NM"
                },
                {
                  "text": "书",
                  "pos": "NC"
                },
                {
                  "text": "，",
                  "pos": "GUESS"
                },
                {
                  "text": "揭底",
                  "pos": "V"
                },
                {
                  "text": "张",
                  "pos": "NC"
                },
                {
                  "text": "伟",
                  "pos": "W"
                },
                {
                  "text": "平",
                  "pos": "A"
                },
                {
                  "text": "。",
                  "pos": "GUESS"
                },
                {
                  "text": "据",
                  "pos": "W"
                },
                {
                  "text": "了解",
                  "pos": "NC"
                },
                {
                  "text": "，",
                  "pos": "GUESS"
                },
                {
                  "text": "张",
                  "pos": "NC"
                },
                {
                  "text": "伟平",
                  "pos": "GUESS"
                },
                {
                  "text": "在",
                  "pos": "D"
                },
                {
                  "text": "圈",
                  "pos": "V"
                },
                {
                  "text": "内",
                  "pos": "A"
                },
                {
                  "text": "人品",
                  "pos": "NC"
                },
                {
                  "text": "争议",
                  "pos": "NC"
                },
                {
                  "text": "比较",
                  "pos": "U"
                },
                {
                  "text": "大",
                  "pos": "A"
                },
                {
                  "text": "，",
                  "pos": "GUESS"
                },
                {
                  "text": "更",
                  "pos": "NC"
                },
                {
                  "text": "有",
                  "pos": "OC"
                },
                {
                  "text": "一些",
                  "pos": "NC"
                },
                {
                  "text": "风流",
                  "pos": "A"
                },
                {
                  "text": "韵事",
                  "pos": "NC"
                },
                {
                  "text": "在",
                  "pos": "D"
                },
                {
                  "text": "坊",
                  "pos": "W"
                },
                {
                  "text": "间",
                  "pos": "W"
                },
                {
                  "text": "流传",
                  "pos": "V"
                },
                {
                  "text": "。",
                  "pos": "GUESS"
                },
                {
                  "text": "一",
                  "pos": "NN"
                },
                {
                  "text": "位",
                  "pos": "NM"
                },
                {
                  "text": "毕业",
                  "pos": "V"
                },
                {
                  "text": "于",
                  "pos": "NC"
                },
                {
                  "text": "北京",
                  "pos": "NP"
                },
                {
                  "text": "电影",
                  "pos": "NC"
                },
                {
                  "text": "学院",
                  "pos": "A"
                },
                {
                  "text": "的",
                  "pos": "OC"
                }
              ]
            }');

            $api = new API($this->testUrl, $this->userKey);
            // for testing, set version_checked to true, otherwise, the mock will fail because the endpoint "/info" does
            // not exist.
            $api->setVersionChecked(true);

            $params = new RosetteParameters();
            $textPath = dirname(__DIR__).'/test/chinese-example.html';
            $params->LoadDocumentFile($textPath, RosetteConstants::$DataFormat['HTML']);
            $result = $api->Morphology($params, RosetteConstants::$MorphologyOutput['PARTS_OF_SPEECH']);

            $posTags = [];
            foreach ($result['posTags'] as $x) {
                array_push($posTags, $x['pos']);
            }
            $posTags = array_slice($posTags, 0, count($GLOBALS['CHINESE_HEAD_TAGS']));
            $this->assertSame(array_diff($GLOBALS['CHINESE_HEAD_TAGS'], $posTags), array_diff($posTags, $GLOBALS['CHINESE_HEAD_TAGS']));
        }
        
        public function testMorphology()
        {
            $this->httpMock('POST', '/morphology/complete', '{
                  "requestId": "fdeaa5ee-5d5f-4b1f-92c2-6ad469896be5",
                  "posTags": [
                    {
                      "text": "Yes",
                      "pos": "NOUN"
                    },
                    {
                      "text": ",",
                      "pos": "CM"
                    },
                    {
                      "text": "Ma",
                      "pos": "NOUN"
                    },
                    {
                      "text": "\'m",
                      "pos": "VBPRES"
                    },
                    {
                      "text": "!",
                      "pos": "SENT"
                    },
                    {
                      "text": "Green",
                      "pos": "ADJ"
                    },
                    {
                      "text": "eggs",
                      "pos": "NOUN"
                    },
                    {
                      "text": "and",
                      "pos": "COORD"
                    },
                    {
                      "text": "ham",
                      "pos": "NOUN"
                    },
                    {
                      "text": "?",
                      "pos": "SENT"
                    },
                    {
                      "text": "I",
                      "pos": "PRONPERS"
                    },
                    {
                      "text": "am",
                      "pos": "VBPRES"
                    },
                    {
                      "text": "Sam",
                      "pos": "PROP"
                    },
                    {
                      "text": ";",
                      "pos": "SENT"
                    },
                    {
                      "text": "I",
                      "pos": "PRONPERS"
                    },
                    {
                      "text": "filter",
                      "pos": "VI"
                    },
                    {
                      "text": "Spam",
                      "pos": "PROP"
                    },
                    {
                      "text": ".",
                      "pos": "SENT"
                    }
                  ],
                  "lemmas": [
                    {
                      "text": "Yes",
                      "lemma": "yes"
                    },
                    {
                      "text": ",",
                      "lemma": ","
                    },
                    {
                      "text": "Ma",
                      "lemma": "ma"
                    },
                    {
                      "text": "\'m",
                      "lemma": "be"
                    },
                    {
                      "text": "!",
                      "lemma": "!"
                    },
                    {
                      "text": "Green",
                      "lemma": "green"
                    },
                    {
                      "text": "eggs",
                      "lemma": "egg"
                    },
                    {
                      "text": "and",
                      "lemma": "and"
                    },
                    {
                      "text": "ham",
                      "lemma": "ham"
                    },
                    {
                      "text": "?",
                      "lemma": "?"
                    },
                    {
                      "text": "I",
                      "lemma": "I"
                    },
                    {
                      "text": "am",
                      "lemma": "be"
                    },
                    {
                      "text": "Sam",
                      "lemma": "Sam"
                    },
                    {
                      "text": ";",
                      "lemma": ";"
                    },
                    {
                      "text": "I",
                      "lemma": "I"
                    },
                    {
                      "text": "filter",
                      "lemma": "filter"
                    },
                    {
                      "text": "Spam",
                      "lemma": "Spam"
                    },
                    {
                      "text": ".",
                      "lemma": "."
                    }
                  ],
                  "compounds": [],
                  "hanReadings": []
                }');
            $api = new API($this->testUrl, $this->userKey);
            // for testing, set version_checked to true, otherwise, the mock will fail because the endpoint "/info" does
            // not exist.
            $api->setVersionChecked(true);
            $result = $api->Morphology($this->hamParams);
            $posTags = [];
            foreach ($result['posTags'] as $x) {
                array_push($posTags, $x['pos']);
            }
            $this->assertSame(array_diff($GLOBALS['MORPHO_EXPECTED_POSES'], $posTags), array_diff($posTags, $GLOBALS['MORPHO_EXPECTED_POSES']));
        }
        
        public function testMorphologyUnspecified()
        {
            $this->httpMock('POST', '/morphology/complete', '{
                  "requestId": "fdeaa5ee-5d5f-4b1f-92c2-6ad469896be5",
                  "posTags": [
                    {
                      "text": "Yes",
                      "pos": "NOUN"
                    },
                    {
                      "text": ",",
                      "pos": "CM"
                    },
                    {
                      "text": "Ma",
                      "pos": "NOUN"
                    },
                    {
                      "text": "\'m",
                      "pos": "VBPRES"
                    },
                    {
                      "text": "!",
                      "pos": "SENT"
                    },
                    {
                      "text": "Green",
                      "pos": "ADJ"
                    },
                    {
                      "text": "eggs",
                      "pos": "NOUN"
                    },
                    {
                      "text": "and",
                      "pos": "COORD"
                    },
                    {
                      "text": "ham",
                      "pos": "NOUN"
                    },
                    {
                      "text": "?",
                      "pos": "SENT"
                    },
                    {
                      "text": "I",
                      "pos": "PRONPERS"
                    },
                    {
                      "text": "am",
                      "pos": "VBPRES"
                    },
                    {
                      "text": "Sam",
                      "pos": "PROP"
                    },
                    {
                      "text": ";",
                      "pos": "SENT"
                    },
                    {
                      "text": "I",
                      "pos": "PRONPERS"
                    },
                    {
                      "text": "filter",
                      "pos": "VI"
                    },
                    {
                      "text": "Spam",
                      "pos": "PROP"
                    },
                    {
                      "text": ".",
                      "pos": "SENT"
                    }
                  ],
                  "lemmas": [
                    {
                      "text": "Yes",
                      "lemma": "yes"
                    },
                    {
                      "text": ",",
                      "lemma": ","
                    },
                    {
                      "text": "Ma",
                      "lemma": "ma"
                    },
                    {
                      "text": "\'m",
                      "lemma": "be"
                    },
                    {
                      "text": "!",
                      "lemma": "!"
                    },
                    {
                      "text": "Green",
                      "lemma": "green"
                    },
                    {
                      "text": "eggs",
                      "lemma": "egg"
                    },
                    {
                      "text": "and",
                      "lemma": "and"
                    },
                    {
                      "text": "ham",
                      "lemma": "ham"
                    },
                    {
                      "text": "?",
                      "lemma": "?"
                    },
                    {
                      "text": "I",
                      "lemma": "I"
                    },
                    {
                      "text": "am",
                      "lemma": "be"
                    },
                    {
                      "text": "Sam",
                      "lemma": "Sam"
                    },
                    {
                      "text": ";",
                      "lemma": ";"
                    },
                    {
                      "text": "I",
                      "lemma": "I"
                    },
                    {
                      "text": "filter",
                      "lemma": "filter"
                    },
                    {
                      "text": "Spam",
                      "lemma": "Spam"
                    },
                    {
                      "text": ".",
                      "lemma": "."
                    }
                  ],
                  "compounds": [],
                  "hanReadings": []
                }');
            $api = new API($this->testUrl, $this->userKey);
            // for testing, set version_checked to true, otherwise, the mock will fail because the endpoint "/info" does
            // not exist.
            $api->setVersionChecked(true);
            $result = $api->Morphology($this->hamParamsU);
            $posTags = [];
            foreach ($result['posTags'] as $x) {
                array_push($posTags, $x['pos']);
            }
            $this->assertSame(array_diff($GLOBALS['MORPHO_EXPECTED_POSES'], $posTags), array_diff($posTags, $GLOBALS['MORPHO_EXPECTED_POSES']));
        }
        
        public function testMorphologyPseudoHTML()
        {
            $this->httpMock('POST', '/morphology/parts-of-speech', '{
                  "requestId": "fdeaa5ee-5d5f-4b1f-92c2-6ad469896be5",
                  "posTags": [
                    {
                      "text": "Yes",
                      "pos": "NOUN"
                    },
                    {
                      "text": ",",
                      "pos": "CM"
                    },
                    {
                      "text": "Ma",
                      "pos": "NOUN"
                    },
                    {
                      "text": "\'m",
                      "pos": "VBPRES"
                    },
                    {
                      "text": "!",
                      "pos": "SENT"
                    },
                    {
                      "text": "Green",
                      "pos": "ADJ"
                    },
                    {
                      "text": "eggs",
                      "pos": "NOUN"
                    },
                    {
                      "text": "and",
                      "pos": "COORD"
                    },
                    {
                      "text": "ham",
                      "pos": "NOUN"
                    },
                    {
                      "text": "?",
                      "pos": "SENT"
                    },
                    {
                      "text": "I",
                      "pos": "PRONPERS"
                    },
                    {
                      "text": "am",
                      "pos": "VBPRES"
                    },
                    {
                      "text": "Sam",
                      "pos": "PROP"
                    },
                    {
                      "text": ";",
                      "pos": "SENT"
                    },
                    {
                      "text": "I",
                      "pos": "PRONPERS"
                    },
                    {
                      "text": "filter",
                      "pos": "VI"
                    },
                    {
                      "text": "Spam",
                      "pos": "PROP"
                    },
                    {
                      "text": ".",
                      "pos": "SENT"
                    }
                  ]
                }');
            $api = new API($this->testUrl, $this->userKey);
            // for testing, set version_checked to true, otherwise, the mock will fail because the endpoint "/info" does
            // not exist.
            $api->setVersionChecked(true);
            $result = $api->Morphology($this->dtHtmlParams, RosetteConstants::$MorphologyOutput['PARTS_OF_SPEECH']);

            $posTags = [];
            foreach ($result['posTags'] as $x) {
                array_push($posTags, $x['pos']);
            }
            $this->assertSame(array_diff($GLOBALS['MORPHO_EXPECTED_POSES'], $posTags), array_diff($posTags, $GLOBALS['MORPHO_EXPECTED_POSES']));
        }
        
        public function testMorphologyXHTML()
        {
            $this->httpMock('POST', '/morphology/parts-of-speech', '{
                "requestId": "82bf8a61-f950-441f-bb04-0675b5d9a7b7",
                  "posTags": [
                    {
                        "text": "The",
                      "pos": "DET"
                    },
                    {
                        "text": "reign",
                      "pos": "NOUN"
                    },
                    {
                        "text": "in",
                      "pos": "PREP"
                    },
                    {
                        "text": "Spain",
                      "pos": "PROP"
                    },
                    {
                        "text": "falls",
                      "pos": "NOUN"
                    },
                    {
                        "text": "mainly",
                      "pos": "ADV"
                    },
                    {
                        "text": "upon",
                      "pos": "PREP"
                    },
                    {
                        "text": "the",
                      "pos": "DET"
                    },
                    {
                        "text": "planes",
                      "pos": "NOUN"
                    },
                    {
                        "text": ".",
                      "pos": "SENT"
                    }
                  ]
                }');
            $api = new API($this->testUrl, $this->userKey);
            // for testing, set version_checked to true, otherwise, the mock will fail because the endpoint "/info" does
            // not exist.
            $api->setVersionChecked(true);
            $result = $api->Morphology($this->dtXHtmlParams, RosetteConstants::$MorphologyOutput['PARTS_OF_SPEECH']);

            $posTags = [];
            foreach ($result['posTags'] as $x) {
                array_push($posTags, $x['pos']);
            }
            $this->assertSame(array_diff($GLOBALS['MORPHOX_EXPECTED_POSES'], $posTags), array_diff($posTags, $GLOBALS['MORPHOX_EXPECTED_POSES']));
        }
        
        public function testMorphologyLemmas()
        {
            $this->httpMock('POST', '/morphology/lemmas', '{
                  "requestId": "7adcaab7-10d2-4f72-b8e1-ae3d38a9d34f",
                  "lemmas": [
                    {
                      "text": "Yes",
                      "lemma": "yes"
                    },
                    {
                      "text": ",",
                      "lemma": ","
                    },
                    {
                      "text": "Ma",
                      "lemma": "ma"
                    },
                    {
                      "text": "\'m",
                      "lemma": "be"
                    },
                    {
                      "text": "!",
                      "lemma": "!"
                    },
                    {
                      "text": "Green",
                      "lemma": "green"
                    },
                    {
                      "text": "eggs",
                      "lemma": "egg"
                    },
                    {
                      "text": "and",
                      "lemma": "and"
                    },
                    {
                      "text": "ham",
                      "lemma": "ham"
                    },
                    {
                      "text": "?",
                      "lemma": "?"
                    },
                    {
                      "text": "I",
                      "lemma": "I"
                    },
                    {
                      "text": "am",
                      "lemma": "be"
                    },
                    {
                      "text": "Sam",
                      "lemma": "Sam"
                    },
                    {
                      "text": ";",
                      "lemma": ";"
                    },
                    {
                      "text": "I",
                      "lemma": "I"
                    },
                    {
                      "text": "filter",
                      "lemma": "filter"
                    },
                    {
                      "text": "Spam",
                      "lemma": "Spam"
                    },
                    {
                      "text": ".",
                      "lemma": "."
                    }
                  ]
                }');
            $api = new API($this->testUrl, $this->userKey);
            // for testing, set version_checked to true, otherwise, the mock will fail because the endpoint "/info" does
            // not exist.
            $api->setVersionChecked(true);
            $result = $api->Morphology($this->hamParams, RosetteConstants::$MorphologyOutput['LEMMAS']);

            $lemmas = [];
            foreach ($result['lemmas'] as $x) {
                array_push($lemmas, $x['lemma']);
            }

            $this->assertSame(array_diff($GLOBALS['MORPHO_EXPECTED_LEMMAS'], $lemmas), array_diff($lemmas, $GLOBALS['MORPHO_EXPECTED_LEMMAS']));
        }
        
        public function testEntities()
        {
            $this->httpMock('POST', '/entities', '{
                  "requestId": "c68785a9-840e-471f-aee0-a72020753071",
                  "entities": [
                    {
                      "indocChainId": 0,
                      "type": "PERSON",
                      "mention": "Rabindranath Tagore",
                      "normalized": "Rabindranath Tagore",
                      "count": 1,
                      "confidence": 0.03516519069671631
                    },
                    {
                      "indocChainId": 1,
                      "type": "PERSON",
                      "mention": "Charu",
                      "normalized": "Charu",
                      "count": 1,
                      "confidence": 0.010698139667510986
                    },
                    {
                      "indocChainId": 2,
                      "type": "PERSON",
                      "mention": "Bankim Chandra Chatterjee",
                      "normalized": "Bankim Chandra Chatterjee",
                      "count": 1,
                      "confidence": 0.024974822998046875
                    }
                  ]
                }');

            $api = new API($this->testUrl, $this->userKey);
            // for testing, set version_checked to true, otherwise, the mock will fail because the endpoint "/info" does
            // not exist.
            $api->setVersionChecked(true);
            $result = $api->Entities($this->tagParams);

            $this->assertEquals(3, count($result['entities']));
        }
        
        public function testCategoriesURI()
        {
            $this->httpMock('POST', '/categories', '{
              "requestId": "20fbb1f4-db58-420f-83bc-3ccaab341da6",
              "categories": [
                {
                  "label": "TECHNOLOGY_AND_COMPUTING",
                  "confidence": 0.32552682365646807
                }
              ]
            }');
            $api = new API($this->testUrl, $this->userKey);
            // for testing, set version_checked to true, otherwise, the mock will fail because the endpoint "/info" does
            // not exist.
            $api->setVersionChecked(true);
            $result = $api->Categories($this->uriParams);

            $categories = [];
            foreach ( $result['categories'] as $x) {
                array_push($categories, $x['label']);
            }
            $this->assertTrue(in_array('TECHNOLOGY_AND_COMPUTING', $categories));
        }
        
        public function testCategories() 
        {
            $this->httpMock('POST', '/categories', '{
              "requestId": "f4855026-231e-4dc2-95aa-f87b91067837",
              "categories": [
                {
                  "label": "TECHNOLOGY_AND_COMPUTING",
                  "confidence": 0.06540835747410939
                }
              ]
            }');
            $api = new API($this->testUrl, $this->userKey);
            // for testing, set version_checked to true, otherwise, the mock will fail because the endpoint "/info" does
            // not exist.
            $api->setVersionChecked(true);

            $result = $api->Categories($this->hamParams);

            $this->assertEquals(1, count($result['categories']));
        }
        
        public function testSentiment()
        {
            $this->httpMock('POST', '/sentiment', '{
              "requestId": "ecfee79c-65f3-4ac9-ac8c-65e9f46d4ea9",
              "sentiment": [
                {
                  "label": "pos",
                  "confidence": 0.5851911883167845
                },
                {
                  "label": "neg",
                  "confidence": 0.4148088116832155
                }
              ]
            }');
            $api = new API($this->testUrl, $this->userKey);
            // for testing, set version_checked to true, otherwise, the mock will fail because the endpoint "/info" does
            // not exist.
            $api->setVersionChecked(true);
            $result = $api->Sentiment($this->hamParams);

            $a = $result['sentiment'];
            uasort($a, function($a1, $a2) {if ($a1['confidence'] == $a2['confidence']) return 0; return $a1['confidence'] > $a2['confidence'] ? -1 : 1; });

            $this->assertEquals('pos', $a[0]['label']);
        }
        
        public function testTranslatedName_From()
        {
            $api = new API($this->testUrl, $this->userKey);
            // for testing, set version_checked to true, otherwise, the mock will fail because the endpoint "/info" does
            // not exist.
            $api->setVersionChecked(true);

            $this->httpMock('POST', '/translated-name', '{
              "requestId": "e887459b-7678-4c8a-92a5-ad0333b611a4",
              "result": {
                "sourceScript": "Hani",
                "sourceLanguageOfOrigin": "zho",
                "sourceLanguageOfUse": "zho",
                "translation": "xi jinping",
                "targetLanguage": "eng",
                "targetScript": "Latn",
                "targetScheme": "HYPY",
                "confidence": 1
              }
            }');
            $params = new RntParameters();
            $params->Set('name', '习近平');
            $params->Set('entityType', 'PERSON');
            $params->Set('targetLanguage', 'eng');
            $result = $api->TranslatedName($params);
            $result = $result['result'];
            $this->assertEquals('Xi Jinping', $result['translation'], 'translation', 0.0, 10, false, true);
            $this->assertEquals('zho', $result['sourceLanguageOfUse']);
            $this->assertEquals('HYPY', $result['targetScheme']);

        }

        public function testTranslatedName_To()
        {
            $api = new API($this->testUrl, $this->userKey);
            // for testing, set version_checked to true, otherwise, the mock will fail because the endpoint "/info" does
            // not exist.
            $api->setVersionChecked(true);

            $this->httpMock('POST', '/translated-name', '{
              "requestId": "a54c4fce-b2bf-464c-8db5-312c626f6042",
              "result": {
                "sourceScript": "Latn",
                "sourceLanguageOfOrigin": "eng",
                "sourceLanguageOfUse": "eng",
                "translation": "国铬普舍",
                "targetLanguage": "zho",
                "targetScript": "Hani",
                "targetScheme": "NATIVE",
                "confidence": 0.005555555555555556
              }
            }');
            $params = new RntParameters();
            $params->Set('name', 'George Bush');
            $params->Set('entityType', 'PERSON');
            $params->Set('sourceScript', 'Latn');
            $params->Set('sourceLanguageOfOrigin', 'eng');
            $params->Set('targetLanguage', 'zho');
            $params->Set('targetScript', 'Hani');
            $params->Set('targetScheme', 'NATIVE');
            $result = $api->TranslatedName($params);
            $result = $result['result'];
            $this->assertEquals('国铬普舍', $result['translation']);
        }
    }
