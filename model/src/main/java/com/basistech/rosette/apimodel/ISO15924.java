/**
 * ***************************************************************************
 * * Copyright (c) 2014-2015 Basis Technology Corporation.
 * *
 * * Licensed under the Apache License, Version 2.0 (the "License");
 * * you may not use this file except in compliance with the License.
 * * You may obtain a copy of the License at
 * *
 * *     http://www.apache.org/licenses/LICENSE-2.0
 * *
 * * Unless required by applicable law or agreed to in writing, software
 * * distributed under the License is distributed on an "AS IS" BASIS,
 * * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * * See the License for the specific language governing permissions and
 * * limitations under the License.
 * ****************************************************************************
 */

package com.basistech.rosette.apimodel;

/**
 * Java enumeration for the ISO15924 system of script codes. There is one enumerated item for each defined code,
 * named after its CODE4 value. Accessors deliver the numeric and English-name properties of each script.
 */
public enum ISO15924 {

    /** for "Afaka" */
    Afak(439, "Afaka"),
    /** for "Caucasian Albanian" */
    Aghb(239, "Caucasian Albanian"),
    /** for "Ahom, Tai Ahom" */
    Ahom(338, "Ahom, Tai Ahom"),
    /** for "Arabic" */
    Arab(160, "Arabic"),
    /** for "Imperial Aramaic" */
    Armi(124, "Imperial Aramaic"),
    /** for "Armenian" */
    Armn(230, "Armenian"),
    /** for "Avestan" */
    Avst(134, "Avestan"),
    /** for "Balinese" */
    Bali(360, "Balinese"),
    /** for "Bamum" */
    Bamu(435, "Bamum"),
    /** for "Bassa Vah" */
    Bass(259, "Bassa Vah"),
    /** for "Batak" */
    Batk(365, "Batak"),
    /** for "Bengali" */
    Beng(325, "Bengali"),
    /** for "Blissymbols" */
    Blis(550, "Blissymbols"),
    /** for "Bopomofo" */
    Bopo(285, "Bopomofo"),
    /** for "Brahmi" */
    Brah(300, "Brahmi"),
    /** for "Braille" */
    Brai(570, "Braille"),
    /** for "Buginese" */
    Bugi(367, "Buginese"),
    /** for "Buhid" */
    Buhd(372, "Buhid"),
    /** for "Chakma" */
    Cakm(349, "Chakma"),
    /** for "Unified Canadian Aboriginal Syllabics" */
    Cans(440, "Unified Canadian Aboriginal Syllabics"),
    /** for "Carian" */
    Cari(201, "Carian"),
    /** for "Cham" */
    Cham(358, "Cham"),
    /** for "Cherokee" */
    Cher(445, "Cherokee"),
    /** for "Cirth" */
    Cirt(291, "Cirth"),
    /** for "Coptic" */
    Copt(204, "Coptic"),
    /** for "Cypriot" */
    Cprt(403, "Cypriot"),
    /** for "Cyrillic" */
    Cyrl(220, "Cyrillic"),
    /** for "Cyrillic (Old Church Slavonic variant)" */
    Cyrs(221, "Cyrillic (Old Church Slavonic variant)"),
    /** for "Devanagari (Nagari)" */
    Deva(315, "Devanagari (Nagari)"),
    /** for "Deseret (Mormon)" */
    Dsrt(250, "Deseret (Mormon)"),
    /** for "Duployan shorthand, Duployan stenography" */
    Dupl(755, "Duployan shorthand, Duployan stenography"),
    /** for "Egyptian demotic" */
    Egyd(70, "Egyptian demotic"),
    /** for "Egyptian hieratic" */
    Egyh(60, "Egyptian hieratic"),
    /** for "Egyptian hieroglyphs" */
    Egyp(50, "Egyptian hieroglyphs"),
    /** for "Elbasan" */
    Elba(226, "Elbasan"),
    /** for "Ethiopic (Geʻez)" */
    Ethi(430, "Ethiopic (Geʻez)"),
    /** for "Georgian (Mkhedruli)" */
    Geor(240, "Georgian (Mkhedruli)"),
    /** for "Khutsuri (Asomtavruli and Nuskhuri)" */
    Geok(241, "Khutsuri (Asomtavruli and Nuskhuri)"),
    /** for "Glagolitic" */
    Glag(225, "Glagolitic"),
    /** for "Gothic" */
    Goth(206, "Gothic"),
    /** for "Grantha" */
    Gran(343, "Grantha"),
    /** for "Greek" */
    Grek(200, "Greek"),
    /** for "Gujarati" */
    Gujr(320, "Gujarati"),
    /** for "Gurmukhi" */
    Guru(310, "Gurmukhi"),
    /** for "Hangul (Hangŭl, Hangeul)" */
    Hang(286, "Hangul (Hangŭl, Hangeul)"),
    /** for "Han (Hanzi, Kanji, Hanja)" */
    Hani(500, "Han (Hanzi, Kanji, Hanja)"),
    /** for "Hanunoo (Hanunóo)" */
    Hano(371, "Hanunoo (Hanunóo)"),
    /** for "Han (Simplified variant)" */
    Hans(501, "Han (Simplified variant)"),
    /** for "Han (Traditional variant)" */
    Hant(502, "Han (Traditional variant)"),
    /** for "Hatran" */
    Hatr(127, "Hatran"),
    /** for "Hebrew" */
    Hebr(125, "Hebrew"),
    /** for "Hiragana" */
    Hira(410, "Hiragana"),
    /** for "Anatolian Hieroglyphs (Luwian Hieroglyphs, Hittite Hieroglyphs)" */
    Hluw(80, "Anatolian Hieroglyphs (Luwian Hieroglyphs, Hittite Hieroglyphs)"),
    /** for "Pahawh Hmong" */
    Hmng(450, "Pahawh Hmong"),
    /** for "Japanese syllabaries (alias for Hiragana + Katakana)" */
    Hrkt(412, "Japanese syllabaries (alias for Hiragana + Katakana)"),
    /** for "Old Hungarian (Hungarian Runic)" */
    Hung(176, "Old Hungarian (Hungarian Runic)"),
    /** for "Indus (Harappan)" */
    Inds(610, "Indus (Harappan)"),
    /** for "Old Italic (Etruscan, Oscan, etc.)" */
    Ital(210, "Old Italic (Etruscan, Oscan, etc.)"),
    /** for "Javanese" */
    Java(361, "Javanese"),
    /** for "Japanese (alias for Han + Hiragana + Katakana)" */
    Jpan(413, "Japanese (alias for Han + Hiragana + Katakana)"),
    /** for "Jurchen" */
    Jurc(510, "Jurchen"),
    /** for "Kayah Li" */
    Kali(357, "Kayah Li"),
    /** for "Katakana" */
    Kana(411, "Katakana"),
    /** for "Kharoshthi" */
    Khar(305, "Kharoshthi"),
    /** for "Khmer" */
    Khmr(355, "Khmer"),
    /** for "Khojki" */
    Khoj(322, "Khojki"),
    /** for "Kannada" */
    Knda(345, "Kannada"),
    /** for "Korean (alias for Hangul + Han)" */
    Kore(287, "Korean (alias for Hangul + Han)"),
    /** for "Kpelle" */
    Kpel(436, "Kpelle"),
    /** for "Kaithi" */
    Kthi(317, "Kaithi"),
    /** for "Tai Tham (Lanna)" */
    Lana(351, "Tai Tham (Lanna)"),
    /** for "Lao" */
    Laoo(356, "Lao"),
    /** for "Latin (Fraktur variant)" */
    Latf(217, "Latin (Fraktur variant)"),
    /** for "Latin (Gaelic variant)" */
    Latg(216, "Latin (Gaelic variant)"),
    /** for "Latin" */
    Latn(215, "Latin"),
    /** for "Lepcha (Róng)" */
    Lepc(335, "Lepcha (Róng)"),
    /** for "Limbu" */
    Limb(336, "Limbu"),
    /** for "Linear A" */
    Lina(400, "Linear A"),
    /** for "Linear B" */
    Linb(401, "Linear B"),
    /** for "Lisu (Fraser)" */
    Lisu(399, "Lisu (Fraser)"),
    /** for "Loma" */
    Loma(437, "Loma"),
    /** for "Lycian" */
    Lyci(202, "Lycian"),
    /** for "Lydian" */
    Lydi(116, "Lydian"),
    /** for "Mahajani" */
    Mahj(314, "Mahajani"),
    /** for "Mandaic, Mandaean" */
    Mand(140, "Mandaic, Mandaean"),
    /** for "Manichaean" */
    Mani(139, "Manichaean"),
    /** for "Mayan hieroglyphs" */
    Maya(90, "Mayan hieroglyphs"),
    /** for "Mende Kikakui" */
    Mend(438, "Mende Kikakui"),
    /** for "Meroitic Cursive" */
    Merc(101, "Meroitic Cursive"),
    /** for "Meroitic Hieroglyphs" */
    Mero(100, "Meroitic Hieroglyphs"),
    /** for "Malayalam" */
    Mlym(347, "Malayalam"),
    /** for "Modi, Moḍī" */
    Modi(323, "Modi, Moḍī"),
    /** for "Moon (Moon code, Moon script, Moon type)" */
    Moon(218, "Moon (Moon code, Moon script, Moon type)"),
    /** for "Mongolian" */
    Mong(145, "Mongolian"),
    /** for "Mro, Mru" */
    Mroo(199, "Mro, Mru"),
    /** for "Meitei Mayek (Meithei, Meetei)" */
    Mtei(337, "Meitei Mayek (Meithei, Meetei)"),
    /** for " Multani" */
    Mult(323, " Multani"),
    /** for "Myanmar (Burmese)" */
    Mymr(350, "Myanmar (Burmese)"),
    /** for "Old North Arabian (Ancient North Arabian)" */
    Narb(106, "Old North Arabian (Ancient North Arabian)"),
    /** for "Nabataean" */
    Nbat(159, "Nabataean"),
    /** for "Nakhi Geba ('Na-'Khi ²Ggŏ-¹baw, Naxi Geba)" */
    Nkgb(420, "Nakhi Geba ('Na-'Khi ²Ggŏ-¹baw, Naxi Geba)"),
    /** for "N’Ko" */
    Nkoo(165, "N’Ko"),
    /** for "Nüshu" */
    Nshu(499, "Nüshu"),
    /** for "Ogham" */
    Ogam(212, "Ogham"),
    /** for "Ol Chiki (Ol Cemet’, Ol, Santali)" */
    Olck(261, "Ol Chiki (Ol Cemet’, Ol, Santali)"),
    /** for "Old Turkic, Orkhon Runic" */
    Orkh(175, "Old Turkic, Orkhon Runic"),
    /** for "Oriya" */
    Orya(327, "Oriya"),
    /** for "Osmanya" */
    Osma(260, "Osmanya"),
    /** for "Palmyrene" */
    Palm(126, "Palmyrene"),
    /** for "Pau Cin Hau" */
    Pauc(263, "Pau Cin Hau"),
    /** for "Old Permic" */
    Perm(227, "Old Permic"),
    /** for "Phags-pa" */
    Phag(331, "Phags-pa"),
    /** for "Inscriptional Pahlavi" */
    Phli(131, "Inscriptional Pahlavi"),
    /** for "Psalter Pahlavi" */
    Phlp(132, "Psalter Pahlavi"),
    /** for "Book Pahlavi" */
    Phlv(133, "Book Pahlavi"),
    /** for "Phoenician" */
    Phnx(115, "Phoenician"),
    /** for "Miao (Pollard)" */
    Plrd(282, "Miao (Pollard)"),
    /** for "Inscriptional Parthian" */
    Prti(130, "Inscriptional Parthian"),
    /** for "Reserved for private use (start)" */
    Qaaa(900, "Reserved for private use (start)"),
    /** for "Reserved for private use (end)" */
    Qabx(949, "Reserved for private use (end)"),
    /** for "Rejang (Redjang, Kaganga)" */
    Rjng(363, "Rejang (Redjang, Kaganga)"),
    /** for "Rongorongo" */
    Roro(620, "Rongorongo"),
    /** for "Runic" */
    Runr(211, "Runic"),
    /** for "Samaritan" */
    Samr(123, "Samaritan"),
    /** for "Sarati" */
    Sara(292, "Sarati"),
    /** for "Old South Arabian" */
    Sarb(105, "Old South Arabian"),
    /** for "Saurashtra" */
    Saur(344, "Saurashtra"),
    /** for "SignWriting" */
    Sgnw(95, "SignWriting"),
    /** for "Shavian (Shaw)" */
    Shaw(281, "Shavian (Shaw)"),
    /** for "Sharada, Śāradā" */
    Shrd(319, "Sharada, Śāradā"),
    /** for "Siddham, Siddhaṃ, Siddhamātṛkā" */
    Sidd(302, "Siddham, Siddhaṃ, Siddhamātṛkā"),
    /** for "Khudawadi, Sindhi" */
    Sind(318, "Khudawadi, Sindhi"),
    /** for "Sinhala" */
    Sinh(348, "Sinhala"),
    /** for "Sora Sompeng" */
    Sora(398, "Sora Sompeng"),
    /** for "Sundanese" */
    Sund(362, "Sundanese"),
    /** for "Syloti Nagri" */
    Sylo(316, "Syloti Nagri"),
    /** for "Syriac" */
    Syrc(135, "Syriac"),
    /** for "Syriac (Estrangelo variant)" */
    Syre(138, "Syriac (Estrangelo variant)"),
    /** for "Syriac (Western variant)" */
    Syrj(137, "Syriac (Western variant)"),
    /** for "Syriac (Eastern variant)" */
    Syrn(136, "Syriac (Eastern variant)"),
    /** for "Tagbanwa" */
    Tagb(373, "Tagbanwa"),
    /** for "Takri, Ṭākrī, Ṭāṅkrī" */
    Takr(321, "Takri, Ṭākrī, Ṭāṅkrī"),
    /** for "Tai Le" */
    Tale(353, "Tai Le"),
    /** for "New Tai Lue" */
    Talu(354, "New Tai Lue"),
    /** for "Tamil" */
    Taml(346, "Tamil"),
    /** for "Tangut" */
    Tang(520, "Tangut"),
    /** for "Tai Viet" */
    Tavt(359, "Tai Viet"),
    /** for "Telugu" */
    Telu(340, "Telugu"),
    /** for "Tengwar" */
    Teng(290, "Tengwar"),
    /** for "Tifinagh (Berber)" */
    Tfng(120, "Tifinagh (Berber)"),
    /** for "Tagalog (Baybayin, Alibata)" */
    Tglg(370, "Tagalog (Baybayin, Alibata)"),
    /** for "Thaana" */
    Thaa(170, "Thaana"),
    /** for "Thai" */
    Thai(352, "Thai"),
    /** for "Tibetan" */
    Tibt(330, "Tibetan"),
    /** for "Tirhuta" */
    Tirh(326, "Tirhuta"),
    /** for "Ugaritic" */
    Ugar(40, "Ugaritic"),
    /** for "Vai" */
    Vaii(470, "Vai"),
    /** for "Visible Speech" */
    Visp(280, "Visible Speech"),
    /** for "Warang Citi (Varang Kshiti)" */
    Wara(262, "Warang Citi (Varang Kshiti)"),
    /** for "Woleai" */
    Wole(480, "Woleai"),
    /** for "Old Persian" */
    Xpeo(30, "Old Persian"),
    /** for "Cuneiform, Sumero-Akkadian" */
    Xsux(20, "Cuneiform, Sumero-Akkadian"),
    /** for "Yi" */
    Yiii(460, "Yi"),
    /** for "Code for inherited script" */
    Zinh(994, "Code for inherited script"),
    /** for "Mathematical notation" */
    Zmth(995, "Mathematical notation"),
    /** for "Symbols" */
    Zsym(996, "Symbols"),
    /** for "Code for unwritten documents" */
    Zxxx(997, "Code for unwritten documents"),
    /** for "Code for undetermined script" */
    Zyyy(998, "Code for undetermined script"),
    /** for "Code for uncoded script" */
    Zzzz(999, "Code for uncoded script");

