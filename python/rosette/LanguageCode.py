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
    

    UNKNOWN = ("UNKNOWN", "xxx", "xx", "Unknown", "Zyyy", [])
    AFRIKAANS = ("AFRIKAANS", "afr", "af", "Afrikaans", "Latn", [])
    ALBANIAN = ("ALBANIAN", "sqi", "sq", "Albanian", "Latn", [])
    AMHARIC = ("AMHARIC", "amh", "am", "Amharic", "Ethi", [])
    ARABIC = ("ARABIC", "ara", "ar", "Arabic", "Arab", [])
    BENGALI = ("BENGALI", "ben", "bn", "Bengali", "Beng", [])
    BULGARIAN = ("BULGARIAN", "bul", "bg", "Bulgarian", "Cyrl", [])
    CATALAN = ("CATALAN", "cat", "ca", "Catalan", "Latn", ["Valencian"])
    CHINESE = ("CHINESE", "zho", "zh", "Chinese", "Hani", [])
    CROATIAN = ("CROATIAN", "hrv", "hr", "Croatian", "Latn", [])
    CZECH = ("CZECH", "ces", "cs", "Czech", "Latn", [])
    DANISH = ("DANISH", "dan", "da", "Danish", "Latn", [])
    DARI = ("DARI", "prs", "zz", "Dari", "Arab", ["Eastern Farsi"])
    DUTCH = ("DUTCH", "nld", "nl", "Dutch", "Latn", ["Flemish"])
    ENGLISH = ("ENGLISH", "eng", "en", "English", "Latn", [])
    ENGLISH_UPPERCASE = ("ENGLISH_UPPERCASE", "uen", "en_uc", "English Uppercase", "Latn", [])
    ESTONIAN = ("ESTONIAN", "est", "et", "Estonian", "Latn", [])
    FINNISH = ("FINNISH", "fin", "fi", "Finnish", "Latn", [])
    FRENCH = ("FRENCH", "fra", "fr", "French", "Latn", [])
    GERMAN = ("GERMAN", "deu", "de", "German", "Latn", [])
    GREEK = ("GREEK", "ell", "el", "Greek", "Grek", ["Modern Greek (1453-)"])
    GUJARATI = ("GUJARATI", "guj", "gu", "Gujarati", "Gujr", [])
    HEBREW = ("HEBREW", "heb", "he", "Hebrew", "Hebr", [])
    HINDI = ("HINDI", "hin", "hi", "Hindi", "Deva", [])
    HUNGARIAN = ("HUNGARIAN", "hun", "hu", "Hungarian", "Latn", [])
    ICELANDIC = ("ICELANDIC", "isl", "is", "Icelandic", "Latn", [])
    INDONESIAN = ("INDONESIAN", "ind", "id", "Indonesian", "Latn", [])
    ITALIAN = ("ITALIAN", "ita", "it", "Italian", "Latn", [])
    JAPANESE = ("JAPANESE", "jpn", "ja", "Japanese", "Hani", [])
    KANNADA = ("KANNADA", "kan", "kn", "Kannada", "Knda", [])
    KINYARWANDA = ("KINYARWANDA", "kin", "rw", "Kinyarwanda", "Latn", [])
    KOREAN = ("KOREAN", "kor", "ko", "Korean", "Hang", [])
    KURDISH = ("KURDISH", "kur", "ku", "Kurdish", "Arab", [])
    LATVIAN = ("LATVIAN", "lav", "lv", "Latvian", "Latn", [])
    LITHUANIAN = ("LITHUANIAN", "lit", "lt", "Lithuanian", "Latn", [])
    MACEDONIAN = ("MACEDONIAN", "mkd", "mk", "Macedonian", "Cyrl", [])
    MALAGASY = ("MALAGASY", "mlg", "mg", "Malagasy", "Latn", [])
    MALAY = ("MALAY", "msa", "ms", "Malay", "Latn", ["Malay (macrolanguage)"])
    MALAYALAM = ("MALAYALAM", "mal", "ml", "Malayalam", "Mlym", [])
    NORWEGIAN = ("NORWEGIAN", "nor", "no", "Norwegian", "Latn", [])
    NORWEGIAN_BOKMAL = ("NORWEGIAN_BOKMAL", "nob", "nb", "Norwegian Bokmal", "Latn", ["Norwegian Bokmål"])
    NORWEGIAN_NYNORSK = ("NORWEGIAN_NYNORSK", "nno", "nn", "Norwegian Nynorsk", "Latn", [])
    NYANJA = ("NYANJA", "nya", "ny", "Nyanja", "Latn", ["Chewa","Chichewa"])
    PEDI = ("PEDI", "nso", "zz", "Pedi", "Latn", ["Northern Sotho","Sepedi"])
    PERSIAN = ("PERSIAN", "fas", "fa", "Persian", "Arab", [])
    PLATEAU_MALAGASY = ("PLATEAU_MALAGASY", "plt", "zz", "Plateau Malagasy", "Latn", [])
    POLISH = ("POLISH", "pol", "pl", "Polish", "Latn", [])
    PORTUGUESE = ("PORTUGUESE", "por", "pt", "Portuguese", "Latn", [])
    PUSHTO = ("PUSHTO", "pus", "ps", "Pushto", "Arab", [])
    ROMANIAN = ("ROMANIAN", "ron", "ro", "Romanian", "Latn", ["Moldavian","Moldovan"])
    RUNDI = ("RUNDI", "run", "rn", "Rundi", "Latn", [])
    RUSSIAN = ("RUSSIAN", "rus", "ru", "Russian", "Cyrl", [])
    SANGO = ("SANGO", "sag", "sg", "Sango", "Latn", [])
    SERBIAN = ("SERBIAN", "srp", "sr", "Serbian", "Zyyy", [])
    SESELWA_CREOLE_FRENCH = ("SESELWA_CREOLE_FRENCH", "crs", "zz", "Seselwa Creole French", "Latn", [])
    SHONA = ("SHONA", "sna", "sn", "Shona", "Latn", [])
    SIMPLIFIED_CHINESE = ("SIMPLIFIED_CHINESE", "zhs", "zh_sc", "Chinese, Simplified", "Hans", [])
    SLOVAK = ("SLOVAK", "slk", "sk", "Slovak", "Latn", [])
    SLOVENIAN = ("SLOVENIAN", "slv", "sl", "Slovenian", "Latn", [])
    SOMALI = ("SOMALI", "som", "so", "Somali", "Latn", [])
    SOUTHERN_SOTHO = ("SOUTHERN_SOTHO", "sot", "st", "Southern Sotho", "Latn", [])
    SOUTH_NDEBELE = ("SOUTH_NDEBELE", "nbl", "nr", "South Ndebele", "Latn", [])
    SPANISH = ("SPANISH", "spa", "es", "Spanish", "Latn", ["Castilian"])
    SWAHILI = ("SWAHILI", "swa", "sw", "Swahili", "Latn", ["Swahili (macrolanguage)"])
    SWATI = ("SWATI", "ssw", "ss", "Swati", "Latn", [])
    SWEDISH = ("SWEDISH", "swe", "sv", "Swedish", "Latn", [])
    TAGALOG = ("TAGALOG", "tgl", "tl", "Tagalog", "Latn", [])
    TAMIL = ("TAMIL", "tam", "ta", "Tamil", "Taml", [])
    TELUGU = ("TELUGU", "tel", "te", "Telugu", "Telu", [])
    THAI = ("THAI", "tha", "th", "Thai", "Thai", [])
    TIGRINYA = ("TIGRINYA", "tir", "ti", "Tigrinya", "Ethi", [])
    TRADITIONAL_CHINESE = ("TRADITIONAL_CHINESE", "zht", "zh_tc", "Chinese, Traditional", "Hant", [])
    TSONGA = ("TSONGA", "tso", "ts", "Tsonga", "Latn", [])
    TSWANA = ("TSWANA", "tsn", "tn", "Tswana", "Latn", [])
    TURKISH = ("TURKISH", "tur", "tr", "Turkish", "Latn", [])
    UKRAINIAN = ("UKRAINIAN", "ukr", "uk", "Ukrainian", "Cyrl", [])
    URDU = ("URDU", "urd", "ur", "Urdu", "Arab", [])
    UZBEK = ("UZBEK", "uzb", "uz", "Uzbek", "Zyyy", [])
    VENDA = ("VENDA", "ven", "ve", "Venda", "Latn", [])
    VIETNAMESE = ("VIETNAMESE", "vie", "vi", "Vietnamese", "Latn", [])
    WESTERN_FARSI = ("WESTERN_FARSI", "pes", "zz", "Western Farsi", "Arab", [])
    XHOSA = ("XHOSA", "xho", "xh", "Xhosa", "Latn", [])
    ZULU = ("ZULU", "zul", "zu", "Zulu", "Latn", [])
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
