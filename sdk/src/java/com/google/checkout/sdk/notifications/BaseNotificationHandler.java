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
package com.google.checkout.sdk.notifications;

import com.google.checkout.sdk.commands.CheckoutException;
import com.google.checkout.sdk.domain.AuthorizationAmountNotification;
import com.google.checkout.sdk.domain.ChargeAmountNotification;
import com.google.checkout.sdk.domain.ChargebackAmountNotification;
import com.google.checkout.sdk.domain.NewOrderNotification;
import com.google.checkout.sdk.domain.NotificationAcknowledgment;
import com.google.checkout.sdk.domain.OrderStateChangeNotification;
import com.google.checkout.sdk.domain.OrderSummary;
import com.google.checkout.sdk.domain.RefundAmountNotification;
import com.google.checkout.sdk.domain.RiskInformationNotification;
import com.google.checkout.sdk.util.Utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Superclass for notification handling: contains useful utilities, but no
 * public methods.
 *
 */
class BaseNotificationHandler {
  protected static final Logger logger =
    Logger.getLogger(NotificationHandler.class.getName());

  /**
   * Calls one of the onFooNotification() methods on the given dispatcher.
   */
  protected void dispatchByType(Notification notification, OrderSummary orderSummary,
      BaseNotificationDispatcher dispatcher) throws Exception {
    if (notification instanceof AuthorizationAmountNotification) {
      dispatcher.onAuthorizationAmountNotification(
          orderSummary, (AuthorizationAmountNotification)notification);
    } else if (notification instanceof ChargeAmountNotification) {
      dispatcher.onChargeAmountNotification(
          orderSummary, (ChargeAmountNotification)notification);
    } else if (notification instanceof ChargebackAmountNotification) {
      dispatcher.onChargebackAmountNotification(
          orderSummary, (ChargebackAmountNotification)notification);
    } else if (notification instanceof NewOrderNotification) {
      dispatcher.onNewOrderNotification(
          orderSummary, (NewOrderNotification)notification);
    } else if (notification instanceof OrderStateChangeNotification) {
      dispatcher.onOrderStateChangeNotification(
          orderSummary, (OrderStateChangeNotification)notification);
    } else if (notification instanceof RefundAmountNotification) {
      dispatcher.onRefundAmountNotification(
          orderSummary, (RefundAmountNotification)notification);
    } else if (notification instanceof RiskInformationNotification) {
      dispatcher.onRiskInformationNotification(
          orderSummary, (RiskInformationNotification)notification);
    } else {
      dispatchUnknownNotification(notification, orderSummary, dispatcher);
    }
  }

  protected void dispatchUnknownNotification(Notification notification, OrderSummary orderSummary,
      BaseNotificationDispatcher dispatcher) throws Exception {
    throw new CheckoutException("Unrecognized notification type " + notification);
  }

  /**
   * Sends a NotificationAcknowledgment with the given serial number.
   * @throws Exception if the acknowledgment could not be sent.
   */
  protected void sendNotificationAcknowledgment(String serialNumber, HttpServletResponse response,
      Notification notification, HttpServletRequest request) throws Exception {
    NotificationAcknowledgment ack = new NotificationAcknowledgment();
    ack.setSerialNumber(serialNumber);

    Utils.toXML(ack.toJAXB(), response.getOutputStream());

    logger.log(Level.INFO,
        "Sent response ack:\n" + Utils.SEND_AND_RECEIVE_DEBUGGING_STRING,
        new Object[]{200, request.getRemoteAddr(), notification, ack});
  }
}
