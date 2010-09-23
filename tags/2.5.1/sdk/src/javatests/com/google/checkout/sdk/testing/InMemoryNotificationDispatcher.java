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

import com.google.checkout.sdk.domain.OrderSummary;
import com.google.checkout.sdk.notifications.BaseNotificationDispatcher;
import com.google.checkout.sdk.notifications.Notification;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Dispatcher which ensures only one attempt to process each serial number
 * can go forward at a time (ie, very basic transactions).</p>
 * <p>This does not persist across JVM invocations since it's in memory, and so
 * shouldn't be used in production systems.</p>
 *
 */
public class InMemoryNotificationDispatcher extends BaseNotificationDispatcher {
  public static final Uniquifier<String> STATIC_UNIQUIFIER = new Uniquifier<String>();
  public static final ConcurrentMap<String, String> STATIC_SUCCESSFULLY_HANDLED =
      new ConcurrentHashMap<String, String>();

  private final Uniquifier<String> uniquifier;
  private final ConcurrentMap<String, String> successfullyHandled;
  private Lock lock;

  /**
   * Creates an InMemory dispatcher to handle the given request using the static uniquifier
   * and successfully handled map.
   */
  public InMemoryNotificationDispatcher(HttpServletRequest request, HttpServletResponse response) {
    this(STATIC_UNIQUIFIER, STATIC_SUCCESSFULLY_HANDLED, request, response);
  }

  public InMemoryNotificationDispatcher(
      Uniquifier<String> uniquifier, ConcurrentMap<String, String> successfullyHandled,
      HttpServletRequest request, HttpServletResponse response) {
    super(request, response);
    this.uniquifier = uniquifier;
    this.successfullyHandled = successfullyHandled;
  }

  @Override
  public void startTransaction(String serialNumber, OrderSummary orderSummary,
      Notification notification)
      throws Exception {
    lock = uniquifier.getLock(serialNumber);
    lock.lock();
  }

  @Override
  public boolean hasAlreadyHandled(String serialNumber, OrderSummary orderSummary,
      Notification notification) throws Exception {
    return successfullyHandled.containsKey(serialNumber);
  }

  @Override
  public void rememberSerialNumber(String serialNumber, OrderSummary orderSummary,
      Notification notification) throws Exception {
    successfullyHandled.put(serialNumber, serialNumber);
  }

  @Override
  public void commitTransaction(String serialNumber, OrderSummary orderSummary,
      Notification notification)
      throws Exception {
    lock.unlock();
  }

  @Override
  public void rollBackTransaction(String serialNumber, OrderSummary orderSummary,
      Notification notification) throws Exception {
    successfullyHandled.remove(serialNumber);
    lock.unlock();
  }
}
