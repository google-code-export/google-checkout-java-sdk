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

import com.google.checkout.sdk.domain.AuthorizationAmountNotification;
import com.google.checkout.sdk.domain.ChargeAmountNotification;
import com.google.checkout.sdk.domain.ChargebackAmountNotification;
import com.google.checkout.sdk.domain.NewOrderNotification;
import com.google.checkout.sdk.domain.OrderStateChangeNotification;
import com.google.checkout.sdk.domain.OrderSummary;
import com.google.checkout.sdk.domain.RefundAmountNotification;
import com.google.checkout.sdk.domain.RiskInformationNotification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>This is the root type to be used in manufacturing your own checkout
 * notification servlet. You should extend this class with your business logic.
 * You should use this class if you are interested in using
 * {@link NotificationHandler#handleNotification} to do either or both of:
 * <ol>
 *  <li>handling notifications</li>
 *  <li>handling database transactionality</li>
 * </ol>
 * </p>
 *
 * <p>To provide business logic for handling a particular notification, override
 * the {@code onFooNotification} methods.  If you have business logic that
 * applies to all notifications, such as storing the OrderSummary from all
 * notifications in the database or logging each notification to {@code stderr},
 * you can override the {@link #onAllNotifications} method. It is invoked before
 * the more specific {@code onFooNotification} method.</p>
 *
 * <p>To glue your business logic to database transactionality you'll need to
 * override the transaction logic methods:
 * <ol>
 *  <li>{@link #startTransaction}</li>
 *  <li>{@link #rollBackTransaction}</li>
 *  <li>{@link #commitTransaction}</li>
 * </ol>
 * and to make sure each notification is processed exactly once, you'll need to
 * override the notification transactional logic methods:
 * <ol>
 *  <li>{@link #hasAlreadyHandled}</li>
 *  <li>{@link #rememberSerialNumber}</li>
 * </ol>
 * If you're using a framework to manage your own transactions, you should
 * either plug {@link #startTransaction}, {@link #rollBackTransaction},
 * and {@link #commitTransaction} into this framework, or leave them
 * unimplemented. You should try to make sure that (as the example code below
 * does) {@link #commitTransaction} or its equivalent is executed before any
 * acknowledgment is sent back to Google Checkout.</p>
 *
 * <p>To use this class: in a Servlet that you've made to handle the
 * {@code POST}s from Google Checkout, pass in a new instance of your
 * subclass of {@link BaseNotificationDispatcher} to
 * {@link NotificationHandler#handleNotification} like so:
 * <code>
 * public class MyNotificationDispatcher extends BaseNotificationDispatcher {...}
 *
 * public class MyServlet extends HttpServlet {
 *   public void doPost(HttpServletRequest request, HttpServletResponse response) {
 *     apiContext.handleNotification(
 *          new MyNotificationDispatcher(request, response));
 *   }
 * }
 * </code>
 * </p>
*
 */
public abstract class BaseNotificationDispatcher {
  final HttpServletRequest request;
  final HttpServletResponse response;

  protected BaseNotificationDispatcher(
      HttpServletRequest request, HttpServletResponse response) {
    this.request = request;
    this.response = response;
  }

  /**
   * With a read lock, determine whether this notification has already been
   * handled.
   * @param serialNumber The serial number of this notification, which is
   *    unique to this order/notification pair, but stable across each attempt
   *    to deliver the notification.
   * @param orderSummary The state of the order whose notification is currently
   *    being handled.
   * @param notification The parsed JAXB object of the notification itself.
   * @return True if this notification has already been successfully handled and
   *    committed; otherwise false.
   */
  protected abstract boolean hasAlreadyHandled(
      String serialNumber,
      OrderSummary orderSummary,
      Notification notification) throws Exception;


  /**
   * Save away the serialNumber so that if hasAlreadyHandled is called with this
   * serial number in the future, it will return "true".
   * @param serialNumber The serial number of this notification, which is
   *    unique to this order/notification pair, but stable across each attempt
   *    to deliver the notification.
   * @param orderSummary The state of the order whose notification is currently
   *    being handled.
   * @param notification The parsed JAXB object of the notification itself.
   */
  protected abstract void rememberSerialNumber(
      String serialNumber,
      OrderSummary orderSummary,
      Notification notification) throws Exception;

  /**
   * Start a new database transaction for handling this notification.
   * @param serialNumber The serial number of this notification, which is
   *    unique to this order/notification pair, but stable across each attempt
   *    to deliver the notification.
   * @param orderSummary The state of the order whose notification is currently
   *    being handled.
   * @param notification The parsed JAXB object of the notification itself.
   */
  protected void startTransaction(
      String serialNumber,
      OrderSummary orderSummary,
      Notification notification) throws Exception {
  }

  /**
   * The notification was successfully handled, so commit the current
   * transaction.
   * @param serialNumber The serial number of this notification, which is
   *    unique to this order/notification pair, but stable across each attempt
   *    to deliver the notification.
   * @param orderSummary The state of the order whose notification is currently
   *    being handled.
   * @param notification The parsed JAXB object of the notification itself.
   */
  protected void commitTransaction(
      String serialNumber,
      OrderSummary orderSummary,
      Notification notification) throws Exception {
  }

  /**
   * An error occurred while handling the notification, so roll back the current
   * transaction.
   * @param serialNumber The serial number of this notification, which is
   *    unique to this order/notification pair, but stable across each attempt
   *    to deliver the notification.
   * @param orderSummary The state of the order whose notification is currently
   *    being handled.
   * @param notification The parsed JAXB object of the notification itself.
   */
  protected void rollBackTransaction(
      String serialNumber,
      OrderSummary orderSummary,
      Notification notification) throws Exception {
  }

  /**
   * Should be overridden with the behavior that should happen when your system
   * encounters any new notification from Checkout.  This method will be called
   * before the specific onFooNotification() methods.
   * @param orderSummary The parsed OrderSummary object from the notification.
   * @param notification The parsed JAXB object of the notification itself.
   */
  protected void onAllNotifications(
      OrderSummary orderSummary,
      Notification notification) throws Exception {
  }

  /**
   * Should be overridden with the behavior that should happen when your system
   * encounters a not-yet-handled RefundAmountNotification.
   * @param orderSummary The parsed OrderSummary object from the notification.
   * @param notification The parsed JAXB object of the notification itself.
   */
  protected void onRefundAmountNotification(
      OrderSummary orderSummary,
      RefundAmountNotification notification) throws Exception {
  }

  /**
   * Should be overridden with the behavior that should happen when your system
   * encounters a not-yet-handled RiskInformationNotification.
   * @param orderSummary The parsed OrderSummary object from the notification.
   * @param notification The parsed JAXB object of the notification itself.
   */
  protected void onRiskInformationNotification(
      OrderSummary orderSummary,
      RiskInformationNotification notification) throws Exception {
  }

  /**
   * Should be overridden with the behavior that should happen when your system
   * encounters a not-yet-handled OrderStateChangeNotification.
   * @param orderSummary The parsed OrderSummary object from the notification.
   * @param notification The parsed JAXB object of the notification itself.
   */
  protected void onOrderStateChangeNotification(
      OrderSummary orderSummary,
      OrderStateChangeNotification notification) throws Exception {
  }

  /**
   * Should be overridden with the behavior that should happen when your system
   * encounters a not-yet-handled NewOrderNotification.
   * @param orderSummary The parsed OrderSummary object from the notification.
   * @param notification The parsed JAXB object of the notification itself.
   */
  protected void onNewOrderNotification(
      OrderSummary orderSummary,
      NewOrderNotification notification) throws Exception {
  }

  /**
   * Should be overridden with the behavior that should happen when your system
   * encounters a not-yet-handled ChargebackAmountNotification.
   * @param orderSummary The parsed OrderSummary object from the notification.
   * @param notification The parsed JAXB object of the notification itself.
   */
  protected void onChargebackAmountNotification(
      OrderSummary orderSummary,
      ChargebackAmountNotification notification) throws Exception {
  }

  /**
   * Should be overridden with the behavior that should happen when your system
   * encounters a not-yet-handled ChargeAmountNotification.
   * @param orderSummary The parsed OrderSummary object from the notification.
   * @param notification The parsed JAXB object of the notification itself.
   */
  protected void onChargeAmountNotification(
      OrderSummary orderSummary,
      ChargeAmountNotification notification) throws Exception {
  }

  /**
   * Should be overridden with the behavior that should happen when your system
   * encounters a not-yet-handled AuthorizationAmountNotification.
   * @param orderSummary The parsed OrderSummary object from the notification.
   * @param notification The parsed JAXB object of the notification itself.
   */
  protected void onAuthorizationAmountNotification(
      OrderSummary orderSummary,
      AuthorizationAmountNotification notification) throws Exception {
  }

  /**
   * Should be overridden with the behavior that should happen when your system
   * encounters a not-yet-handled RefundAmountNotification.
   * @param orderSummary The parsed OrderSummary object from the notification.
   * @param notification The parsed JAXB object of the notification itself.
   */
  protected void onRiskAmountNotification(
      OrderSummary orderSummary,
      RefundAmountNotification notification) throws Exception {
  }
}
