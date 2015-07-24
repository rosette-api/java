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

module.exports = DocumentParamSetBase;

DocumentParamSetBase.prototype.serialize = function() {
  var v = {};
  for (var key in this.params) {
    if (this.params[key]) {
      v[key] = this.params[key];
    }
  }
  return v;
};

