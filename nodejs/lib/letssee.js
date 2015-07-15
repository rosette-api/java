"use strict";

var rosConst = require("./rosetteConstants");

function MyCustomType(value) {
  this.property = value;
}

MyCustomType.prototype.method = function() {
  return this.property;
};

var bob = new MyCustomType(5);
console.log(bob.method());

console.log(rosConst.dataFormat);

var RosetteException = require("./RosetteException");
try {
  throw new RosetteException(400, "nope", "not happening");
} catch (e) {
  console.log(e);
}

var acceptable = [rosConst.dataFormat.HTML, rosConst.dataFormat.UNSPECIFIED, rosConst.dataFormat.XHTML];
//console.log(acceptable.contains(rosConst.dataFormat.SIMPLE));

var json = {
  "code": "unsupportedLanguage",
  "message": "Arabic is not supported by Rosette Categorizer",
  "requestId": "de587a1c-a23a-4d4a-a52e-1fa96fe13f33"
};

console.log("mesage" in json);
