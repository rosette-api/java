/*
 * Copyright 2021 Basis Technology Corp.
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
import com.basistech.util.LanguageCode;
import lombok.Builder;
import lombok.Value;

import java.util.EnumMap;
import java.util.List;

/**
 * Events options
 */
@Value
@Builder
@JacksonMixin
public class EventsOptions extends Options {
    /**
     * Default options
     */
    public static final EventsOptions DEFAULT = EventsOptions.builder().build();

    /**
     * workspaceId to use.
     */
    String workspaceId;

    EnumMap<LanguageCode, List<String>> plan;
}
