/*
* Copyright 2014 Basis Technology Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.basistech.rosette.apimodel;

import com.basistech.util.LanguageCode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * This class represents the common information for all document processing requests to the Rosette API.
 * Most applications do not use this class directly; the methods of the {@code RosetteAPI} class
 * create request objects. More complex applications may create objects of the subclasses
 * of this class for themselves via the fluent Builder classes.
 * <br>
 * On the wire, a request is a json object. All the endpoints accept the same set of items,
 * represented here, that describe the input document text.
 * <br>
 * Applications specify the text to process in three ways:
 * <ol>
 *     <li>Plain text, as the {@code content} item in the Json object.</li>
 *     <li>A binary file image, attached as an additional MIME part to the request.
 *     The application provides a MIME content type in {@code contentType}.</li>
 *     <li>A URL of a data to download. The application provides the URL in
 *     {@code contentUri}. Note that the Rosette API respects the content type
 *     returned by the server for downloaded data.</li>
 * </ol>
 * In this object the 'content' item is an {@link Object}; it contains a {@link String}
 * for plain text, and an {@link java.io.InputStream} for binary data. {@link Request.Builder}
 * provides several alternative methods for setting this information.
 *
 * This class includes a 'genre' field. If no genre is specified, then the system
 * applies generic processing. Valid values for genre are specified in the API documentation.
 */
public abstract class Request {

    private final LanguageCode language;
    private final Object content;
    private final String contentUri;
    private final String contentType;
    private final String genre;

    /**
     * Constructor for {@code Request}
     * @param language language code
     * @param content raw data
     * @param contentUri uri pointing to the data
     * @param contentType byte array of data
     */
    protected Request(
            LanguageCode language,
            String genre,
            Object content,
            String contentUri,
            String contentType) {
        this.language = language;
        this.content = content;
        this.contentUri = contentUri;
        this.contentType = contentType;
        this.genre = genre;
    }

    /**
     * get the language code
     * @return the language code
     */
    public LanguageCode getLanguage() {
        return language;
    }

    /**
     * get content to process if it's a String.
     * @return the content if a String, else null.
     */
    public String getContent() {
        if (content instanceof String) {
            return (String) content;
        } else {
            return null;
        }
    }

    /**
     * get the content as an array of bytes 
     * @return the content as bytes
     */
    public InputStream getContentBytes() {
        if (content instanceof InputStream) {
            return (InputStream)content;
        } else {
            return null;
        }
    }

    public Object getRawContent() {
        return content;
    }

    /**
     * get the URI to accessible content (content and contentURI are mutually exlcusive) 
     * @return the content URI
     */
    public String getContentUri() {
        return contentUri;
    }

    /**
     * get the content type 
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * get the genre.
     * @return the genre.
     */
    public String getGenre() {
        return genre;
    }

    @Override
    public int hashCode() {
        int result = language != null ? language.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (contentUri != null ? contentUri.hashCode() : 0);
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code Request}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Request)) {
            return false;
        }

        Request that = (Request) o;
        return language != null ? language.equals(that.getLanguage()) : that.language == null
                && content != null ? content.equals(that.content) : that.content == null
                && contentUri != null ? contentUri.equals(that.getContentUri()) : that.contentUri == null
                && contentType != null ? contentType.equals(that.getContentType()) : that.contentType == null
                && genre != null ? genre.equals(that.getGenre()) : that.genre == null;
    }

    /**
     * Base class for builders for the request objects.
     * @param <T> The type of the request object.
     */
    public abstract static class Builder<T extends Request, O, B extends Builder<T, O, B>> {
        protected LanguageCode language;
        protected Object content;
        protected String contentUri;
        protected String contentType;
        protected String genre;
        protected O options;

        protected abstract B getThis();

        /**
         * Set the language of the input.
         * @param language the language.
         * @return this
         */
        public B language(LanguageCode language) {
            this.language = language;
            return getThis();
        }

        /**
         * @return the language, if any, for this request.
         */
        LanguageCode language() {
            return language;
        }

        /**
         * Set the content for this request as a string of plain text.
         * @param content the content.
         * @return this.
         */
        public B content(String content) {
            this.content = content;
            return getThis();
        }

        /**
         * @return the content for this request.
         */
        public String contentString() {
            return (String) content;
        }

        /**
         * Set the content for this request to be the URI of data to download.
         * Only 'http:' URI's are supported.
         * @param uri the URI.
         * @return this.
         */
        public B contentUri(String uri) {
            this.contentUri = uri;
            return getThis();
        }

        /**
         * @return the URI of the content to retrieve, if any.
         */
        public String contentUri() {
            return contentUri;
        }

        /**
         * Specify the content as bytes with a content type. Use this for
         * formats other than plain text.
         * @param bytes The data.
         * @param contentType the content type.
         * @return this.
         */
        public B contentBytes(byte[] bytes, String contentType) {
            this.content = new ByteArrayInputStream(bytes);
            this.contentType = contentType;
            return getThis();
        }

        /**
         * Specify the content as bytes with a content type. Use this for
         * formats other than plain text.
         * @param bytes The data.
         * @param contentType the content type.
         * @return this.
         */
        public B contentBytes(InputStream bytes, String contentType) {
            this.content = bytes;
            this.contentType = contentType;
            return getThis();
        }

        /**
         * @return the content bytes, if any.
         */
        public InputStream contentBytes() {
            return (InputStream)content;
        }

        /**
         * @return the content type for the content bytes.
         */
        public String contentType() {
            return contentType;
        }

        /**
         * Set the options for this request.
         * @param options the options.
         * @return this.
         */
        public B options(O options) {
            this.options = options;
            return getThis();
        }

        /**
         * @return the options for this request.
         */
        public O options() {
            return options;
        }

        public B genre(String genre) {
            this.genre = genre;
            return getThis();
        }

        public String genre() {
            return genre;
        }

        /**
         * Construct the request object.
         * @return the request object.
         */
        public abstract T build();
    }
}
