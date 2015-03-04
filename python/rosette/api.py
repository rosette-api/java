"""
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2015 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
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
    SIMPLE = "text/plain"
    JSON = "application/json"
    HTML = "text/html"
    XHTML = "application/xhtml+xml"
    BINARY = "application/octet-stream"

class InputUnit(Enum):
    DOC = "doc"
    SENTENCE= "sentence"

class MorphologyOutput(Enum):
    LEMMAS = "lemmas"
    PARTS_OF_SPEECH = "parts-of-speech"
    COMPOUND_COMPONENTS = "compound-components"
    HAN_READINGS = "han-readings"
    COMPLETE = "complete"
    
class RosetteParamSetBase:
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

    def forSerialize(self):
        v = {}
        for (key,val) in self.__params.items():
            if val is None:
                pass
            elif isinstance(val, Enum):
                v[key] = val.value;
            else:
                v[key] = val
        return v

class RosetteParameters(RosetteParamSetBase):
    def __init__(self):
        RosetteParamSetBase.__init__(self, ("content", "contentUri", "contentType", "unit"))

    def _validateUri(self, uri):
        parsed = urlparse(uri)
        if parsed.scheme not in VALID_SCHEMES:
            raise RosetteException ("bad URI", "URI scheme not one of " + repr(VALID_SCHEMES), uri)
        if '.' not in parsed.netloc:
            raise RosetteException ("bad URI", "URI net location has no dot.", uri)

    def serializable(self):
        if self["contentUri"] is not None:
            self._validateUri(self["contentUri"])

        if self["content"] is None:
            if self["contentUri"] is None:
                raise RosetteException("bad argument", "Must supply one of Content or ContentUri", "bad arguments")
        else:  #self["content"] not None
            if self["contentUri"] is not None:
                raise RosetteException("bad argument", "Cannot supply both Content and ContentUri", "bad arguments")
            if not isinstance(self["contentType"], DataFormat):
                raise RosetteException("bad argument", "Parameter 'contentType' not of DataFormat Enum", repr(self["contentType"]))
        if not isinstance(self["unit"], InputUnit):
             raise RosetteException("bad argument", "Parameter 'unit' not of InputUnit Enum", repr(self["unit"]))
        slz = self.forSerialize()
        if self["contentType"] in (DataFormat.HTML, DataFormat.XHTML, DataFormat.BINARY):
            slz["content"] = base64.b64encode(slz["content"])
        return slz

    def LoadDocumentFile(self, path, dtype):
        if not dtype in (DataFormat.HTML, DataFormat.XHTML, DataFormat.BINARY):\
            raise RosetteException(dtype, "Must supply one of HTML, XHTML, or BINARY", "bad arguments")
        self.LoadDocumentString(open(path).read(), dtype)

    def LoadDocumentString(self, s, dtype):
        if not isinstance(dtype, DataFormat):
            raise RosetteException(dtype, "Must supply DataFormat object.", "bad arguments")
        self["content"] = s
        self["contentType"] = dtype
        self["unit"] = InputUnit.DOC
        

class RntParameters(RosetteParamSetBase):
    def __init__(self):
        RosetteParamSetBase.__init__(self, ("name", "targetLanguageCode", "entityType", "sourceLanguageOfOriginCode", "sourceLanguageOfUseCode", "sourceScriptCode", "targetLanguageCode", "targetScriptCode", "targetSchemeCode"))

    def serializable(self):
        for n in ("name", "targetLanguageCode"):  #required
            if self[n] is None:
                raise RosetteException("missing parameter", "Required RNT parameter not supplied", repr(n))
        return self.forSerialize()

class Operator:

    def __init__(self, api, suburl):
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
        url = self.service_url + '/' + self.suburl + "/info"
        self.logger.info('info: ' + url)
        headers = {'Accept':'application/json'}
        if self.user_key is not None:
            headers["user_key"] = self.user_key
        r = requests.get(url, headers=headers)
        return self.__finish_result(r, "info")

    def ping(self):
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
        params_to_serialize = parameters.serializable()
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
    Rosette Python Client Binding API.
    This binding uses 'requests' (http://docs.python-requests.org/).
    """
    def __init__(self, user_key = None, service_url='http://api.rosette.com/rest/v1'):
        """ Create an API object."""
        self.user_key = user_key
        self.service_url = service_url
        self.logger = logging.getLogger('rosette.api')
        self.logger.info('Initialized on ' + self.service_url)
        self.debug = False
        self.useMultipart = False

    def _setUseMultipart(self, value):
        self.useMultipart = value

    def ping(self):
        return Operator(self, None)

    def language(self):
        return Operator(self, "language")

    def sentences(self):
        return Operator(self, "sentences")

    def tokens(self):
        return Operator(self, "tokens")

    def morphology(self, subsub):
        if not isinstance(subsub, MorphologyOutput):
            raise RosetteException("bad argument", "Argument not a MorphologyOutput enum object", repr(subsub))
        return Operator(self, "morphology/" + subsub.value)

    def entities(self, linked):
        if  linked:
            return Operator(self, "entities/linked")
        else:
            return Operator(self, "entities")

    def categories(self):
        return Operator(self, "categories")

    def sentiment(self):
        return Operator(self, "sentiment")

    def translated_name(self):
        return Operator(self, "translated-name")
