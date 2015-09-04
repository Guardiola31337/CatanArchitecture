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

package com.pguardiola.catanarchitecture.storage.inmemory;

import com.pguardiola.catanarchitecture.events.EventsPort;
import com.pguardiola.catanarchitecture.modules.vertical.folders.Folder;
import com.pguardiola.catanarchitecture.modules.vertical.folders.LoadFoldersResponse;
import com.pguardiola.catanarchitecture.storage.StoragePort;
import java.util.List;

public class InMemoryStorageAdapter implements StoragePort {
  private final EventsPort eventsPort;
  private List<Folder> folders;

  public InMemoryStorageAdapter(EventsPort eventsPort, List<Folder> folders) {
    this.eventsPort = eventsPort;
    this.folders = folders;
  }

  @Override public void loadFolders() {
    eventsPort.broadcast(new LoadFoldersResponse(folders));
  }
}
