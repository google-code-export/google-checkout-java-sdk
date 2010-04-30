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
import com.google.checkout.sdk.notifications.Notification;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Purely for tests, extends the InMemoryNotificationDispatcher to keep on itself
 * a log of all calls made.
 * 
*
 */
public class HistoricalNotificationDispatcher extends InMemoryNotificationDispatcher {
  private final List<DispatcherHistoryElement> history;
  public HistoricalNotificationDispatcher(HttpServletRequest request, HttpServletResponse response) {
    super(request, response);
    history = new ArrayList<DispatcherHistoryElement>();
  }
  
  public List<DispatcherHistoryElement> getHistory() {
    synchronized(history) {
      return history;
    }
  }
  
  @Override
  public void startTransaction(String serialNumber, OrderSummary orderSummary,
      Notification notification) throws Exception {
    boolean before = super.hasAlreadyHandled(serialNumber, orderSummary, notification);
    super.startTransaction(serialNumber, orderSummary, notification);
    boolean after = super.hasAlreadyHandled(serialNumber, orderSummary, notification);
    history.add(new DispatcherHistoryElement(serialNumber, "startTransaction",
        before, after));
  }
  
  @Override
  public boolean hasAlreadyHandled(String serialNumber, OrderSummary orderSummary,
      Notification notification) throws Exception {
    boolean hasAlreadyHandled = super.hasAlreadyHandled(serialNumber, orderSummary, notification);
    history.add(new DispatcherHistoryElement(serialNumber, "hasAlreadyHandled",
        hasAlreadyHandled, hasAlreadyHandled));
    return hasAlreadyHandled;
  }
  
  @Override
  public void rememberSerialNumber(String serialNumber, OrderSummary orderSummary,
      Notification notification) throws Exception {
    boolean before = super.hasAlreadyHandled(serialNumber, orderSummary, notification);
    super.rememberSerialNumber(serialNumber, orderSummary, notification);
    boolean after = super.hasAlreadyHandled(serialNumber, orderSummary, notification);
    history.add(new DispatcherHistoryElement(serialNumber, "rememberSerialNumber",
        before, after));
  }
  
  @Override
  public void commitTransaction(String serialNumber, OrderSummary orderSummary,
      Notification notification) throws Exception {
    boolean before = super.hasAlreadyHandled(serialNumber, orderSummary, notification);
    super.commitTransaction(serialNumber, orderSummary, notification);
    history.add(new DispatcherHistoryElement(serialNumber, "commitTransaction",
        before, true));
  }

  @Override
  public void rollBackTransaction(String serialNumber, OrderSummary orderSummary,
      Notification notification) throws Exception {
    boolean before = super.hasAlreadyHandled(serialNumber, orderSummary, notification);
    super.rollBackTransaction(serialNumber, orderSummary, notification);
    boolean after = super.hasAlreadyHandled(serialNumber, orderSummary, notification);
    history.add(new DispatcherHistoryElement(serialNumber, "rollBackTransaction",
        before, after));
  }

  public static class DispatcherHistoryElement {
    private final String serialNumber;
    private final String stage;
    private final boolean oldState;
    private final boolean newState;

    public DispatcherHistoryElement(String serialNumber, String stage,
        boolean oldState, boolean newState) {
      this.serialNumber = serialNumber;
      this.stage = stage;
      this.oldState = oldState;
      this.newState = newState;
    }
    
    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (!(obj instanceof DispatcherHistoryElement)) {
        return false;
      }
      DispatcherHistoryElement that = (DispatcherHistoryElement) obj;
      return this.serialNumber.equals(that.serialNumber)
          && this.stage.equals(that.stage)
          && (this.oldState == that.oldState)
          && (this.newState == that.newState);
    }
    
    @Override
    public int hashCode() {
      int hash = 1;
      hash = hash * 31 + serialNumber.hashCode();
      hash = hash * 31 + stage.hashCode();
      hash = hash * 31 + (oldState ? 1 : 0);
      hash = hash * 31 + (newState ? 1 : 0);
      return hash;
    }
    
    @Override
    public String toString() {
      return new StringBuilder("DispatcherHistoryElement(")
          .append(serialNumber).append(",")
          .append(stage).append(",")
          .append(stateString(oldState)).append("->")
          .append(stateString(newState)).append(")").toString();
    }
    
    private String stateString(boolean state) {
      return state ? "complete" : "incomplete";
    }
  }
}
