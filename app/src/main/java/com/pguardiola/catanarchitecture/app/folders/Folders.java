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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.pguardiola.CustomItemClickListener;
import com.pguardiola.HexAdapter;
import com.pguardiola.SpacesItemDecoration;
import com.pguardiola.catanarchitecture.app.BaseApp;
import com.pguardiola.catanarchitecture.app.R;
import com.pguardiola.catanarchitecture.events.EventsPort;
import java.util.List;

public class Folders extends AppCompatActivity implements FoldersView {

  private static final int SPAN_COUNT = 2;
  private RecyclerView foldersRecyclerView;
  private RecyclerView.Adapter foldersAdapter;
  private GridLayoutManager layoutManager;
  private EventsPort eventsPort;
  private FoldersPresenter foldersPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.folders);

    foldersRecyclerView = (RecyclerView) findViewById(R.id.folders);

    foldersRecyclerView.setHasFixedSize(true);

    int squareSideLengthInPixels = getResources().getDimensionPixelSize(R.dimen.square_side_length);
    float radius = radius(squareSideLengthInPixels);
    float adjacent = adjacent(radius);

    int spacingInPixels = -(Math.round(radius - adjacent));

    foldersRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

    layoutManager = new GridLayoutManager(this, SPAN_COUNT, LinearLayoutManager.VERTICAL, false);
    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int position) {
        return position % 3 == 0 ? 2 : 1;
      }
    });

    foldersRecyclerView.setLayoutManager(layoutManager);

    eventsPort = obtainEventsPort();
    foldersPresenter = new FoldersPresenter(this, eventsPort);
  }

  @Override
  protected void onResume() {
    super.onResume();
    foldersPresenter.update();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_folders, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public void setFolders(final List<String> theFolders) {
    foldersAdapter = new HexAdapter(theFolders, new CustomItemClickListener() {
      @Override public void onItemClick(View view, int position) {
        Toast.makeText(view.getContext(), theFolders.get(position), Toast.LENGTH_SHORT).show();
      }
    });
    foldersRecyclerView.setAdapter(foldersAdapter);
  }

  private int radius(int diameter) {
    return diameter / 2;
  }

  private float adjacent(float radius) {
    return (float) (Math.sqrt(3) * radius / 2);
  }

  private EventsPort obtainEventsPort() {
    BaseApp app = (BaseApp) getApplication();
    return app.provideEventsPort();
  }
}
