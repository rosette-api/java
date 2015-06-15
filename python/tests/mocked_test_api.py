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

# To run tests, run `py.test mocked_test_api.py`

import codecs
import httpretty
import json
import logging
import os
import pytest
import re
from rosette.api import API, Operator, RosetteParameters, RntParameters, RniParameters, RosetteException

import sys
_IsPy3 = sys.version_info[0] == 3
print (_IsPy3)


# Set up mocking
def mock_rosette_api():
    # For post requests, define what to return
    def request_callback(request, uri, headers):
        # Filename is in the user_key field as a workaround as the mocked tests don't require a user key
        filename = request.headers["user_key"]
        # Construct the base name for the response file from the filename
        # Example filename: eng-doc-categories
        resp_file = os.path.dirname(__file__) + "/../../mock-data/response/" + filename
        # Retrieve data from the corresponding status file
        with open(resp_file + ".status", "r") as status_file:
            status = status_file.read()
        # Retrieve data from corresponding json file
        with open(resp_file + ".json", "r") as resp_json_file:
            resp = resp_json_file.read()
        return int(status), headers, resp

    # Register mocked URLs with HTTPretty
    # All post requests dynamically respond based on request
    for end in ["categories", "entities", "entities/linked", "language", "matched-name", "morphology/complete",
                "morphology/compound-components", "morphology/han-readings", "morphology/lemmas",
                "morphology/parts-of-speech", "sentences", "sentiment", "tokens", "translated-name"]:
        httpretty.register_uri(httpretty.POST, "https://api.rosette.com/rest/v1/" + end, body=request_callback)

    # Get requests (info and ping) always have the same response because there is no request body
    with open(os.path.dirname(__file__) + "/../../mock-data/response/info.json", "r") as info_file:
        body = info_file.read()
    httpretty.register_uri(httpretty.GET, "https://api.rosette.com/rest/v1/info", body=body, status=200)

    with open(os.path.dirname(__file__) + "/../../mock-data/response/ping.json", "r") as ping_file:
        body = ping_file.read()
    httpretty.register_uri(httpretty.GET, "https://api.rosette.com/rest/v1/ping", body=body, status=200)


# Set up app based on request file name
class App:
    def __init__(self, filename):
        self.url = "https://api.rosette.com/rest/v1"
        # Set user key as filename as a workaround - tests don"t require user key
        # Filename is necessary to get the correct response in the mocked test
        key = filename
        logging.info("URL " + self.url)
        self.api = API(service_url=self.url, user_key=key)
        # Default to RosetteParameters as self.params
        self.params = RosetteParameters()
        # Name matching endpoint requires RniParameters
        if "matched-name" in filename:
            self.params = RniParameters()
        # Name translation requires RntParameters
        elif "translated-name" in filename:
            self.params = RntParameters()
        # Filename is only "" if it is a ping or info test -- they do not have request files
        if filename != "":
            # Find and load contents of request file into parameters
            with open(os.path.dirname(__file__) + "/../../mock-data/request/" + filename + ".json", "r", errors='ignore') as inp_file:
                params_dict = json.loads(inp_file.read(), 'utf-8')
            for key in params_dict:
                self.params[key] = params_dict[key]


# Run through all files in the mock-data directory, extract endpoint, and create a list of tuples of the form
# (input filename, output filename, endpoint) as the elements
def categorize_reqs():
    # Define the regex pattern of file names. Example: eng-doc-categories.json
    pattern = re.compile('(\w+-\w+-([a-z_-]+))[.]json')
    files = []
    # Loop through all file names in the mock-data/request directory
    for filename in ["eng-doc-morphology_complete.json"]: # Comment this and uncomment below to run on all morphology_complete files
    #for filename in os.listdir(os.path.dirname(__file__) + "/../../mock-data/request"):
        if filename != ".DS_Store" and "morphology_complete":
            # Extract the endpoint (the part after the first two "-" but before .json
            endpt = pattern.match(filename).group(2)
            # Add (input, output, endpoint) to files list
            files.append((pattern.match(filename).group(1), os.path.dirname(__file__) + "/../../mock-data/response/" +
                        filename, endpt))
    return files

# Setup for tests - register urls with HTTPretty and compile a list of all necessary information about each file
# in mock-data/request so that tests can be run
mock_rosette_api()
docs_list = categorize_reqs()


# Test that pinging the API is working properly
def test_ping():
    app = App("")
    # HTTPretty intercepts calls while enabled
    httpretty.enable()
    op = app.api.ping()
    result = op.ping()
    httpretty.disable()
    assert result["message"] == "Rosette API at your service"


# Test that getting the info about the API is being called correctly
def test_info():
    app = App("")
    httpretty.enable()
    op = Operator(app.api, None)
    result = op.info()
    httpretty.disable()
    assert result["buildNumber"] == "6bafb29d"
    assert result["name"] == "Rosette API"


# Test all other endpoints
# docs_list is the list of information from documents in the mock-data/request directory above
# @pytest.mark.parametrize("inp, expected, endpt", docs_list) means that it will call the below test for each tuple
# in the docs_list feeding the elements of the tuple as arguments to the test
@pytest.mark.parametrize("inp, expected, endpt", docs_list)
def test_all(inp, expected, endpt):
    error = False
    # Create an instance of the app, feeding the filename to be stored as the user key so the response will be correct
    app = App(inp)
    # Open the expected response file and store the data
    with open(expected, "r", errors='ignore') as expected_file:
        res = json.loads(expected_file.read(), 'utf-8')
    # Check to see if this particular request should throw an exception for an unsupported language
    if "code" in res:
        if res["code"] == "unsupportedLanguage":
            error = True
    httpretty.enable()
    functions = {"categories": app.api.categories(),
                 "entities": app.api.entities(None),
                 "entities_linked": app.api.entities(True),
                 "language": app.api.language(),
                 "matched-name": app.api.matched_name(),
                 "morphology_complete": app.api.morphology(),
                 "sentiment": app.api.sentiment(),
                 "translated-name": app.api.translated_name()}
    # Find the correct function based on the endpoint and create an operator
    op = functions[endpt]
    # If the request is expected to throw an exception, try complete the operation and pass the test only if it fails
    if error:
        try:
            op.operate(app.params)
            assert False
        except RosetteException:
            assert True
            return
    # Otherwise, actually complete the operation and check that it got the correct result
    result = op.operate(app.params)
    httpretty.disable()
    if result == res:
        assert True
    else:
        assert False
