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

package com.basistech.rosette.apimodel;

import java.util.Arrays;

/**
 * Class containing data common to RaaS client requests
 */
public abstract class Request {

    private String language;
    private String content;
    private String contentUri;
    private byte[] contentBytes;
    private String contentType;
    private InputUnit unit;
    
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
            this.unit = InputUnit.doc;
        } else {
            this.unit = unit;
        }
    }

    /**
     * get the language code
     * @return the language code
     */
    public String getLanguage() {
        return language;
    }

    /**
     * get content to process (JSON string or base64 encoding of non-JSON string) 
     * @return the content
     */
    public String getContent() {
        return content;
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

    /**
     * get the content type 
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * get the {@code InputUnit} code
     * @see InputUnit
     * @return the {@code InputUnit} code
     */
    public InputUnit getUnit() {
        return unit;
    }

    /**
     * set the language code 
     * @param language the language code
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * set content to process (JSON string or base64 encoding of non-JSON string)
     * @param content the content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * set the URI to accessible content (content and contentURI are mutually exlcusive) 
     * @param contentUri the content URI
     */
    public void setContentUri(String contentUri) {
        this.contentUri = contentUri;
    }

    /**
     * set the content as an array of bytes 
     * @param contentBytes the content as bytes
     */
    public void setContentBytes(byte[] contentBytes) {
        this.contentBytes = contentBytes;
    }

    /**
     * set the content type
     * @param contentType the content type
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * set the {@code InputUnit} code 
     * @see InputUnit
     * @param unit the {@code InputUnit} code
     */
    public void setUnit(InputUnit unit) {
        this.unit = unit;
    }

    @Override
    public int hashCode() {
        int result = language != null ? language.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (contentUri != null ? contentUri.hashCode() : 0);
        result = 31 * result + (contentBytes != null ? Arrays.hashCode(contentBytes) : 0);
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code Request}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Request) {
            Request that = (Request) o;
            return language != null ? language.equals(that.getLanguage()) : that.language == null
                    && content != null ? content.equals(that.getContent()) : that.content == null
                    && contentUri != null ? contentUri.equals(that.getContentUri()) : that.contentUri == null
                    && contentType != null ? contentType.equals(that.getContentType()) : that.contentType == null
                    && unit != null ? unit.equals(that.getUnit()) : that.unit == null
                    && contentBytes != null ? Arrays.equals(contentBytes, that.getContentBytes()) : that.contentBytes == null;
        } else {
            return false;
        }
    }
}
