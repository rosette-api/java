<?php

/**
 * class NameTranslationParameters.
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
 * Class NameTranslationParameters.
 */
class NameTranslationParameters extends RosetteParamsSetBase
{
    /**
     * @var string name to translate
     */
    public $name;
    /**
     * @var string targetLanguage ISO 639-3 code for the translation language
     */
    public $targetLanguage;
    /**
     * @var string entityType type of entity, e.g. PERSON, LOCATION, ORGANIZATION (optional)
     */
    public $entityType;
    /**
     * @var string sourceLanguageOfOrigin ISO 693-3 code for name's language of origin (optional)
     */
    public $sourceLanguageOfOrigin;
    /**
     * @var string sourceLanguageOfUse ISO 693-3 code for name's language of use (optional)
     */
    public $sourceLanguageOfUse;
    /**
     * @var string sourceScript ISO 15924 code for name's script (optional)
     */
    public $sourceScript;
    /**
     * @var string targetScript ISO 15924 code for name's script (optional)
     */
    public $targetScript;
    /**
     * @var string targetScheme transliteration scheme for the translation (optional)
     */
    public $targetScheme;
    /**
     * constructor.
     */
    public function __construct()
    {
    }

    /**
     * Validates parameters.
     *
     * @throws RosetteException
     */
    public function validate()
    {
        if (empty($this->name)) {
            throw new RosetteException(
                sprintf('Required name translation parameter not supplied: name'),
                RosetteException::$BAD_REQUEST_FORMAT
            );
        }
        if (empty($this->targetLanguage)) {
            throw new RosetteException(
                sprintf('Required name translation parameter not supplied: targetLanguage'),
                RosetteException::$BAD_REQUEST_FORMAT
            );
        }
    }
}
