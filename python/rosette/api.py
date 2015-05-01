#!/usr/bin/env python

"""
Code and constant classes for operating Rosette Web Service from python.

This data and information is proprietary to, and a valuable trade secret
of, Basis Technology Corp.  It is given in confidence by Basis Technology
and may only be used as permitted under the license agreement under which
it has been distributed, and in no other way.

Copyright (c) 2015 Basis Technology Corporation All rights reserved.

The technical data and information provided herein are provided with
`limited rights', and the computer software provided herein is provided
with `restricted rights' as those terms are defined in DAR and ASPR
7-104.9(a).
"""
_ACCEPTABLE_SERVER_VERSION = "0.5"
_GZIP_BYTEARRAY = bytearray([0x1F, 0x8b, 0x08])
N_RETRIES = 3


import sys
_IsPy3 = sys.version_info[0] == 3

import logging
import json
import base64
import gzip
from io import BytesIO

try:
    from urlparse import urlparse
except ImportError:
    from urllib.parse import urlparse
try:
    import httplib
except ImportError:
    import http.client as httplib

if _IsPy3:
    _GZIP_SIGNATURE = _GZIP_BYTEARRAY
else:
    _GZIP_SIGNATURE = str(_GZIP_BYTEARRAY)


class _ReturnObject:
    def __init__(self, js, code):
        self._json = js
        self.status_code = code

    def json(self):
        return self._json


def _my_loads(obj):
    if _IsPy3:
        return json.loads(obj.decode("utf-8"))  # if py3, need chars.
    else:
        return json.loads(obj)

def _retrying_request(op, url, data, headers):
    parsed = urlparse(url)
    loc = parsed.netloc
    if (parsed.scheme == "https"):
        conn = httplib.HTTPSConnection(loc)
    else:
        conn = httplib.HTTPConnection(loc)
    rdata = None
    for i in range(N_RETRIES):
        conn.request(op, url, data, headers)
        response = conn.getresponse()
        status = response.status
        rdata = response.read()
        if status < 500:
            conn.close()
            return rdata, status
        conn.close()
        # Do not wait to retry -- the model is that a bunch of dynamically-routed
        # resources has failed -- Retry means some other set of servelets and their
        # underlings will be called up, and maybe they'll do better.
        # This will not help with a persistent or impassible delay situation,
        # but the former case is thought to be more likely.
    message = None
    code = "unknownError"
    if rdata is not None:
        try:
            the_json = _my_loads(rdata)
            if "message" in the_json:
                message = the_json["message"]
            if "code" in the_json:
                code = the_json["code"]
        except:
            pass

    if message is None:
        message = "A retryable network operation has not succeeded after " + str(N_RETRIES) + " attempts"

    raise RosetteException(code, message, url)

def _get_http(url, headers):
    (rdata, status) = _retrying_request("GET", url, None, headers)
    return _ReturnObject(_my_loads(rdata), status)

def _post_http(url, data, headers):
    if data is None:
        json_data = ""
    else:
        json_data = json.dumps(data)

    (rdata,status) = _retrying_request("POST", url, json_data, headers)

    if len(rdata) > 3 and rdata[0:3] == _GZIP_SIGNATURE:
        buf = BytesIO(rdata)
        rdata = gzip.GzipFile(fileobj=buf).read()

    return _ReturnObject(_my_loads(rdata), status)


class RosetteException(Exception):
    """Exception thrown by all Rosette API operations for errors local and remote.

    TBD. Right now, the only valid operation is conversion to __str__.
    """

    def __init__(self, status, message, response_message):
        self.status = status
        self.message = message
        self.response_message = response_message

    def __str__(self):
        sst = self.status
        if not (isinstance(sst, str)):
            sst = repr(sst)
        return sst + ": " + self.message + ":\n  " + self.response_message


class _PseudoEnum:
    def __init__(self):
        pass

    @classmethod
    def validate(cls, value, name):
        values = []
        for (k, v) in vars(cls).items():
            if not k.startswith("__"):
                values += [v]

        # this is still needed to make sure that the parameter NAMES are known.
        # If python didn't allow setting unknown values, this would be a language error.
        if value not in values:
            raise RosetteException("unknownVariable", "The value supplied for " + name +
                                   " is not one of " + ", ".join(values) + ".", repr(value))


