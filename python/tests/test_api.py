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

import pytest
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

class App:
    def __init__(self):
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

@pytest.fixture
def app():
    return App()

def test_ping(app):
    op = app.api.ping()
    result = op.ping()
    assert result['message'] == 'Rosette API at your service'

def test_language(app):
    op = app.api.language()
    result = op.info()
    assert result is not None

def test_detection(app):
    op = app.api.language()
    result = op.operate(app.HamParams)

    assert result['requestId'] is not None
    ary = result['languageDetections']
    assert len(ary) != 0
    sary = sorted(ary, key=lambda x: -x['confidence'])
    assert sary[0]['language'] == "eng"

def test_sentences(app):
    op = app.api.sentences()
    result = op.operate(app.HamParams)
    assert result['sentences'] is not None
    assert len(result['sentences']) == 3

def test_tokens(app):
    op = app.api.tokens()
    result = op.operate(app.HamParams)
    assert len(result['tokens']) == 18

def test_morphology_File(app):
    parms = RosetteParameters()
    text_path = os.path.join(app.dir, "chinese-example.html")
    parms.load_document_file(text_path, DataFormat.HTML)
    op = app.api.morphology(MorphologyOutput.PARTS_OF_SPEECH)
    result = op.operate(parms)
    tags = list(map(lambda x: x["pos"], result["posTags"]))  # list for py3
    assert tags[0:len(CHINESE_HEAD_TAGS)] == CHINESE_HEAD_TAGS

def test_morphology(app):
    op = app.api.morphology()  # test default
    result = op.operate(app.HamParams)
    presult = [x['pos'] for x in result['posTags']]
    assert presult == MORPHO_EXPECTED_POSES

def test_morphologyUnspecified(app):
    op = app.api.morphology()  # test default
    result = op.operate(app.HamParamsU)
    presult = [x['pos'] for x in result['posTags']]
    assert presult == MORPHO_EXPECTED_POSES

def test_morphology_PseudoHTML(app):
    op = app.api.morphology(MorphologyOutput.PARTS_OF_SPEECH)
    result = op.operate(app.dt_html_params)
    presult = [x['pos'] for x in result['posTags']]
    assert presult == MORPHO_EXPECTED_POSES

def test_morphology_XHTML(app):
    op = app.api.morphology(MorphologyOutput.PARTS_OF_SPEECH)
    result = op.operate(app.dt_xhtml_params)
    presult = [x['pos'] for x in result['posTags']]
    assert presult == MORPHOX_EXPECTED_POSES

def test_morphology_lemmas(app):
    op = app.api.morphology(MorphologyOutput.LEMMAS)
    result = op.operate(app.HamParams)
    lemma_result = [x['lemma'] for x in result['lemmas']]
    assert lemma_result == MORPHO_EXPECTED_LEMMAS

def test_entities(app):
    op = app.api.entities(None)  # "linked" flag
    result = op.operate(app.TagParams)
    # Not the right answer, but what it currently gets.
    assert len(result['entities']) == 3

def test_categoriesUri(app):
    op = app.api.categories()
    result = op.operate(app.UriParams)
    cats = result['categories']
    catkeys = [x['label'] for x in cats]
    assert "TECHNOLOGY_AND_COMPUTING" in catkeys
        
def test_categories(app):
    op = app.api.categories()
    result = op.operate(app.HamParams)
    assert len(result['categories']) == 1

def test_sentiment(app):
    op = app.api.sentiment()
    result = op.operate(app.HamParams)
    ary = result['sentiment']
    sary = sorted(ary, key=lambda x: -x['confidence'])
    assert sary[0]['label'] == "pos"

def test_translated_name(app):
    op = app.api.translated_name()
    params = RntParameters()
    params["name"] = "كريم عبد الجبار"
    params["entityType"] = "PERSON"
    params["targetLanguage"] = "eng"
    result = op.operate(params)
    result = result["result"]
    assert result["translation"] == "Karim 'Abd-al-Jabbar"
    assert result["sourceLanguageOfUse"] == "ara"
    assert result["targetScheme"] == "IC"

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
    assert result['translation'] == u"جُورْج بُوش"

def test_matched_name(app):
    op = app.api.matched_name()

    params = RniParameters()
    params["name1"] = { "text" : "John Doe" }
    params["name2"] = { "text" : "Jane Doe" }
    result = op.operate(params)
    result = result["result"]
    assert result["score"] != 1.0

    params["name1"] = { "text" : "John Doe" }
    params["name2"] = { "text" : "John Doe" }
    result = op.operate(params)
    result = result["result"]
    assert result["score"] == 1.0
