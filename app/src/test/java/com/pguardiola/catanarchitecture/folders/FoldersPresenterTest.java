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

package com.pguardiola.catanarchitecture.folders;

import com.pguardiola.catanarchitecture.app.folders.FoldersPresenter;
import com.pguardiola.catanarchitecture.app.folders.FoldersPresenterImpl;
import com.pguardiola.catanarchitecture.app.folders.FoldersView;
import com.pguardiola.catanarchitecture.events.EventsPort;
import com.pguardiola.catanarchitecture.events.InMemoryEventsAdapter;
import com.pguardiola.catanarchitecture.modules.horizontal.commons.Callback;
import com.pguardiola.catanarchitecture.modules.vertical.folders.Folder;
import com.pguardiola.catanarchitecture.modules.vertical.folders.LoadFoldersCommand;
import com.pguardiola.catanarchitecture.modules.vertical.folders.LoadFoldersFinished;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.Matchers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FoldersPresenterTest {
  @Test public void whenOnResumedSetupOnFoldersReceived() throws Exception {
    EventsPort eventsPort = mock(EventsPort.class);
    FoldersPresenter presenter = new FoldersPresenterImpl(null, eventsPort);

    presenter.onResume();

    verify(eventsPort).on(Matchers.<Class<LoadFoldersFinished>>any(),
        Matchers.<Callback<LoadFoldersFinished>>any());
  }

  @Test public void whenOnResumedLoadFoldersCommandIsBroadcast() throws Exception {
    EventsPort eventsPort = mock(EventsPort.class);
    FoldersPresenter presenter = new FoldersPresenterImpl(null, eventsPort);

    presenter.onResume();

    verify(eventsPort).broadcast(any(LoadFoldersCommand.class));
  }

  @Test public void whenOnResumedSetFoldersIsCalled() throws Exception {
    EventsPort eventsPort = new InMemoryEventsAdapter();
    FoldersView foldersView = mock(FoldersView.class);
    FoldersPresenter presenter = new FoldersPresenterImpl(foldersView, eventsPort);

    presenter.onResume();

    List<Folder> folders = new ArrayList<>();
    folders.add(new Folder("Foo", 5));
    folders.add(new Folder("test", 27));
    folders.add(new Folder("folDER", 4));
    eventsPort.broadcast(new LoadFoldersFinished(folders));

    List<String> viewFolders = new ArrayList<>();
    viewFolders.add("Foo");
    viewFolders.add("test");
    viewFolders.add("folDER");

    verify(foldersView).setFolders(eq(viewFolders));
  }
}
