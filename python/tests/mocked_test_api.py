# -*- coding: utf-8 -*-

# To run tests, run `py.test python/tests/mocked_tes_api.py` from the ws-client-bindings directory
# Must be run from this level in order to access the correct file paths for test files

import httpretty
import json
import logging
import os
import pytest
from rosette.api import API, Operator, RosetteParameters, RntParameters, DataFormat, MorphologyOutput, RniParameters, RosetteException
import sys
_IsPy3 = sys.version_info[0] == 3

try:
    from urlparse import urlparse
except ImportError:
    from urllib.parse import urlparse
try:
    import httplib
except ImportError:
    import http.client as httplib

# Set up mocking
def mock_rosette_api():

    # For post requests, define what to return
    def request_callback(request, uri, headers):
        filename = request.headers["user_key"]
        resp_file = 'mock-data/response/'+filename
        status_file = open(resp_file+'.status')
        status = status_file.read()
        status_file.close()
        resp_json_file = open(resp_file+'.json')
        resp = resp_json_file.read()
        resp_json_file.close()
        return int(status), headers, resp

    # Register mocked URLs with HTTPretty
    for end in ['categories', 'entities', 'entities/linked', 'language', 'matched-name', 'morphology/complete',
                'morphology/compound-components', 'morphology/han-readings', 'morphology/lemmas',
                'morphology/parts-of-speech', 'sentences', 'sentiment', 'tokens', 'translated-name']:
        httpretty.register_uri(
                httpretty.POST, "https://api.rosette.com/rest/v1/"+end,
                body=request_callback)

    info_file = open('mock-data/response/info.json')
    httpretty.register_uri(httpretty.GET, "https://api.rosette.com/rest/v1/info", body=info_file.read(), status=200)
    info_file.close()

    ping_file = open('mock-data/response/ping.json')
    httpretty.register_uri(httpretty.GET, "https://api.rosette.com/rest/v1/ping", body=ping_file.read(), status=200)
    ping_file.close()

# Set up app based on request file name
class App:
    def __init__(self, filename):
        self.url = "https://api.rosette.com/rest/v1"
        # Set user key as filename as a workaround - tests don't require user key
        key = filename
        logging.info("URL " + self.url)
        self.api = API(service_url=self.url, user_key=key)
        if "matched-name" in filename:
            self.params = RniParameters()
            f = open("mock-data/request/"+filename+".json")
            paramsDict = json.loads(f.read())
            for key in paramsDict:
                self.params[key] = paramsDict[key]
            f.close()
        elif "translated-name" in filename:
            self.params = RntParameters()
            f = open("mock-data/request/"+filename+".json")
            paramsDict = json.loads(f.read())
            for key in paramsDict:
                self.params[key] = paramsDict[key]
            f.close()
        elif filename != '':
            self.params = RosetteParameters()
            f = open("mock-data/request/"+filename+".json")
            paramsDict = json.loads(f.read())
            for key in paramsDict:
                self.params[key] = paramsDict[key]
            f.close()

def test_ping():
    app = App('')
    httpretty.enable()
    mock_rosette_api()
    op = app.api.ping()
    result = op.ping()
    httpretty.disable()
    assert result['message'] == 'Rosette API at your service'

def test_info():
    app = App('')
    httpretty.enable()
    mock_rosette_api()
    op = Operator(app.api, None)
    result = op.info()
    httpretty.disable()
    assert result["buildNumber"] == "6bafb29d"
    assert result["name"] == "Rosette API"

def categorizeReqs():
    docs = {}
    limiter = 0
    for end in ['categories', 'entities', 'entities_linked', 'language', 'matched-name', 'morphology_complete',
                'morphology_compound-components', 'morphology_han-readings', 'morphology_lemmas',
                'morphology_parts-of-speech', 'sentences', 'sentiment', 'tokens', 'translated-name']:
        docs[end] = []
    for filename in os.listdir("mock-data/request"):
        if "morphology" not in filename and "name" not in filename:
            limiter += 1
            endpt = filename.split(".")[0].split("-")[-1]
            docs[endpt].append((filename.split(".")[0], "mock-data/response/"+filename))
        elif "name" in filename:
            if "matched" in filename:
                endpt = "matched-name"
            else:
                endpt = "translated-name"
            docs[endpt].append((filename.split(".")[0], "mock-data/response/"+filename))
        elif "morphology" in filename:
            facet = filename.split("morphology")[-1].split(".")[0]
            docs["morphology"+facet].append((filename.split(".")[0], "mock-data/response/"+filename))
    return docs

