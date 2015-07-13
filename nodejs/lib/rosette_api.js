/**
 * Add copyright block here
 */

"use strict";

// For example tests - delete later
exports.awesome = function() {
  return "awesome";
};

var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

/**
 * Simple ping.
 * @param {string} foo - Not a real param but testing JSDoc.
 * @return A python dictionary including the ping message of the API
 **/
exports.ping = function ping() {
  var oReq = new XMLHttpRequest();
  oReq.open("GET", "https://api.rosette.com/rest/v1/ping", false);
  oReq.setRequestHeader("user_key", "7eb3562318e5242b5a89ad80011f1e22");
  oReq.send();
  return JSON.parse(oReq.responseText);
};

exports.ping();
