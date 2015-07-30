/**
 * Testing for Rosette API.
 * These tests DO NOT hit the API and instead mock that part.
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

"use strict";

var Api = require("../target/instrumented/lib/Api");
var DocumentParameters = require("../target/instrumented/lib/DocumentParameters");
var NameMatchingParameters = require("../target/instrumented/lib/NameMatchingParameters");
var NameTranslationParameters = require("../target/instrumented/lib/NameTranslationParameters");
var RosetteException = require("../target/instrumented/lib/RosetteException");

var fs = require("fs");
var nock = require("nock");
var sinon = require("sinon");
var zlib = require("zlib");

/*
 ======== A Handy Little Nodeunit Reference ========
 https://github.com/caolan/nodeunit

 Test methods:
 test.expect(numAssertions)
 test.done()
 Test assertions:
 test.ok(value, [message])
 test.equal(actual, expected, [message])
 test.notEqual(actual, expected, [message])
 test.deepEqual(actual, expected, [message])
 test.notDeepEqual(actual, expected, [message])
 test.strictEqual(actual, expected, [message])
 test.notStrictEqual(actual, expected, [message])
 test.throws(block, [error], [message])
 test.doesNotThrow(block, [error], [message])
 test.ifError(value)
 */
function setMock() {
  nock.disableNetConnect();

  nock("https://api.rosette.com/rest/v1")
    .persist()
    .get("/ping")
    .reply(200, new Buffer(fs.readFileSync("../../mock-data/response/ping.json")));

  nock("https://api.rosette.com/rest/v1")
    .persist()
    .get("/info")
    .reply(200, new Buffer(fs.readFileSync("../../mock-data/response/info.json")));

  var endpoints = ["categories", "entities", "entities/linked", "language", "matched-name", "morphology/complete",
    "morphology/compound-components", "morphology/han-readings", "morphology/lemmas", "morphology/parts-of-speech",
    "sentences", "sentiment", "tokens", "translated-name"];
  for (var j = 0; j < endpoints.length; j++) {
    nock("https://api.rosette.com/rest/v1")
      .persist()
      .post("/" + endpoints[j])
      .reply(function (uri, request) {
        request = JSON.parse(request);
        var file = request.file;
        var statusCode = parseInt(fs.readFileSync("../../mock-data/response/" + file + ".status"));
        // Because a call to the API returns a buffer
        var result = new Buffer(fs.readFileSync("../../mock-data/response/" + file + ".json"));
        return [statusCode, result];
      });
  }
}

// Tests all endpoints other than ping and info with mocked data
exports.testAllEndpoints = {
  setUp: function(callback) {
    setMock();
    callback();
  },
  tearDown: function(callback) {
    nock.cleanAll();
    nock.enableNetConnect();
    callback();
  },
  "test endpoints": function(test) {
    var files = fs.readdirSync("../../mock-data/request");

    // All the regex necessary to extract info for the tests
    var filenameRe = /(\w+).json/;
    var endpointRe = /\w+-\w+-(\w+).json/;
    var morphoRe = /morphology_(\w+)/;
    // Make sure the right number of assertions happen
    test.expect(files.length);
    // So the checkResult function knows how many it's gone through
    var counter = 0;

    function checkResult(err, result) {
      var expected = JSON.parse(fs.readFileSync("../../mock-data/response/" + files[counter]));
      var errorExpected = false;

      // Figure out if there should be an error
      if ("code" in expected) {
        if (expected.code === "unsupportedLanguage") {
          errorExpected = true;
        }
      }

      if (!err) {
        if (!errorExpected) {
          test.deepEqual(result, expected, "Testing if result matches");
        }
        else {
          test.ok(false, "Should have been an error");
        }
      } else
      {
        if (errorExpected) {
          test.ok(true, "Error was expected");
        }
        else {
          test.ok(false, "Error was not expected, but was thrown");
        }
      }
      if (++counter === files.length) {
        test.done();
      }
    }

    for (var i = 0; i < files.length; i++) {
      var parameters, endpt;
      // For all request files
      if (files[i].indexOf(".json") > -1) {
        // Set up API with the file name as the user key
        var api = new Api("0123456789");
        var input = JSON.parse(fs.readFileSync("../../mock-data/request/" + files[i]));

        // Anything not matched-name or translated-name
        if (files[i].indexOf("name") === -1) {
          parameters = new DocumentParameters();
          parameters.params = input;
          // Add extra parameter so that the nock can respond correctly
          parameters.params.file = files[i].replace(filenameRe, "$1");
          endpt = files[i].replace(endpointRe, "$1");

          if (endpt.indexOf("morphology") === -1) {
            if (endpt === "categories") {
              api.categories(parameters, function (err, res) {
                checkResult(err, res);
              });
            }
            if (endpt === "language") {
              api.language(parameters, function (err, res) {
                checkResult(err, res);
              });
            }
            if (endpt === "sentences") {
              api.sentences(parameters, function (err, res) {
                checkResult(err, res);
              });
            }
            if (endpt === "sentiment") {
              api.sentiment(parameters, function (err, res) {
                checkResult(err, res);
              });
            }
            if (endpt === "tokens") {
              api.tokens(parameters, function (err, res) {
                checkResult(err, res);
              });
            }
            if (endpt === "entities") {
              api.entities(parameters, null, function (err, res) {
                checkResult(err, res);
              });
            }
            if (endpt === "entities_linked") {
              api.entities(parameters, true, function (err, res) {
                checkResult(err, res);
              });
            }
          }
          // morphology
          else {
            endpt = endpt.replace(morphoRe, "$1");
            api.morphology(parameters, endpt, function (err, res) {
              checkResult(err, res);
            });
          }
        }
        // name related endpoints
        else {
          if (files[i].indexOf("matched-name") > -1) {
            parameters = new NameMatchingParameters();
            parameters.params = input;
            parameters.params.file = files[i].replace(filenameRe, "$1");
            api.matchedName(parameters, function (err, res) {
              checkResult(err, res);
            });
          }
          // translated-name
          else {
            parameters = new NameTranslationParameters();
            parameters.params = input;
            parameters.params.file = files[i].replace(filenameRe, "$1");
            api.matchedName(parameters, function (err, res) {
              checkResult(err, res);
            });
          }
        }
      }
      else {
        counter++;
        test.ok(true, "Make the expected number of assertions");
      }
    }
  }
};

