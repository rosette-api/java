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

// inherit from DocumentParamSetBase
util.inherits(DocumentParameters, DocumentParamSetBase);

DocumentParameters.prototype.loadDocumentString = function(s, dataType) {
  if (!dataType) { dataType = rosetteConstants.dataFormat.UNSPECIFIED; }
  this.setItem("content", s);
  this.setItem("contentType", dataType);
  this.setItem("unit", rosetteConstants.inputUnit.DOC);
};

DocumentParameters.prototype.loadDocumentFile = function(path, dataType) {
  if (!dataType) { dataType = rosetteConstants.dataFormat.UNSPECIFIED; }
  if ([rosetteConstants.dataFormat.HTML, rosetteConstants.dataFormat.XHTML,
      rosetteConstants.dataFormat.UNSPECIFIED].indexOf(dataType) === -1) {
    throw new RosetteException("badArgument", "Must supply one of HTML, XHTML, or UNSPECIFIED", dataType);
  }
  //var s = fs.readFileSync(path, "utf8");
  var s = fs.readFileSync(path, "base64");
  console.log(s);
  this.loadDocumentString(s, dataType);
};

DocumentParameters.prototype.validate = function() {
  if (this.params.content == null) {
    if (this.params.contentUri == null) {
      throw new RosetteException("badArgument", "Must supply one of Content or ContentUri", "bad arguments");
    }
  }
  else { // Content is not null
    if (this.params.contentUri != null) {
      throw new RosetteException("badArgument", "Cannot supply both Content and ContentUri", "bad arguments");
    }
  }
};

//Export the constructor function as the export of this module file.
module.exports = DocumentParameters;
