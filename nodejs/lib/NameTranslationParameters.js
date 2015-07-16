"use strict";

var util = require("util");
var DocumentParamSetBase = require("./DocumentParamSetBase");
var RosetteException = require("./RosetteException");

function NameTranslationParameters() {
  DocumentParamSetBase.call(this, ["name", "targetLanguage", "entityType", "sourceLanguageOfOrigin",
    "sourceLanguageOfUse", "sourceScript", "targetScript", "targetScheme"]); //super constructor
}

// inherit from DocumentParamSetBase
util.inherits(NameTranslationParameters, DocumentParamSetBase);

NameTranslationParameters.prototype.validate = function() {
  if (this.params.name == null) {
    throw new RosetteException("missingParameter", "Required Name Translation parameter not supplied", "name");
  }
  if (this.params.targetLanguage == null) {
    throw new RosetteException("missingParameter", "Required Name Translation parameter not supplied", "targetLanguage");
  }
};

//Export the constructor function as the export of this module file.
module.exports = NameTranslationParameters;
