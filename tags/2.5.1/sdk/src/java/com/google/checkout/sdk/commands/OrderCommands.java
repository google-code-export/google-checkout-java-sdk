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
package com.google.checkout.sdk.commands;

import com.google.checkout.sdk.domain.AuthorizationAmountNotification;
import com.google.checkout.sdk.domain.ChargeAmountNotification;
import com.google.checkout.sdk.domain.ItemId;
import com.google.checkout.sdk.domain.OrderStateChangeNotification;
import com.google.checkout.sdk.domain.OrderSummary;
import com.google.checkout.sdk.domain.RefundAmountNotification;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>Makes and posts Google Checkout post-order processing commands for
 * the specified order. This can be used as follows:
 * <code>
 * ApiContext apiContext = new ApiContext(...);
 * String orderNumber = ...;
 * apiContext.orderCommands(orderNumber)
 *      .chargeAndShipOrder();
 * </code>
 * which will, naturally enough, charge and ship the {@code orderNumber}.</p>
 * <p>All methods except for {@link #itemCommands} methods will actually take
 * action against Google Checkout before they return, and will return any
 * response Checkout makes.</p>
 *
 */
public interface OrderCommands {

  /**
   * Causes Google Checkout to cancel an order. It is an error to invoke this
   * on an order which has any amount of money charged but not yet refunded.
   * @param reason Why you are canceling this order. This will be shown to the
   *    buyer on their receipt page.
   * @return The state change transition XML object into the Canceled state.
   * @throws CheckoutException If underlying communication throws an exception.
   * @see #refundOrder
   */
  OrderStateChangeNotification cancelOrder(
      String reason) throws CheckoutException;


  /**
   * Refund the entire outstanding charged amount of an order.
   * @param reason Why you are refunding this order. This will be shown to the
   *    buyer on their receipt page.
   * @return The refund amount XML object representing this change.
   * @throws CheckoutException If underlying communication throws an exception.
   * @see #refundOrder(String, BigDecimal)
   */
  RefundAmountNotification refundOrder(
      String reason) throws CheckoutException;

  /**
   * @see #refundOrder(String, BigDecimal)
   */
  RefundAmountNotification refundOrder(
      String reason, double amount) throws CheckoutException;

  /**
   * Refund the specified amount (which must be less than or equal to the
   * currently charged amount) of an order.
   * @param reason Why you are refunding this order. This will be shown to the
   *    buyer on their receipt page.
   * @param amount The amount to refund.
   * @return The refund amount XML object representing this change.
   * @throws CheckoutException If underlying communication throws an exception.
   */
  RefundAmountNotification refundOrder(
      String reason, BigDecimal amount) throws CheckoutException;


  /**
   * Entirely charge an order and mark it as shipped.
   * @return The XML object representing this change, which can be used to learn
   *    fee information.
   * @throws CheckoutException If underlying communication throws an exception.
   */
  ChargeAmountNotification chargeAndShipOrder() throws CheckoutException;

  /**
   * @see #chargeAndShipOrder(BigDecimal)
   */
  ChargeAmountNotification chargeAndShipOrder(
      double amount) throws CheckoutException;

  /**
   * Charge an order in the specified amount -- which must be less than or equal
   * to the total value of the order minus the currently charged amount -- and
   * mark it as shipped.
   * @param amount The amount to charge.
   * @return The XML object representing this change, which can be used to learn
   *    fee information.
   * @throws CheckoutException If underlying communication throws an exception.
   */
  ChargeAmountNotification chargeAndShipOrder(
      BigDecimal amount) throws CheckoutException;

  /**
   * Entirely charge an order and mark all items in it as shipped using the
   * specified tracking data.
   * @param shipping The package tracking data.
   * @return The XML object representing this change, which can be used to learn
   *    fee information.
   * @throws CheckoutException If underlying communication throws an exception.
   */
  ChargeAmountNotification chargeAndShipOrder(
      TrackingDataBuilder shipping) throws CheckoutException;

  /**
   * @see #chargeAndShipOrder(BigDecimal, TrackingDataBuilder)
   */
  ChargeAmountNotification chargeAndShipOrder(
      double amount, TrackingDataBuilder shipping) throws CheckoutException;

  /**
   * Charge an order in the specified amount -- which must be less than or equal
   * to the total value of the order minus the currently charged amount -- and
   * mark all items in it as shipped using the specified tracking data.
   * @param amount The amount to charge.
   * @param shipping The package tracking data.
   * @return The XML object representing this change, which can be used to learn
   *    fee information.
   * @throws CheckoutException If underlying communication throws an exception.
   */
  ChargeAmountNotification chargeAndShipOrder(
      BigDecimal amount, TrackingDataBuilder shipping) throws CheckoutException;

  /**
   * Entirely charge an order and mark specified items as shipped according to
   * the {@link ItemShippingInformationBuilder}. This may leave some items
   * unshipped.
   * @param shipping The package tracking data.
   * @return The XML object representing this change, which can be used to learn
   *    fee information.
   * @throws CheckoutException If underlying communication throws an exception.
   */
  ChargeAmountNotification chargeAndShipOrder(
      ItemShippingInformationBuilder shipping) throws CheckoutException;

  /**
   * @see #chargeAndShipOrder(BigDecimal, ItemShippingInformationBuilder)
   */
  ChargeAmountNotification chargeAndShipOrder(
      double amount, ItemShippingInformationBuilder shipping) throws CheckoutException;

  /**
   * Charge an order in the specified amount -- which must be less than or equal
   * to the total value of the order minus the currently charged amount -- and
   * mark specified items as shipped according to the
   * {@link ItemShippingInformationBuilder}. This may leave some items unshipped.
   * @param amount The amount to charge.
   * @param shipping The package tracking data.
   * @return The XML object representing this change, which can be used to learn
   *    fee information.
   * @throws CheckoutException If underlying communication throws an exception.
   */
  ChargeAmountNotification chargeAndShipOrder(
      BigDecimal amount, ItemShippingInformationBuilder shipping) throws CheckoutException;


  /**
   * Requests an additional auth of an order. <i>This method only appears to be
   * synchronous!</i> It returns after communicating with Google Checkout, but
   * the order may take several days to become authorized.
   * You must listen for an {@link AuthorizationAmountNotification} before being
   * able to charge this order, and it is not guaranteed that you will receive
   * one for various reasons. A successful return from this method
   * does not indicate that the order will definitely be authorized.
   * @throws CheckoutException If underlying communication throws an exception.
   */
  void authorizeOrder() throws CheckoutException;

  /**
   * Mark specified items as shipped according to the
   * {@link ItemShippingInformationBuilder}. This may leave some items unshipped.
   * @param shipping The package tracking data.
   * @throws CheckoutException If underlying communication throws an exception.
   */
  void shipItems(
      ItemShippingInformationBuilder shipping) throws CheckoutException;

  /**
   * Mark the order as "archived", which removes it from the UI and sets the
   * "archived" bit on all future notifications.
   * @throws CheckoutException If underlying communication throws an exception.
   */
  void archiveOrder() throws CheckoutException;

  /**
   * Unmark the order as "archived". It will now appear in the UI again, and
   * unset the "archived" bit on future notifications.
   * @throws CheckoutException If underlying communication throws an exception.
   */
  void unarchiveOrder() throws CheckoutException;

  /**
   * Convenience method. This will fetch the Order Summary element for this
   * order, equivalent to {@link ReportsRequester#requestNotification(String)}.
   * @return The OrderSummary element for this {@code OrderCommands}'s order.
   * @throws CheckoutException If underlying communication throws an exception.
   */
  OrderSummary getOrderSummary() throws CheckoutException;

  /**
   * Returns an {@link ItemCommands} object for this order and the specified
   * merchant item ids.
   * @param itemIds A list of merchant item ids.
   * @return A fluent helper for item-level operations.
   * @throws CheckoutException If underlying communication throws an exception.
   */
  ItemCommands itemCommands(List<String> itemIds) throws CheckoutException;

  /**
   * Returns an {@link ItemCommands} object for this order and the specified
   * merchant item ids.
   * @param itemIds An array of merchant item ids.
   * @return A fluent helper for item-level operations.
   * @throws CheckoutException If underlying communication throws an exception.
   */
  ItemCommands itemCommands(String... itemIds) throws CheckoutException;

  /**
   * Returns an {@link ItemCommands} object for this order and the specified
   * merchant item ids.
   * @param itemIds An array of item id XML objects.
   * @return A fluent helper for item-level operations.
   * @throws CheckoutException If underlying communication throws an exception.
   */
  ItemCommands itemCommands(ItemId... itemIds) throws CheckoutException;

  /**
   * <p>Makes and posts Google Checkout item-level commands for the specified
   * set of items. This can be used as follows:
   * <code>
   * ApiContext apiContext = new ApiContext(...);
   * String orderNumber = ...;
   * String itemIds = ...;
   * apiContext.orderCommands(orderNumber).itemCommands(itemIds)
   *      .cancelItems();
   * </code>
   * which will, naturally enough, cancel {@code itemIds} from order
   * {@code orderNumber}.</p>
   * <p>All methods actually take action against Google Checkout before they
   * return. Because the commands are oriented around merchant item ids, you
   * <i>must</i> specify merchant item ids on the initial order: there is no way
   * to add these after the fact, or use this class without specifying them.</p>
   * <p> There's a slight asymmetry between
   * {@link ItemCommands#shipItems(String, String)} and
   * {@link OrderCommands#shipItems}. If you wish to ship individual items each
   * with their own tracking data, consider the latter, as it is more expressive.
   * </p>
   *
   * @see OrderCommands#itemCommands
   */
  public static interface ItemCommands {

    /**
     * @param reason Why the items are being canceled
     * @throws CheckoutException If underlying communication throws an exception.
     */
    void cancelItems(String reason) throws CheckoutException;

    /**
     * @throws CheckoutException If underlying communication throws an exception.
     */
    void backorderItems() throws CheckoutException;

    /**
     * @throws CheckoutException If underlying communication throws an exception.
     */
    void returnItems() throws CheckoutException;

    /**
     * Applies the carrier and tracking number as tracking data to this
     * {@code ItemCommands}'s set of items (shipping all of the items in one
     * box). If you wish to apply different tracking data to each item, you must
     * use {@link OrderCommands#shipItems}.
     * @param carrier The carrier for this shipment, such as USPS, FedEx, or UPS.
     * @param trackingNumber The tracking number for this shipment, as given by
     *  the carrier.
     * @throws CheckoutException If underlying communication throws an exception.
     */
    void shipItems(String carrier, String trackingNumber) throws CheckoutException;
  }
}
