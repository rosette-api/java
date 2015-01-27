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
from enum import Enum
import sys
import pprint

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
# TODO: when do we turn on compression? Always?

class ResultFormat(Enum):
    SIMPLE = ""
    ROSETTE = "rosette"

class DataFormat(Enum):
    SIMPLE = "text/plain"
    JSON = "application/json"

class InputUnit(Enum):
    DOC = "doc"
    SENTENCE= "sentence"

class MorphologyOutput(Enum):
    LEMMAS = "lemmas"
    PARTS_OF_SPEECH = "parts-of-speech"
    COMPOUND_COMPONENTS = "compound-components"
    HAN_READINGS = "han-readings"
    COMPLETE = "complete"
    
# TODO: set up as a fixed collection of properties.

class RaasParameters:
    def __init__(self):
        self.content = None
        self.contentUri = None
        self.contentType = None
        self.unit = None

    def serializable(self):
        v = {}
        v['content'] = self.content
        v['contentUri'] = self.contentUri
        if isinstance(self.unit, InputUnit):
            v['unit'] = self.unit.value
        else:
             raise RosetteException("bad argument", "Parameter 'unit' not of InputUnit Enum", repr(self.unit))

        if isinstance(self.contentType, DataFormat):
            v['contentType'] = self.contentType.value
        else:
            raise RosetteException("bad argument", "Parameter 'contentType' not of DataFormat Enum", repr(self.contentType))
        return v

class RntParameters:
    def __init__(self):
        self.name = None
        self.targetLanguage = None
        self.entityType = None

    def serializable(self):
        v = {}
        for n in ("name", "targetLanguage", "entityType"):
            v[n] = self.__dict__[n]
        return v

class Operator:
    # take a session when we do OAuth2
    def __init__(self, service_url, logger, suburl):
        self.service_url = service_url
        self.logger = logger
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
                                   '"' + ename + '" "' + self.suburl + "\" failed to communicate with Raas",
                                   msg)


    def getInfo(self, result_format):
        url = self.service_url + '/' + self.suburl + "/info"
        if result_format == ResultFormat.ROSETTE:
            url = url + "?output=rosette"
        self.logger.info('getInfo: ' + url)
        headers = {'Accept':'application/json'}
        r = requests.get(url, headers=headers)
        return self.__finish_result(r, "getInfo")

    def ping(self):
        url = self.service_url + '/ping'
        self.logger.info('Ping: ' + url)
        headers = {'Accept':'application/json'}
        r = requests.get(url, headers=headers)
        return self.__finish_result(r, "ping")

    def operate(self, parameters, result_format):
        url = self.service_url + '/' + self.suburl
        if result_format == ResultFormat.ROSETTE:
            url = url + "?output=rosette"
        self.logger.info('operate: ' + url)
        headers = {}
        headers['Accept'] = 'application/json'
        headers['Content-Type'] = "application/json"
        params_to_serialize = parameters.serializable()
        r = requests.post(url, headers=headers, json=params_to_serialize)
        return self.__finish_result(r, "operate")

class API:
    """
    RaaS Python Client Binding API.
    This binding uses 'requests' (http://docs.python-requests.org/).
    """
    def __init__(self, key = None, service_url='https://api.rosette.com/rest/v1'):
        """ Supply the key used for the API."""
        self.key = key
        self.service_url = service_url
        self.logger = logging.getLogger('rosette.api')
        self.logger.info('Initialized on ' + self.service_url)
        self.debug = False

    def pinger(self):
        return Operator(self.service_url, self.logger, None)

    def language_detection(self):
        return Operator(self.service_url, self.logger, "language")

    def sentences_split(self):
        return Operator(self.service_url, self.logger, "sentences")

    def tokenize(self):
        return Operator(self.service_url, self.logger, "tokens")

    def morphology(self, subsub):
        if not isinstance(subsub, MorphologyOutput):
            raise RosetteException("bad argument", "Argument not a MorphologyOutput enum object", repr(subsub))
        return Operator(self.service_url, self.logger, "morphology/" + subsub.value)

    def entities(self, linked):
        if  linked:
            return Operator(self.service_url, self.logger, "entities/linked")
        else:
            return Operator(self.service_url, self.logger, "entities")

    def categories(self):
        return Operator(self.service_url, self.logger, "categories")

    def sentiment(self):
        return Operator(self.service_url, self.logger, "sentiment")

    def translate_name(self):
        return Operator(self.service_url, self.logger, "translated-name")
