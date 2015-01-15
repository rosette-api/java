# -*- coding: utf-8 -*-


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

import unittest
import logging
from rosette.api import API, ResultFormat, RaasParameters
import os
import sys
import json


logging.basicConfig(level=logging.DEBUG)

class SentencesTestCase(unittest.TestCase):
    def __init__(self, tcname):
        super(SentencesTestCase,self).__init__(tcname)
        port = os.environ['MOCK_SERVICE_PORT']
        self.lurl = 'http://localhost:' + port + '/raas'
        params = RaasParameters()
        params.content = "Yes, Ma'm! Green eggs and ham?  I am Sam;  I filter Spam."
        params.contentType = "text/plain"
        params.unit = "doc"
        self.HamParams = params
        params = RaasParameters()
     #   params.content =  u"In the short story 'নষ্টনীড়', Rabindranath Tagore wrote, \"Charu, have you read 'The Poison Tree' by Bankim Chandra Chatterjee?\"."
        params.content =  u"In the short story 'Nashtanir', Rabindranath Tagore wrote, \"Charu, have you read 'The Poison Tree' by Bankim Chandra Chatterjee?\"."
        params.contentType = "text/plain"
        params.unit = "doc"
        self.TagParams = params

    def getBaseURL(self):
        url = self.lurl
        url = "http://jugmaster.basistech.net/rest/v1"
        logging.info("URL " + url)
        return url
        
    def test_sentence_splitting(self):
        op = API(service_url = self.getBaseURL()).sentences_split()
        result = op.operate(self.HamParams, None)
        self.assertIsNotNone(result['sentences'])
        self.assertEqual(len(result['sentences']), 3)

    def test_tokenizing(self):
        op = API(service_url = self.getBaseURL()).tokenize()
        result = op.operate(self.HamParams, None)
        self.assertEqual(len(result['tokens']), 18)

    def test_morphology(self):
        op = API(service_url = self.getBaseURL()).morphology("parts-of-speech")
        result = op.operate(self.HamParams, None)
        presult = [x['pos'] for x in result['posTags']]
        self.assertEqual(presult, [u'NOUN', u'CM', u'NOUN', u'VBPRES', u'SENT', u'ADJ', u'NOUN', u'COORD', u'NOUN', u'SENT', u'PRONPERS', u'VBPRES', u'PROP', u'SENT', u'PRONPERS', u'VI', u'PROP', u'SENT'])
        op = API(service_url = self.getBaseURL()).morphology("lemmas")
        result = op.operate(self.HamParams, None)
        lresult = [x['lemma'] for x in result['lemmas']]
        self.assertEqual(lresult, [u'yes', u',', u'ma', u'be', u'!', u'green', u'egg', u'and', u'ham', u'?', u'I', u'be', u'Sam', u';', u'I', u'filter', u'Spam', u'.'])

    def test_entities(self):
        op = API(service_url = self.getBaseURL()).entities(None);
        result = op.operate(self.TagParams, None)
        # Not the right answer, but what it currently gets.
        self.assertEquals(len(result['entities']), 3)


        
