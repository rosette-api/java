"""
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2014 Basis Technology Corporation All rights reserved.
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
    SIMPLE = "",
    ROSETTE = "rosette"

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
        v['contentType'] = self.contentType
        v['unit'] = self.unit
        return v


class LanguageDetection:
    # take a session when we do OAuth2
    def __init__(self, service_url, logger):
        self.service_url = service_url
        self.logger = logger
        self.pp = pprint.PrettyPrinter(indent=4)

    def info(self):
        # retrieve info
        info_url = self.service_url + '/language/info'
        self.logger.info('language/info: ' + info_url)
        headers = {}
        headers['Accept'] = 'application/json';
        r = requests.get(info_url, headers=headers)
        if r.status_code == 200:
            return r.json()
        else:
            raise RosetteException(r.status_code, "\"info\" failed to communicate with language detection service.", r.text)

    def detect(self, parameters, result_format):
        detect_url = self.service_url + '/language'
        if result_format == ResultFormat.ROSETTE:
            detect_url = detect_url + "?output=rosette"
        self.logger.info('language: ' + detect_url)
        headers = {}
        headers['Accept'] = 'application/json'
        headers['Content-Type'] = 'application/json'
        params_to_serialize =  {"content" : parameters.content} # parameters.serializable()
        r = requests.post(detect_url, headers=headers, json=params_to_serialize)
        if r.status_code == 200:
            return r.json()
        else:

            print >>sys.stderr, "Detect fail: R:"
            self.pp.pprint(r.__dict__)
            print >>sys.stderr, "RESPONSE TEXT:", r.text
            print >>sys.stderr, "R.request:", r.request.__dict__
            raise RosetteException(r.status_code, "\"detect\" failed to communicate with language detection service.", r.json()['message'])

# TODO: set up as a fixed collection of properties.
class SentenceSplitParameters:
    def __init__(self):
        self.content = None
        self.contentUri = None
        self.contentType = None
        self.unit = None
        # The rest is the options

        self.minValidChars = 0
        self.profileDepth = 0
        self.ambiguityThreshold = 0
        self.invalidityThreshold = 0
        self.languageHintWeight = 0
        self.encodingHint = None
        self.encodingHintWeight = 0
        self.languageWeightHint = {}
        self.languageHint = None
        
    def serializable(self):
        v = {}
        v['content'] = self.content
        v['contentUri'] = self.contentUri
        v['contentType'] = self.contentType
        v['unit'] = self.unit
        o = {}
        v['options'] = o
        o['minValidChars'] = self.minValidChars
        o['profileDepth'] = self.profileDepth
        o['ambiguityThreshold'] = self.ambiguityThreshold
        o['invalidityThreshold'] = self.invalidityThreshold
        o['languageHintWeight'] = self.languageHintWeight
        o['encodingHint'] = self.encodingHint
        o['encodingHintWeight'] = self.encodingHintWeight
        o['languageWeightHint'] = self.languageWeightHint
        o['languageHint'] = self.languageHint
        return v

class Operator:
    # take a session when we do OAuth2
    def __init__(self, service_url, logger, suburl):
        self.service_url = service_url
        self.logger = logger
        self.suburl = suburl

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
        if r.status_code == 200:
            return r.json()
        else:
            print >>sys.stderr, "RESPONSE TEXT:", r.text
            raise RosetteException(r.status_code, "\"operate\" \"" + suburl + "\" failed to communicate with language  service.", r.json()['message'])


class API:
    """
    RaaS Python Client Binding API.
    This binding uses 'requests' (http://docs.python-requests.org/).
    """
    # initial default value for the URL here is wrong.
    def __init__(self, key = None, service_url='http://rosette.basistech.net/raas'):
        """ Supply the key used for the API."""
        self.key = key
        self.service_url = service_url
        self.logger = logging.getLogger('rosette.api')
        self.logger.info('Initialized on ' + self.service_url)
        self.debug = False

    def ping(self):
        ping_url = self.service_url + '/ping'
        self.logger.info('Ping: ' + ping_url)
        headers = {}
        headers['Accept'] = 'application/json';
        r = requests.get(ping_url, headers=headers)
        if r.status_code == 200:
            return r.json()
        else:
            raise RosetteException(r.status_code, "Failed to communicate with ping service.")

    def language_detection(self):
        return LanguageDetection(self.service_url, self.logger)

    def sentences_split(self):
        return Operator(self.service_url, self.logger, "sentences")

    def tokenize(self):
        return Operator(self.service_url, self.logger, "tokens")
