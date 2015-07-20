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

var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var zlib = require("zlib");
var rosetteConstants = require("./rosetteConstants");
var RosetteException = require("./RosetteException");
var DocumentParameters = require("./DocumentParameters");

/**
 * Compatible server version.
 *
 * @type string
 */
var COMPATIBLE_VERSION = "0.5";

// ----------------------------------------------- API Class --------------------------------------------

/**
 * @class
 *
 * Node.js Client Binding API; representation of a Api server.
 * Call instance methods upon this object to communicate with particular
 * Api server endpoints.
 * Aside from ping() and info(), most of the methods require the construction
 * of either a DocumentParameters object or a NameTranslationParameters object.  These
 * provide the content data that will be processed by the service.
 *
 * @example var api = new API(userKey, serviceUrl);
 * @copyright 2014-2015 Basis Technology Corporation.
 * @license http://www.apache.org/licenses/LICENSE-2.0
 * @param {string} userKey - Your Rosette API key
 * @param {string} [serviceUrl] - A different URL to get information from.
 */
function Api(userKey, serviceUrl) {
  /**
   * @type {string}
   * @desc The Rosette API key used for authentication
   */
  this.userKey = userKey;
  /**
   * @type {string}
   * @desc URL of the API
   * @default
   */
  this.serviceUrl = "https://api.rosette.com/rest/v1";
  if (serviceUrl) {
    this.serviceUrl = serviceUrl;
  }
  /**
   * @desc True if the version has already been checked.  Saves round trips.
   * @type {boolean}
   * @default false
   */
  this.versionChecked = false;
  /**
   * @desc Number of times to try connecting
   * @type {number}
   */
  this.nRetries = 3;
}

/**
 * function retryingRequest.
 *
 * Encapsulates the GET/POST and retries N_RETRIES
 *
 * @return {JSON} response {"json": json, "statusCode": status}
 *
 * @throws RosetteException
 *
 * @param {string} op - operation
 * @param {string} url - target URL
 * @param {JSON} headers - header data
 * @param {JSON} [data] - submission data
 */
Api.prototype.retryingRequest = function(op, url, headers, data) {
  for (var i = 0; i < this.nRetries; i++) {
    var xhr = new XMLHttpRequest();
    xhr.setDisableHeaderCheck(true); // so that I can set accept-encoding to gzip
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
    var rdata = "";
    var status = xhr.status;
    // If gzip encoded, decompress
    if (xhr.getResponseHeader("Content-Encoding") === "gzip") {
      rdata = zlib.inflateSync(rdata);
    }
    rdata = JSON.parse(xhr.responseText);
    xhr.abort();
    if (status < 500) {
      return {"json": rdata, "statusCode": status};
    }
  }
  // If status >= 500 continue: Gather error information
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
  }
  if (!message) {
    message = "A retryable network operation has not succeeded after " + this.nRetries + " attempts";
  }
  throw new RosetteException(code, message, url);
};

/**
 * Standard get helper function
 *
 * @param {string} url
 * @param {JSON} headers
 * @returns {JSON}
 * @throws RosetteException
 */
Api.prototype.getHttp = function(url, headers) {
  return this.retryingRequest("GET", url, headers);
};

/**
 * Standard post helper function
 *
 * @param {string} url
 * @param {JSON} headers
 * @param {JSON} data
 * @returns {JSON}
 * @throws RosetteException
 */
Api.prototype.postHttp = function(url, headers, data) {
  var response = this.retryingRequest("POST", url, headers, data);
  return response; // gzip stuff...
};

/**
 * Checks if the server version is compatible with the API version
 *
 * @throws RosetteException
 * @returns {boolean}
 */
Api.prototype.checkVersion = function() {
  if (this.versionChecked) {
    return true;
  }
  var info = this.info();
  var version = info.version;
  var strVersion = version.split(".", 2).join(".");
  if (strVersion !== COMPATIBLE_VERSION) {
    throw new RosetteException("badServerVersion", "The server version is not " + COMPATIBLE_VERSION, strVersion);
  }
  this.versionChecked = true;
  return true;
};

/**
 * Internal operations processor for most endpoints
 *
 * @param {(DocumentParameters|NameMatchingParameters|NameTranslationParameters)} parameters
 * @param {string} subUrl
 * @throws RosetteException
 * @returns {JSON}
 */
Api.prototype.callEndpoint = function(parameters, subUrl) {
  // Check that version is compatible
  this.checkVersion();
  this.subUrl = subUrl;
  if (this.useMultiPart && parameters.contentType !== rosetteConstants.dataFormat.SIMPLE) {
    throw new RosetteException("incompatible", "Multipart requires contentType SIMPLE", parameters.contentType);
  }
  var url = this.serviceUrl + "/" + subUrl;
  var headers = {"Accept": "application/json", "Accept-Encoding": "gzip"};
  if (this.userKey) {
    headers["user_key"] = this.userKey;
  }
  // Check that parameters follow their guidelines
  parameters.validate();
  var r = this.postHttp(url, headers, parameters.params); // should be parameters serialized ???
  return this.finishResult(r, "callEndpoint");
};

