/******************************************************************************
 ** Copyright (c) 2014-2015 Basis Technology Corporation.
 **
 ** Licensed under the Apache License, Version 2.0 (the "License");
 ** you may not use this file except in compliance with the License.
 ** You may obtain a copy of the License at
 **
 **     http://www.apache.org/licenses/LICENSE-2.0
 **
 ** Unless required by applicable law or agreed to in writing, software
 ** distributed under the License is distributed on an "AS IS" BASIS,
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ** See the License for the specific language governing permissions and
 ** limitations under the License.
 ******************************************************************************/

package com.basistech.rosette.apimodel;

import java.util.HashMap;

/**
 * Named constants for the numeric codes for BT_Xlit_Scheme.
 * NOTE: see the documentation of specific Basis products to see which schemes they support.
 */
public enum TransliterationScheme {
    //CHECKSTYLE:OFF
    /** xlit scheme code for FBIS
     description: US Foreign Broadcast Information Service */
    FBIS (0, "fbis", "FBIS",
            "US Foreign Broadcast Information Service"),

    /** xlit scheme code for BGN
     description: US Board on Geographic Names */
    BGN (1, "bgn", "BGN",
            "US Board on Geographic Names"),

    /** xlit scheme code for Basis
     description: Basis Technology Corp. */
    BASIS (2, "basis", "Basis",
            "Basis Technology Corp."),

    /** xlit scheme code for IC
     description: US Intelligence Community */
    IC (3, "ic", "IC",
            "US Intelligence Community"),

    /** xlit scheme code for SATTS
     description: Standard Arabic Technical Transliteration System */
    SATTS (4, "satts", "SATTS",
            "Standard Arabic Technical Transliteration System"),

    /** xlit scheme code for Buckwalter
     description: Tim Buckwalter/Qamus */
    BUCKWALTER (5, "buckwalter", "Buckwalter",
            "Tim Buckwalter/Qamus"),

    /** xlit scheme code for UNRSGN
     description: United Nations Romanization System for Geographic Names */
    UNRSGN (6, "unrsgn", "UNRSGN",
            "United Nations Romanization System for Geographic Names"),

    /** xlit scheme code for Library of Congress
     description: American Library Association/Library of Congress Transliteration */
    ALA_LC (7, "ala_lc", "Library of Congress",
            "American Library Association/Library of Congress Transliteration"),

    /** xlit scheme code for I.G.N. System 1973
     description: Also called Variant B of the Amended Beirut System */
    IGN (8, "ign", "I.G.N. System 1973",
            "Also called Variant B of the Amended Beirut System"),

    /** xlit scheme code for Royal Jordanian Geographic Centre System
     description: Essentially the same as the amended Beirut system. The sub-macron is used instead of the cedilla. */
    RJGC (9, "rjgc", "Royal Jordanian Geographic Centre System",
            "Essentially the same as the amended Beirut system. The sub-macron is used instead of the cedilla."),

    /** xlit scheme code for ISO 233:1984
     description: Gives every character and diacritical mark a unique equivalent. */
    ISO233_1984 (10, "iso233_1984", "ISO 233:1984",
            "Gives every character and diacritical mark a unique equivalent."),

    /** xlit scheme code for Survey of Egypt System
     description: Survey of Egypt System. */
    SES (11, "ses", "Survey of Egypt System",
            "Survey of Egypt System."),

    /** xlit scheme code for Deutsche Morgenlandishe Gesellschaft
     description:  */
    DMG (12, "dmg", "Deutsche Morgenlandishe Gesellschaft",
            ""),

    /** xlit scheme code for Encyclopedia of Islam
     description:  */
    EI (13, "ei", "Encyclopedia of Islam",
            ""),

    /** xlit scheme code for Kohanimeandmebaas
     description:  */
    KNAB (14, "knab", "Kohanimeandmebaas",
            ""),

    /** xlit scheme code for Allworth
     description:  */
    ALLWORTH (15, "allworth", "Allworth",
            ""),

