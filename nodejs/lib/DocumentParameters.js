"use strict";

var util = require("util");
var fs = require("fs");
var DocumentParamSetBase = require("./DocumentParamSetBase");
var rosetteConstants = require("./rosetteConstants");
var RosetteException = require("./RosetteException");

function DocumentParameters() {
  DocumentParamSetBase.call(this, ["content", "contentUri", "contentType", "unit", "language"]); //super constructor
  this.setItem("unit", rosetteConstants.inputUnit.DOC);
}

// inherit from Error
util.inherits(DocumentParameters, DocumentParamSetBase);

DocumentParameters.prototype.loadDocumentString = function(s, dataType) {
  if (!dataType) dataType = rosetteConstants.dataFormat.UNSPECIFIED;
  this.setItem("content", s);
  this.setItem("contentType", dataType);
  this.setItem("unit", rosetteConstants.inputUnit.DOC);
};

DocumentParameters.prototype.loadDocumentFile = function(path, dataType) {
  if (!dataType) dataType = rosetteConstants.dataFormat.UNSPECIFIED;
  console.log(rosetteConstants.dataFormat.SIMPLE.valueOf() === "hi" || rosetteConstants.dataFormat.SIMPLE);
  if ([rosetteConstants.dataFormat.HTML, rosetteConstants.dataFormat.XHTML,
      rosetteConstants.dataFormat.UNSPECIFIED].indexOf(dataType) === -1) {
    throw new RosetteException("badArgument", "Must supply one of HTML, XHTML, or UNSPECIFIED", dataType);
  }
  var s = fs.readFileSync(path, "utf8");
  this.loadDocumentString(s, dataType);
};

//Export the constructor function as the export of this module file.
module.exports = DocumentParameters;
