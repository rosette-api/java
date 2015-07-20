/**
 * RosetteException.
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

//Utils module loaded
var util = require("util");

/**
 * Error Class ValueOutOfRangeError
 * */
function RosetteException(status, message, responseMessage) {

    /*INHERITANCE*/
    Error.call(this); //super constructor
    Error.captureStackTrace(this, this.constructor); //super helper method to include stack trace in error object

    //Set the name for the ERROR
    this.name = this.constructor.name; //set our function’s name as error name.

    //Define error message
    this.message = [
        status,
        ": ",
        message,
        ": ",
        responseMessage
    ].join(""); //Concat and make a string.
}

// inherit from Error
util.inherits(RosetteException, Error);

//Export the constructor function as the export of this module file.
module.exports = RosetteException;