class DataFormat(_PseudoEnum):
    """Data Format, as much as it is known."""
    SIMPLE = "text/plain"
    """The data is unstructured text, supplied as a possibly-unicode string."""
    JSON = "application/json"
    """To be supplied.  The API uses JSON internally, but that is not what this refers to."""
    HTML = "text/html"
    """The data is a 'loose' HTML page; that is, it may not be HTML-compliant, or may even not
    really be HTML. The data must be a narrow (single-byte) string, not a python Unicode string,
    perhaps read from a file. (Of course, it can be UTF-8 encoded)."""
    XHTML = "application/xhtml+xml"
    """The data is a compliant XHTML page. The data must be a narrow (single-byte) string, not a
    python Unicode string, perhaps read from a file. (Of course, it can be UTF-8 encoded)."""
    UNSPECIFIED = "application/octet-stream"
    """The data is of unknown format, it may be a binary data type (the contents of a binary file),
    or may not.  It will be sent as is and identified and analyzed by the server."""

class InputUnit(_PseudoEnum):
    """Elements are used in the L{RosetteParameters} class to specify whether textual data
    is to be treated as one sentence or possibly many."""
    DOC = "doc"
    """The data is a whole document; it may or may not contain multiple sentences."""
    SENTENCE = "sentence"
    """The data is a single sentence."""

class MorphologyOutput(_PseudoEnum):
    LEMMAS = "lemmas"
    PARTS_OF_SPEECH = "parts-of-speech"
    COMPOUND_COMPONENTS = "compound-components"
    HAN_READINGS = "han-readings"
    COMPLETE = "complete"


class _RosetteParamSetBase:
    def __init__(self, repertoire):
        self.__params = {}
        for k in repertoire:
            self.__params[k] = None

    def __setitem__(self, key, val):
        if key not in self.__params:
            raise RosetteException("badKey", "Unknown Rosette parameter key", repr(key))
        self.__params[key] = val

    def __getitem__(self, key):
        if key not in self.__params:
            raise RosetteException("badKey", "Unknown Rosette parameter key", repr(key))
        return self.__params[key]

    def _for_serialize(self):
        v = {}
        for (key, val) in self.__params.items():
            if val is None:
                pass
            else:
                v[key] = val
        return v


def _byteify(s):  # py 3 only
    l = len(s)
    b = bytearray(l)
    for ix in range(l):
        oc = ord(s[ix])
        assert (oc < 256)
        b[ix] = oc
    return b