    /** xlit scheme code for ISO 9:1995
     description:  */
    ISO9_1995 (16, "iso9_1995", "ISO 9:1995",
            ""),

    /** xlit scheme code for The World's Writing Systems
     description:  */
    WWS (17, "wws", "The World's Writing Systems",
            ""),

    /** xlit scheme code for JDEC - Afghanistan
     description: Joint Document Exploitation Center - Afghanistan */
    JDEC_AF (18, "jdec_af", "JDEC - Afghanistan",
            "Joint Document Exploitation Center - Afghanistan"),

    /** xlit scheme code for MELTS
     description: Middle East Languages Transliteration Standard */
    MELTS (19, "melts", "MELTS",
            "Middle East Languages Transliteration Standard"),

    /** xlit scheme code for ISO 233-2:1993
     description: Establishes a simplified system vs the stringent rules established by ISO 233:1984 for Arabic. */
    ISO233_2_1993 (20, "iso233_2_1993", "ISO 233-2:1993",
            "Establishes a simplified system vs the stringent rules established by ISO 233:1984 for Arabic."),

    /** xlit scheme code for ISO 233-3:1999
     description: ISO 233 Part 3, for Persian */
    ISO233_3_1999 (21, "iso233_3_1999", "ISO 233-3:1999",
            "ISO 233 Part 3, for Persian"),

    /** xlit scheme code for ASCII-limited Transliteration Scheme used in Wikipedia
     description: ASCII restricted; possibly a variation on more formal transliteration schemes. */
    WIKIPEDIA_ASCII (22, "wikipedia_ascii", "ASCII-limited Transliteration Scheme used in Wikipedia",
            "ASCII restricted; possibly a variation on more formal transliteration schemes."),

    /** xlit scheme code for ASCII subset of Latin Script
     description: Custom scheme to Basis for transliterating characters using a broader portion of the Latin Script to solely ASCII, based on removing diacritics and using the closest letter visually. */
    ASCII (99, "ascii", "ASCII subset of Latin Script",
            "Custom scheme to Basis for transliterating characters using a broader portion of the Latin Script to solely ASCII, based on removing diacritics and using the closest letter visually."),

    /** xlit scheme code for IC (+BGN for places)
     description: IC for everything but places, BGN for those */
    IC_BGN (100, "ic_bgn", "IC (+BGN for places)",
            "IC for everything but places, BGN for those"),

    /** xlit scheme code for Undiacritized BGN
     description: US Board on Geographic Names - diacritics removed */
    UND_BGN (101, "und_bgn", "Undiacritized BGN",
            "US Board on Geographic Names - diacritics removed"),

    /** xlit scheme code for Extended IC
     description: US Intelligence Community - extended */
    EXT_IC (102, "ext_ic", "Extended IC",
            "US Intelligence Community - extended"),

    /** xlit scheme code for Basis Diacritization
     description: Diacritization according to a private-use standard by Basis Technology Corp. */
    BASIS_DIA (199, "basis_dia", "Basis Diacritization",
            "Diacritization according to a private-use standard by Basis Technology Corp."),

    /** xlit scheme code for KORDA
     description: Korean Romanization for Data Applications */
    KORDA (200, "korda", "KORDA",
            "Korean Romanization for Data Applications"),

    /** xlit scheme code for Morse
     description: Morse */
    MORSE (201, "morse", "Morse",
            "Morse"),

    /** xlit scheme code for Yale
     description: Yale */
    YALE (202, "yale", "Yale",
            "Yale"),

    /** xlit scheme code for McCune-Reischauer
     description: McCune-Reischauer */
    MCR (203, "mcr", "McCune-Reischauer",
            "McCune-Reischauer"),

    /** xlit scheme code for Lukoff
     description: Lukoff */
    LUKOFF (204, "lukoff", "Lukoff",
            "Lukoff"),

    /** xlit scheme code for 2959-SK
     description: 2959-SK, Developed by South Korea's Ministry of Education */
    SK (205, "sk", "2959-SK",
            "2959-SK, Developed by South Korea's Ministry of Education"),

