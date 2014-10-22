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
from rosette.api import API, ResultFormat, LanguageDetectionParameters
import os

logging.basicConfig(level=logging.DEBUG)

class RliTestCase(unittest.TestCase):
    def test_info(self):
        port = os.environ['mock-service-port']
        url = 'http://localhost:' + port + '/raas'
        logging.info("URL " + url)
        lid = API(service_url = url).language_detection()
        result = lid.info()
        self.assertIsNotNone(result['requestId'])

    def test_simple_detection(self):
        port = os.environ['mock-service-port']
        url = 'http://localhost:' + port + '/raas'
        logging.info("URL " + url)
        params = LanguageDetectionParameters()
        params.content = "dummy text"
        # the mock services can't respond to the individual params.
        lid = API(service_url = url).language_detection()
        result = lid.detect(params, ResultFormat.SIMPLE)
