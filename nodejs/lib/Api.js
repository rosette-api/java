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

var http = require("http");
var https = require("https");
var URL = require("url");
var zlib = require("zlib");
var rosetteConstants = require("./rosetteConstants");
var RosetteException = require("./RosetteException");
var DocumentParameters = require("./DocumentParameters");
var RelationshipsParameters = require("./RelationshipsParameters");

/**
 * Compatible server version.
 *
 * @type string
 */
var BINDING_VERSION = "0.7";

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
  this.nRetries = 1;
}

/**
 * Set the number of failed retries before giving up.
 * @param {number} numRetries - Number of times to retry before failing a call
 */
Api.prototype.setNumRetries = function(numRetries) {
  if (typeof numRetries === "number") {
    this.nRetries = numRetries;
  }
};

/**
 * Checks if the server version is compatible with the API version
 *
 * @throws RosetteException
 * @returns {boolean}
 */
Api.prototype.checkVersion = function(api) {
  if (this.versionChecked) {
    return true;
  }
  this.compareBindingToServer(function(err, res) {
    if (err) {
      throw err;
    }
    if (!res.versionChecked) {
      throw new RosetteException("incompatibleVersion", "The server version is not compatible with binding version " + BINDING_VERSION, res.Version);
    }
    api.versionChecked = true;
  });
};

/**
 * function retryingRequest.
 *
 * Encapsulates the GET/POST and retries N_RETRIES
 *
 * @return {JSON} response {"json": json, "statusCode": status}
 *
 * @throws RosetteException
 *
 * @param {function} callback - Function to be executed with final result (just passing until finishResult)
 * @param {string} op - operation
 * @param {string} url - target URL
 * @param {JSON} [data] - submission data
 * @param {string} action - Action to be passed to finishResult
 * @param {Api} api - API this is acting on
 * @param {number} [tryNum] - number of times this has been called already
 */
Api.prototype.retryingRequest = function(err, callback, op, url, data, action, api, tryNum) {
  if (err) {
    return callback(err);
  }
  if (!tryNum) {
    tryNum = 0;
  }
  var urlParts = URL.parse(url);
  var protocol = http;
  if (urlParts.protocol === "https:") {
    protocol = https;
  }
  var headers = {"accept": "application/json", "accept-encoding": "gzip", "content-type": "application/json"};
  if (this.userKey) {
    headers["user_key"] = this.userKey;
  }
  var result = new Buffer("");

  var options = {
    hostname: urlParts.hostname,
    path: urlParts.path,
    method: op,
    headers: headers,
    agent: false
  };
  if (urlParts.port) {
    options.port = urlParts.port;
  }

  var req = protocol.request(options, function (res) {
    res.on("data", function (resp) {
      result = Buffer.concat([result, resp]);
    });
    res.on("end", function (error) {
      if (error) {
        return callback(error);
      }
      if(res.headers["content-encoding"] === "gzip") {
        result = zlib.gunzipSync(result);
      }
      // ensure that request closes to avoid tooManyRequests errors
      req.abort();

      if(res.statusCode < 500) {
        api.finishResult(null, {"json": JSON.parse(result.toString()), "statusCode": res.statusCode}, action, callback);
      }
      else {
        var message = null;
        var code = "unknownError";
        if (result != null) {
          try {
            result = JSON.parse(result.toString());
            if ("message" in result) {
              message = result.message;
            }
            if ("code" in result) {
              code = result.code;
            }
          } catch (e) {
            message = e.message;
          }
        }
        if (!message) {
          message = "A retryable network operation has not succeeded";
        }
        if (tryNum >= api.nRetries) {
          err = new RosetteException(code, message, url);
          return callback(err);
        }
        else {
          api.retryingRequest(null, callback, op, url, data, action, api, ++tryNum);
        }
      }
    });
  });

  if(data) {
    req.write(JSON.stringify(data));
  }

  req.on("error", function(e) {
    callback(e);
  });
  req.end();
};

/**
 * Internal operations processor for most endpoints
 *
 * @param {function} callback - Function to be executed with final result (just passing until finishResult)
 * @param {(DocumentParameters|NameMatchingParameters|NameTranslationParameters)} parameters
 * @param {string} subUrl
 * @throws RosetteException
 */
Api.prototype.callEndpoint = function(callback, parameters, subUrl) {
  var err = null;
  // Check that version is compatible
  var api = this;
  this.checkVersion(api);

  // If parameters is a string (check both types of string)
  if (typeof parameters === "string" || parameters instanceof String) {
    var text = parameters;
    if (subUrl === "relationships") {
        parameters = new RelationshipsParameters();
        parameters.setItem("content", text);
    }
    else if (subUrl !== "matched-name" && subUrl !== "translated-name") {
        parameters = new DocumentParameters();
        parameters.setItem("content", text);
    }
    else {
        err = new RosetteException("incompatible", "Text-only input only works for DocumentParameter endpoints", subUrl);
        return callback(err);
    }
  }
  if (this.useMultiPart && parameters.contentType !== rosetteConstants.dataFormat.SIMPLE) {
    err = new RosetteException("incompatible", "Multipart requires contentType SIMPLE", parameters.contentType);
    return callback(err);
  }
  var url = this.serviceUrl + "/" + subUrl;

  // Check that parameters follow their guidelines
  parameters.validate();
  // Call with serialized parameters
  this.retryingRequest(err, callback, "POST", url, parameters.filterNulls(), "callEndpoint", api);
};

