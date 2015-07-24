/**
 * Testing for Rosette API.
 * These tests DO hit the API and test each endpoint all the way through.
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
var rosetteConstants = require("../target/instrumented/lib/rosetteConstants");

/*
  ======== A Handy Little Nodeunit Reference ========
  https://github.com/caolan/nodeunit

  Test methods:
    test.expect(numAssertions)
    test.done()
  Test assertions:
    test.ok(value, [message])
    test.equal(actual, expected, [message])           <---
    test.notEqual(actual, expected, [message])
    test.deepEqual(actual, expected, [message])
    test.notDeepEqual(actual, expected, [message])
    test.strictEqual(actual, expected, [message])
    test.notStrictEqual(actual, expected, [message])
    test.throws(block, [error], [message])            <---
    test.doesNotThrow(block, [error], [message])
    test.ifError(value)
*/

var userKey = "7eb3562318e5242b5a89ad80011f1e22";

/**
 * Testing of all endpoints that use get requests.
 * No mocking.
 */
exports.getEndpointsFull = {
  setUp: function (callback) {
    this.api = new Api(userKey);
    callback();
  },
  "info": function (test) {
    this.api.info(function(res) {
      test.equal("57179c38", res.buildNumber, "Comparing build number");
      test.done();
    });
  },
  "ping": function (test) {
    this.api.ping(function(res) {
      test.equal("Rosette API at your service", res.message, "Comparing ping message");
      test.done();
    });
  },
  "languageInfo": function (test) {
    this.api.languageInfo(function(res) {
      test.ok("supportedLanguages" in res, "supportedLanguages field in result");
      test.done();
    });
  }
};

function allFalse(arr) {
  var noTrue = true;
  for (var i in arr) {
    if (arr[i] !== false) {
      noTrue = false;
    }
  }
  return noTrue;
}

/**
 * Testing of all endpoints except morphology that take document parameters
 * No mocking.
 */
exports.documentParamEndpointsFull = {
  setUp: function (callback) {
    this.api = new Api(userKey);
    this.docParams = new DocumentParameters();
    var content = "President Obama urges the Congress and Speaker Boehner to pass the $50 billion spending bill ";
    content += "based on Christian faith by July 1st or Washington will become totally dysfunctional, ";
    content += "a terrible outcome for American people.";
    this.docParams.setItem("content", content);
    callback();
  },
  "categories": function (test) {
    this.api.categories(this.docParams, function(res) {
      test.equal("LAW_GOVERNMENT_AND_POLITICS", res.categories[0].label, "Comparing category");
      test.done();
    });
  },
  "entities": function (test) {
    this.api.entities(this.docParams, false, function(res) {
      var included = ["President", "Obama", "Congress", "$50 billion", "American"];
      for (var i in res.entities) {
        var mention = res.entities[i].mention;
        var index = included.indexOf(mention);
        if (index !== -1) {
          included[index] = false;
        }
      }
      test.ok(allFalse(included), "Caught entities");
      test.done();
    });
  },
  "entities_linked": function (test) {
    this.api.entities(this.docParams, true, function(res) {
      var correct = true;
      var collected = 0;
      for (var i in res.entities) {
        var entity = res.entities[i];
        if (entity.mention === "Obama") {
          if (entity.entityId === "Q76") {
            collected++;
          }
          else {
            correct = false;
          }
        }
        if (entity.mention === "Congress") {
          if (entity.entityId === "Q11268") {
            collected++;
          }
          else {
            correct = false;
          }
        }
        if (entity.mention === "Washington") {
          if (entity.entityId === "Q61") {
            collected++;
          }
          else {
            correct = false;
          }
        }
      }
      test.equal(3, collected, "Correct number checked");
      test.ok(correct, "Check if all checked entities have correct Q ID");
      test.done();
    });
  },
  "language": function (test) {
    this.api.language(this.docParams, function(res) {
      test.equal("eng", res.languageDetections[0].language, "Comparing language");
      test.done();
    });
  },
  "sentences": function (test) {
    this.docParams.setItem("content", "Here is the first sentence. Here is the second.");
    this.api.sentences(this.docParams, function(res) {
      test.equal(2, res.sentences.length, "Comparing number of sentences");
      test.done();
    });
  },
  "sentiment": function (test) {
    this.api.sentiment(this.docParams, function(res) {
      test.equal("neg", res.sentiment[0].label, "Comparing strongest sentiment");
      test.done();
    });
  },
  "tokens": function (test) {
    this.docParams.setItem("content", "北京大学生物系主任办公室内部会议");
    this.api.tokens(this.docParams, function(res) {
      test.equal(6, res.tokens.length, "Testing if right number of tokens");
      test.done();
    });
  }
};

