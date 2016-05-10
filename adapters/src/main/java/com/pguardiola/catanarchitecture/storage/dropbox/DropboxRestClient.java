/*
 * Copyright (C) 2015 Pablo Guardiola Sánchez.
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

package com.pguardiola.catanarchitecture.storage.dropbox;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

class DropboxRestClient {
  private static final String BASE_URL = "https://api.dropbox.com/1";
  private DropboxAPI apiService;

  DropboxRestClient() {
    Gson gson = new GsonBuilder().setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
        .create();

    RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL)
        .setEndpoint(BASE_URL)
        .setConverter(new GsonConverter(gson))
        .build();

    apiService = restAdapter.create(DropboxAPI.class);
  }

  DropboxAPI getApiService() {
    return apiService;
  }
}
