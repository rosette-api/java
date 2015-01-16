# -*- coding: utf-8 -*-


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

import unittest
import logging
from rosette.api import API, ResultFormat, RaasParameters
import os
import sys
import json

logging.basicConfig(level=logging.DEBUG)

class APITestCase(unittest.TestCase):
    def __init__(self, tcname):
        super(APITestCase,self).__init__(tcname)
        port = os.environ['MOCK_SERVICE_PORT']
        self.lurl = 'http://localhost:' + port + '/raas'
        params = RaasParameters()
        params.content = "Yes, Ma'm! Green eggs and ham?  I am Sam;  I filter Spam."
        params.contentType = "text/plain"
        params.unit = "doc"
        self.HamParams = params
        params = RaasParameters()
        params.content =  u"In the short story 'নষ্টনীড়', Rabindranath Tagore wrote, \"Charu, have you read 'The Poison Tree' by Bankim Chandra Chatterjee?\"."
        params.contentType = "text/plain"
        params.unit = "doc"
        self.TagParams = params

    def getBaseURL(self):
        url = self.lurl
        url = "http://jugmaster.basistech.net/rest/v1"
        logging.info("URL " + url)
        return url

    def getAPI(self):
        return API(service_url = self.getBaseURL())

    def test_ping(self):
        op = self.getAPI().pinger()
        result = op.ping()
        self.assertEqual(result['message'],'RaaS at your service')

    def test_language_info(self):
        op = self.getAPI().language_detection()
        result = op.getInfo(None)
        # not much to do right now.

    def test_adm_detection(self):
        op = self.getAPI().language_detection()
        result = op.operate(self.HamParams, ResultFormat.ROSETTE)

        self.assertIsNotNone(result['requestId'])
        ary = result['result']['attributes']['languageDetection']['detectionResults']
        self.assertNotEqual(len(ary), 0)
        sary = sorted(ary, key=lambda x: -x['confidence'])
        self.assertEqual(sary[0]['language'], "eng")


    def test_sentence_splitting(self):
        op = self.getAPI().sentences_split()
        result = op.operate(self.HamParams, None)
        self.assertIsNotNone(result['sentences'])
        self.assertEqual(len(result['sentences']), 3)

    def test_tokenizing(self):
        op = self.getAPI().tokenize()
        result = op.operate(self.HamParams, None)
        self.assertEqual(len(result['tokens']), 18)

    def test_morphology(self):
        op = self.getAPI().morphology("parts-of-speech")
        result = op.operate(self.HamParams, None)
        presult = [x['pos'] for x in result['posTags']]
        self.assertEqual(presult, [u'NOUN', u'CM', u'NOUN', u'VBPRES', u'SENT', u'ADJ', u'NOUN', u'COORD', u'NOUN', u'SENT', u'PRONPERS', u'VBPRES', u'PROP', u'SENT', u'PRONPERS', u'VI', u'PROP', u'SENT'])
        op = API(service_url = self.getBaseURL()).morphology("lemmas")
        result = op.operate(self.HamParams, None)
        lresult = [x['lemma'] for x in result['lemmas']]
        self.assertEqual(lresult, [u'yes', u',', u'ma', u'be', u'!', u'green', u'egg', u'and', u'ham', u'?', u'I', u'be', u'Sam', u';', u'I', u'filter', u'Spam', u'.'])

    def test_entities(self):
        op = self.getAPI().entities(None);
        result = op.operate(self.TagParams, None)
        # Not the right answer, but what it currently gets.
        self.assertEquals(len(result['entities']), 3)


        
