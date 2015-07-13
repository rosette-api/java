"use strict";

var rosApi = require("../lib/rosette_api");

/*
  ======== A Handy Little Nodeunit Reference ========
  https://github.com/caolan/nodeunit

  Test methods:
    tests.expect(numAssertions)
    tests.done()
  Test assertions:
    tests.ok(value, [message])
    tests.equal(actual, expected, [message])           <---
    tests.notEqual(actual, expected, [message])
    tests.deepEqual(actual, expected, [message])
    tests.notDeepEqual(actual, expected, [message])
    tests.strictEqual(actual, expected, [message])
    tests.notStrictEqual(actual, expected, [message])
    tests.throws(block, [error], [message])            <---
    tests.doesNotThrow(block, [error], [message])
    tests.ifError(value)
*/

// Just an example tests
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
 * Simple ping tests example.
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
