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

import com.pguardiola.catanarchitecture.events.EventsPort;
import com.pguardiola.catanarchitecture.modules.vertical.folders.Folder;
import com.pguardiola.catanarchitecture.modules.vertical.folders.LoadFoldersResponse;
import com.pguardiola.catanarchitecture.storage.StoragePort;
import java.util.ArrayList;
import java.util.List;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DropboxStorageAdapter implements StoragePort {
  private final EventsPort eventsPort;
  private final DropboxAPI client;
  private List<Folder> folders;

  public DropboxStorageAdapter(EventsPort eventsPort, DropboxAPI client) {
    this.eventsPort = eventsPort;
    this.client = client;
  }

  @Override public void loadFolders() {
    client.obtainFolders("root", new retrofit.Callback<DropboxFoldersResponse>() {
      @Override public void success(DropboxFoldersResponse apiUserResponse, Response response) {
        folders = new ArrayList<>();
        for (DropboxFolderDTO folder : apiUserResponse.getContents()) {
          Folder newFolder = new Folder(folder.getName(), 0);
          folders.add(newFolder);
        }
        eventsPort.broadcast(new LoadFoldersResponse(folders));
      }

      @Override public void failure(RetrofitError error) {
        System.out.println("Failure!");
      }
    });
  }
}
