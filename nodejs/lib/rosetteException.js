//FILE ValueOutOfRangeError.js
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
        "Status: ",
        status,
        " message: ",
        message,
        ": ",
        responseMessage
    ].join(" "); //Concat and make a string.
}

// inherit from Error
util.inherits(RosetteException, Error);

//Export the constructor function as the export of this module file.
module.exports = RosetteException;
