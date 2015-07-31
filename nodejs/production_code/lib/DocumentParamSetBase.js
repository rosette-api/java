/**
 * abstract class RosetteParamsSetBase.
 *
 * The base class for the parameter classes that are used for Rosette API operations.
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

var RosetteException = require("./RosetteException");

/**
 * The base class for the parameter classes that are used for Rosette API operations.
 *
 * @param {Array} repertoire - Keys in the params object
 * @abstract
 * @constructor
 */
function DocumentParamSetBase(repertoire) {
  /**
   * Parameters object to keep track of properties
   * @type {Object}
   */
  this.params = {};
  for (var key in repertoire) {
    this.params[repertoire[key]] = null;
  }
}

/**
 * Set a parameter
 * @param {string} key - Property to set
 * @param value -  Value to set it to
 */
DocumentParamSetBase.prototype.setItem = function(key, value) {
  if (!(key in this.params)) {
    throw new RosetteException("badKey", "Unknown Rosette parameter key", key);
  }
  this.params[key] = value;
};

/**
 * Get a parameter
 * @param {string} key - Property to access
 * @returns {*} Value of property
 */
DocumentParamSetBase.prototype.getItem = function(key) {
  if (!(key in this.params)) {
    throw new RosetteException("badKey", "Unknown Rosette parameter key", key);
  }
  return this.params[key];
};

DocumentParamSetBase.prototype.filterNulls = function() {
  var v = {};
  for (var key in this.params) {
    if (this.params[key]) {
      v[key] = this.params[key];
    }
  }
  return v;
};

DocumentParamSetBase.prototype.okayString = function(string) {
  return string != null && /\S/.test(string);
};

module.exports = DocumentParamSetBase;
