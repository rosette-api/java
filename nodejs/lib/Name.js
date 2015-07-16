"use strict";

var util = require("util");
var RosetteException = require("./RosetteException");

function Name(text, entityType, language, script) {
  Object.call(this); //super constructor
  if (text) {
    this.text = text;
  } else {
    throw new RosetteException("missingParameter", "Required Name parameter not supplied", "text");
  }
  if (entityType) {
    this.entityType = entityType;
  }
  if (language) {
    this.language = language;
  }
  if (script) {
    this.script = script;
  }
}

// inherit from Error
util.inherits(Name, Object);

//Export the constructor function as the export of this module file.
module.exports = Name;