docs = categorizeReqs()

@pytest.mark.parametrize("input,expected", docs["categories"])
def test_categories(input, expected):
    error = False
    app = App(input)
    f = open(expected)
    res = json.loads(f.read())
    f.close()
    if "code" in res:
        if res["code"] == "unsupportedLanguage":
            error = True
    httpretty.enable()
    op = app.api.categories()
    if error:
        try:
            result = op.operate(app.params)
            assert False
        except RosetteException:
            assert True
            return
    result = op.operate(app.params)
    httpretty.disable()
    assert result == res

@pytest.mark.parametrize("input, expected", docs["sentiment"])
def test_sentiment(input, expected):
    error = False
    app = App(input)
    f = open(expected)
    res = json.loads(f.read())
    f.close()
    if "code" in res:
        if res["code"] == "unsupportedLanguage":
            error = True
    httpretty.enable()
    op = app.api.sentiment()
    if error:
        try:
            result = op.operate(app.params)
            assert False
        except RosetteException:
            assert True
            return
    result = op.operate(app.params)
    httpretty.disable()
    assert result == res

@pytest.mark.parametrize("input, expected", docs["language"])
def test_language(input, expected):
    app = App(input)
    f = open(expected)
    res = json.loads(f.read())
    f.close()
    httpretty.enable()
    op = app.api.language()
    result = op.operate(app.params)
    httpretty.disable()
    assert result == res

@pytest.mark.parametrize("input, expected", docs["entities"])
def test_entities(input, expected):
    error = False
    app = App(input)
    f = open(expected)
    res = json.loads(f.read())
    f.close()
    if "code" in res:
        if res["code"] == "unsupportedLanguage":
            error = True
    httpretty.enable()
    op = app.api.entities(None)
    if error:
        try:
            result = op.operate(app.params)
            assert False
        except RosetteException:
            print input
            assert True
            return
    result = op.operate(app.params)
    httpretty.disable()
    assert result == res

@pytest.mark.parametrize("input, expected", docs["entities_linked"])
def test_entities_linked(input, expected):
    error = False
    app = App(input)
    f = open(expected)
    res = json.loads(f.read())
    f.close()
    if "code" in res:
        if res["code"] == "unsupportedLanguage":
            error = True
    httpretty.enable()
    op = app.api.entities(True)
    if error:
        try:
            result = op.operate(app.params)
            assert False
        except RosetteException:
            print input
            assert True
            return
    result = op.operate(app.params)
    httpretty.disable()
    assert result == res

@pytest.mark.parametrize("input, expected", docs["morphology_complete"])
def test_morphology_complete(input, expected):
    error = False
    app = App(input)
    f = open(expected)
    res = json.loads(f.read())
    f.close()
    if "code" in res:
        if res["code"] == "unsupportedLanguage":
            error = True
    httpretty.enable()
    op = app.api.morphology()
    if error:
        try:
            result = op.operate(app.params)
            assert False
        except RosetteException:
            print input
            assert True
            return
    result = op.operate(app.params)
    httpretty.disable()
    assert result == res

@pytest.mark.parametrize("input, expected", docs["matched-name"])
def test_matched_name(input, expected):
    error = False
    app = App(input)
    f = open(expected)
    res = json.loads(f.read())
    f.close()
    if "code" in res:
        if res["code"] == "unsupportedLanguage":
            error = True
    httpretty.enable()
    op = app.api.matched_name()
    if error:
        try:
            result = op.operate(app.params)
            assert False
        except RosetteException:
            print input
            assert True
            return
    result = op.operate(app.params)
    httpretty.disable()
    assert result == res

@pytest.mark.parametrize("input, expected", docs["translated-name"])
def test_translated_name(input, expected):
    error = False
    app = App(input)
    f = open(expected)
    res = json.loads(f.read())
    f.close()
    if "code" in res:
        if res["code"] == "unsupportedLanguage":
            error = True
    httpretty.enable()
    op = app.api.translated_name()
    if error:
        try:
            result = op.operate(app.params)
            assert False
        except RosetteException:
            print input
            assert True
            return
    result = op.operate(app.params)
    httpretty.disable()
    assert result == res
