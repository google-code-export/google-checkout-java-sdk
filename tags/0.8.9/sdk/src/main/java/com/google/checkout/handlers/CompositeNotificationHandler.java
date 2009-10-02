/*******************************************************************************
 * Copyright (C) 2007 Google Inc.
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

package com.google.checkout.handlers;

import com.google.checkout.MerchantInfo;
import com.google.checkout.notification.CheckoutNotification;
import java.util.HashMap;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class CompositeNotificationHandler implements NotificationHandler {
  private HashMap notificationHandlers;
  
  public CompositeNotificationHandler() {
    notificationHandlers = new HashMap();
  }
  
  /**
   * Processes each CheckoutNotification as required.
   * 
   * @param mi The merchant info
   * @param notification The notification message
   * @throws com.google.checkout.handlers.CheckoutHandlerException if an error 
   * occured
   * while processing the notification
   */
  public void handle(MerchantInfo mi, CheckoutNotification notification) 
    throws CheckoutHandlerException {
   
    NotificationHandler handler = 
      (NotificationHandler)notificationHandlers.get(notification.getType());
    
    if (handler == null) {
      throw new 
        CheckoutHandlerException("Could not find handler for the notification " 
        + "type: " + notification.getType());
    }
    
    handler.handle(mi, notification);
  }
  
  /**
   * Associates the NotificationHandler with the specified notificationType. If 
   * there is an existing NotificationHandler, it is replaced with the new
   * NotificationHandler.
   * 
   * @param notificationType The notification type
   * @param notificationHandler The handler that will handle notifications of 
   * the specified notificationType
   */
  public void register(String notificationType, NotificationHandler 
    notificationHandler) {
    notificationHandlers.put(notificationType, notificationHandler);
  }
}

