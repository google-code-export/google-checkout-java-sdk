/*******************************************************************************
 * Copyright (C) 2009 Google Inc.
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

import com.google.checkout.sdk.commands.ApiContext;
import com.google.checkout.sdk.commands.CheckoutException;
import com.google.checkout.sdk.domain.OrderSummary;
import com.google.checkout.sdk.util.Utils;

import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class provides methods for handling notifications from checkout.
 *
 * To handle notifications, extend the BaseNotificationDispatcher class with your
 * business-specific logic.  Then, in a Servlet that's handling the POST form checkout,
 * pass in a new instance of your child class to NotificationHandler.handleNotification():
 *
 * <code>
 * public class MyServlet extends HttpServlet {
 *   public void doPost(HttpServletRequest request, HttpServletResponse response) {
 *     apiContext.handleNotification(
 *          new MyNotificationDispatcher(request, response));
 *   }
 * }
 * </code>
*
 */
public class NotificationHandler extends BaseNotificationHandler {
  private static final String SERIAL_NUMBER_PARAMETER = "serial-number";

  private final ApiContext apiContext;

  public NotificationHandler(ApiContext apiContext) {
    this.apiContext = apiContext;
  }

  /**
   * Uses the given notification dispatcher to handle a notification POST for Checkout.  Can handle
   * XML, HTML and serial-number notifications.
   *
   * 1. Gets the notification from the request, re-throwing any exceptions.
   * 2. Calls dispatcher.startTransaction()
   * 3. Calls dispatcher.hasAlreadyHandled() to see if the notification has already been handled.
   * 4. If the notification has not been handled:
   *        Calls dispatcher.onAllNotifications()
   *        Calls dispatcher.onFooNotification() where X is the notification type.
   *        Calls dispatcher.rememberSerialNumber()
   * 5. Calls dispatcher.commitTransaction()
   * 6. Sends a notification acknowledgment, ignoring any exceptions.
   *
   * If any exceptions occur during steps 2 through 5,
   * calls dispatcher.rollbackTransaction() and throws a CheckoutException.
   *
   * @throws CheckoutException if an exceptions occurred in steps 1 through 5.
   */
  public void handleNotification(BaseNotificationDispatcher dispatcher) throws CheckoutException {
    Notification notification = getNotificationFromRequest(dispatcher.request);
    String serialNumber = notification.getSerialNumber();
    OrderSummary orderSummary = notification.getOrderSummary();

    try {
      dispatcher.startTransaction(serialNumber, orderSummary, notification);

      if (!dispatcher.hasAlreadyHandled(serialNumber, orderSummary, notification)) {
        dispatcher.onAllNotifications(orderSummary, notification);
        dispatchByType(notification, orderSummary, dispatcher);
        dispatcher.rememberSerialNumber(serialNumber, orderSummary, notification);
      }

      dispatcher.commitTransaction(serialNumber, orderSummary, notification);
    } catch (Exception e) {
      logger.log(Level.INFO, "Caught exception while processing", e);
      try {
        dispatcher.rollBackTransaction(serialNumber, orderSummary, notification);
        dispatcher.response.sendError(
            HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Error in Server");
      } catch (Exception secondaryException) {
        logger.log(Level.WARNING, "Secondary Exception caught while rolling back transaction.",
            secondaryException);
      }
      throw new CheckoutException("Rolled back transaction due to exception.", e);
    }

    try {
      sendNotificationAcknowledgment(
          serialNumber, dispatcher.response, notification, dispatcher.request);
    } catch (Exception e) {
      // No big deal.  If Checkout re-sends this notification
      // dispatcher.hasAlreadyHandled() will return true.
      logger.log(Level.FINE, "Exception while sending notification acknowledgment:", e);
    }
  }


  /**
   * Returns the a JAXB notification object for the given request.
   *
   * Can handle XML, HTML and serial-number notification. For HTML and serial-number notifications,
   * makes a call to Checkout to get the notification XML.  For XML requests, validates
   * the basic auth headers.
   *
   * @throws CheckoutException if the basic auth header was invalid or the notification could not be
   *    retrieved.
   */
  public Notification getNotificationFromRequest(HttpServletRequest request) throws CheckoutException {
    if (Utils.isSerialNumberRequest(request)) {
      String serialNumber = request.getParameter(SERIAL_NUMBER_PARAMETER);
      if (serialNumber == null) {
        throw new CheckoutException("Couldn't find serial number in parameters");
      }
      return apiContext.reportsRequester().requestNotification(serialNumber);
    } else {
      String auth = request.getHeader("Authorization");
      if (!apiContext.isValidAuth(auth)) {
        throw new CheckoutException("Invalid auth found");
      }
      try {
        return (Notification)Utils.fromXML(request.getInputStream()).getValue();
      } catch (Exception e) {
        throw new CheckoutException("Could not retrieve notification", e);
      }
    }
  }
}
