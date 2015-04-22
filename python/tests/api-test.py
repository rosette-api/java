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
from rosette.api import API, RosetteParameters, RntParameters, DataFormat, MorphologyOutput, RniParameters
import os
import sys

sys.stderr.write("PYTHON version " + sys.version + "\n")

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
MORPHO_EXPECTED_POSES = [u'NOUN', u'CM', u'NOUN', u'VBPRES', u'SENT', u'ADJ', u'NOUN', u'COORD', u'NOUN', u'SENT',
                         u'PRONPERS', u'VBPRES', u'PROP', u'SENT', u'PRONPERS', u'VI', u'PROP', u'SENT']
MORPHO_EXPECTED_LEMMAS = [u'yes', u',', u'ma', u'be', u'!', u'green', u'egg', u'and', u'ham', u'?', u'I', u'be',
                          u'Sam', u';', u'I', u'filter', u'Spam', u'.']
MORPHOX_EXPECTED_POSES = [u'DET', u'NOUN', u'PREP', u'PROP', u'NOUN',  # last wrong
                          u'ADV', u'PREP', u'DET', u'NOUN', u'SENT']
CHINESE_HEAD_TAGS = [u'GUESS', u'GUESS', u'GUESS', u'GUESS', u'GUESS', u'GUESS', u'GUESS', u'GUESS', u'GUESS',
                     u'GUESS', u'GUESS', u'NC', u'V', u'V', u'E', u'NN', u'NC', u'A', u'NC', u'GUESS', u'GUESS',
                     u'NN', u'NC', u'GUESS', u'D', u'GUESS', u'GUESS', u'NC', u'GUESS', u'A', u'NC', u'V', u'NC',
                     u'W', u'W', u'NM', u'NC', u'GUESS', u'V', u'NC', u'W', u'A', u'GUESS', u'W', u'NC', u'GUESS',
                     u'NC', u'GUESS', u'D', u'V', u'A', u'NC', u'NC', u'U', u'A', u'GUESS', u'NC', u'OC', u'NC', u'A',
                     u'NC', u'D', u'W', u'W', u'V', u'GUESS', u'NN', u'NM', u'V', u'NC', u'NP', u'NC', u'A', u'OC']


class APITestCase(unittest.TestCase):
    def __init__(self, tcname):
        super(APITestCase, self).__init__(tcname)

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
        key = os.getenv('RAAS_USER_KEY')  # c/b None
        logging.info("URL " + self.url)
        self.api = API(service_url=self.url, user_key=key)
        params = RosetteParameters()
        params["content"] = HAM_SENTENCE
        self.HamParams = params
        uparams = RosetteParameters()
        uparams["content"] = HAM_SENTENCE
        uparams["contentType"] = DataFormat.UNSPECIFIED
        self.HamParamsU = uparams

        dt_html_params = RosetteParameters()
        dt_html_params["content"] = HAM_SENTENCE
        dt_html_params["contentType"] = DataFormat.HTML  # grosse Luege
        self.dt_html_params = dt_html_params

        dt_xhtml_params = RosetteParameters()
        dt_xhtml_params["content"] = XHTML
        dt_xhtml_params["contentType"] = DataFormat.XHTML
        self.dt_xhtml_params = dt_xhtml_params

        params = RosetteParameters()
        params["content"] = u"In the short story 'নষ্টনীড়', Rabindranath Tagore wrote,\
        \"Charu, have you read 'The Poison Tree' by Bankim Chandra Chatterjee?\"."
        self.TagParams = params

        params = RosetteParameters()
        params["contentUri"] = "http://www.basistech.com/"
        self.UriParams = params

        (directory, file_name) = os.path.split(os.path.realpath(__file__))
        self.dir = directory

    def test_ping(self):
        op = self.api.ping()
        result = op.ping()
        self.assertEqual(result['message'], 'Rosette API at your service')

    def test_language(self):
        op = self.api.language()
        result = op.info()
        # not much to do right now.
        return result

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
        text_path = os.path.join(self.dir, "chinese-example.html")
        parms.load_document_file(text_path, DataFormat.HTML)
        op = self.api.morphology(MorphologyOutput.PARTS_OF_SPEECH)
        result = op.operate(parms)
        tags = list(map(lambda x: x["pos"], result["posTags"]))  # list for py3
        self.assertEqual(tags[0:len(CHINESE_HEAD_TAGS)], CHINESE_HEAD_TAGS)

    def test_morphology(self):
        op = self.api.morphology()  # test default
        result = op.operate(self.HamParams)
        presult = [x['pos'] for x in result['posTags']]
        self.assertEqual(presult, MORPHO_EXPECTED_POSES)

    def test_morphologyUnspecified(self):
        op = self.api.morphology()  # test default
        result = op.operate(self.HamParamsU)
        presult = [x['pos'] for x in result['posTags']]
        self.assertEqual(presult, MORPHO_EXPECTED_POSES)

    def test_morphology_PseudoHTML(self):
        op = self.api.morphology(MorphologyOutput.PARTS_OF_SPEECH)
        result = op.operate(self.dt_html_params)
        presult = [x['pos'] for x in result['posTags']]
        self.assertEqual(presult, MORPHO_EXPECTED_POSES)

    def test_morphology_XHTML(self):
        op = self.api.morphology(MorphologyOutput.PARTS_OF_SPEECH)
        result = op.operate(self.dt_xhtml_params)
        presult = [x['pos'] for x in result['posTags']]
        self.assertEqual(presult, MORPHOX_EXPECTED_POSES)

    def test_morphology_lemmas(self):
        op = self.api.morphology(MorphologyOutput.LEMMAS)
        result = op.operate(self.HamParams)
        lemma_result = [x['lemma'] for x in result['lemmas']]
        self.assertEqual(lemma_result, MORPHO_EXPECTED_LEMMAS)

    def test_entities(self):
        op = self.api.entities(None)  # "linked" flag
        result = op.operate(self.TagParams)
        # Not the right answer, but what it currently gets.
        self.assertEquals(len(result['entities']), 3)

    def test_categoriesUri(self):
        op = self.api.categories()
        result = op.operate(self.UriParams)
        cats = result['categories']
        catkeys = [x['label'] for x in cats]
        self.assertTrue("TECHNOLOGY_AND_COMPUTING" in catkeys)
        
    def test_categories(self):
        op = self.api.categories()
        result = op.operate(self.HamParams)
        self.assertEquals(len(result['categories']), 1)

    def test_sentiment(self):
        op = self.api.sentiment()
        result = op.operate(self.HamParams)
        ary = result['sentiment']
        sary = sorted(ary, key=lambda x: -x['confidence'])
        self.assertEqual(sary[0]['label'], "pos")

    def test_translated_name(self):
        op = self.api.translated_name()

        params = RntParameters()
        params["name"] = "كريم عبد الجبار"
        params["entityType"] = "PERSON"
        params["targetLanguage"] = "eng"
        result = op.operate(params)
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

        result = op.operate(params)
        result = result["result"]
        self.assertEqual(result['translation'], u"جُورْج بُوش")

    def test_matched_name(self):
        op = self.api.matched_name()

        params = RniParameters()
        params["name1"] = { "text" : "John Doe" }
        params["name2"] = { "text" : "Jane Doe" }
        result = op.operate(params)
        result = result["result"]
        self.assertNotEquals(result["score"], 1.0)

        params["name1"] = { "text" : "John Doe" }
        params["name2"] = { "text" : "John Doe" }
        result = op.operate(params)
        result = result["result"]
        self.assertEquals(result["score"], 1.0)
