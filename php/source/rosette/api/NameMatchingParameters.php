<?php

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
namespace rosette\api;

/**
 * Class NameMatchingParameters.
 */
class NameMatchingParameters extends RosetteParamsSetBase
{
    /**
     * @var Name sourceName source name
     */
    public $sourceName;
    /**
     * @var Name targetName target name
     */
    public $targetName;
    /**
     * constructor.
     *
     * @param Name - sourceName source name to be matched
     * @param Name - targetName target name to be matched
     */
    public function __construct(Name $sourceName, Name $targetName)
    {
        $this->sourceName = $sourceName;
        $this->targetName = $targetName;
    }

    /**
     * Validates parameters.
     *
     * @throws RosetteException
     */
    public function validate()
    {
        if (empty($this->sourceName)) {
            throw new RosetteException(
                sprintf('Required name matching parameter not supplied: sourceName'),
                RosetteException::$BAD_REQUEST_FORMAT
            );
        }
        if (empty($this->targetName)) {
            throw new RosetteException(
                sprintf('Required name matching parameter not supplied: targetName'),
                RosetteException::$BAD_REQUEST_FORMAT
            );
        }
    }
}
