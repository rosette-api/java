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

abstract class RosetteConstants
{
    public static $DataFormat = [
        'SIMPLE' => '',
        'JSON' => 'application/json',
        'HTML' => 'text/html',
        'XHTML' => 'application/xhtml+xml',
        'UNSPECIFIED' => 'application/octet-stream'
    ];

    public static $InputUnit = [
        'DOC' => 'doc',
        'SENTENCE' => 'sentence'
    ];

    public static $MorphologyOutput = [
        'LEMMAS' => 'lemmas',
        'PARTS_OF_SPEECH' => 'parts-of-speech',
        'COMPOUND_COMPONENTS' => 'compound-components',
        'HAN_READINGS' => 'han-readings',
        'COMPLETE' => 'complete'
    ];

}