    /** xlit scheme code for Joseon Gwahagwon
     description: Joseon Gwahagwon */
    JG (206, "jg", "Joseon Gwahagwon",
            "Joseon Gwahagwon"),

    /** xlit scheme code for Revised Romanization of Korean
     description: 2000 Revised Romanization of Korean by Ministry of Culture and Tourism */
    MOCT (207, "moct", "Revised Romanization of Korean",
            "2000 Revised Romanization of Korean by Ministry of Culture and Tourism"),

    /** xlit scheme code for Pinyin
     description: Hanyu Pinyin (without tone annotations) */
    HYPY (300, "hypy", "Pinyin",
            "Hanyu Pinyin (without tone annotations)"),

    /** xlit scheme code for Hanyu Pinyin Toned
     description: Hanyu Pinyin with tone annotations */
    HYPY_TONED (301, "hypy_toned", "Hanyu Pinyin Toned",
            "Hanyu Pinyin with tone annotations"),

    /** xlit scheme code for Wade-Giles
     description: Wade-Giles */
    WADE_GILES (302, "wade_giles", "Wade-Giles",
            "Wade-Giles"),

    /** xlit scheme code for Tongyong Pinyin
     description: Tongyong  Pinyin (without tone annotations) */
    TYPY (303, "typy", "Tongyong Pinyin",
            "Tongyong  Pinyin (without tone annotations)"),

    /** xlit scheme code for Tongyong Pinyin Toned
     description: Tongyong Pinyin with tone annotations */
    TYPY_TONED (304, "typy_toned", "Tongyong Pinyin Toned",
            "Tongyong Pinyin with tone annotations"),

    /** xlit scheme code for Zongwen (Tibetan) Pinyin
     description: Zongwen (Tibetan) Pinyin */
    ZWPY (305, "zwpy", "Zongwen (Tibetan) Pinyin",
            "Zongwen (Tibetan) Pinyin"),

    /** xlit scheme code for Cantonese Pinyin
     description: Standard Cantonese Pinyin */
    CTPY (306, "ctpy", "Cantonese Pinyin",
            "Standard Cantonese Pinyin"),

    /** xlit scheme code for POJ
     description: Peh-oe-ji (POJ) */
    POJ (307, "poj", "POJ",
            "Peh-oe-ji (POJ)"),

    /** xlit scheme code for Guangdong Romanization
     description: Guangdong Romanization */
    GAUNGDONG (308, "gaungdong", "Guangdong Romanization",
            "Guangdong Romanization"),

    /** xlit scheme code for Chinese Telegraph Code
     description: Chinese Telegraph Code */
    CTC (309, "ctc", "Chinese Telegraph Code",
            "Chinese Telegraph Code"),

    /** xlit scheme code for ISO :11940
     description: Romanization of the Thai */
    ISO_11940 (310, "iso_11940", "ISO :11940",
            "Romanization of the Thai"),

    /** xlit scheme code for Hebon Romaji
     description: Hebon-shiki Romaji (or Hepburn) scheme for Japanese */
    HEBON (400, "hebon", "Hebon Romaji",
            "Hebon-shiki Romaji (or Hepburn) scheme for Japanese"),

    /** xlit scheme code for Kunrei-shiki Romaji
     description: Kunrei-shiki Romaji scheme for Japanese */
    KUNREI (401, "kunrei", "Kunrei-shiki Romaji",
            "Kunrei-shiki Romaji scheme for Japanese"),

    /** xlit scheme code for ISCII
     description: Indian Script Code for Information Interchange */
    ISCII (500, "iscii", "ISCII",
            "Indian Script Code for Information Interchange"),

    /** xlit scheme code for PASCII
     description: Perso-Arabic Script Code for Information Interchange */
    PASCII (501, "pascii", "PASCII",
            "Perso-Arabic Script Code for Information Interchange"),

    /** xlit scheme code for ArabTeX
     description:  */
    ARABTEX (502, "arabtex", "ArabTeX",
            ""),

    /** xlit scheme code for Linguistic Survey of India
     description:  */
    LSI (503, "lsi", "Linguistic Survey of India",
            ""),

