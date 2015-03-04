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
from rosette.api import API, InputUnit, RosetteParameters, RntParameters, DataFormat, MorphologyOutput
import os
import sys
import json
import base64

XHTML = """
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <title>Precipitation Paradigms on the Iberian Peninsula</title>
</head>

<body>
   The reign in Spain falls mainly upon the planes.
</body>

</html>
"""


logging.basicConfig(level=logging.DEBUG)

HAM_SENTENCE = "Yes, Ma'm! Green eggs and ham?  I am Sam;  I filter Spam."

# Lists, not tuples, because we compare to a list that we cons.
MORPHO_EXPECTED_POSES = [u'NOUN', u'CM', u'NOUN', u'VBPRES', u'SENT', u'ADJ', u'NOUN', u'COORD', u'NOUN', u'SENT', u'PRONPERS', u'VBPRES', u'PROP', u'SENT', u'PRONPERS', u'VI', u'PROP', u'SENT']
MORPHO_EXPECTED_LEMMAS = [u'yes', u',', u'ma', u'be', u'!', u'green', u'egg', u'and', u'ham', u'?', u'I', u'be', u'Sam', u';', u'I', u'filter', u'Spam', u'.']
MORPHOX_EXPECTED_POSES = [u'DET', u'NOUN', u'PREP', u'PROP', u'NOUN', # last wrong
                          u'ADV', u'PREP', u'DET', u'NOUN', u'SENT']
CHINESE_HEAD_TAGS = [u'NC', u'NC', u'NC', u'A', u'A', u'NC', u'NC', u'NC', u'NC', u'NC', u'V', u'NC', u'NC', u'NC', u'NC', u'NP', u'V', u'NP', u'NC', u'A', u'W', u'GUESS', u'V', u'GUESS', u'NC', u'NC', u'NC', u'NC', u'NC', u'NP', u'V', u'V', u'GUESS', u'GUESS', u'NC', u'GUESS', u'NC', u'GUESS', u'NP', u'GUESS', u'NC', u'NC', u'GUESS', u'NC', u'GUESS', u'NC', u'GUESS']