exports.testInfo = {
  setUp: function(callback) {
    setMock();
    callback();
  },
  tearDown: function(callback) {
    nock.cleanAll();
    nock.enableNetConnect();
    callback();
  },
  "test info": function(test) {
    test.expect(1);
    var api = new Api("0123456789");
    var expected = JSON.parse(fs.readFileSync("../../mock-data/response/info.json"));
    api.info(function (err, res) {
      if (err) {
        test.ok(false, "Shouldn't have thrown an error");
        test.done();
      }
      test.deepEqual(res, expected, "Test if info works as expected");
      test.done();
    });
  }
};

exports.testPing = {
  setUp: function(callback) {
    setMock();
    callback();
  },
  tearDown: function(callback) {
    nock.cleanAll();
    nock.enableNetConnect();
    callback();
  },
  "test ping": function(test) {
    test.expect(1);
    var api = new Api("0123456789");
    var expected = JSON.parse(fs.readFileSync("../../mock-data/response/ping.json"));
    api.ping(function (err, res) {
      if (err) {
        test.ok(false, "Shouldn't have thrown an error");
        test.done();
      }
      test.deepEqual(res, expected, "Test if ping works as expected");
      test.done();
    });
  }
};

function setFailMock() {
  nock.disableNetConnect();

  var resp = {
    "code": "serviceUnavailable",
    "message": "We’re temporarially offline for maintanance. Please try again later.",
    "requestId": "de587a1c-a23a-4d4a-a52e-1fa96fe13f33"
  };
  nock("https://api.rosette.com/rest/v1")
    .persist()
    .get("/info")
    .reply(503, resp);
}

exports.testRetryingRequestCorrectError = {
  setUp: function(callback) {
    setFailMock();
    callback();
  },
  tearDown: function(callback) {
    nock.cleanAll();
    nock.enableNetConnect();
    callback();
  },
  "test return correct error": function(test) {
    test.expect(1);
    var api = new Api("0123456789");
    api.info(function (err, res) {
      if (err) {
        test.equal(err.message, "serviceUnavailable: We’re temporarially offline for maintanance. Please try again later.: https://api.rosette.com/rest/v1/info", "Check error message");
        test.done();
      }
      else {
        test.ok(false, "Should have been an error");
        test.done();
      }
    });
  }
};

function setPartialFailMock() {
  nock.disableNetConnect();

  var resp = {
    "code": "serviceUnavailable",
    "message": "We’re temporarially offline for maintanance. Please try again later.",
    "requestId": "de587a1c-a23a-4d4a-a52e-1fa96fe13f33"
  };
  nock("https://api.rosette.com/rest/v1")
    .get("/info")
    .reply(503, resp);

  nock("https://api.rosette.com/rest/v1")
    .get("/info")
    .reply(200, new Buffer(fs.readFileSync("../../mock-data/response/info.json")));
}

// Test that it actually retries because the first response will return a 503 status code but the second will return 200
exports.testRetryingRequest = {
  setUp: function(callback) {
    setPartialFailMock();
    callback();
  },
  tearDown: function(callback) {
    nock.cleanAll();
    nock.enableNetConnect();
    callback();
  },
  "test return correct error": function(test) {
    test.expect(1);
    var api = new Api("0123456789");
    var expected = JSON.parse(fs.readFileSync("../../mock-data/response/info.json"));
    api.info(function (err, res) {
      if (err) {
        test.ok(false, "Shouldn't have thrown an error");
        test.done();
      }
      test.deepEqual(res, expected, "Test if info works as expected");
      test.done();
    });
  }
};
