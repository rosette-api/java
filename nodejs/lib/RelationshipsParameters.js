/**
 * class RelationshipsParameters.
 *
 * Parameter class for the Rosette API relationships endpoints.  Based on the RelationshipsParameters
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

var util = require("util");
var fs = require("fs");
var DocumentParamSetBase = require("./DocumentParamSetBase");
var rosetteConstants = require("./rosetteConstants");
var RosetteException = require("./RosetteException");

/**
 * @constructor
 *
 * @extends DocumentParamSetBase
 * @copyright 2014-2015 Basis Technology Corporation.
 * @license http://www.apache.org/licenses/LICENSE-2.0
 * @throws RosetteException
 */
function RelationshipsParameters() {
  DocumentParamSetBase.call(this, ["content", "contentUri", "contentType", "unit", "language", "options"]); //super constructor
  this.setItem("unit", rosetteConstants.inputUnit.DOC);
  this.setItem("options", { accuracyMode: "PRECISION" });
}

// inherit from DocumentParamSetBase
util.inherits(RelationshipsParameters, DocumentParamSetBase);

/**
 * Setter for the options parameter
 * @param {string} key
 * @param value
 *
 * @throws RosetteException
 */
RelationshipsParameters.prototype.setOption = function(key, value) {
    if (!(key in this.params.options)) {
        throw new RosetteException("badKey", "Unknown Rosette parameter options key", key);
    }
    this.params.options[key] = value;
};

/**
 * Loads a string into the object.
 *
 * The string will be taken as bytes or as Unicode dependent upon its native type and the data type asked for;
 * if the type is HTML or XHTML, bytes are expected, the encoding to be determined by the server.
 * The document unit size remains (by default) inputUnit.DOC.
 *
 * @param {string} stringData
 * @param {string} [dataType]
 *
 * @throws RosetteException
 */
RelationshipsParameters.prototype.loadDocumentString = function(stringData, dataType) {
  if (!dataType) { dataType = rosetteConstants.dataFormat.UNSPECIFIED; }
  this.setItem("content", stringData);
  this.setItem("contentType", dataType);
  this.setItem("unit", rosetteConstants.inputUnit.DOC);
};

/**
 * Loads a file into the object.
 *
 * The file will be read as bytes; the appropriate conversion will be determined by the server.
 * The document unit size remains
 * by default inputUnit.DOC.
 *
 * @param {string} path - Path to a file
 * @param {string} [dataType]
 */
RelationshipsParameters.prototype.loadDocumentFile = function(path, dataType) {
  if (!dataType) { dataType = rosetteConstants.dataFormat.UNSPECIFIED; }
  if ([rosetteConstants.dataFormat.HTML, rosetteConstants.dataFormat.XHTML,
      rosetteConstants.dataFormat.UNSPECIFIED].indexOf(dataType) === -1) {
    throw new RosetteException("badArgument", "Must supply one of HTML, XHTML, or UNSPECIFIED", dataType);
  }
  var s = fs.readFileSync(path, "base64");
  this.loadDocumentString(s, dataType);
};

/**
 * Validates parameters
 * @throws RosetteException
 */
RelationshipsParameters.prototype.validate = function() {
  if (!this.okayString(this.params.content) && !this.okayString(this.params.contentUri)) {
    throw new RosetteException("badArgument", "Must supply one of Content or ContentUri", "bad arguments");
  }
  if (this.okayString(this.params.content) && this.okayString(this.params.contentUri)) {
    throw new RosetteException("badArgument", "Cannot supply both Content and ContentUri", "bad arguments");
  }
  if (this.params.options.accuracyMode !== 'PRECISION' && this.params.accuracyMode !== 'RECALL') {
    throw new RosetteException('badOption', 'accuracyMode must be either PRECISION or RECALL', 'bad options');
  }
};

//Export the constructor function as the export of this module file.
module.exports = RelationshipsParameters;
