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

    Arab = ("Arab", 160, "Arabic")
    Armn = ("Armn", 230, "Armenian")
    Avst = ("Avst", 134, "Avestan")
    Bali = ("Bali", 360, "Balinese")
    Batk = ("Batk", 365, "Batak")
    Beng = ("Beng", 325, "Bengali")
    Blis = ("Blis", 550, "Blissymbols")
    Bopo = ("Bopo", 285, "Bopomofo")
    Brah = ("Brah", 300, "Brahmi")
    Brai = ("Brai", 570, "Braille")
    Bugi = ("Bugi", 367, "Buginese")
    Buhd = ("Buhd", 372, "Buhid")
    Cans = ("Cans", 440, "Unified Canadian Aboriginal Syllabics")
    Cari = ("Cari", 201, "Carian")
    Cham = ("Cham", 358, "Cham")
    Cher = ("Cher", 445, "Cherokee")
    Cirt = ("Cirt", 291, "Cirth")
    Copt = ("Copt", 204, "Coptic")
    Cprt = ("Cprt", 403, "Cypriot")
    Cyrl = ("Cyrl", 220, "Cyrillic")
    Cyrs = ("Cyrs", 221, "Cyrillic (Old Church Slavonic variant)")
    Deva = ("Deva", 315, "Devanagari (Nagari)")
    Dsrt = ("Dsrt", 250, "Deseret (Mormon)")
    Egyd = ("Egyd", 70, "Egyptian demotic")
    Egyh = ("Egyh", 60, "Egyptian hieratic")
    Egyp = ("Egyp", 50, "Egyptian hieroglyphs")
    Ethi = ("Ethi", 430, "Ethiopic (Ge'ez)")
    Geor = ("Geor", 240, "Georgian (Mkhedruli)")
    Geok = ("Geok", 241, "Khutsuri (Asomtavruli and Nuskhuri)")
    Glag = ("Glag", 225, "Glagolitic")
    Goth = ("Goth", 206, "Gothic")
    Grek = ("Grek", 200, "Greek")
    Gujr = ("Gujr", 320, "Gujarati")
    Guru = ("Guru", 310, "Gurmukhi")
    Hang = ("Hang", 286, "Hangul (Hangeul, Hankul)")
    Hani = ("Hani", 500, "Han (Hanzi, Kanji, Hanja)")
    Hano = ("Hano", 371, "Hanunoo")
    Hans = ("Hans", 501, "Han (Simplified variant)")
    Hant = ("Hant", 502, "Han (Traditional variant)")
    Hebr = ("Hebr", 125, "Hebrew")
    Hira = ("Hira", 410, "Hiragana")
    Hmng = ("Hmng", 450, "Pahawh Hmong")
    Hrkt = ("Hrkt", 412, "(alias for Hiragana + Katakana)")
    Hung = ("Hung", 176, "Old Hungarian")
    Inds = ("Inds", 610, "Indus (Harappan)")
    Ital = ("Ital", 210, "Old Italic (Etruscan, Oscan, etc.)")
    Java = ("Java", 361, "Javanese")
    Jpan = ("Jpan", 413, "Japanese (alias for Han + Hiragana + Katakana)")
    Kali = ("Kali", 357, "Kayah Li")
    Kana = ("Kana", 411, "Katakana")
    Khar = ("Khar", 305, "Kharoshthi")
    Khmr = ("Khmr", 355, "Khmer")
    Knda = ("Knda", 345, "Kannada")
    Kore = ("Kore", 287, "Korean (alias for Hangul + Han)")
    Lana = ("Lana", 351, "Lanna")
    Laoo = ("Laoo", 356, "Lao")
    Latf = ("Latf", 217, "Latin (Fraktur variant)")
    Latg = ("Latg", 216, "Latin (Gaelic variant)")
    Latn = ("Latn", 215, "Latin")
    Lepc = ("Lepc", 335, "Lepcha (Rong)")
    Limb = ("Limb", 336, "Limbu")
    Lina = ("Lina", 400, "Linear A")
    Linb = ("Linb", 401, "Linear B")
    Lyci = ("Lyci", 202, "Lycian")
    Lydi = ("Lydi", 116, "Lydian")
    Mand = ("Mand", 140, "Mandaic, Mandaean")
    Mani = ("Mani", 139, "Manichaean")
    Maya = ("Maya", 90, "Mayan hieroglyphs")
    Mero = ("Mero", 100, "Meroitic")
    Mlym = ("Mlym", 347, "Malayalam")
    Moon = ("Moon", 218, "Moon (Moon code, Moon script, Moon type)")
    Mong = ("Mong", 145, "Mongolian")
    Mtei = ("Mtei", 337, "Meitei Mayek (Meithei, Meetei)")
    Mymr = ("Mymr", 350, "Myanmar (Burmese)")
    Nkoo = ("Nkoo", 165, "N'Ko")
    Ogam = ("Ogam", 212, "Ogham")
    Olck = ("Olck", 261, "Ol Chiki (Ol Cemet', Ol, Santali)")
    Orkh = ("Orkh", 175, "Orkhon")
    Orya = ("Orya", 327, "Oriya")
    Osma = ("Osma", 260, "Osmanya")
    Perm = ("Perm", 227, "Old Permic")
    Phag = ("Phag", 331, "Phags-pa")
    Phlv = ("Phlv", 133, "Book Pahlavi")
    Phnx = ("Phnx", 115, "Phoenician")
    Plrd = ("Plrd", 282, "Pollard Phonetic")
    Qaaa = ("Qaaa", 900, "Reserved for private use (start)")
    Qabx = ("Qabx", 949, "Reserved for private use (end)")
    Rjng = ("Rjng", 363, "Rejang, Redjang, Kaganga")
    Roro = ("Roro", 620, "Rongorongo")
    Runr = ("Runr", 211, "Runic")
    Samr = ("Samr", 123, "Samaritan")
    Sara = ("Sara", 292, "Sarati")
    Saur = ("Saur", 344, "Saurashtra")
    Sgnw = ("Sgnw", 95, "SignWriting")
    Shaw = ("Shaw", 281, "Shavian (Shaw)")
    Sinh = ("Sinh", 348, "Sinhala")
    Sund = ("Sund", 362, "Sundanese")
    Sylo = ("Sylo", 316, "Syloti Nagri")
    Syrc = ("Syrc", 135, "Syriac")
    Syre = ("Syre", 138, "Syriac (Estrangelo variant)")
    Syrj = ("Syrj", 137, "Syriac (Western variant)")
    Syrn = ("Syrn", 136, "Syriac (Eastern variant)")
    Tagb = ("Tagb", 373, "Tagbanwa")
    Tale = ("Tale", 353, "Tai Le")
    Talu = ("Talu", 354, "New Tai Lue")
    Taml = ("Taml", 346, "Tamil")
    Telu = ("Telu", 340, "Telugu")
    Teng = ("Teng", 290, "Tengwar")
    Tfng = ("Tfng", 120, "Tifinagh (Berber)")
    Tglg = ("Tglg", 370, "Tagalog")
    Thaa = ("Thaa", 170, "Thaana")
    Thai = ("Thai", 352, "Thai")
    Tibt = ("Tibt", 330, "Tibetan")
    Ugar = ("Ugar", 40, "Ugaritic")
    Vaii = ("Vaii", 470, "Vai")
    Visp = ("Visp", 280, "Visible Speech")
    Xpeo = ("Xpeo", 30, "Old Persian")
    Xsux = ("Xsux", 20, "Cuneiform, Sumero-Akkadian")
    Yiii = ("Yiii", 460, "Yi")
    Zxxx = ("Zxxx", 997, "Code for unwritten documents")
    Zyyy = ("Zyyy", 998, "Code for undetermined script")
    Zzzz = ("Zzzz", 999, "Code for uncoded script");
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
