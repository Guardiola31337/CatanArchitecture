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

package com.pguardiola.catanarchitecture.app.folders;

import android.os.Environment;
import com.pguardiola.catanarchitecture.events.EventsPort;
import com.pguardiola.catanarchitecture.modules.vertical.folders.Folder;
import com.pguardiola.catanarchitecture.modules.vertical.folders.LoadFoldersResponse;
import com.pguardiola.catanarchitecture.storage.StoragePort;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SDCardStorageAdapter implements StoragePort {

  private final EventsPort eventsPort;
  private List<Folder> folders;

  public SDCardStorageAdapter(EventsPort eventsPort) {
    this.eventsPort = eventsPort;
  }

  @Override public void loadFolders() {
    List<String> foldersName = new ArrayList<>();

    String rootSD = Environment.getExternalStorageDirectory().toString();
    File file = new File(rootSD);
    File list[] = file.listFiles();

    for (int i = 0; i < list.length; i++) {
      if (list[i].isDirectory() && !list[i].isHidden()) {
        foldersName.add(list[i].getName());
      }
    }

    folders = new ArrayList<>();
    for (String folder : foldersName) {
      Folder newFolder = new Folder(folder, 0);
      folders.add(newFolder);
    }
    eventsPort.broadcast(new LoadFoldersResponse(folders));
  }
}
