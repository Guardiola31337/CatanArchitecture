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

package com.pguardiola.catanarchitecture.hexagon;

import com.pguardiola.catanarchitecture.data.InMemoryDataAdapter;
import com.pguardiola.catanarchitecture.events.InMemoryEventsAdapter;
import com.pguardiola.catanarchitecture.hexagon.domain.events.FoldersLoadedEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StorageModuleTest {
  @Test public void storageWithSomeFolders() throws Exception {
    List<FolderDTO> folders = new ArrayList<FolderDTO>() {{
      add(new FolderDTO("Foo"));
      add(new FolderDTO("test"));
      add(new FolderDTO("folDER"));
    }};
    DataPort dataPort = new InMemoryDataAdapter(folders);
    EventsPort eventsPort = new InMemoryEventsAdapter();
    Storage storage = new InMemoryStorage(dataPort, eventsPort);
    StorageModule storageModule = new StorageModule(storage);

    storageModule.run();

    List<FolderDTO> storageFolders = storageModule.obtainFoldersFromStorage();
    assertEquals("Foo", storageFolders.get(0).getName());
    assertEquals("test", storageFolders.get(1).getName());
    assertEquals("folDER", storageFolders.get(2).getName());
  }
}
