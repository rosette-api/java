/**
 * Testing for Rosette API.
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

var rosApi = require("../instrumented/lib/rosetteApi.js");

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

// Just an example test
exports.awesome = {
  setUp: function(done) {
    // setup here
    done();
  },
  "no args": function(test) {
    test.expect(1);
    // tests here
    test.equal(rosApi.awesome(), "awesome", "should be awesome.");
    test.done();
  }
};


/**
 * Simple ping test example.
 * @param {string} foo - Not a real param but testing JSDoc.
 **/
exports.ping = {
  setUp: function(done) {
    // setup here
    done();
  },
  "no args": function(test) {
    test.expect(1);
    // tests here
    test.equal(rosApi.ping().message, "Rosette API at your service", "Ping message incorrect");
    test.done();
  }
};
