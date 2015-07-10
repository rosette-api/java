/**
 * Add copyright block here
 */

"use strict";

// For example test - delete later
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

//exports.ping();


///
/// ----------------------------------------     REAL CODE BELOW     -------------------------------------------
///

var N_RETRIES = 3;

/**
 * function retryingRequest.
 *
 * Encapsulates the GET/POST and retries N_RETRIES
 *
 * @return {array} [response text, status code]
 *
 * @throws RosetteException
 *
 * @param {string} op - operation
 * @param {string} url - target URL
 * @param {JSON} headers - header data
 * @param [data] - submission data
 */
function retryingRequest(op, url, headers, data) {
  for (var i = 0; i < N_RETRIES; i++) {
    var xhr = new XMLHttpRequest();
    xhr.open(op, url, false);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    for (var key in headers) {
      xhr.setRequestHeader(key, headers[key]);
    }
    if (!data) {
      xhr.send();
    } else {
      //xhr.send(data);
      console.log("here");
      xhr.send(JSON.parse(data));
    }
    var rdata = JSON.parse(xhr.responseText);
    var status = xhr.status;
    if (status < 500) {
      return [rdata, status];
    }
  }
  var message = null;
  var code = "unknownError";
  if (rdata != null) {
    try {
      if ("message" in rdata) {
        message = rdata["message"];
      }
      if ("code" in rdata) {
        code = rdata["code"];
      }
    } catch (e) {
      console.log(e);
    }
  } else {
    message = "A retryable network operation has not succeeded after " + str(N_RETRIES) + " attempts";
    throw RosetteException(code, message, url);
  }
}

var data = JSON.stringify({"content": "Many children aren't signed up for the KidCare program because parents don't know it exists."});
console.log(data);
console.log(retryingRequest("POST", "https://api.rosette.com/rest/v1/categories",
    {"user_key": "7eb3562318e5242b5a89ad80011f1e22"}, data));

