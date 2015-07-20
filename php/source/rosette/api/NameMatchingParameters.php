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
     * constructor.
     *
     * @param Name - name1 source name to be matched
     * @param Name - name2 target name to be matched
     */
    public function __construct(Name $name1, Name $name2)
    {
        parent::__construct(array('name1', 'name2'));
        $this->params['name1'] = $name1;
        $this->params['name2'] = $name2;
    }

    /**
     * Validates parameters.
     *
     * @throws RosetteException
     */
    public function validate()
    {
        foreach (array('name1', 'name2') as $key) {
            if (empty($this->get($key))) {
                throw new RosetteException(
                    sprintf('Required name matching parameter not supplied: %s', $key),
                    RosetteException::$BAD_REQUEST_FORMAT
                );
            }
        }
    }
}
