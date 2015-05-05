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

require_once 'RosetteConstants.php';
require_once 'RosetteParamsSetBase.php';
/**
 * class RosetteParameters
 *
 * Parameter object for all operations requiring input other than translated_name.
 * Four fields, C{content}, C{contentType}, C{unit}, and C{inputUri}, are set via
 * the subscript operator, e.g., C{params["content"]}, or the
 * convenience instance methods L{RosetteParameters.load_document_file}
 * and L{RosetteParameters.load_document_string}. The unit size and
 * data format are defaulted to L{InputUnit.DOC} and L{DataFormat.SIMPLE}.
 *
 * Using subscripts instead of instance variables facilitates diagnosis.
 *
 * If the field C{contentUri} is set to the URL of a web page (only
 * protocols C{http, https, ftp, ftps} are accepted), the server will
 * fetch the content from that web page.  In this case, neither C{content}
 * nor C{contentType} may be set.
 */
class RosetteParameters extends RosetteParamsSetBase
{
    /**
     * @throws RosetteException
     */
    public function __construct()
    {
        parent::__construct(["content", "contentUri", "contentType", "unit"]);
        $this->Set("unit", RosetteConstants::$InputUnit['DOC']);
    }

    /**
     * Internal to API - do not use
     */
    public function Serializable()
    {
        if ($this->Get("content") == null) {
            if ($this->Get("contentUri") == null) {
                throw new RosetteException("Must supply one of Content or ContentUri", RosetteException::$BAD_ARGUMENT);
            }
        }
        else {
            if ($this->Get("contentUri") != null) {
                throw new RosetteException("Cannot supply both Content and ContentUri", RosetteException::$BAD_ARGUMENT);
            }
        }
        $serialized = $this->ForSerialize();
        if (empty($this->Get("contentType")) && empty($this->Get("contentUri"))) {
            $serialized['contentType'] = RosetteConstants::$DataFormat['SIMPLE'];
        }
        elseif (in_array($this->Get('contentType'), [RosetteConstants::$DataFormat['HTML'], RosetteConstants::$DataFormat['XHTML'], RosetteConstants::$DataFormat['UNSPECIFIED']])) {
            $content = $serialized['content'];
            $encoded = base64_encode($content);
            $serialized['content'] = $encoded;
        }
        return $serialized;
    }

    /**
     * Loads a file into the object.
     *
     * The file will be read as bytes; the appropriate conversion will be determined by the server.  The document unit size remains
     * by default L{InputUnit.DOC}.
     *
     * @param $path : Pathname of a file acceptable to the C{open}
     * function.
     * @param null $dataType
     * @throws RosetteException
     */
    public function LoadDocumentFile($path, $dataType = null)
    {
        if (!$dataType) {
            $dataType = RosetteConstants::$DataFormat['UNSPECIFIED'];
        }
        if (!in_array($dataType, [RosetteConstants::$DataFormat['HTML'], RosetteConstants::$DataFormat['XHTML'], RosetteConstants::$DataFormat['UNSPECIFIED']])) {
            throw new RosetteException(sprintf("Must supply one of HTML, XHTML, or UNSPECIFIED: %s", $dataType), RosetteException::$BAD_ARGUMENT);
        }
        $this->LoadDocumentString(file_get_contents($path), $dataType);
    }

    /**
     * Loads a string into the object.
     *
     * The string will be taken as bytes or as Unicode dependent upon its native type and the data type asked for; if the
     * type is HTML or XHTML, bytes are expected, the encoding to be determined by the server.
     * The document unit size remains (by default) L{InputUnit.DOC}.
     *
     * @param $stringData
     * @param $dataType
     * @throws RosetteException
     * @internal param $stringData : A string, possibly a unicode-string, to be loaded for subsequent analysis, as per the C{data_type}..
     * @internal param $dataType : The data type of the string, as per L{DataFormat}..
     *
     */
    public function LoadDocumentString($stringData, $dataType)
    {
        $this->Set("content", $stringData);
        $this->Set('contentType', $dataType);
        $this->Set('unit', RosetteConstants::$InputUnit['DOC']);
    }

}
