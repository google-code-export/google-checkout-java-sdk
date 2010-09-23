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

/**
 * An implementation of EnvironmentInterface that builds itself
 * from format strings.
 *
 * Use the constants {@link #PRODUCTION} and {@link #SANDBOX} for the
 * appropriate environments. Since merchant ID and merchant key values are
 * different in these environments, it's a good idea to reference this class
 * only when you are creating an {@link ApiContext}, since then you can ensure
 * that all three (id, key, and environment) match.
 *
 */
public class Environment implements EnvironmentInterface {
  /**
   * The real Checkout environment. This environment charges users,
   * charges fees, and gives payouts.
   */
  public static final Environment PRODUCTION = new Environment(
      "https://checkout.google.com/api/checkout/v2/merchantCheckout/Merchant/%s",
      "https://checkout.google.com/api/checkout/v2/request/Merchant/%s",
      "https://checkout.google.com/api/checkout/v2/reports/Merchant/%s");

  /**
   * The test Checkout environment. This environment does not authenticate
   * credit cards, does not charge anyone, and cannot generate real revenue.
   * Orders can, however, be placed in this environment, and they go through
   * the full order lifecycle.
   */
  public static final Environment SANDBOX = new Environment(
      "https://sandbox.google.com/checkout/api/checkout/v2/merchantCheckout/Merchant/%s",
      "https://sandbox.google.com/checkout/api/checkout/v2/request/Merchant/%s",
      "https://sandbox.google.com/checkout/api/checkout/v2/reports/Merchant/%s");

  private final String cartPostUrlFormatString;
  private final String orderProcessingUrlFormatString;
  private final String reportsUrlFormatString;

  /**
   * For unit testing purposes, you may create your own Environments, using urls
   * which you presumably control. Otherwise, {@link #PRODUCTION} or
   * {@link #SANDBOX} are more appropriate.
   * @param cartPostUrlFormatString Where to send carts to be purchased.
   * @param postPurchaseUrlFormatString Where to send commands.
   * @param reportsUrlFormatString Whence to request order data.
   */
  public Environment(
      String cartPostUrlFormatString,
      String postPurchaseUrlFormatString,
      String reportsUrlFormatString) {
    this.cartPostUrlFormatString = cartPostUrlFormatString;
    this.orderProcessingUrlFormatString = postPurchaseUrlFormatString;
    this.reportsUrlFormatString = reportsUrlFormatString;
  }

  /**
   * You should never have to directly invoke this method. Instead, it's used
   * by {@link ApiContext}'s helper objects to, ultimately, communicate with
   * Google Checkout.
   * @return The appropriate URL for the requested command.
   */
  @Override
  public String getUrl(CommandType command, String merchantId) {
    switch(command) {
      case CART_POST:
        return String.format(cartPostUrlFormatString, merchantId);
      case ORDER_PROCESSING:
        return String.format(orderProcessingUrlFormatString, merchantId);
      case REPORTS:
        return String.format(reportsUrlFormatString, merchantId);
      default:
        throw new IllegalArgumentException("Unrecognized command: " + command);
    }
  }
}
