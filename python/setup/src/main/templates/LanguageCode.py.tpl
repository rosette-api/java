#!/usr/bin/python
# -*- coding: utf-8 -*-
#
# This data and information is proprietary to, and a valuable trade secret
# of, Basis Technology Corp.  It is given in confidence by Basis Technology
# and may only be used as permitted under the license agreement under which
# it has been distributed, and in no other way.
#
# Copyright (c) 2012 Basis Technology Corporation All rights reserved.
#
# The technical data and information provided herein are provided with
# `limited rights', and the computer software provided herein is provided
# with `restricted rights' as those terms are defined in DAR and ASPR
# 7-104.9(a).

# Enumeration for a set of ISO 639-based language codes used in Basis products.

# LanguageCodes are based on the Feb 10, 2009 version of ISO 639-3. A LanguageCode is either
# standard, meaning that it is based on an ISO 639-3 language code, or nonstandard,
# meaning that it is a Basis extension.
# The nonstandard LanguageCodes are:

# UNKNOWN
# SIMPLIFIED_CHINESE
# TRADITIONAL_CHINESE
# ENGLISH_UPPERCASE

languageCodeByISO639_3 = {}
"""A dictionary that retrieves ISO-639 objects by their ISO-639-3 codes."""

languageCodeByISO639_1 = {}
"""A dictionary that retrieves ISO-639 objects by their ISO-639-1 codes."""

class LanguageCode():
    """Enum containing constants for languages.

    iso639_1: ISO-639-1 code for this language.
    iso639_3: ISO-639-3 (Feb 10, 2009) code for this language.
    readable: Human-readable name for this language.
    script: ISO15924 code for the most common script used to write this language.
    auxNames: A tuple of additional human-readable names for this language

    The class attributes are named after LanguageCodeItem symbols. These are convenient
    for filling in the PlainTextInputOptions. A LanguageCode is either
    standard, meaning that it is based on an ISO 639-3 language code, or nonstandard,
    meaning that it is a Basis extension.
    The nonstandard LanguageCodes are: UNKNOWN, SIMPLIFIED_CHINESE, TRADITIONAL_CHINESE, ENGLISH_UPPERCASE.
    """
    
[% id = iter(xrange(len(language_names))) %][< for (language_names) >][%
iso639_1 = language['iso639-1']
if "basis_iso639_1_suffix" in language:
    iso639_1 += language['basis_iso639_1_suffix']
declaration = ("    "
    + language['symbol']
                + " = (\"" + language['symbol']
                + "\", \""   + language['iso639-3']
                + "\", \"" + iso639_1
                + "\", \"" + language['name']
                + "\", \"" + language['script']
                + "\""
     )
declaration += ", ["
for field in ['aux_name0', 'aux_name1', 'aux_name2', 'aux_name3']:
    if field in language:
        if field != 'aux_name0':
            declaration += ","
        declaration += "\"" + language[field] + "\""
declaration += "]"
declaration += ")"
%]
[= declaration =][< end-for >]
    def __init__(self, symbol, iso639_1, iso639_3, readable, script, auxNames):
        self.symbol = symbol
        self.iso639_1 = iso639_1
        self.iso639_3 = iso639_3
        self.readable = readable
        self.script = script
        self.auxNames = auxNames
        languageCodeByISO639_3[iso639_3] = self
        languageCodeByISO639_1[iso639_1] = self

def languageCodeByISO639(code):
    """
    Lookup a LanguageCode object by its ISO-639 code; looks up as -3, then falls back to -1.
    """
    if code in languageCodeByISO639_3:
        return languageCodeByISO639_3[code]
    if code in languageCodeByISO639_1:
        return languageCodeByISO639_1[code]
    raise Exception("Undefined ISO639 code " + code)

for (k, v) in list(vars(LanguageCode).items()):
    if not k.startswith("__"):
        (a,b,c,d,e,f) = v
        setattr(LanguageCode, k, LanguageCode(a,b,c,d,e,f))
