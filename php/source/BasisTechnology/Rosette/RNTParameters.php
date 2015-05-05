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
 * Parameter object for C{translated_name} endpoint.
 *
 * The following values may be set by the indexing (i.e.,C{ parms["name"]}) operator.  The values are all strings (when not C{None}).
 * All are optional except C{name} and C{targetLanguage}.  Scripts are in ISO15924 codes, and languages in ISO639 (two- or three-letter) codes.
 * See the RNT documentation for more description of these terms, as well as the content of the return result.
 * C{name} The name to be translated.
 * C{targetLanguage} The language into which the name is to be translated.
 * C{entityType} The entity type (TBD) of the name.
 * C{sourceLanguageOfOrigin} The language of origin of the name.
 * C{sourceLanguageOfUse} The language of use of the name.
 * C{sourceScript} The script in which the name is supplied.
 * C{targetScript} The script into which the name should be translated.
 * C{targetScheme} The transliteration scheme by which the translated name should be rendered.
 */
class RNTParameters extends RosetteParamsSetBase
{
    /**
     * constructor
     */
    public function __construct() {
        parent::__construct(['name', 'targetLanguage', 'entityType', 'sourceLanguageOfOrigin',
            'sourceLanguageOfUse', 'sourceScript', 'targetScript', 'targetScheme']);
    }

    /**
     * @return array
     * @throws RosetteException
     */
    public function Serializable()
    {
        foreach (['name', 'targetLanguage'] as $key) {
            if ($this->Get($key) == null) {
                throw new RosetteException(sprintf("Required RNT parameter not supplied: %s", $key), RosetteException::$MISSING_PARAMETER);
            }
        }

        return $this->ForSerialize();
    }
}