class RosetteParameters(_RosetteParamSetBase):
    """Parameter object for all operations requiring input other than
    translated_name.
    Four fields, C{content}, C{contentType}, C{unit}, and C{inputUri}, are set via
    the subscript operator, e.g., C{params["content"]}, or the
    convenience instance methods L{RosetteParameters.load_document_file}
    and L{RosetteParameters.load_document_string}. The unit size and
    data format are defaulted to L{InputUnit.DOC} and L{DataFormat.SIMPLE}.

    Using subscripts instead of instance variables facilitates diagnosis.

    If the field C{contentUri} is set to the URL of a web page (only
    protocols C{http, https, ftp, ftps} are accepted), the server will
    fetch the content from that web page.  In this case, neither C{content}
    nor C{contentType} may be set.
    """
    
    def __init__(self):
        """Create a L{RosetteParameters} object.  Default data format
    is L{DataFormat.SIMPLE}, unit is L{InputUnit.DOC}."""
        _RosetteParamSetBase.__init__(self, ("content", "contentUri", "contentType", "unit"))
        self["unit"] = InputUnit.DOC  # default

    def serializable(self):
        """Internal. Do not use."""

        if self["content"] is None:
            if self["contentUri"] is None:
                raise RosetteException("badArgument", "Must supply one of Content or ContentUri", "bad arguments")
        else:  # self["content"] not None
            if self["contentUri"] is not None:
                raise RosetteException("badArgument", "Cannot supply both Content and ContentUri", "bad arguments")


        slz = self._for_serialize()
        if self["contentType"] is None and self["contentUri"] is None:
            slz["contentType"] = DataFormat.SIMPLE
        elif self["contentType"] in (DataFormat.HTML, DataFormat.XHTML, DataFormat.UNSPECIFIED):
            content = slz["content"]
            if _IsPy3 and isinstance(content, str):
                content = _byteify(content)

            encoded = base64.b64encode(content)
            if _IsPy3:
                encoded = encoded.decode("utf-8")  # if py3, need chars.
            slz["content"] = encoded
        return slz

    def load_document_file(self, path, data_type=DataFormat.UNSPECIFIED):
        """Loads a file into the object.
        The file will be read as bytes; the appropriate conversion will
        be determined by the server.  The document unit size remains
        by default L{InputUnit.DOC}.
        @parameter path: Pathname of a file acceptable to the C{open} function.
        @parameter data_type: One of L{DataFormat.HTML}, L{DataFormat.XHTML}, or L{DataFormat.UNSPECIFIED}.
        No other types are acceptable at this time, although HTML is broad enough to include text strings
        without markup.
        If the data type is unknown, or describes a binary file, use the default (L{DataFormat.UNSPECIFIED}).
        @type data_type: L{DataFormat}
        """
        if data_type not in (DataFormat.HTML, DataFormat.XHTML, DataFormat.UNSPECIFIED):
            raise RosetteException("badArgument", "Must supply one of HTML, XHTML, or UNSPECIFIED", data_type)
        self.load_document_string(open(path, "rb").read(), data_type)

    def load_document_string(self, s, data_type):
        """Loads a string into the object.
        The string will be taken as bytes or as Unicode dependent upon
        its native python type and the data type asked for; if the
        type is HTML or XHTML, bytes, not python Unicode, are expected,
        the encoding to be determined by the server.
        The document unit size remains (by default) L{InputUnit.DOC}.
        @parameter s: A string, possibly a unicode-string, to be loaded
        for subsequent analysis, as per the C{data_type}.
        @parameter data_type: The data type of the string, as per L{DataFormat}.
        @type data_type: L{DataFormat}
        """
        self["content"] = s
        self["contentType"] = data_type
        self["unit"] = InputUnit.DOC
        

class RntParameters(_RosetteParamSetBase):
    """Parameter object for C{translated_name} endpoint.
    The following values may be set by the indexing (i.e.,C{ parms["name"]}) operator.  The values are all
    strings (when not C{None}).
    All are optional except C{name} and C{targetLanguage}.  Scripts are in
    ISO15924 codes, and languages in ISO639 (two- or three-letter) codes.  See the RNT documentation for more
    description of these terms, as well as the content of the return result.

    C{name} The name to be translated.

    C{targetLangauge} The language into which the name is to be translated.

    C{entityType} The entity type (TBD) of the name.

    C{sourceLanguageOfOrigin} The language of origin of the name.

    C{sourceLanguageOfUse} The language of use of the name.

    C{sourceScript} The script in which the name is supplied.

    C{targetScript} The script into which the name should be translated.

    C{targetScheme} The transliteration scheme by which the translated name should be rendered.
    """

    def __init__(self):
        _RosetteParamSetBase.__init__(self, ("name", "targetLanguage", "entityType", "sourceLanguageOfOrigin",
                                             "sourceLanguageOfUse", "sourceScript", "targetScript", "targetScheme"))

    def serializable(self):

        """Internal. Do not use."""
        for n in ("name", "targetLanguage"):  # required
            if self[n] is None:
                raise RosetteException("missingParameter", "Required RNT parameter not supplied", repr(n))
        return self._for_serialize()


class RniParameters(_RosetteParamSetBase):
    """Parameter object for C{matched_name} endpoint.
    All are required.

    C{name1} The name to be matched, a C{name} object.

    C{name2} The name to be matched, a C{name} object.

    The C{name} object contains these fields:

    C{text} Text of the name, required.

    C{language} Language of the name in ISO639 three-letter code, optional.

    C{script} The ISO15924 code of the name, optional.

    C{entityType} The entity type, can be "PERSON", "LOCATION" or "ORGANIZATION", optional.
    """

    def __init__(self):
        _RosetteParamSetBase.__init__(self, ("name1", "name2"))

    def serializable(self):

        """Internal. Do not use."""
        for n in ("name1", "name2"):  # required
            if self[n] is None:
                raise RosetteException("missingParameter", "Required RNI parameter not supplied", repr(n))
        return self._for_serialize()