    private final int numeric;
    private final String english;

    ISO15924(int numeric, String english) {
        this.numeric = numeric;
        this.english = english;
    }

    /**
     * Get the 4-character code for this script. This returns the same value as {@link #name()}.
     * @return the 4-character code for this script.
     */
    public String code4() {
        return name();
    }

    /**
     * Get the numeric code for this script.
     * @return the numeric code for this script.
     */
    public int numeric() {
        return this.numeric;
    }

    /**
     * Get the English name for this script.
     * @return the English name for this script.
     */
    public String englishName() {
        return english;
    }

    /**
     * Get the numeric code for this script.
     * @return the numeric code for this script.
     */
    int getNativeCode() {
        return numeric;
    }

    private static ISO15924[] staticValues = values();

    /**
     * Locate a script by native code.
     * @param nativeCode
     * @return ISO15924 object
     */
    static ISO15924 lookupByNativeCode(int nativeCode) {
        for (ISO15924 v : staticValues) {
            if (v.getNativeCode() == nativeCode)
                return v;
        }
        throw new RuntimeException("Invalid ISO15924 native code " + nativeCode);
    }

    /**
     * Locate a script by numeric code value.
     * @param numeric the numeric value.
     * @return the enumerated value.
     */
    public static ISO15924 lookupByNumeric(int numeric) {
        return lookupByNativeCode(numeric);
    }

    /**
     * Locate a script by 4-character code value.
     * @param code4 the 4-character code value.
     * @return the enumerated value.
     */
    public static ISO15924 lookupByCode4(String code4) {
        for (ISO15924 v : staticValues) {
            if (code4.equals(v.toString()))
                return v;
        }
        return null;
    }
}
