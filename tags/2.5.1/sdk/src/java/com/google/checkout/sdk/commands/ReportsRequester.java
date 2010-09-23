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

import com.google.checkout.sdk.commands.EnvironmentInterface.CommandType;
import com.google.checkout.sdk.domain.NotificationHistoryRequest;
import com.google.checkout.sdk.domain.OrderSummary;
import com.google.checkout.sdk.domain.OrderSummaryRequest;
import com.google.checkout.sdk.domain.OrderSummaryResponse;
import com.google.checkout.sdk.notifications.Notification;

import java.util.List;

import javax.xml.bind.JAXBElement;

/**
 * Makes and posts Google Checkout report requests.
 *
 */
public class ReportsRequester {

  private final ApiContext apiContext;

  ReportsRequester(ApiContext apiContext) {
    this.apiContext = apiContext;
  }

  /**
   * Fetches the notification object XML which corresponds to
   * {@code serialNumber}. This object is an element of
   * {@code com.google.checkout.sdk.domain}:
   * <ul>
   *   <li>{@link com.google.checkout.sdk.domain.NewOrderNotification}</li>
   *   <li>{@link com.google.checkout.sdk.domain.AuthorizationAmountNotification}</li>
   *   <li>{@link com.google.checkout.sdk.domain.OrderStateChangeNotification}</li>
   *   <li>And so on.</li>
   * </ul>
   * @param serialNumber The serial number of the notification to fetch.
   * @return The notification corresponding to the serial number.
   * @throws CheckoutException If underlying communication throws an exception.
   */
  public Notification requestNotification(String serialNumber) throws CheckoutException {
    NotificationHistoryRequest request = new NotificationHistoryRequest();
    request.setSerialNumber(serialNumber);
    // this is a specific notification domain object.
    return (Notification)postRequest(request.toJAXB());
  }

  /**
   * Fetches the order summaries which correspond to the specified
   * {@code googleOrderNumbers}. They will reflect the current state of those
   * orders.
   * @param googleOrderNumbers The order ids of interest.
   * @return A list of the relevant objects
   * @throws CheckoutException If underlying communication throws an exception.
   */
  public List<OrderSummary> requestOrderSummaries(List<String> googleOrderNumbers)
      throws CheckoutException {
    OrderSummaryRequest request = new OrderSummaryRequest();
    OrderSummaryRequest.OrderNumbers orderNumbers = new OrderSummaryRequest.OrderNumbers();
    orderNumbers.getGoogleOrderNumber().addAll(googleOrderNumbers);
    request.setOrderNumbers(orderNumbers);

    OrderSummaryResponse response = (OrderSummaryResponse)postRequest(request.toJAXB());

    return response.getOrderSummaries().getOrderSummary();
  }

  protected Object postRequest(JAXBElement<?> request) {
    return apiContext.postCommand(CommandType.REPORTS, request);
  }
}
