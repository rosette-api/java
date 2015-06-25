<?php
/**
 * Name represents an entity name in Rosette API
 *
 * @copyright 2014-2015 Basis Technology Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * @license http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 **/
namespace rosette\api;

/**
 * Class Name
 * @package rosette\api
 */
class Name
{
    /**
     * Textual form of the name
     * @var
     */
    public $text;
    /**
     * Entity type of the name
     * @var
     */
    public $entityType;
    /**
     * Language of the name
     * @var
     */
    public $language;

    /**
     * Script in which the name is written
     * @var
     */
    public $script;

    /**
     * Constructor
     *
     * @param $text
     * @param $entityType
     * @param $language
     * @param $script
     * @throws RosetteException
     */
    public function __construct($text, $entityType = null, $language = null, $script = null)
    {
        if ($text === null) {
            throw new RosetteException(
                sprintf("The text of a name is required"),
                RosetteException::$BAD_REQUEST_FORMAT
            );
        }
        $this->text = $text;
        $this->entityType = $entityType;
        $this->language = $language;
        $this->script = $script;
    }

    /**
     * Magic method to return the stringized form of NameObject
     * @return mixed
     */
    public function __toString()
    {
        $a = [];
        $ref = new \ReflectionClass($this);
        foreach ($ref->getProperties() as $property) {
            $name = $property->name;
            $value = $property->getValue($this);
            if (!is_null($value)) {
                $a[$name] = $value;
            }
        }
        return json_encode($a);
    }
}
