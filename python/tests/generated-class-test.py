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
import sys
import unittest
from rosette.LanguageCode import LanguageCode, languageCodeByISO639
from rosette.ISO15924 import ISO15924, iso15924ByCode4 

class LanguageCodeTestCase(unittest.TestCase):
    def test_anyoneHome(self):
        self.assertNotEqual(LanguageCode.ENGLISH, LanguageCode.FINNISH)
    def test_lookup(self):
        self.assertEqual(LanguageCode.FINNISH, languageCodeByISO639('fin'))

class ISO15924TestCase(unittest.TestCase):
    def test_lookupCode4(self):
        self.assertEquals(ISO15924.Hani, iso15924ByCode4['Hani'])
