<?php
namespace BasisTechnology\Rosette;

/**
 * This data and information is proprietary to, and a valuable trade secret
 *  of, Basis Technology Corp.  It is given in confidence by Basis Technology
 * and may only be used as permitted under the license agreement under which
 * it has been distributed, and in no other way.
 *
 * @copyright (c) 2015 Basis Technology Corporation All rights reserved.
 *
 * The technical data and information provided herein are provided with
 * `limited rights', and the computer software provided herein is provided
 * with `restricted rights' as those terms are defined in DAR and ASPR
 * 7-104.9(a).
 */

/**
 * class RosetteParamsSetBase
 *
 * Provides a base class for handling the parameterized data used for submissions
 *
 */
abstract class RosetteParamsSetBase
{
    public $params = [];

    /**
     * @param $repertoire
     */
    public function __construct($repertoire)
    {
        foreach ($repertoire as $key) {
            $this->params[$key] = '';
        }
    }

    /**
     * @param $key
     * @param $val
     * @throws RosetteException
     */
    public function Set($key, $val)
    {
        if (!array_key_exists($key, $this->params)) {
            throw new RosetteException(sprintf("Unknown Rosette parameter key %s", $key), RosetteException::$BAD_KEY);
        }
        $this->params[$key] = $val;
    }

    /**
     * @param $key
     * @return mixed
     * @throws RosetteException
     */
    public function Get($key)
    {
        if (!array_key_exists($key, $this->params)) {
            throw new RosetteException(sprintf("Unknown Rosette parameter key %s", $key), RosetteException::$BAD_KEY);
        }
        return $this->params[$key];
    }

    /**
     * @return array
     */
    public function ForSerialize()
    {
        $result = [];
        foreach ($this->params as $key => $value) {
            if ($value != null) {
                $result[$key] = $value;
            }
        }
        return $result;
    }

    public abstract function Serializable();
}