/**
 * Processes the response, returning either the decoded Json or throwing an exception.
 *
 * @param {JSON} result - Json of the form {json, statusCode}
 * @param {string} action - Description of goal to be used in error message if necessary
 * @throws RosetteException
 * @returns {JSON}
 */
Api.prototype.finishResult = function(result, action) {
  var code = result.statusCode;
  var json = result.json;
  // If all is well, return the json
  if (code === 200) {
    return json;
  }
  // Otherwise collect information to construct error
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

/**
 * Calls the info endpoint
 *
 * @throws RosetteException
 * @returns {JSON}
 */
Api.prototype.info = function() {
  var url = this.serviceUrl + "/info";
  var headers = {"Accept": "application/json"};
  if (this.userKey) {
    headers["user_key"] = this.userKey;
  }
  return this.finishResult(this.getHttp(url, headers), "info");
};

/**
 * Calls the ping endpoint
 *
 * @throws RosetteException
 * @returns {JSON}
 */
Api.prototype.ping = function() {
  var url = this.serviceUrl + "/ping";
  var headers = {"Accept": "application/json"};
  if (this.userKey) {
    headers["user_key"] = this.userKey;
  }
  return this.finishResult(this.getHttp(url, headers), "ping");
};

/**
 * Calls the language endpoint
 *
 * @param {DocumentParameters} parameters
 * @throws RosetteException
 * @returns {JSON}
 */
Api.prototype.language = function(parameters) {
  return this.callEndpoint(parameters, "language");
};

/**
 * Calls the languageInfo endpoint
 *
 * @throws RosetteException
 * @returns {JSON}
 */
Api.prototype.languageInfo = function() {
  var url = this.serviceUrl + "/language/info";
  var headers = {"Accept": "application/json"};
  if (this.userKey) {
    headers["user_key"] = this.userKey;
  }
  var result = this.getHttp(url, headers);
  return this.finishResult(result, "language-info");
};

/**
 * Calls the sentences endpoint
 *
 * @param {DocumentParameters} parameters
 * @throws RosetteException
 * @returns {JSON}
 */
Api.prototype.sentences = function(parameters) {
  return this.callEndpoint(parameters, "sentences");
};

/**
 * Calls the tokens endpoint
 *
 * @param {DocumentParameters} parameters
 * @throws RosetteException
 * @returns {JSON}
 */
Api.prototype.tokens = function(parameters) {
  return this.callEndpoint(parameters, "tokens");
};

/**
 * Calls the morphology endpoint
 *
 * @param {DocumentParameters} parameters
 * @param {string} [facet] - The type of morphological analysis requested. Must come from
 * RosetteConstants.morphologyOutput. Defaults to complete
 * @throws RosetteException
 * @returns {JSON}
 */
Api.prototype.morphology = function(parameters, facet) {
  if (!facet) {
    facet = rosetteConstants.morpholoyOutput.COMPLETE;
  }
  return this.callEndpoint(parameters, "morphology/" + facet);
};

/**
 * Calls the entities endpoint
 *
 * @param {DocumentParameters} parameters
 * @param {boolean} linked - True if you want linked entities, false if you want no linking. Defaults to false.
 * @throws RosetteException
 * @returns {JSON}
 */
Api.prototype.entities = function(parameters, linked) {
  if (!linked) {
    return this.callEndpoint(parameters, "entities");
  }
  return this.callEndpoint(parameters, "entities/linked");
};

/**
 * Calls the categories endpoint
 *
 * @param {DocumentParameters} parameters
 * @throws RosetteException
 * @returns {JSON}
 */
Api.prototype.categories = function(parameters) {
  return this.callEndpoint(parameters, "categories");
};

/**
 * Calls the sentiment endpoint
 *
 * @param {DocumentParameters} parameters
 * @throws RosetteException
 * @returns {JSON}
 */
Api.prototype.sentiment = function(parameters) {
  return this.callEndpoint(parameters, "sentiment");
};

/**
 * Calls the name translation endpoint
 *
 * @param {NameTranslationParameters} parameters
 * @throws RosetteException
 * @returns {JSON}
 */
Api.prototype.translatedName = function(parameters) {
  return this.callEndpoint(parameters, "translated-name");
};

/**
 * Calls the name matching endpoint
 *
 * @param {NameMatchingParameters} parameters
 * @throws RosetteException
 * @returns {JSON}
 */
Api.prototype.matchedName = function(parameters) {
  return this.callEndpoint(parameters, "matched-name");
};

//var api = new Api("7eb3562318e5242b5a89ad80011f1e22");
//api.checkVersion();

//Export the constructor function as the export of this module file.
module.exports = Api;

//var api = new Api("7eb3562318e5242b5a89ad80011f1e22");
//var docParams = new DocumentParameters();
//docParams.setItem("content", "testing this out.");
//console.log(api.language(docParams));
