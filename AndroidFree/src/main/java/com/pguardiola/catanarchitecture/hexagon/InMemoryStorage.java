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

import com.pguardiola.catanarchitecture.hexagon.domain.events.FoldersLoadedEvent;
import java.util.List;

public class InMemoryStorage implements Storage {
  private final DataPort dataPort;
  private final EventsPort eventsPort;
  private List<FolderDTO> folders;

  public InMemoryStorage(DataPort dataPort, EventsPort eventsPort) {
    this.eventsPort = eventsPort;
    this.dataPort = dataPort;
  }

  @Override public void loadFolders() {
    folders = dataPort.obtainFolders();
    eventsPort.broadcast(new FoldersLoadedEvent());
  }

  @Override public List<FolderDTO> obtainUpdatedFolders() {
    return folders;
  }
}
