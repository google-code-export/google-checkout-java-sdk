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
import com.google.checkout.exceptions.CheckoutException;
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
//    registerDefaultHandlers();
  }
  
  public void process(MerchantInfo mi, CheckoutNotification notification) 
    throws CheckoutException, UnknownHandlerException {
   
    NotificationHandler handler = (NotificationHandler)notificationHandlers.get(notification.getType());
    
    if (handler == null) {
      throw new UnknownHandlerException();
    }
    
    handler.process(mi, notification);
  }
  
  public void register(String notificationType, NotificationHandler nh) {
    notificationHandlers.put(notificationType, nh);
  }
}