class Operator:
    """L{Operator} objects are invoked via their instance methods to obtain results
    from the Rosette server described by the L{API} object from which they
    are created.  Each L{Operator} object communicates with a specific endpoint
    of the Rosette server, specified at its creation.  Use the specific
    instance methods of the L{API} object to create L{Operator} objects bound to
    corresponding endpoints.

    Use L{Operator.ping} to ping, and L{Operator.info} to retrieve server info.
    For all other types of requests, use L{Operator.operate}, which accepts
    an argument specifying the data to be processed and certain metadata.

    The results of all operations are returned as python dictionaries, whose
    keys and values correspond exactly to those of the corresponding
    JSON return value described in the Rosette web service documentation.
    """

    def __init__(self, api, suburl):
        """This method should not be invoked by the user.  Creation is reserved
        for internal use by API objects."""

        self.service_url = api.service_url
        self.user_key = api.user_key
        self.logger = api.logger
        self.useMultipart = api.useMultipart
        self.checker = lambda: api.check_version()
        self.suburl = suburl

    def __finish_result(self, r, ename):
        code = r.status_code
        the_json = r.json()
        if code == 200:
            return the_json
        else:
            if 'message' in the_json:
                msg = the_json['message']
            else:
                msg = the_json['code']  # punt if can't get real message
            if self.suburl is None:
                complaint_url = "Top level info"
            else:
                complaint_url = ename + " " + self.suburl

            if "code" in the_json:
                serverCode = the_json["code"]
            else:
                serverCode = "unknownError"

            raise RosetteException(serverCode,
                                   complaint_url + " : failed to communicate with Rosette",
                                   msg)

    def _set_use_multipart(self, value):
        self.useMultipart = value

    def info(self):
        """Issues an "info" request to the L{Operator}'s specific endpoint.
        @return: A dictionary telling server version and other
        identifying data."""
        if self.suburl is not None:
            self.checker()
            url = self.service_url + '/' + self.suburl + "/info"
        else:
            url = self.service_url + "/info"
        self.logger.info('info: ' + url)
        headers = {'Accept': 'application/json'}
        if self.user_key is not None:
            headers["user_key"] = self.user_key
        r = _get_http(url, headers=headers)
        return self.__finish_result(r, "info")

    def ping(self):
        """Issues a "ping" request to the L{Operator}'s (server-wide) endpoint.
        @return: A dictionary if OK.  If the server cannot be reached,
        or is not the right server or some other error occurs, it will be
        signalled."""

        url = self.service_url + '/ping'
        self.logger.info('Ping: ' + url)
        headers = {'Accept': 'application/json'}
        if self.user_key is not None:
            headers["user_key"] = self.user_key
        r = _get_http(url, headers=headers)
        return self.__finish_result(r, "ping")

    def operate(self, parameters):
        """Invokes the endpoint to which this L{Operator} is bound.
        Passes data and metadata specified by C{parameters} to the server
        endpoint to which this L{Operator} object is bound.  For all
        endpoints except C{translated_name} and C{matched_name}, it must be a L{RosetteParameters}
        object; for C{translated_name}, it must be an L{RntParameters} object;
        for C{matched_name}, it must be an L{RniParameters} object.

        In all cases, the result is returned as a python dictionary
        conforming to the JSON object described in the endpoint's entry
        in the Rosette web service documentation.

        @param parameters: An object specifying the data,
        and possible metadata, to be processed by the endpoint.  See the
        details for those object types.
        @type parameters: For C{translated_name}, L{RntParameters}, otherwise L{RosetteParameters}
        @return: A python dictionary expressing the result of the invocation.
        """

        self.checker()

        if self.useMultipart and (parameters['contentType'] != DataFormat.SIMPLE):
            raise RosetteException("incompatible", "Multipart requires contentType SIMPLE",
                                   repr(parameters['contentType']))
        url = self.service_url + '/' + self.suburl
        self.logger.info('operate: ' + url)
        params_to_serialize = parameters.serializable()
        headers = {'Accept': "application/json", 'Accept-Encoding': "gzip"}
        if self.user_key is not None:
            headers["user_key"] = self.user_key
        headers['Content-Type'] = "application/json"
        r = _post_http(url, params_to_serialize, headers)
        return self.__finish_result(r, "operate")


