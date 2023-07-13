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
package com.basistech.rosette.api;

import com.basistech.rosette.apimodel.Request;
import com.basistech.rosette.apimodel.Response;

import java.util.concurrent.Callable;

/**
 * This class encompasses a future request that can be sent concurrently
 * @param <R> type of the response object
 */
public class RosetteRequest<R extends Response> implements Callable<R> {
    private final HttpRosetteAPI api;
    private final Request request;
    private final String servicePath;
    private final Class<R> responseClass;
    private R response;

    RosetteRequest(HttpRosetteAPI api,
                   Request request,
                   String servicePath, Class<R> responseClass) {
        this.api = api;
        this.request = request;
        this.servicePath = servicePath;
        this.responseClass = responseClass;
    }

    @Override
    public R call() {
        this.response = api.perform(this.servicePath, this.request, this.responseClass);
        return this.response;
    }

    public R getResponse() {
        return this.response;
    }
}
