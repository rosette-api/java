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
from rosette.api import API
import os

logging.basicConfig(level=logging.DEBUG)

class PingTestCase(unittest.TestCase):
    def test_ping(self):
        port = os.environ['MOCK_SERVICE_PORT']
        # here is where the mock services live.
        url = 'http://localhost:' + port + '/raas'
        logging.info("URL " + url)
        api = API(service_url = url)
        result = api.ping()
        self.assertIsNotNone(result['message'])
