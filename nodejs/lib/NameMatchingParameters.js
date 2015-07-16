"use strict";

var util = require("util");
var DocumentParamSetBase = require("./DocumentParamSetBase");
var Name = require("./Name");
var RosetteException = require("./RosetteException");

function NameMatchingParameters(name1, name2) {
  DocumentParamSetBase.call(this, ["name1", "name2"]); //super constructor
  this.params.name1 = name1;
  this.params.name2 = name2;
}

// inherit from DocumentParamSetBase
util.inherits(NameMatchingParameters, DocumentParamSetBase);

NameMatchingParameters.prototype.validate = function() {
  if (this.params.name1 == null) {
    throw new RosetteException("missingParameter", "Required Name Translation parameter not supplied", "name1");
  }
  if (this.params.name2 == null) {
    throw new RosetteException("missingParameter", "Required Name Translation parameter not supplied", "name2");
  }
};

//Export the constructor function as the export of this module file.
module.exports = NameMatchingParameters;
