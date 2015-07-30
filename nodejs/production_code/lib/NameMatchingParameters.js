/**
 * class NameMatchingParameters.
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
 * Parameters that are necessary for name translation operations.
 *
 * @param {Name} name1 - Source name to be matched
 * @param {Name} name2 - Target name to be matched
 * @extends DocumentParamSetBase
 * @copyright 2014-2015 Basis Technology Corporation.
 * @license http://www.apache.org/licenses/LICENSE-2.0
 * @constructor
 */
function NameMatchingParameters(name1, name2) {
  DocumentParamSetBase.call(this, ["name1", "name2"]); //super constructor
  this.params.name1 = name1;
  this.params.name2 = name2;
}

// inherit from DocumentParamSetBase
util.inherits(NameMatchingParameters, DocumentParamSetBase);

/**
 * Validates parameters
 * @throws RosetteException
 */
NameMatchingParameters.prototype.validate = function() {
  if (this.params.name1 == null) {
    throw new RosetteException("missingParameter", "Required Name Translation parameter not supplied", "name1");
  }
  if (this.params.name2 == null) {
    throw new RosetteException("missingParameter", "Required Name Translation parameter not supplied", "name2");
  }
  if (!this.okayString(this.params.name1.text)) {
    throw new RosetteException("missingParameter", "Required Name Translation parameter not supplied", "name1");
  }
  if (!this.okayString(this.params.name1.text)) {
    throw new RosetteException("missingParameter", "Required Name Translation parameter not supplied", "name2");
  }
};

//Export the constructor function as the export of this module file.
module.exports = NameMatchingParameters;
