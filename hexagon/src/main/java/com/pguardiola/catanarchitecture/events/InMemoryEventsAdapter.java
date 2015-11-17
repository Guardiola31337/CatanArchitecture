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

package com.pguardiola.catanarchitecture.events;

import com.pguardiola.catanarchitecture.modules.horizontal.commons.Callback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InMemoryEventsAdapter implements EventsPort {
  private Map<Class, Set<Callback>> subscribers = new HashMap<Class, Set<Callback>>();
  private List firedEvents = new ArrayList();

  @Override public <T> void on(Class<T> type, Callback<T> command) {
    if (!subscribers.containsKey(type)) {
      subscribers.put(type, new HashSet<Callback>());
    }
    subscribers.get(type).add(command);
  }

  @Override @SuppressWarnings("unchecked") public void broadcast(Object event) {
    firedEvents.add(event);
    if (subscribers.containsKey(event.getClass())) {
      for (Callback command : subscribers.get(event.getClass())) {
        command.call(event);
      }
    }
  }

  public boolean hasBeenFired(Class type) {
    for (Object event : firedEvents) {
      if (event.getClass().equals(type)) {
        return true;
      }
    }
    return false;
  }

  @SuppressWarnings("unchecked") public <T> List<T> eventsOf(Class<T> type) {
    List events = new ArrayList();
    for (Object event : firedEvents) {
      if (event.getClass().equals(type)) {
        events.add(event);
      }
    }
    return events;
  }
}
