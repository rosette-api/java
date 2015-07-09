"use strict";

var rosApi = require("../lib/rosette_api.js");

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
