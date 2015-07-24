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

sinon.stub(Api.prototype, "retryingRequest", function(callback, op, url, headers, data, action, api) {
  var file = headers["user_key"];
  var statusCode = parseInt(fs.readFileSync("../mock-data/response/" + file + ".status"));
  // Because a call to the API returns a buffer
  var result = new Buffer(fs.readFileSync("../mock-data/response/" + file + ".json"));
  var resHeaders = {};

  // to test gzip
  if (JSON.stringify(result).length > 200) {
    result = zlib.gzipSync(result);
    resHeaders["content-encoding"] = "gzip";
  }
  if (resHeaders["content-encoding"] === "gzip") {
    result = zlib.gunzipSync(result);
  }
  if (statusCode < 500) {
    api.finishResult({"json": JSON.parse(result.toString()), "statusCode": statusCode}, action, callback);
  }
  else {
    result = JSON.parse(result.toString());
    var message = null;
    var code = "unknownError";
    if (result != null) {
      try {
        if ("message" in result) {
          message = result.message;
        }
        if ("code" in result) {
          code = result.code;
        }
      } catch (e) {
        console.error(e);
      }
    }
    if (!message) {
      message = "A retryable network operation has not succeeded";
    }
    throw new RosetteException(code, message, url);
  }
});

sinon.stub(Api.prototype, "checkVersion", function() {
  return true;
});

// Tests all endpoints other than ping and info with mocked data
exports.testAllMocked = function(test){
  var files = fs.readdirSync("../mock-data/request");
  // All the regex necessary to extract info for the tests
  var filenameRe = /(\w+).json/;
  var endpointRe = /\w+-\w+-(\w+).json/;
  var morphoRe = /morphology_(\w+)/;
  var result = "";
  // Make sure the right number of assertions happen
  test.expect(files.length);

  for (var i = 0; i < files.length; i++) {
    var parameters, endpt;
    // For all request files
    if (files[i].indexOf(".json") > -1) {
      var errorExpected = false;
      try {
        // Set up API with the file name as the user key
        var api = new Api(files[i].replace(filenameRe, "$1"));
        var input = JSON.parse(fs.readFileSync("../mock-data/request/" + files[i]));
        var expected = JSON.parse(fs.readFileSync("../mock-data/response/" + files[i]));

        // Figure out if there should be an error
        if ("code" in expected) {
          if (expected.code === "unsupportedLanguage") {
            errorExpected = true;
          }
        }

        if (files[i].indexOf("name") === -1) {
          parameters = new DocumentParameters();
          parameters.params = input;
          endpt = files[i].replace(endpointRe, "$1");

          if (endpt.indexOf("morphology") === -1) {
            if (endpt === "categories") {
              api.categories(parameters, function (res) {
                result = res;
              });
            }
            if (endpt === "language") {
              api.language(parameters, function (res) {
                result = res;
              });
            }
            if (endpt === "sentences") {
              api.sentences(parameters, function (res) {
                result = res;
              });
            }
            if (endpt === "sentiment") {
              api.sentiment(parameters, function (res) {
                result = res;
              });
            }
            if (endpt === "tokens") {
              api.tokens(parameters, function (res) {
                result = res;
              });
            }
            if (endpt === "entities") {
              api.entities(parameters, null, function (res) {
                result = res;
              });
            }
            if (endpt === "entities_linked") {
              api.entities(parameters, true, function (res) {
                result = res;
              });
            }
          }
          // morphology
          else {
            endpt = endpt.replace(morphoRe, "$1");
            api.morphology(parameters, endpt, function (res) {
              result = res;
            });
          }
        }
        // name related endpoints
        else {
          if (api.userKey.indexOf("matched-name") > -1) {
            parameters = new NameMatchingParameters();
            parameters.params = input;
            api.matchedName(parameters, function(res) {
              result = res;
            });
          }
          // translated-name
          else {
            parameters = new NameTranslationParameters();
            parameters.params = input;
            api.matchedName(parameters, function(res) {
              result = res;
            });
          }
        }
        // If there are no errors test that the result is as expected
        if (!errorExpected) {
          test.deepEqual(result, expected, "Testing if result matches");
        }
        else {
          test.ok(false, "Should have been an error");
        }
      } catch (e) {
        if (errorExpected) {
          test.ok(true, "Error was expected");
        }
        else {
          test.ok(false, "Error was not expected, but was thrown");
        }
      }
    }
    else {
      test.ok(true, "Make the expected number of assertions");
    }
  }
  test.done();
};

exports.testInfoMocked = function(test) {
  test.expect(1);
  var api = new Api("info");
  var expected = JSON.parse(fs.readFileSync("../mock-data/response/info.json"));
  api.info(function(res) {
    test.deepEqual(res, expected, "Test if info works as expected");
  });
  test.done();
};

exports.testPingMocked = function(test) {
  test.expect(1);
  var api = new Api("ping");
  var expected = JSON.parse(fs.readFileSync("../mock-data/response/ping.json"));
  api.info(function(res) {
    test.deepEqual(res, expected, "Test if ping works as expected");
  });
  test.done();
};
