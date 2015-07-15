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
var NameTranslationParameters = require("./NameTranslationParameters");
var NameMatchingParameters = require("./NameMatchingParameters");

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
      return {json: rdata, statusCode: status};
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

// ----------------------------------------------- API Class --------------------------------------------

function Api(userKey, serviceUrl) {
  this.userKey = userKey;
  if (serviceUrl) {
    this.serviceUrl = serviceUrl;
  }
  else {
    this.serviceUrl = "https://api.rosette.com/rest/v1";
  }
  this.versionChecked = false; // Not sure what this is...
  this.useMultiPart = false; //or this...
}

Api.prototype.checkVersion = function() {
  if (this.versionChecked) {
    return true;
  }
  // Fill in the rest after endpoint caller
};

Api.prototype.callEndpoint = function(parameters, subUrl) {
  this.checkVersion();
  this.subUrl = subUrl;
  if (this.useMultiPart && parameters.contentType !== rosetteConstants.dataFormat.SIMPLE) {
    throw new RosetteException("incompatible", "Multipart requires contentType SIMPLE", parameters.contentType);
  }
  var url = this.serviceUrl + "/" + subUrl;
  //var headers = {};
  var headers = {"Accept": "application/json"/*, "Accept-Encoding": "gzip"*/};
  if (this.userKey) {
    headers["user_key"] = this.userKey;
  }
  parameters.validate();
  var r = postHttp(url, headers, parameters.params); // should be parameters serialized ???
  return this.finishResult(r, "callEndpoint");
};

Api.prototype.finishResult = function(result, action) {
  var code = result.statusCode;
  var json = result.json;
  if (code === 200) {
    return json;
  }
  var msg = "";
  var complaintUrl = "";
  var serverCode = "";
  if ("message" in json) {
    msg = json.message;
  }
  else {
    msg = json.code; // punt if can't get real message
  }
  if (!this.subUrl) {
    complaintUrl = "Top level info";
  }
  else {
    complaintUrl = action + " " + this.subUrl;
  }
  if ("code" in json) {
    serverCode = json.code;
  }
  else {
    serverCode = "unknownError";
  }
  throw new RosetteException(serverCode, complaintUrl + " : failed to communicate with Rosette", msg);
};

Api.prototype.info = function() {
  console.log(this);
  console.log(this.subUrl);
  var url = "";
  //if (this.subUrl) { // What's this about??? --- nonexistant in php and breaks this code
  //  this.checkVersion();
  //  url = this.serviceUrl + "/" + this.subUrl + "/info";
  //}
  //else
  url = this.serviceUrl + "/info";
  var headers = {"Accept": "application/json"};
  if (this.userKey) {
    headers["user_key"] = this.userKey;
  }
  console.log("url " + url);
  return this.finishResult(getHttp(url, headers), "info");
};

Api.prototype.ping = function() {
  var url = this.serviceUrl + "/ping";
  var headers = {"Accept": "application/json"};
  if (this.userKey) {
    headers["user_key"] = this.userKey;
  }
  return this.finishResult(getHttp(url, headers), "ping");
};

var api = new Api("7eb3562318e5242b5a89ad80011f1e22", "https://api.rosette.com/rest/v1");
var docParams = new DocumentParameters();
docParams.params.content = "Many children aren't signed up for the KidCare program because parents don't know it exists.";
docParams.params.contentType = rosetteConstants.dataFormat.SIMPLE;
docParams.loadDocumentFile("/Users/rhausmann/git/ws-client-bindings/nodejs/lib/test.html", rosetteConstants.dataFormat.HTML);
//console.log(docParams.params.contentType);
var cat = api.callEndpoint(docParams, "sentiment");
console.log(cat);
//console.log(cat.sentiment);
console.log(api.info());
console.log(api.ping());

//var data = {"content": "Many children aren't signed up for the KidCare program because parents don't know it exists.",
//  "contentType": 'application/json'};
//var myKey = "7eb3562318e5242b5a89ad80011f1e22";
//
//console.log(getHttp("https://api.rosette.com/rest/v1/ping", {"user_key": myKey}));
//console.log(postHttp("https://api.rosette.com/rest/v1/categories", {"user_key": myKey}, data));
//console.log(getHttp("https://api.rosette.com/rest/v1/categories/info", {"user_key": myKey}));