class API:
    """
    Rosette Python Client Binding API; representation of a Rosette server.
    Call instance methods upon this object to obtain L{Operator} objects
    which can communicate with particular Rosette server endpoints.
    """
    def __init__(self, user_key=None, service_url='https://api.rosette.com/rest/v1'):
        """ Create an L{API} object.
        @param user_key: (Optional; required for servers requiring authentication.) An authentication string to be sent
         as user_key with all requests.  The default Rosette server requires authentication.
         to the server.
        @param service_url: (Optional) The root URL (string) of the Rosette service to which this L{API} object will be
         bound.  The default is that of Basis Technology's public Rosette server.
        """
        self.user_key = user_key
        self.service_url = service_url
        self.logger = logging.getLogger('rosette.api')
        self.logger.info('Initialized on ' + self.service_url)
        self.debug = False
        self.useMultipart = False
        self.version_checked = False
        
    def check_version(self):
        if self.version_checked:
            return True
        op = Operator(self, None)
        result = op.info()
        version = ".".join(result["version"].split(".")[0:2])
        if version != _ACCEPTABLE_SERVER_VERSION:
            raise RosetteException("badServerVersion", "The server version is not " + _ACCEPTABLE_SERVER_VERSION,
                                   version)
        self.version_checked = True
        return True

    def _set_use_multipart(self, value):
        self.useMultipart = value

    def ping(self):
        """
        Create a ping L{Operator} for the server.
        @return: An L{Operator} object which can ping the server to which this L{API} object is bound.
        """
        return Operator(self, None)

    def language(self):
        """
        Create an L{Operator} for language identification.
         @return: An L{Operator} object which can perform language identification upon texts to which it is applied."""
        return Operator(self, "language")

    def sentences(self):
        """Create an L{Operator} to break a text into sentences.
         @return: An L{Operator} object which will break into sentences the
         texts to which it is applied."""
        return Operator(self, "sentences")

    def tokens(self):
        """Create an L{Operator} to break a text into tokens.
         @return: An L{Operator} object which will tokenize the
         texts to which it is applied."""
        return Operator(self, "tokens")

    def morphology(self, facet=MorphologyOutput.COMPLETE):
        """Create an L{Operator} to morphologically analyze a text.
        Produce an operator which returns a specific facet
        of the morphological analyses of texts to which it is applied.
        L{MorphologyOutput.COMPLETE} (the default) requests all available facets.
        @param facet: The facet desired, to be returned by the created L{Operator}.
        @type facet: An element of L{MorphologyOutput}.
        """
        return Operator(self, "morphology/" + facet)

    def entities(self, linked):
        """Create an L{Operator} to identify named entities found in the texts
        to which it is applied.  Linked entity information is optional, and
        its need must be specified at the time the operator is created.
        @param linked: Specifies whether or not linked entity information will
        be wanted.
        @type linked: Boolean
        """
        if linked:
            return Operator(self, "entities/linked")
        else:
            return Operator(self, "entities")

    def categories(self):
        """Create an L{Operator} to identify categories of the texts
        to which is applied.
        @return: An L{Operator} object which can return category tags
        of texts to which it is applied."""
        return Operator(self, "categories")

    def sentiment(self):
        """Create an L{Operator} to identify sentiments of the texts
        to which is applied.
        @return: An L{Operator} object which can return sentiments
        of texts to which it is applied."""
        return Operator(self, "sentiment")

    def translated_name(self):
        """Create an L{Operator} to perform name analysis and translation
        upon the names to which it is applied.
        Note that that L{Operator}'s L{Operator.operate} method requires an L{RntParameters} argument,
        not the L{RosetteParameters} required by L{Operator}s created by
        other instance methods.
        @return: An L{Operator} which can perform name analysis and translation.
        """
        return Operator(self, "translated-name")

    def matched_name(self):
        """Create an L{Operator} to perform name matching.
        Note that that L{Operator}'s L{Operator.operate} method requires an L{RniParameters} argument,
        not the L{RosetteParameters} required by L{Operator}s created by
        other instance methods.
        @return: An L{Operator} which can perform name matching.
        """
        return Operator(self, "matched-name")
