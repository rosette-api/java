/**
 * Name represents an entity name in Rosette API.
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
var RosetteException = require("./RosetteException");

/**
 * Name represents an entity name in Rosette API.
 *
 * @param {string} text - Textual form of the name
 * @param {string} [entityType] - Entity type of the name.
 * @param {string} [language] - Language of the name.
 * @param {string} [script] - Script in which the name is written.
 * @copyright 2014-2015 Basis Technology Corporation.
 * @license http://www.apache.org/licenses/LICENSE-2.0
 * @constructor
 * @throws RosetteException
 */
function Name(text, entityType, language, script) {
  Object.call(this); //super constructor
  if (text) {
    /**
     * Textual form of the name
     * @type string
     */
    this.text = text;
  } else {
    throw new RosetteException("missingParameter", "Required Name parameter not supplied", "text");
  }
  if (entityType) {
    /**
     * Entity type of the name.
     * @type {string}
     */
    this.entityType = entityType;
  }
  if (language) {
    /**
     * Language of the name.
     * @type {string}
     */
    this.language = language;
  }
  if (script) {
    /**
     * Script in which the name is written.
     * @type {string}
     */
    this.script = script;
  }
}

// inherit from Error
util.inherits(Name, Object);

//Export the constructor function as the export of this module file.
module.exports = Name;