    /** xlit scheme code for National Cartography Center (NCC) of Iran
     description: The Transliteration/Transcription system of the National Cartography Center (NCC) of Iran. */
    NCC (600, "ncc", "National Cartography Center (NCC) of Iran",
            "The Transliteration/Transcription system of the National Cartography Center (NCC) of Iran."),

    /** xlit scheme code for Folk
     description: Folk */
    FOLK (1997, "folk", "Folk",
            "Folk"),

    /** xlit scheme code for Unknown
     description: Unknown */
    UNKNOWN (1998, "unknown", "Unknown",
            "Unknown"),

    /** xlit scheme code for Native
     description: Native */
    NATIVE (1999, "native", "Native",
            "Native");

    private final int BT_Xlit_Scheme;
    private final String name;
    private final String presentationName;
    private final String description;


    /*
     * We employ a variation of "Initialization on demand holder" idiom
     * http://en.wikipedia.org/wiki/Initialization_on_demand_holder_idiom
     * to implement lazy initialization of the two HashMaps we use.
     * The following two small private static classes are for this trick.
     */
    private static class CatalogByBTXlitScheme{
        private static final HashMap<Integer, TransliterationScheme> it;
        static{
            it = new HashMap<Integer, TransliterationScheme>();
            for (TransliterationScheme t : values()) {
                it.put(t.BT_Xlit_Scheme, t);
            }
        }
    }

    private static class CatalogByName{
        private static HashMap<String, TransliterationScheme> it;
        static{
            it = new HashMap<String, TransliterationScheme>();
            for (TransliterationScheme t : values()) {
                it.put(t.name, t);
            }
        }
    }

    private TransliterationScheme(int BT_Xlit_Scheme, String name, String presentationName,
                                  String description){
        this.BT_Xlit_Scheme = BT_Xlit_Scheme;
        this.name = name;
        this.presentationName = presentationName;
        this.description = description;
    }

    // want these to not be in the .tpl
    // might like these next few to only be Basis accessible (how?)
    /**
     * Retrieves a static TranslitearationScheme object of the given id.
     * @param BT_Xlit_Scheme numeric id of the scheme to get.
     * @return TransliterationScheme object corresponding to the param.
     */
    public static TransliterationScheme getObjectByBT_Xlit_Scheme(int BT_Xlit_Scheme) {

        TransliterationScheme ts = CatalogByBTXlitScheme.it.get(BT_Xlit_Scheme);
        if(ts == null)
            throw new IllegalArgumentException("Unknown BT_Xlit_Scheme code: "+BT_Xlit_Scheme);
        return ts;
    }

    static TransliterationScheme lookupByNativeCode(int nativeCode) {
        return getObjectByBT_Xlit_Scheme(nativeCode);
    }

    /**
     * Returns the numeric code (C++ BT_Xlit_Scheme) for this transliteration scheme.
     * @return Numeric code
     **/

    public int getNativeCode() {
        return BT_Xlit_Scheme;
    }

    /**
     * Retrieves a static TransliterationScheme object of the given name.
     * @param name Name of the scheme to retrieve.
     * @return TransliterationScheme object corresponding to the param.
     */
    public static TransliterationScheme getObjectByName(String name) {

        TransliterationScheme ts = CatalogByName.it.get(name);
        if(ts == null)
            throw new IllegalArgumentException("Unknown TransliterationScheme name: "+name);
        return ts;
    }

    /**
     * Gets the internally recognized name, such as what might be passed into NameTranslator.translateName or would be used to create a new TransliterationScheme
     * @return Internal name for the transliteration scheme
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the name as should be presented visibly to a user
     * @return Transliteration scheme as should be viewed on a form or in output
     */
    public String getPresentationName(){
        return presentationName;
    }

    /**
     * Gets the description of the transliteration scheme, which generally would be displayed, for example, on a form when a particular scheme is chosen from a dropdown box
     * @return Short description of this particular scheme
     */
    public String getDescription(){
        return description;
    }
}
