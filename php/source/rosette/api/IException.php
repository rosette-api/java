<?php

/**
 * interface IException.
 *
 * Provides a base exception for the RosetteException and any future exceptions.
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
namespace rosette\api;

/**
 * Interface IException.
 */
interface IException
{
    /* Protected methods inherited from Exception class */
    /**
     * Returns the message associated with the exception.
     *
     * @return mixed
     */
    public function getMessage();                 // Exception message

    /**
     * Returns the code associated with the exception.
     *
     * @return mixed
     */
    public function getCode();                    // User-defined Exception code

    /**
     * Returns the file in which the exception occurred.
     *
     * @return mixed
     */
    public function getFile();                    // Source filename

    /**
     * Returns the line at which the exception occurred.
     *
     * @return mixed
     */
    public function getLine();                    // Source line

    /**
     * Returns a trace of the exception.
     *
     * @return mixed
     */
    public function getTrace();                   // An array of the backtrace()

    /**
     * Returns the trace in string format.
     *
     * @return mixed
     */
    public function getTraceAsString();           // Formatted string of trace

    /**
     * Override that returns the string representation of the exception.
     *
     * @return mixed
     */
    public function __toString();                 // formatted string for display

    /**
     * Constructor.
     *
     * @param null $message
     * @param int  $code
     */
    public function __construct($message = null, $code = 0);
}
