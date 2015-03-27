#!/usr/bin/python
# -*- coding: utf-8 -*-
#
# This data and information is proprietary to, and a valuable trade secret
# of, Basis Technology Corp.  It is given in confidence by Basis Technology
# and may only be used as permitted under the license agreement under which
# it has been distributed, and in no other way.
#
# Copyright (c) 2014 Basis Technology Corporation All rights reserved.
#
# The technical data and information provided herein are provided with
# `limited rights', and the computer software provided herein is provided
# with `restricted rights' as those terms are defined in DAR and ASPR
# 7-104.9(a).

iso15924ByCode4 = {}
""" A dictionary that retrieves ISO15924 objects by their 'char4' values."""

iso15924ByNumeric = {}
""" A dictionary that retrieves ISO15924 objects by their numeric values."""

class ISO15924():
    """Class containing constants for the ISO15924 system of script codes.

    code4: The ISO-15924 code4 value.
    numeric: The ISO-15924 numeric value.
    readable: The human-readable name.

    There is one attribute for each defined code, named after its CODE4 value.
    """
[% id = iter(xrange(len(iso15924definitions))) %][< for (iso15924definitions) >]
    [= code['char4'] =] = ("[= code['char4'] =]", [= int(code['numeric']) =], "[= code['name'] =]")[< end-for >];
    def __init__(self, code4, numeric, readable):
        self.code4 = code4
        self.numeric = numeric
        self.readable = readable
        iso15924ByCode4[code4] = self
        iso15924ByNumeric[numeric] = self

for (k, v) in list(vars(ISO15924).items()):
    if not k.startswith("__"):
        (code4, numeric, readable) = v
        setattr(ISO15924, k, ISO15924(code4, numeric, readable))
