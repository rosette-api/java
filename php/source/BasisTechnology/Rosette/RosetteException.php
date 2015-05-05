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
require_once 'CustomException.php';
/**
 * class RosetteException
 *
 * Custom exception to handle Rosette specific errors
 */
class RosetteException extends CustomException {
    public static $UNKNOWN_VARIABLE = -1;
    public static $BAD_KEY = -2;
    public static $BAD_ARGUMENT = -3;
    public static $MISSING_PARAMETER = -4;
    public static $INCOMPATIBLE = -5;
    public static $BAD_SERVER_VERSION = -6;
    public static $UNKNOWN_ERROR = -7;
}