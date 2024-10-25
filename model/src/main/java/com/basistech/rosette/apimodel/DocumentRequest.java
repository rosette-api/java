/*
 * Copyright 2017-2024 Basis Technology Corp.
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
import lombok.Builder;
import lombok.Value;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * This class represents the common information for all document processing requests to the Analytics API.
 * Most applications do not use this class directly; the methods of the {@code RosetteAPI} class
 * create request objects. More complex applications may create objects of
 * this class for themselves via the {@link DocumentRequest.DocumentRequestBuilder}.
 * <br>
 * On the wire, a request is a json object. All the endpoints accept the same set of items,
 * represented here, that describe the input document.
 * <br>
 * Applications specify the text to process in three ways:
 * <ol>
 *     <li>Plain text, as the {@code content} item in the Json object.</li>
 *     <li>A binary file image, attached as an additional MIME part to the request.
 *     The application provides a MIME content type in {@code contentType}.</li>
 *     <li>A URL of a data to download. The application provides the URL in
 *     {@code contentUri}. Note that the Analytics API respects the content type
 *     returned by the server for downloaded data.</li>
 * </ol>
 * In this object the 'content' item is an {@link Object}; it contains a {@link String}
 * for plain text, or an {@link java.io.InputStream} for binary data. {@link DocumentRequest.DocumentRequestBuilder}
 * provides several alternative methods for setting this information.
 */
@Value
public final class DocumentRequest<O extends Options> extends Request {

    /**
     * @return the language code
     */
    private final LanguageCode language;

    /**
     * @return the request content
     */
    private final Object content;

    /**
     * @return the URI of request content
     */
    private final String contentUri;

    /**
     * @return request content type
     */
    private final String contentType;

    /**
     * @return request options
     */
    private final O options;

    @Builder     // workaround for inheritance https://github.com/rzwitserloot/lombok/issues/853
    public DocumentRequest(String profileId,
                           LanguageCode language,
                           Object content,
                           String contentUri,
                           String contentType,
                           O options) {
        super(profileId);
        this.language = language;
        this.content = content;
        this.contentUri = contentUri;
        this.contentType = contentType;
        this.options = options;
    }

    /**
     * get content to process if it's a String.
     *
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
     *
     * @return the content as bytes
     */
    public InputStream getContentBytes() {
        if (content instanceof InputStream) {
            return (InputStream) content;
        } else {
            return null;
        }
    }

    /**
     * get content as an object
     *
     * @return the content as object
     */
    public Object getRawContent() {
        return content;
    }

    // need more fluent builder methods in addition to lombok defaults
    public static class DocumentRequestBuilder<O extends Options> {
        /**
         * Specify the content as String. Use this for plain text.
         *
         * @param data The data.
         * @return this.
         */
        public DocumentRequestBuilder<O> content(String data) {
            this.content = data;
            return this;
        }

        /**
         * Specify the content as bytes with a content type. Use this for
         * formats other than plain text.
         *
         * @param bytes       The data.
         * @param contentType the content type.
         * @return this.
         */
        public DocumentRequestBuilder<O> content(byte[] bytes, String contentType) {
            this.content = new ByteArrayInputStream(bytes);
            this.contentType = contentType;
            return this;
        }

        /**
         * Specify the content as bytes with a content type. Use this for
         * formats other than plain text.
         *
         * @param bytes       The data.
         * @param contentType the content type.
         * @return this.
         */
        public DocumentRequestBuilder<O> content(InputStream bytes, String contentType) {
            this.content = bytes;
            this.contentType = contentType;
            return this;
        }
    }
}
