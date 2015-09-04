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
import com.pguardiola.catanarchitecture.modules.horizontal.commons.Callback;
import com.pguardiola.catanarchitecture.events.EventsPort;
import com.pguardiola.catanarchitecture.events.InMemoryEventsAdapter;
import com.pguardiola.catanarchitecture.modules.vertical.folders.FoldersModule;
import com.pguardiola.catanarchitecture.modules.vertical.folders.LoadFoldersCommand;
import com.pguardiola.catanarchitecture.modules.vertical.folders.LoadFoldersResponse;
import com.pguardiola.catanarchitecture.storage.StoragePort;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import java.util.concurrent.Executor;
import org.junit.Test;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import static org.junit.Assert.assertEquals;

public class DropboxStorageTest {

  @Test public void mockWebServerWorking() throws Exception {
    MockWebServer server = new MockWebServer();
    Gson gson = new GsonBuilder().setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create();

    // Schedule some responses.
    server.enqueue(new MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
        .setBody(
            "{\"contents\":[{\"name\":\"Dropbox Foo\"},{\"name\":\"Dropbox test\"},{\"name\":\"Dropbox folDER\"}]}"));

    // Start the server.
    server.start();

    RestAdapter restAdapter = new RestAdapter.Builder().setExecutors(new Executor() {
      @Override public void execute(Runnable command) {
        command.run();
      }
    }, null)
        .setEndpoint(server.getUrl("/").toString())
        .setConverter(new GsonConverter(gson))
        .build();
    DropboxAPI apiService = restAdapter.create(DropboxAPI.class);

    EventsPort eventsPort = new InMemoryEventsAdapter();
    StoragePort storagePort = new DropboxStorageAdapter(eventsPort, apiService);
    FoldersModule foldersModule = new FoldersModule(storagePort, eventsPort);

    eventsPort.on(LoadFoldersResponse.class, new Callback<LoadFoldersResponse>() {
      @Override public void call(LoadFoldersResponse event) {
        assertEquals("Dropbox Foo", event.folders.get(0).obtainName());
        assertEquals("Dropbox test", event.folders.get(1).obtainName());
        assertEquals("Dropbox folDER", event.folders.get(2).obtainName());
      }
    });

    foldersModule.run();
    eventsPort.broadcast(new LoadFoldersCommand());

    // Shut down the server. Instances cannot be reused.
    server.shutdown();
  }
}