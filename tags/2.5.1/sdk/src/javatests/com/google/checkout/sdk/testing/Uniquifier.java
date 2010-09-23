/*******************************************************************************
 * Copyright (C) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.google.checkout.sdk.testing;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Given a key object, returns a unique lock object for the set of objects that
 * are equal (in the {@link #equals(Object)} sense) to it. This allows
 * synchronization on objects received across the wire.
 *
 */
public class Uniquifier<K> {
  private final ConcurrentMap<K, Lock> elements;

  public Uniquifier() {
    elements = new ConcurrentHashMap<K, Lock>();
  }

  /**
   * @param key The object whose equality class should be detected.
   * @return The unique lock for the equality class corresponding to {@code key}.
   */
  public Lock getLock(K key) {
    Lock newlock = new ReentrantLock();
    Lock oldlock = elements.putIfAbsent(key, newlock);
    if (oldlock == null) {
      return newlock;
    }
    return oldlock;
  }
}
