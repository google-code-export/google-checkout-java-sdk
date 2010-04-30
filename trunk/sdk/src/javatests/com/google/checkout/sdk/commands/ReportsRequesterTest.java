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

import com.google.checkout.sdk.domain.FinancialOrderState;
import com.google.checkout.sdk.domain.FulfillmentOrderState;
import com.google.checkout.sdk.domain.OrderStateChangeNotification;
import com.google.checkout.sdk.testing.AbstractCommandTestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Tests for fetching data about Google Checkout orders.
 * 
*
 */
public class ReportsRequesterTest extends AbstractCommandTestCase {
  private static String DATE_STRING = "2007-03-19T15:06:29.051Z";
  private static String EXAMPLE_NOTIFICATION =
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
      + "<order-state-change-notification xmlns=\"http://checkout.google.com/schema/2\""
      + "    serial-number=\"c821426e-7caa-4d51-9b2e-48ef7ecd6423\">"
      + "    <google-order-number>841171949013218</google-order-number>"
      + "    <new-financial-order-state>CHARGING</new-financial-order-state>"
      + "    <new-fulfillment-order-state>NEW</new-fulfillment-order-state>"
      + "    <previous-financial-order-state>CHARGEABLE</previous-financial-order-state>"
      + "    <previous-fulfillment-order-state>NEW</previous-fulfillment-order-state>"
      + "    <timestamp>" + DATE_STRING + "</timestamp>"
      + "</order-state-change-notification>";

  public void testGettingUrl() throws Exception {
    ByteArrayInputStream input = new ByteArrayInputStream(EXAMPLE_NOTIFICATION.getBytes("utf-8"));
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    ByteArrayInputStream error = new ByteArrayInputStream("".getBytes());
    Object object = new TestingApiContext(environment, input, output, error)
        .reportsRequester()
        // the specific number is mocked and doesn't matter, but the parsing _does_...
        .requestNotification("c821426e-7caa-4d51-9b2e-48ef7ecd6423");
    OrderStateChangeNotification oscn = (OrderStateChangeNotification)object;

    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
        + "<notification-history-request xmlns=\"http://checkout.google.com/schema/2\">\n"
        + "    <serial-number>c821426e-7caa-4d51-9b2e-48ef7ecd6423</serial-number>\n"
        + "</notification-history-request>\n", output.toString("utf-8"));

    
    // textually identical to the XML EXAMPLE_NOTIFICATION string.
    assertEquals("841171949013218", oscn.getGoogleOrderNumber());
    assertEquals(FinancialOrderState.CHARGING, oscn.getNewFinancialOrderState());
    assertEquals(FinancialOrderState.CHARGEABLE, oscn.getPreviousFinancialOrderState());
    assertEquals(FulfillmentOrderState.NEW, oscn.getNewFulfillmentOrderState());
    assertEquals(FulfillmentOrderState.NEW, oscn.getPreviousFulfillmentOrderState());
    assertEquals(DATE_STRING, oscn.getTimestamp().toXMLFormat());    
  }
}