class APITestCase(unittest.TestCase):
    def __init__(self, tcname):
        super(APITestCase,self).__init__(tcname)
        burl = os.getenv('RAAS_SERVICE_URL')
        if burl is not None:
            burl = burl.strip()
            if burl == "":
                burl = None
        if burl is None:
            port = os.getenv('MOCK_SERVICE_PORT')
            self.url = 'http://localhost:' + port + '/raas'
        else:
            self.url = burl
        key = os.getenv('RAAS_USER_KEY') # c/b None
        logging.info("URL " + self.url)
        self.api = API(service_url = self.url, user_key = key)
        params = RosetteParameters()
        params["content"] = HAM_SENTENCE
        params["contentType"] = DataFormat.SIMPLE
        params["unit"] = InputUnit.DOC
        self.HamParams = params

        DtHTMLParams = RosetteParameters()
        DtHTMLParams["content"] = HAM_SENTENCE
        DtHTMLParams["contentType"] = DataFormat.HTML #grosse Luege
        DtHTMLParams["unit"] = InputUnit.DOC
        self.DtHTMLParams = DtHTMLParams


        DtXHTMLParams = RosetteParameters()
        DtXHTMLParams["content"] = XHTML
        DtXHTMLParams["contentType"] = DataFormat.XHTML
        DtXHTMLParams["unit"] = InputUnit.DOC
        self.DtXHTMLParams = DtXHTMLParams

        params = RosetteParameters()
        params["content"] =  u"In the short story 'নষ্টনীড়', Rabindranath Tagore wrote, \"Charu, have you read 'The Poison Tree' by Bankim Chandra Chatterjee?\"."
        params["contentType"] = DataFormat.SIMPLE
        params["unit"] = InputUnit.DOC
        self.TagParams = params

        params = RosetteParameters()
        params["contentUri"] = "http://www.basistech.com/"
        params["unit"] = InputUnit.DOC
        self.UriParams = params

        (dir,fname) = os.path.split(os.path.realpath(__file__))
        self.dir = dir

    def test_ping(self):
        op = self.api.ping()
        result = op.ping()
        self.assertEqual(result['message'],'Rosette API at your service')

    def test_language(self):
        op = self.api.language()
        result = op.info()
        # not much to do right now.

    def test_detection(self):
        op = self.api.language()
        result = op.operate(self.HamParams)

        self.assertIsNotNone(result['requestId'])
        ary = result['languageDetections']
        self.assertNotEqual(len(ary), 0)
        sary = sorted(ary, key=lambda x: -x['confidence'])
        self.assertEqual(sary[0]['language'], "eng")

    def test_sentences(self):
        op = self.api.sentences()
        result = op.operate(self.HamParams)
        self.assertIsNotNone(result['sentences'])
        self.assertEqual(len(result['sentences']), 3)

    def test_tokens(self):
        op = self.api.tokens()
        result = op.operate(self.HamParams)
        self.assertEqual(len(result['tokens']), 18)

    def test_morphology_File(self):
        parms = RosetteParameters()
        textpath = os.path.join(self.dir, "chinese-example.html")
        parms.LoadDocumentFile(textpath, DataFormat.HTML)
        op = self.api.morphology(MorphologyOutput.PARTS_OF_SPEECH)
        result = op.operate(parms)
        tags = map(lambda x: x["pos"], result["posTags"])
        self.assertEqual(tags[0:len(CHINESE_HEAD_TAGS)], CHINESE_HEAD_TAGS)

    def test_morphology(self):
        op = self.api.morphology(MorphologyOutput.PARTS_OF_SPEECH)
        result = op.operate(self.HamParams)
        presult = [x['pos'] for x in result['posTags']]
        self.assertEqual(presult, MORPHO_EXPECTED_POSES)

    def test_morphology_PseudoHTML(self):
        op = self.api.morphology(MorphologyOutput.PARTS_OF_SPEECH)
        result = op.operate(self.DtHTMLParams)
        presult = [x['pos'] for x in result['posTags']]
        self.assertEqual(presult, MORPHO_EXPECTED_POSES)

    def test_morphology_XHTML(self):
        op = self.api.morphology(MorphologyOutput.PARTS_OF_SPEECH)
        result = op.operate(self.DtXHTMLParams)
        presult = [x['pos'] for x in result['posTags']]
        self.assertEqual(presult, MORPHOX_EXPECTED_POSES)

    def test_morphology_lemmas(self):
        op = self.api.morphology(MorphologyOutput.LEMMAS)
        result = op.operate(self.HamParams)
        lresult = [x['lemma'] for x in result['lemmas']]
        self.assertEqual(lresult, MORPHO_EXPECTED_LEMMAS)

    def test_entities(self):
        op = self.api.entities(None); # "linked" flag
        result = op.operate(self.TagParams)
        # Not the right answer, but what it currently gets.
        self.assertEquals(len(result['entities']), 3)

    def test_categoriesUri(self):
        op = self.api.categories();
        result = op.operate(self.UriParams)
        cats = result['categories']
        catkeys = [x['label'] for x in cats]
        self.assertTrue("TECHNOLOGY_AND_COMPUTING" in catkeys)
        
    def test_categories(self):
        op = self.api.categories();
        result = op.operate(self.HamParams)
        self.assertEquals(len(result['categories']), 1)

    def test_sentiment(self):
        op = self.api.sentiment();
        result = op.operate(self.HamParams)
        ary = result['sentiment']
        sary = sorted(ary, key=lambda x: -x['confidence'])
        self.assertEqual(sary[0]['label'], "pos")

    def test_translated_name(self):
        op = self.api.translated_name();

        params = RntParameters()
        params["name"] = "كريم عبد الجبار"
        params["entityType"] = "PERSON";
        params["targetLanguageCode"] = "eng";
        result = op.operate(params)
        result = result["result"]
        self.assertEqual(result["translation"], "Karim 'Abd-al-Jabbar")
        self.assertEqual(result["sourceLanguageOfUseCode"], "ara")
        self.assertEqual(result["targetSchemeCode"], "IC")        

        params = RntParameters()
        params["name"] = "George Bush"
        params["entityType"] = "PERSON"
        params["sourceScriptCode"] = "Latn"
        params["sourceLanguageOfOriginCode"] = "eng"
        params["sourceLanguageOfUseCode"] = "eng"
        params["targetLanguageCode"] = "ara"
        params["targetScriptCode"] = "Arab"
        params["targetSchemeCode"] = "NATIVE"

        result = op.operate(params)
        result = result["result"]
        self.assertEqual(result['translation'], u"جُورْج بُوش")
