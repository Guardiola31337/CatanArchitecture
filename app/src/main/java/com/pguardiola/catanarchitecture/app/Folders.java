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

package com.pguardiola.catanarchitecture.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.pguardiola.HexAdapter;
import com.pguardiola.SpacesItemDecoration;
import java.util.ArrayList;
import java.util.List;

public class Folders extends AppCompatActivity {

  private static final int SPAN_COUNT = 2;
  private RecyclerView foldersView;
  private RecyclerView.Adapter foldersAdapter;
  private GridLayoutManager layoutManager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.folders);

    foldersView = (RecyclerView) findViewById(R.id.folders);

    foldersView.setHasFixedSize(true);

    int squareSideLengthInPixels = getResources().getDimensionPixelSize(R.dimen.square_side_length);
    float radius = squareSideLengthInPixels / 2;
    float adjacent = (float) (Math.sqrt(3) * radius / 2);

    int spacingInPixels = -(Math.round(radius - adjacent));

    foldersView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

    layoutManager = new GridLayoutManager(this, SPAN_COUNT, LinearLayoutManager.VERTICAL, false);
    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int position) {
        return position % 3 == 0 ? 2 : 1;
      }
    });

    foldersView.setLayoutManager(layoutManager);

    List<String> folders = new ArrayList<String>() {
      {
        add("Foo");
        add("test");
        add("folDER");
        add("Foo");
        add("test");
        add("folDER");
        add("Foo");
        add("test");
        add("folDER");
        add("Foo");
        add("test");
        add("folDER");
        add("Foo");
        add("test");
        add("folDER");
        add("Foo");
        add("test");
        add("folDER");
        add("Foo");
        add("test");
        add("folDER");
      }
    };
    foldersAdapter = new HexAdapter(folders);
    foldersView.setAdapter(foldersAdapter);
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
}
