/**
 * Rosette API.
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

// For example test - delete later
exports.awesome = function() {
  return "awesome";
};

var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var rosetteConstants = require("./rosetteConstants.js");
var RosetteException = require("./RosetteException");
var DocumentParamSetBase = require("./DocumentParamSetBase");
var DocumentParameters = require("./DocumentParameters");

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
 * @param {JSON} [data] - submission data
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
      xhr.send(JSON.stringify(data));
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
        message = rdata.message;
      }
      if ("code" in rdata) {
        code = rdata.code;
      }
    } catch (e) {
      console.log(e);
    }
  } else {
    message = "A retryable network operation has not succeeded after " + N_RETRIES + " attempts";
    throw new RosetteException(code, message, url);
  }
}

function getHttp(url, headers) {
  return retryingRequest("GET", url, headers);
}

function postHttp(url, headers, data) {
  return retryingRequest("POST", url, headers, data);
}

var docParam = new DocumentParameters(["language", "unit"]);
console.log(docParam.params);
docParam.setItem("language", "eng");
console.log(docParam.params);
console.log(docParam.getItem("language"));

//var data = {"content": "Many children aren't signed up for the KidCare program because parents don't know it exists."};
//var myKey = "7eb3562318e5242b5a89ad80011f1e22";
//
//console.log(getHttp("https://api.rosette.com/rest/v1/ping", {"user_key": myKey}));
//console.log(postHttp("https://api.rosette.com/rest/v1/categories", {"user_key": myKey}, data));
//console.log(getHttp("https://api.rosette.com/rest/v1/categories/info", {"user_key": myKey}));
