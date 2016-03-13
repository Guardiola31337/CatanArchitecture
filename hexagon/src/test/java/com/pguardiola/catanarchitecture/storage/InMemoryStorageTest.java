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

package com.pguardiola.catanarchitecture.storage;

import com.pguardiola.catanarchitecture.events.EventsPort;
import com.pguardiola.catanarchitecture.events.InMemoryEventsAdapter;
import com.pguardiola.catanarchitecture.modules.horizontal.commons.Callback;
import com.pguardiola.catanarchitecture.modules.vertical.folders.Folder;
import com.pguardiola.catanarchitecture.modules.vertical.folders.FoldersModule;
import com.pguardiola.catanarchitecture.modules.vertical.folders.LoadFoldersCommand;
import com.pguardiola.catanarchitecture.modules.vertical.folders.LoadFoldersFinished;
import com.pguardiola.catanarchitecture.modules.vertical.folders.LoadFoldersResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InMemoryStorageTest {

  @Test public void storageWithSomeFolders() throws Exception {
    List<Folder> folders = new ArrayList<Folder>();
    folders.add(new Folder("Foo", 5));
    folders.add(new Folder("test", 27));
    folders.add(new Folder("folDER", 4));

    EventsPort eventsPort = new InMemoryEventsAdapter();
    StoragePort storagePort = new InMemoryStorageAdapter(eventsPort, folders);
    FoldersModule foldersModule = new FoldersModule(storagePort, eventsPort);

    eventsPort.on(LoadFoldersFinished.class, new Callback<LoadFoldersFinished>() {
      @Override public void call(LoadFoldersFinished event) {
        assertEquals("Foo", event.folders.get(0).obtainName());
        assertEquals("test", event.folders.get(1).obtainName());
        assertEquals("folDER", event.folders.get(2).obtainName());
      }
    });

    foldersModule.run();
    eventsPort.broadcast(new LoadFoldersCommand());
  }

  @Test public void foldersLoadedResponseEventHasBeenFired() throws Exception {
    InMemoryEventsAdapter eventsPort = new InMemoryEventsAdapter();
    StoragePort storagePort =
        new InMemoryStorageAdapter(eventsPort, Collections.<Folder>emptyList());
    FoldersModule foldersModule = new FoldersModule(storagePort, eventsPort);

    foldersModule.run();
    eventsPort.broadcast(new LoadFoldersCommand());

    assertTrue(eventsPort.hasBeenFired(LoadFoldersResponse.class));
  }

  @Test public void loadFoldersFinishedEventHasBeenFired() throws Exception {
    InMemoryEventsAdapter eventsPort = new InMemoryEventsAdapter();
    StoragePort storagePort =
        new InMemoryStorageAdapter(eventsPort, Collections.<Folder>emptyList());
    FoldersModule foldersModule = new FoldersModule(storagePort, eventsPort);

    foldersModule.run();
    eventsPort.broadcast(new LoadFoldersCommand());

    assertTrue(eventsPort.hasBeenFired(LoadFoldersFinished.class));
  }
}