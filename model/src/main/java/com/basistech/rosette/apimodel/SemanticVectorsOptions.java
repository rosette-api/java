/*
 * Copyright 2023 Basis Technology Corp.
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

import com.basistech.rosette.annotations.JacksonMixin;
import lombok.Builder;
import lombok.Value;

/**
 * Semantic Vectors options
 */
@Value
@Builder
@JacksonMixin
public class SemanticVectorsOptions extends Options {

    /**
     * @return whether embeddings should be returned for each token in addition to the whole document
     */
    private Boolean perToken;

    /**
     * @return the generation of embeddings to use for all languages, defaulting to second generation embeddings unless
     * the language supports first generation embeddings
     */
    private EmbeddingsMode embeddingsMode;
}
