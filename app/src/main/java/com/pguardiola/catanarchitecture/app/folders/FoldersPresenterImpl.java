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

import com.pguardiola.catanarchitecture.events.EventsPort;
import com.pguardiola.catanarchitecture.modules.horizontal.commons.Callback;
import com.pguardiola.catanarchitecture.modules.vertical.folders.Folder;
import com.pguardiola.catanarchitecture.modules.vertical.folders.LoadFoldersCommand;
import com.pguardiola.catanarchitecture.modules.vertical.folders.LoadFoldersFinished;
import java.util.ArrayList;
import java.util.List;

public class FoldersPresenterImpl implements FoldersPresenter {

  private final FoldersView foldersView;
  private final EventsPort eventsPort;
  private final Callback onFoldersReceived;

  public FoldersPresenterImpl(final FoldersView foldersView, EventsPort eventsPort) {
    this.foldersView = foldersView;
    this.eventsPort = eventsPort;
    onFoldersReceived = initializeOnFinishedListener(foldersView);
  }

  @Override public void onResume() {
    eventsPort.on(LoadFoldersFinished.class, onFoldersReceived);
    eventsPort.broadcast(new LoadFoldersCommand());
  }

  private Callback<LoadFoldersFinished> initializeOnFinishedListener(
      final FoldersView foldersView) {
    return new Callback<LoadFoldersFinished>() {
      @Override public void call(LoadFoldersFinished event) {
        final List<String> folders = new ArrayList<>();
        for (Folder folder : event.folders) {
          folders.add(folder.obtainName());
        }
        foldersView.setFolders(folders);
      }
    };
  }
}
