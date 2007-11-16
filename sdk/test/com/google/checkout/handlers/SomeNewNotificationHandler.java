/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.checkout.handlers;

import com.google.checkout.GoogleOrder;
import com.google.checkout.MerchantInfo;
import com.google.checkout.exceptions.CheckoutException;
import com.google.checkout.notification.SomeNewNotification;

/**
 *
 * @author charlesdang
 */
public class SomeNewNotificationHandler implements MessageHandler {
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
      SomeNewNotification notification =
          new SomeNewNotification(notificationMsg);
      String ack = getAckString();
      GoogleOrder order =
          GoogleOrder.findOrCreate(mi.getMerchantId(), notification
            .getGoogleOrderNumber());

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
