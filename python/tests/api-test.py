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
from rosette.api import API, ResultFormat, InputUnit, RaasParameters, RntParameters, DataFormat, MorphologyOutput
import os
import sys
import json

logging.basicConfig(level=logging.DEBUG)



class APITestCase(unittest.TestCase):
    def __init__(self, tcname):
        super(APITestCase,self).__init__(tcname)
        burl = os.environ['RAAS_SERVICE_URL']
        if burl is not None:
            burl = burl.strip()
            if burl == "":
                burl = None
        if burl is None:
            port = os.environ['MOCK_SERVICE_PORT']
            self.url = 'http://localhost:' + port + '/raas'
        else:
            self.url = burl
        logging.info("URL " + self.url)
        self.api = API(service_url = self.url)
        params = RaasParameters()
        params["content"] = "Yes, Ma'm! Green eggs and ham?  I am Sam;  I filter Spam."
        params["contentType"] = DataFormat.SIMPLE
        params["unit"] = InputUnit.DOC
        self.HamParams = params

        params = RaasParameters()
        params["content"] =  u"In the short story 'নষ্টনীড়', Rabindranath Tagore wrote, \"Charu, have you read 'The Poison Tree' by Bankim Chandra Chatterjee?\"."
        params["contentType"] = DataFormat.SIMPLE
        params["unit"] = InputUnit.DOC
        self.TagParams = params

        params = RaasParameters()
        params["contentUri"] = "http://www.basistech.com/"
        params["unit"] = InputUnit.DOC
        self.UriParams = params

    def test_ping(self):
        op = self.api.pinger()
        result = op.ping()
        self.assertEqual(result['message'],'RaaS at your service')

    def test_language_info(self):
        op = self.api.language_detection()
        result = op.getInfo(None)
        # not much to do right now.

    def test_adm_detection(self):
        op = self.api.language_detection()
        result = op.operate(self.HamParams, ResultFormat.ROSETTE)

        self.assertIsNotNone(result['requestId'])
        ary = result['result']['attributes']['languageDetection']['detectionResults']
        self.assertNotEqual(len(ary), 0)
        sary = sorted(ary, key=lambda x: -x['confidence'])
        self.assertEqual(sary[0]['language'], "eng")


    def test_sentence_splitting(self):
        op = self.api.sentences_split()
        result = op.operate(self.HamParams, None)
        self.assertIsNotNone(result['sentences'])
        self.assertEqual(len(result['sentences']), 3)

    def test_tokenizing(self):
        op = self.api.tokenize()
        result = op.operate(self.HamParams, None)
        self.assertEqual(len(result['tokens']), 18)

    def test_morphology(self):
        op = self.api.morphology(MorphologyOutput.PARTS_OF_SPEECH)
        result = op.operate(self.HamParams, None)
        presult = [x['pos'] for x in result['posTags']]
        self.assertEqual(presult, [u'NOUN', u'CM', u'NOUN', u'VBPRES', u'SENT', u'ADJ', u'NOUN', u'COORD', u'NOUN', u'SENT', u'PRONPERS', u'VBPRES', u'PROP', u'SENT', u'PRONPERS', u'VI', u'PROP', u'SENT'])
        op = self.api.morphology(MorphologyOutput.LEMMAS)
        for multipart in (False, True):
            op.useMultipart = multipart
            result = op.operate(self.HamParams, None)
            lresult = [x['lemma'] for x in result['lemmas']]
            self.assertEqual(lresult, [u'yes', u',', u'ma', u'be', u'!', u'green', u'egg', u'and', u'ham', u'?', u'I', u'be', u'Sam', u';', u'I', u'filter', u'Spam', u'.'])

    def test_entities(self):
        op = self.api.entities(None); # "linked" flag
        result = op.operate(self.TagParams, None)
        # Not the right answer, but what it currently gets.
        self.assertEquals(len(result['entities']), 3)

    def test_categoriesUri(self):
        op = self.api.categories();
        result = op.operate(self.UriParams, None)
        cats = result['categories']
        catkeys = [x['label'] for x in cats]
        self.assertTrue("TECHNOLOGY_AND_COMPUTING" in catkeys)

        
    def test_categories(self):
        op = self.api.categories();
        result = op.operate(self.HamParams, None)
        self.assertEquals(len(result['categories']), 1)

    def test_sentiment(self):
        op = self.api.sentiment();
        result = op.operate(self.HamParams, None)
        ary = result['sentiment']
        sary = sorted(ary, key=lambda x: -x['confidence'])
        self.assertEqual(sary[0]['label'], "pos")

    def test_translate_name(self):
        op = self.api.translate_name();

        params = RntParameters()
        params["name"] = "كريم عبد الجبار"
        params["entityType"] = "PERSON";
        params["targetLanguage"] = "eng";
        result = op.operate(params, None)
        result = result["result"]
        self.assertEqual(result["translation"], "Karim 'Abd-al-Jabbar")
        self.assertEqual(result["sourceLanguageOfUse"], "ara")
        self.assertEqual(result["targetScheme"], "IC")        

        params = RntParameters()
        params["name"] = "George Bush"
        params["entityType"] = "PERSON"
        params["sourceScript"] = "Latn"
        params["sourceLanguageOfOrigin"] = "eng"
        params["sourceLanguageOfUse"] = "eng"
        params["targetLanguage"] = "ara"
        params["targetScript"] = "Arab"
        params["targetScheme"] = "NATIVE"

        result = op.operate(params, None)
        result = result["result"]
        self.assertEqual(result['translation'], u"جُورْج بُوش")
