/******************************************************************************
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2015 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
 ******************************************************************************/

package com.basistech.rosette.model;

/**
 * Class containing data common to RaaS client requests
 */
public abstract class Request {

    private String language;
    private String content;
    private String contentUri;
    private byte[] contentBytes;
    private String contentType;
    private String unit;
    
    protected Request() { }

    /**
     * Constructor for {@code Request}
     * Fields for the three ways content can come in:
     * 1. a "content" raw data
     * 2. a "contentUri" pointing to the data
     * 3. a "contentBytes" byte array for cases where data comes to us raw as an attachment
     * @param language language code
     * @param content raw data
     * @param contentUri uri pointing to the data
     * @param contentType byte array of data
     * @param unit input unit code
     */
    protected Request(
            String language,
            String content,
            String contentUri,
            String contentType,
            InputUnit unit
    ) {
        this.language = language;
        this.content = content;
        this.contentUri = contentUri;
        this.contentType = contentType;
        if (unit == null) {
            this.unit = InputUnit.DOC.toValue();
        } else {
            this.unit = unit.toValue();
        }
    }

    /**
     * get the language code (ignored for /language endpoint)
     * @return the language code
     */
    public String getLanguage() {
        return language;
    }

    /**
     * string input to process (JSON string or base64 encoding of non-JSON string) 
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * set the attached content bytes and content type
     * @param contentBytes the content bytes
     * @param contentType the content type
     */
    public void setAttachedContent(byte[] contentBytes, String contentType) {
        this.contentBytes = contentBytes;
        this.contentType = contentType;
    }

    /**
     * get the content as an array of bytes 
     * @return the content as bytes
     */
    public byte[] getContentBytes() {
        return contentBytes;
    }

    /**
     * get the URI to accessible content (content and contentURI are mutually exlcusive) 
     * @return the content URI
     */
    public String getContentUri() { return contentUri; }
    
    public String getContentType() {
        return contentType;
    }

    /**
     * get the {@code InputUnit} code
     * @see InputUnit
     * @return the InputUnit code
     */
    public String getUnit() {
        return unit;
    }
}
