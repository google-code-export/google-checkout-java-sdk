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

import com.google.checkout.exceptions.CheckoutException;
import com.google.checkout.GoogleOrder;
import com.google.checkout.MerchantInfo;
import com.google.checkout.notification.NewOrderNotification;

/**
 * 
 * @author simonjsmith
 * @author inder
 */
public class NewOrderNotificationHandler implements MessageHandler {

  /**
   * 
   * @param mi
   * @param notificationMsg
   * @return
   * @throws com.google.checkout.exceptions.CheckoutException
   */
  public String process(MerchantInfo mi, String notificationMsg)
      throws CheckoutException {
    try {
      NewOrderNotification notification =
          new NewOrderNotification(notificationMsg);
      String ack = getAckString();
      GoogleOrder order =
          GoogleOrder.findOrCreate(mi.getMerchantId(), notification
              .getGoogleOrderNo());

      order
          .setLastFulStatus(notification.getFulfillmentOrderState().toString());
      order.setLastFinStatus(notification.getFinancialOrderState().toString());
      order.setBuyerEmail(notification.getBuyerBillingAddress().getEmail());
      order.setOrderAmount("" + notification.getOrderTotal());
      order.addIncomingMessage(notification.getTimestamp(), notification
          .getRootNodeName(), notification.getXmlPretty(), ack);
      return ack;
    } catch (Exception e) {
      throw new CheckoutException(e);
    }
  }

  private String getAckString() {
    return NotificationAcknowledgment.getAckString();
  }
}
