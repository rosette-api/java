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

class InputUnit(Enum):
    DOC = "doc"
    SENTENCE= "sentence"

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
        if self.unit:
            v['unit'] = self.unit.value
        else:
            v['unit'] = None
        return v

class Operator:
    # take a session when we do OAuth2
    def __init__(self, service_url, logger, suburl):
        self.service_url = service_url
        self.logger = logger
        self.suburl = suburl

    def __finish_result(self, r, ename):
        if r.status_code == 200:
            return r.json()
        else:
            raise RosetteException(r.status_code,
                                    '"' + ename + '" "' + self.suburl + "\" failed to communicate with Raas",
                                    r.json()['message'])

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
    # initial default value for the URL here is wrong.
    def __init__(self, key = None, service_url='http://rosette.basistech.net/raas'):
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
        return Operator(self.service_url, self.logger, "morphology/" + subsub)

    def entities(self, subsub):
        if subsub is None:
            return Operator(self.service_url, self.logger, "entities")
        else:
            return Operator(self.service_url, self.logger, "entities/" + subsub)

    def categories(self):
        return Operator(self.service_url, self.logger, "categories")

    def sentiment(self):
        return Operator(self.service_url, self.logger, "sentiment")
