"""
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

import requests
import logging
import json
import base64
from enum import Enum
from urlparse import urlparse
import sys



VALID_SCHEMES = ('http', 'https', 'ftp', 'ftps')

# this will get more complex in a hurry.
class RosetteException(Exception):
    def __init__(self, status, message, response_message):
        self.status = status
        self.message = message
        self.response_message = response_message
    def __str__(self):
        return self.message + ":\n " + self.response_message

# TODO: Set up OAuth2 session and use it with requests.
# We'll need something to talk to for that, and we won't it for integration tests.

class DataFormat(Enum):
    """Data Format, as much as it is known.  Semantics are subtle, please read.
    """
    SIMPLE = "text/plain"
    """The data is unstructured text, supplied as a possibly-unicode string."""
    JSON = "application/json"
    """To be supplied.  The API uses JSON internally, but that is not what this refers to."""
    HTML = "text/html"
    """The data is a 'loose' HTML page; that is, it may not be HTML-compliant, or may even not really be HTML. The data must be a narrow (single-byte) string, not a python Unicode string, perhaps read from a file. (Of course, it can be UTF-8 encoded)."""
    XHTML = "application/xhtml+xml"
    """The data is a compliant XHTML page. The data must be a narrow (single-byte) string, not a python Unicode string, perhaps read from a file. (Of course, it can be UTF-8 encoded."""

class InputUnit(Enum):
    DOC = "doc"
    SENTENCE= "sentence"

class MorphologyOutput(Enum):
    LEMMAS = "lemmas"
    PARTS_OF_SPEECH = "parts-of-speech"
    COMPOUND_COMPONENTS = "compound-components"
    HAN_READINGS = "han-readings"
    COMPLETE = "complete"
    
class _RosetteParamSetBase:
    def __init__(self,repertoire):
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

    def _forSerialize(self):
        v = {}
        for (key,val) in self.__params.items():
            if val is None:
                pass
            elif isinstance(val, Enum):
                v[key] = val.value;
            else:
                v[key] = val
        return v

class RosetteParameters(_RosetteParamSetBase):
    def __init__(self):
        _RosetteParamSetBase.__init__(self, ("content", "contentUri", "contentType", "unit"))
        self["unit"] = InputUnit.DOC  #defaults

    def _validateUri(self, uri):
        parsed = urlparse(uri)
        if parsed.scheme not in VALID_SCHEMES:
            raise RosetteException ("bad URI", "URI scheme not one of " + repr(VALID_SCHEMES), uri)
        if '.' not in parsed.netloc:
            raise RosetteException ("bad URI", "URI net location has no dot.", uri)

    def _serializable(self):
        if self["contentUri"] is not None:
            self._validateUri(self["contentUri"])

        if self["content"] is None:
            if self["contentUri"] is None:
                raise RosetteException("bad argument", "Must supply one of Content or ContentUri", "bad arguments")
        else:  #self["content"] not None
            if self["contentUri"] is not None:
                raise RosetteException("bad argument", "Cannot supply both Content and ContentUri", "bad arguments")
        if self["contentType"] is None:
            pass
        elif not isinstance(self["contentType"], DataFormat):
                raise RosetteException("bad argument", "Parameter 'contentType' not of DataFormat Enum", repr(self["contentType"]))
        if not isinstance(self["unit"], InputUnit):
             raise RosetteException("bad argument", "Parameter 'unit' not of InputUnit Enum", repr(self["unit"]))
        slz = self._forSerialize()
        if self["contentType"] is None and self["contentUri"] is None:
            slz["contentType"] = DataFormat.SIMPLE.value
        elif self["contentType"] in (DataFormat.HTML, DataFormat.XHTML):
            slz["content"] = base64.b64encode(slz["content"])
        return slz

    def LoadDocumentFile(self, path, dtype):
        if not dtype in (DataFormat.HTML, DataFormat.XHTML):\
            raise RosetteException(dtype, "Must supply one of HTML or XHTML", "bad arguments")
        self.LoadDocumentString(open(path).read(), dtype)

    def LoadDocumentString(self, s, dtype):
        if not isinstance(dtype, DataFormat):
            raise RosetteException(dtype, "Must supply DataFormat object.", "bad arguments")
        self["content"] = s
        self["contentType"] = dtype
        self["unit"] = InputUnit.DOC
        

class RntParameters(_RosetteParamSetBase):
    def __init__(self):
        _RosetteParamSetBase.__init__(self, ("name", "targetLanguageCode", "entityType", "sourceLanguageOfOriginCode", "sourceLanguageOfUseCode", "sourceScriptCode", "targetLanguageCode", "targetScriptCode", "targetSchemeCode"))

    def _serializable(self):
        for n in ("name", "targetLanguageCode"):  #required
            if self[n] is None:
                raise RosetteException("missing parameter", "Required RNT parameter not supplied", repr(n))
        return self._forSerialize()

class Operator:
    """L{Operator} objects are invoked via their instance methods to obtain results
    from the Rosette server described by the L{API} object from which they
    are created.  Each L{Operator} object communicates with a specific endpoint
    of the Rosette server, specified at its creation.  Use the specific
    instance methods of the L{API} object to create L{Operator} objects bound to
    corresponding endpoints."""

    def __init__(self, api, suburl):
        """This method should not be invoked by the user.  Creation is reserved
        for internal use by API objects."""

        self.service_url = api.service_url
        self.user_key = api.user_key
        self.logger = api.logger
        self.useMultipart = api.useMultipart
        self.suburl = suburl

    def __finish_result(self, r, ename):
        code = r.status_code
        theJSON = r.json()
        if code == 200:
            return theJSON
        else:
            if 'message' in theJSON:
                msg = theJSON['message']
            else:
                msg = theJSON['code'] #yuck*1.5
            raise RosetteException(code,
                                   '"' + ename + '" "' + self.suburl + "\" failed to communicate with Rosette",
                                   msg)


    def _setUseMultipart(self, value):
        self.useMultipart = value

    def info(self):
        """Issues an "info" request to the L{Operator}'s specific endpoint.
        @return: A dictionary telling server version and other
        identifying data."""
        url = self.service_url + '/' + self.suburl + "/info"
        self.logger.info('info: ' + url)
        headers = {'Accept':'application/json'}
        if self.user_key is not None:
            headers["user_key"] = self.user_key
        r = requests.get(url, headers=headers)
        return self.__finish_result(r, "info")

    def ping(self):
        """Issues a "ping" request to the L{Operator}'s (server-wide) endpoint.
        @return: A dictionary if OK.  If the server cannot be reached,
        or is not the right server or some other error occurs, it will be
        signalled."""

        
        url = self.service_url + '/ping'
        self.logger.info('Ping: ' + url)
        headers = {'Accept':'application/json'}
        if self.user_key is not None:
            headers["user_key"] = self.user_key
        r = requests.get(url, headers=headers)
        return self.__finish_result(r, "ping")

    def operate(self, parameters):
        if self.useMultipart and (parameters['contentType'] != DataFormat.SIMPLE):
            raise RosetteException("incompatible", "Multipart requires contentType SIMPLE", repr(parameters['contentType']))
        url = self.service_url + '/' + self.suburl
        self.logger.info('operate: ' + url)
        params_to_serialize = parameters._serializable()
        headers = {'Accept':"application/json", 'Accept-Encoding':"gzip"}
        if self.user_key is not None:
            headers["user_key"] = self.user_key
        if self.useMultipart and 'content' in params_to_serialize:
            files = {'content':('content', params_to_serialize['content'], "application/octet-stream"),
                     'options':('options', json.dumps({"unit":params_to_serialize["unit"]}), "application/json")}
            r = requests.post(url, headers=headers, files=files)
        else:
            headers['Content-Type'] = "application/json"
            r = requests.post(url, headers=headers, json=params_to_serialize)

        return self.__finish_result(r, "operate")

class API:
    """
    Rosette Python Client Binding API; representation of a Rosette server.
    Call instance methods upon this object to obtain L{Operator} objects
    which can communicate with particular Rosette server endpoints.

    This binding requires the 'requests' package (U{http://docs.python-requests.org/}).
    """
    def __init__(self, user_key = None, service_url='http://api.rosette.com/rest/v1'):
        """ Create an L{API} object.
        @param user_key: (Optional; required for servers requiring authentication.) An authentication string to be sent as user_key with all requests.  The default Rosette server requires authentication.
         to the server.
        @param service_url: (Optional) The root URL (string) of the Rosette service to which this L{API} object will be bound.  The default is that of Basis Technology's public Rosette server.
        """
        self.user_key = user_key
        self.service_url = service_url
        self.logger = logging.getLogger('rosette.api')
        self.logger.info('Initialized on ' + self.service_url)
        self.debug = False
        self.useMultipart = False

    def _setUseMultipart(self, value):
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

    def morphology(self, facet):
        """Create an L{Operator} to morphologically analyze a text.
        Produce an operator which returns a specific facet
        of the morphological analyses of texts to which it is applied.
        L{MorphologyOutput.COMPLETE} requests all available facets.
        @param facet: The facet desired, to be returned by the created L{Operator}.
        @type facet: An element of the C{enum} L{MorphologyOutput}.
        """
        if not isinstance(facet, MorphologyOutput):
            raise RosetteException("bad argument", "Argument not a MorphologyOutput enum object", repr(facet))
        return Operator(self, "morphology/" + facet.value)

    def entities(self, linked):
        """Create an L{Operator} to identify named entities found in the texts
        to which it is applied.  Linked entity information is optional, and
        its need must be specified at the time the operator is created.
        @param linked: Specifies whether or not linked entity information will
        be wanted.
        @type linked: Boolean
        """
        if  linked:
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
        Note that that L{Operator} requires an L{RntParameters} argument,
        not the L{RosetteParameters} required by L{Operator}s created by
        other instance methods.
        @return: An L{Operator} which can perform name analysis and translation.
        """
        return Operator(self, "translated-name")
