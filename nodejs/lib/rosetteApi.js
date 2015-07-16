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
var rosetteConstants = require("./rosetteConstants.js");
var RosetteException = require("./RosetteException");
//var DocumentParamSetBase = require("./DocumentParamSetBase");
//var DocumentParameters = require("./DocumentParameters");
//var NameTranslationParameters = require("./NameTranslationParameters");
//var NameMatchingParameters = require("./NameMatchingParameters");
//var Name = require("./Name");

/**
 * Simple ping.
 * @param {string} foo - Not a real param but testing JSDoc.
 * @return A python dictionary including the ping message of the API
 **/

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
  this.nRetries = 3;
}

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
Api.prototype.retryingRequest = function(op, url, headers, data) {
  for (var i = 0; i < this.nRetries; i++) {
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
    xhr.abort();
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
    message = "A retryable network operation has not succeeded after " + this.nRetries + " attempts";
    throw new RosetteException(code, message, url);
  }
};

Api.prototype.getHttp = function(url, headers) {
  return this.retryingRequest("GET", url, headers);
};

Api.prototype.postHttp = function(url, headers, data) {
  return this.retryingRequest("POST", url, headers, data);
};

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
  var headers = {"Accept": "application/json"/*, "Accept-Encoding": "gzip"*/};
  if (this.userKey) {
    headers["user_key"] = this.userKey;
  }
  parameters.validate();
  var r = this.postHttp(url, headers, parameters.params); // should be parameters serialized ???
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
  var url = this.serviceUrl + "/info";
  var headers = {"Accept": "application/json"};
  if (this.userKey) {
    headers["user_key"] = this.userKey;
  }
  return this.finishResult(this.getHttp(url, headers), "info");
};

Api.prototype.ping = function() {
  var url = this.serviceUrl + "/ping";
  var headers = {"Accept": "application/json"};
  if (this.userKey) {
    headers["user_key"] = this.userKey;
  }
  return this.finishResult(this.getHttp(url, headers), "ping");
};

Api.prototype.language = function(parameters) {
  return this.callEndpoint(parameters, "language");
};

Api.prototype.languageInfo = function() {
  var url = this.serviceUrl + "/language/info";
  var headers = {"Accept": "application/json"};
  if (this.userKey) {
    headers["user_key"] = this.userKey;
  }
  var result = this.getHttp(url, headers);
  return this.finishResult(result, "language-info");
};

Api.prototype.sentences = function(parameters) {
  return this.callEndpoint(parameters, "sentences");
};

Api.prototype.tokens = function(parameters) {
  return this.callEndpoint(parameters, "tokens");
};

Api.prototype.morphology = function(parameters, facet) {
  if (!facet) {
    facet = rosetteConstants.morpholoyOutput.COMPLETE;
  }
  return this.callEndpoint(parameters, "morphology/" + facet);
};

Api.prototype.entities = function(parameters, linked) {
  if (!linked) {
    return this.callEndpoint(parameters, "entities");
  }
  return this.callEndpoint(parameters, "entities/linked");
};

Api.prototype.categories = function(parameters) {
  return this.callEndpoint(parameters, "categories");
};

Api.prototype.sentiment = function(parameters) {
  return this.callEndpoint(parameters, "sentiment");
};

Api.prototype.translatedName = function(parameters) {
  return this.callEndpoint(parameters, "translated-name");
};

Api.prototype.matchedName = function(parameters) {
  return this.callEndpoint(parameters, "matched-name");
};

Api.prototype.translatedName = function(parameters) {
  return this.callEndpoint(parameters, "translated-name");
};

//Export the constructor function as the export of this module file.
module.exports = Api;

