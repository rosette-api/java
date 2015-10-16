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
     * Serialize into a json string.
     *
     * @return string
     */
    public function serialize()
    {
        $this->validate();

        return json_encode($this);
    }
}
