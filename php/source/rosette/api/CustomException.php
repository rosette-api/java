<?php

/**
 * CustomException class provides the implementation of IException and the base for RosetteException and any future
 * exception classes.
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
 * Class CustomException.
 */
class CustomException extends \Exception implements IException
{
    /**
     * Exception message.
     *
     * @var string
     */
    protected $message = 'Unknown exception';
    /**
     * User-defined exception code.
     *
     * @var int
     */
    protected $code = 0;
    /**
     * Source filename of exception.
     *
     * @var
     */
    protected $file;
    /**
     * Source line of exception.
     *
     * @var
     */
    protected $line;

    /**
     * Constructor.
     *
     * @param null $message
     * @param int  $code
     */
    public function __construct($message = null, $code = 0)
    {
        if (!$message) {
            throw new $this('Unknown '.get_class($this));
        }
        $code = is_numeric($code) ? $code : 0;
        parent::__construct($message, $code);
    }

    /**
     * Magic method to return the stringized form of CustomException.
     *
     * @return string
     */
    public function __toString()
    {
        return get_class($this)." '{$this->message}' in {$this->file}({$this->line})\n"
        ."{$this->getTraceAsString()}";
    }
}
