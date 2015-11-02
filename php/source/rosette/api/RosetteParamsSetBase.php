<?php

/**
 * abstract class RosetteParamsSetBase.
 *
 * The base class for the parameter classes that are used for Rosette API operations.
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
 * Class RosetteParamsSetBase.
 */
abstract class RosetteParamsSetBase
{
    /**
     * Constructor.
     */
    protected function __construct()
    {
    }

    /**
     * Sets a property based on the name.
     *
     * @param string $propertyName the name of the property
     * @param mixed  $value        the value to set
     */
    public function set($propertyName, $value)
    {
        if (property_exists($this, $propertyName)) {
            $this->{$propertyName} = $value;
        }
    }

    /**
     * Gets a property value based on the property name.
     *
     * @param string $propertyName the name of the property
     *
     * @return mixed property value
     *
     * @throws RosetteException if property name not found
     */
    public function get($propertyName)
    {
        if (property_exists($this, $propertyName)) {
            return $this->{$propertyName};
        } else {
            throw new RosetteException(
                'Property name not found',
                RosetteException::$INVALID_PROPERTY_NAME
            );
        }
    }

    /**
     * Validates parameters before serializing them.
     *
     * @return mixed
     *
     * @throws RosetteException
     */
    abstract public function validate();

    /**
     * Recursively removes empty properties to facilitate json encoding.
     *
     * @param $obj Object to clean
     *
     * @return mixed
     */
    private function removeEmptyProperties($obj)
    {
        $objVars = get_object_vars($obj);

        if (count($objVars) > 0) {
            foreach ($objVars as $propName => $propVal) {
                if (gettype($propVal) === 'object') {
                    $cObj = $this->removeEmptyProperties($propVal);
                    if ($cObj === null) {
                        unset($obj->$propName);
                    } else {
                        $obj->$propName = $cObj;
                    }
                } else {
                    if (empty($propVal)) {
                        unset($obj->$propName);
                    }
                }
            }
        } else {
            return;
        }

        return $obj;
    }

    /**
     * Serialize into a json string.
     *
     * @return string
     */
    public function serialize()
    {
        $this->validate();

        $classObject = $this->removeEmptyProperties($this);

        return json_encode($this);
    }
}
