<?php
/**
 * class RelationshipsParameters.
 *
 * Parameter class for the standard Rosette API endpoints.  Does not include Name Translation
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
 * Class RelationshipsParameters.
 */
class RelationshipsParameters extends DocumentParameters
{
    /**
     * @var array options contains options specific to relationships
     */
    public $options;

    /**
     * Constructor.
     */
    public function __construct()
    {
        parent::__construct();
        $this->options = array('accuracyMode' => RosetteConstants::$RelationshipOptions['PRECISION']);
    }

    /**
     * Validation specific to relationships.
     *
     * @throws RosetteException
     */
    public function validate()
    {
        parent::validate();
    }

    /**
     * @param $key   string options key name, e.g. accuracyMode
     * @param $value string options value, e.g. 'PRECISION', 'RECALL'
     */
    public function setOption($key, $value)
    {
        $this->options[$key] = $value;
    }

    /**
     * @param $key string options key name, e.g. accuracyMode
     *
     * @return mixed option value if set or null
     */
    public function getOption($key)
    {
        return array_key_exists($key, $this->options) ? $this->options[$key] : null;
    }
}
