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

import java.util.EnumSet;

/**
 * <code>LanguageCodes</code> are based on the Feb 10, 2009 version of ISO 639-3.  A <code>LanguageCode</code> is either
 * standard, meaning that it is based on an ISO 639-3 language code, or nonstandard, meaning that it is a Basis extension.
 * The nonstandard <code>LanguageCodes</code> are:
 *
 * <ul>
 * <li>UNKNOWN</li>
 * <li>SIMPLIFIED_CHINESE</li>
 * <li>TRADITIONAL_CHINESE</li>
 * <li>ENGLISH_UPPERCASE</li>
 * </ul>
 *
 */
public enum LanguageCode {

    xxx("UNKNOWN"),
    afr("AFRIKAANS"),
    sqi("ALBANIAN"),
    amh("AMHARIC"),
    ara("ARABIC"),
    ben("BENGALI"),
    bul("BULGARIAN"),
    cat("CATALAN"),
    zho("CHINESE"),
    hrv("CROATIAN"),
    ces("CZECH"),
    dan("DANISH"),
    prs("DARI"),
    nld("DUTCH"),
    eng("ENGLISH"),
    uen("ENGLISH_UPPERCASE"),
    est("ESTONIAN"),
    fin("FINNISH"),
    fra("FRENCH"),
    deu("GERMAN"),
    ell("GREEK"),
    guj("GUJARATI"),
    heb("HEBREW"),
    hin("HINDI"),
    hun("HUNGARIAN"),
    isl("ICELANDIC"),
    ind("INDONESIAN"),
    ita("ITALIAN"),
    jpn("JAPANESE"),
    kan("KANNADA"),
    kin("KINYARWANDA"),
    kor("KOREAN"),
    kur("KURDISH"),
    lav("LATVIAN"),
    lit("LITHUANIAN"),
    mkd("MACEDONIAN"),
    mlg("MALAGASY"),
    msa("MALAY"),
    mal("MALAYALAM"),
    nor("NORWEGIAN"),
    nob("NORWEGIAN_BOKMAL"),
    nno("NORWEGIAN_NYNORSK"),
    nya("NYANJA"),
    nso("PEDI"),
    fas("PERSIAN"),
    plt("PLATEAU_MALAGASY"),
    pol("POLISH"),
    por("PORTUGUESE"),
    pus("PUSHTO"),
    ron("ROMANIAN"),
    run("RUNDI"),
    rus("RUSSIAN"),
    sag("SANGO"),
    srp("SERBIAN"),
    crs("SESELWA_CREOLE_FRENCH"),
    sna("SHONA"),
    zhs("SIMPLIFIED_CHINESE"),
    slk("SLOVAK"),
    slv("SLOVENIAN"),
    som("SOMALI"),
    sot("SOUTHERN_SOTHO"),
    nbl("SOUTH_NDEBELE"),
    spa("SPANISH"),
    swa("SWAHILI"),
    ssw("SWATI"),
    swe("SWEDISH"),
    tgl("TAGALOG"),
    tam("TAMIL"),
    tel("TELUGU"),
    tha("THAI"),
    tir("TIGRINYA"),
    zht("TRADITIONAL_CHINESE"),
    tso("TSONGA"),
    tsn("TSWANA"),
    tur("TURKISH"),
    ukr("UKRAINIAN"),
    urd("URDU"),
    uzb("UZBEK"),
    ven("VENDA"),
    vie("VIETNAMESE"),
    pes("WESTERN_FARSI"),
    xho("XHOSA"),
    zul("ZULU");

    private String label;

    /**
     * constructor for {@code LanguageCode} which sets a label for reference
     */
    LanguageCode(String label) {
        this.label = label;
    }

    /**
     * get the label
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * set the label
     * @param label the label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * checks if value is a valid {@code LanguageCode} enum
     * @param value input value
     * @return {@code LanguageCode corresponding to input value}
     * @throws IllegalArgumentException
     */
    public static LanguageCode forValue(String value) throws IllegalArgumentException {
        for (LanguageCode model : EnumSet.allOf(LanguageCode.class)) {
            if (model.getLabel().equalsIgnoreCase(value)) {
                return model;
            }
        }
        throw new IllegalArgumentException("invalid language code: " + value);
    }

    /**
     * get the label
     * @return the label
     */
    public String toValue() {
        return label;
    }
}
