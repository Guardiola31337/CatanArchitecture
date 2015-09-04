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

package com.pguardiola.catanarchitecture.modules.vertical.folders;

import com.pguardiola.catanarchitecture.modules.horizontal.commons.Callback;
import com.pguardiola.catanarchitecture.events.EventsPort;
import com.pguardiola.catanarchitecture.storage.StoragePort;

public class FoldersModule implements Runnable {
  private final EventsPort eventsPort;
  private final StoragePort storagePort;

  public FoldersModule(StoragePort storagePort, EventsPort eventsPort) {
    this.storagePort = storagePort;
    this.eventsPort = eventsPort;
  }

  @Override public void run() {
    eventsPort.on(LoadFoldersCommand.class, new Callback<LoadFoldersCommand>() {
      @Override public void call(final LoadFoldersCommand event) {
        storagePort.loadFolders();
      }
    });

    eventsPort.on(LoadFoldersResponse.class, new Callback<LoadFoldersResponse>() {
      @Override public void call(LoadFoldersResponse event) {
        for (Folder folder : event.folders) {
          System.out.println(folder.obtainName() + "\n");
        }
      }
    });
  }
}