/**
 * Testing of all morphology endpoints.
 * No mocking.
 */
exports.morphologyEndpointsFull = {
  setUp: function (callback) {
    this.api = new Api(userKey);
    this.docParams = new DocumentParameters();
    callback();
  },
  "complete": function (test) {
    this.docParams.setItem("content", "The quick brown fox jumped over the lazy dog. Yes he did.");
    this.api.morphology(this.docParams, null, function(res) {
      var includes = true;
      if (!("posTags" in res)) {
        includes = false;
      }
      if (!("lemmas" in res)) {
        includes = false;
      }
      if (!("compounds" in res)) {
        includes = false;
      }
      if (!("hanReadings" in res)) {
        includes = false;
      }
      test.ok(includes, "Testing if it result complete morphology");
      test.done();
    });
  },
  "compound-components": function (test) {
    this.docParams.setItem("content", "Rechtsschutzversicherungsgesellschaften");
    this.api.morphology(this.docParams, rosetteConstants.morpholoyOutput.COMPOUND_COMPONENTS, function(res) {
      test.equal(4, res.compounds[0].compoundComponents.length, "Test if right number of components");
      test.done();
    });
  },
  "han-readings": function (test) {
    this.docParams.setItem("content", "北京大学生物系主任办公室内部会议");
    this.api.morphology(this.docParams, rosetteConstants.morpholoyOutput.HAN_READINGS, function(res) {
      test.equal(5, res.hanReadings.length, "Test if right number of han readings");
      test.done();
    });
  },
  "lemmas": function (test) {
    this.docParams.setItem("content", "The fact is that the geese just went back to get a rest and I'm not banking on their return soon");
    this.api.morphology(this.docParams, rosetteConstants.morpholoyOutput.LEMMAS, function(res) {
      test.equal(22, res.lemmas.length, "Test if right number of lemmas");
      test.done();
    });
  },
  "parts-of-speach": function (test) {
    this.docParams.setItem("content", "The fact is that the geese just went back to get a rest and I'm not banking on their return soon");
    this.api.morphology(this.docParams, rosetteConstants.morpholoyOutput.PARTS_OF_SPEECH, function(res) {
      test.equal("DET", res.posTags[0].pos, "Test if correct first part of speech");
      test.done();
    });
  }
};

/**
 * Testing of all name-related endpoints.
 * No mocking.
 */
exports.nameEndpointsFull = {
  setUp: function (callback) {
    this.api = new Api(userKey);
    callback();
  },
  "matched": function (test) {
    var name1 = {"text": "Michael Jackson", "language": "eng", "entityType": "PERSON"};
    var name2 = {"text": "迈克尔·杰克逊", "entityType": "PERSON"};
    var matchParams = new NameMatchingParameters(name1, name2);
    this.api.matchedName(matchParams, function(res) {
      var okayScore = true;
      if (res.result.score < .7) {
        okayScore = false;
      }
      if (res.result.score > .9) {
        okayScore = false;
      }
      test.ok(okayScore, "Test if score is reasonable");
      test.done();
    });
  },
  "translated": function (test) {
    var translationParams = new NameTranslationParameters();
    translationParams.setItem("name", "معمر محمد أبو منيار القذافي‎");
    translationParams.setItem("entityType", "PERSON");
    translationParams.setItem("targetLanguage", "eng");
    this.api.translatedName(translationParams, function(res) {
      test.equal("Mu\'ammar Muhammad Abu-Minyar al-Qadhafi", res.result.translation, "Test if translationis as expected");
      test.done();
    });
  }
};