/**
 * Processes the response, returning either the decoded Json or throwing an exception.
 *
 * @param {JSON} result - Json of the form {json, statusCode}
 * @param {string} action - Description of goal to be used in error message if necessary
 * @throws RosetteException
 * @param {function} callback - Function to be executed with final result
 */
Api.prototype.finishResult = function(err, result, action, callback) {
  if (err) {
    return callback(err);
  }
  var code = result.statusCode;
  var json = result.json;
  // If all is well, return the json
  if (code === 200) {
    callback(err, json);
  }
  // Otherwise collect information to construct error
  else {
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
    err = new RosetteException(serverCode, complaintUrl + " : failed to communicate with Rosette", msg);
    callback(err);
  }
};

/**
  * Calls the info endpoint
  *
  * @throws RosetteException
  * @param {function} callback
  */
Api.prototype.info = function(callback) {
  var url = this.serviceUrl + "/info";
  this.retryingRequest(null, callback, "GET", url, null, "info", this);
};

/**
 * Calls the ping endpoint
 *
 * @throws RosetteException
 * @param {function} callback
 */
Api.prototype.ping = function(callback) {
  var url = this.serviceUrl + "/ping";
  this.retryingRequest(null, callback, "GET", url, null, "ping", this);
};

/**
 * Calls the internal checkVersion endpoint
 *
 * @throws RosetteException
 * @param {function} callback
 */
Api.prototype.compareBindingToServer = function(callback) {
  var url = this.serviceUrl + "/info?clientVersion=" + BINDING_VERSION;
  this.retryingRequest(null, callback, "POST", url, null, "info", this);
};

/**
 * Calls the language endpoint
 *
 * @param {DocumentParameters} parameters
 * @param {function} callback
 * @throws RosetteException
 */
Api.prototype.language = function(parameters, callback) {
  this.callEndpoint(callback, parameters, "language");
};

/**
 * Calls the languageInfo endpoint
 * @param {function} callback
 * @throws RosetteException
 */
Api.prototype.languageInfo = function(callback) {
  var url = this.serviceUrl + "/language/info";
  this.retryingRequest(null, callback, "GET", url, null, "language-info", this);
};

/**
 * Calls the sentences endpoint
 *
 * @param {DocumentParameters} parameters
 * @param {function} callback
 * @throws RosetteException
 */
Api.prototype.sentences = function(parameters, callback) {
  this.callEndpoint(callback, parameters, "sentences");
};

/**
 * Calls the tokens endpoint
 *
 * @param {DocumentParameters} parameters
 * @param {function} callback
 * @throws RosetteException
 */
Api.prototype.tokens = function(parameters, callback) {
  this.callEndpoint(callback, parameters, "tokens");
};

/**
 * Calls the morphology endpoint
 *
 * @param {DocumentParameters} parameters
 * @param {string} [facet] - The type of morphological analysis requested. Must come from
 * RosetteConstants.morphologyOutput. Defaults to complete
 * @param {function} callback
 * @throws RosetteException
 */
Api.prototype.morphology = function(parameters, facet, callback) {
  if (!facet) {
    facet = rosetteConstants.morpholoyOutput.COMPLETE;
  }
  this.callEndpoint(callback, parameters, "morphology/" + facet);
};

/**
 * Calls the entities endpoint
 *
 * @param {DocumentParameters} parameters
 * @param {boolean} linked - True if you want linked entities, false if you want no linking. Defaults to false.
 * @param {function} callback
 * @throws RosetteException
 */
Api.prototype.entities = function(parameters, linked, callback) {
  if (!linked) {
    this.callEndpoint(callback, parameters, "entities");
  }
  else {
    this.callEndpoint(callback, parameters, "entities/linked");
  }
};

/**
 * Calls the categories endpoint
 *
 * @param {DocumentParameters} parameters
 * @param {function} callback
 * @throws RosetteException
 */
Api.prototype.categories = function(parameters, callback) {
  this.callEndpoint(callback, parameters, "categories");
};

/**
 * Calls the sentiment endpoint
 *
 * @param {DocumentParameters} parameters
 * @param {function} callback
 * @throws RosetteException
 */
Api.prototype.sentiment = function(parameters, callback) {
  this.callEndpoint(callback, parameters, "sentiment");
};

/**
 * Calls the name translation endpoint
 *
 * @param {NameTranslationParameters} parameters
 * @param {function} callback
 * @throws RosetteException
 */
Api.prototype.translatedName = function(parameters, callback) {
  this.callEndpoint(callback, parameters, "translated-name");
};

/**
 * Calls the name matching endpoint
 *
 * @param {NameMatchingParameters} parameters
 * @param {function} callback
 * @throws RosetteException
 */
Api.prototype.matchedName = function(parameters, callback) {
  this.callEndpoint(callback, parameters, "matched-name");
};

/**
 * Calls the relationships endpoint
 *
 * @param {RelationshipsParameters} parameters
 * @param {function} callback
 * @throws RosetteException
 */
Api.prototype.relationships = function(parameters, callback) {
  this.callEndpoint(callback, parameters, "relationships");
};

module.exports = Api;

