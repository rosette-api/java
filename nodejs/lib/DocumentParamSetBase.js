"use strict";

var RosetteException = require("./RosetteException");

function DocumentParamSetBase(repertoire) {
  this.params = {};
  for (var key in repertoire) {
    this.params[repertoire[key]] = null;
  }
}

DocumentParamSetBase.prototype.setItem = function(key, value) {
  if (!(key in this.params)) {
    throw new RosetteException("badKey", "Unknown Rosette parameter key", key);
  }
  this.params[key] = value;
};

DocumentParamSetBase.prototype.getItem = function(key) {
  if (!(key in this.params)) {
    throw new RosetteException("badKey", "Unknown Rosette parameter key", key);
  }
  return this.params[key];
};

module.exports = DocumentParamSetBase;

// ------------------------------------    No serialize method yet    ----------------------------------
