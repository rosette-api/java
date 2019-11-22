/*******************************************************************************
 * Copyright 2019 Basis Technology Corp.
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
 *
 ******************************************************************************/

package com.basistech.rosette.apimodel;

import com.basistech.util.LanguageCode;
import lombok.Builder;
import lombok.Value;

@Value
public final class ConfigurationRequest<T extends Configuration> extends Request {
    private final LanguageCode language;
    private final T configuration;

    @Builder
    public ConfigurationRequest(String profileId, LanguageCode language, T configuration) {
        super(profileId);
        this.language = language;
        this.configuration = configuration;
    }
}
