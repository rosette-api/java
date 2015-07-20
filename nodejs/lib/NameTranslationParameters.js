/**
 * class NameTranslationParameters.
 *
 * Parameters that are necessary for name translation operations.
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
var DocumentParamSetBase = require("./DocumentParamSetBase");
var RosetteException = require("./RosetteException");

/**
 * @extends DocumentParamSetBase
 * @copyright 2014-2015 Basis Technology Corporation.
 * @license http://www.apache.org/licenses/LICENSE-2.0
 * @constructor
 */
function NameTranslationParameters() {
  DocumentParamSetBase.call(this, ["name", "targetLanguage", "entityType", "sourceLanguageOfOrigin",
    "sourceLanguageOfUse", "sourceScript", "targetScript", "targetScheme"]); //super constructor
}

// inherit from DocumentParamSetBase
util.inherits(NameTranslationParameters, DocumentParamSetBase);

/**
 * Validates parameters.
 * @throws RosetteException
 */
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
