<?php
/**
 * class DocumentParameters
 *
 * Parameter class for the standard Rosette API endpoints.  Does not include Name Translation
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
 * Class DocumentParameters
 * @package rosette\api
 */
class DocumentParameters extends RosetteParamsSetBase
{
    /**
     * Constructor
     * @throws RosetteException
     */
    public function __construct()
    {
        parent::__construct(["content", "contentUri", "contentType", "unit", "language"]);
        $this->Set("unit", RosetteConstants::$InputUnit['DOC']);
    }

    /**
     * Internal method to provide the serialized representation of the parameters
     * @throws RosetteException
     */
    public function serializable()
    {
        if ($this->Get("content") == null) {
            if ($this->Get("contentUri") == null) {
                throw new RosetteException(
                    "Must supply one of Content or ContentUri",
                    RosetteException::$INVALID_DATATYPE
                );
            }
        } else {
            if ($this->Get("contentUri") != null) {
                throw new RosetteException(
                    "Cannot supply both Content and ContentUri",
                    RosetteException::$INVALID_DATATYPE
                );
            }
        }
        $serialized = $this->ForSerialize();
        if (empty($this->Get("contentType")) && empty($this->Get("contentUri"))) {
            $serialized['contentType'] = RosetteConstants::$DataFormat['SIMPLE'];
        } elseif (in_array(
            $this->Get('contentType'),
            [RosetteConstants::$DataFormat['HTML'],
                RosetteConstants::$DataFormat['XHTML'],
                RosetteConstants::$DataFormat['UNSPECIFIED']]
        )
        ) {
            $content = $serialized['content'];
            $encoded = base64_encode($content);
            $serialized['content'] = $encoded;
        }
        return $serialized;
    }

    /**
     * Loads a file into the object.
     *
     * The file will be read as bytes; the appropriate conversion will be determined by the server.
     * The document unit size remains
     * by default L{InputUnit.DOC}.
     *
     * @param $path : Pathname of a file acceptable to the C{open}
     * function.
     * @param null $dataType
     * @throws RosetteException
     */
    public function loadDocumentFile($path, $dataType = null)
    {
        if (!$dataType) {
            $dataType = RosetteConstants::$DataFormat['UNSPECIFIED'];
        }
        if (!in_array(
            $dataType,
            [RosetteConstants::$DataFormat['HTML'],
                RosetteConstants::$DataFormat['XHTML'],
                RosetteConstants::$DataFormat['UNSPECIFIED']]
        )
        ) {
            throw new RosetteException(
                sprintf("Must supply one of HTML, XHTML, or UNSPECIFIED: %s", $dataType),
                RosetteException::$INVALID_DATATYPE
            );
        }
        $this->loadDocumentString(file_get_contents($path), $dataType);
    }

    /**
     * Loads a string into the object.
     *
     * The string will be taken as bytes or as Unicode dependent upon its native type and the data type asked for;
     * if the type is HTML or XHTML, bytes are expected, the encoding to be determined by the server.
     * The document unit size remains (by default) L{InputUnit.DOC}.
     *
     * @param $stringData
     * @param $dataType
     * @throws RosetteException
     */
    public function loadDocumentString($stringData, $dataType)
    {
        $this->Set("content", $stringData);
        $this->Set('contentType', $dataType);
        $this->Set('unit', RosetteConstants::$InputUnit['DOC']);
    }
}
