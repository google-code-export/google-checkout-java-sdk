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

package com.google.checkout.notification;

import com.google.checkout.CheckoutException;
import com.google.checkout.util.TestUtils;
import com.google.checkout.util.Utils;

import junit.framework.TestCase;

import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class OrderStateChangeNotificationTest extends TestCase {
  private String orderStateChangeNotificationMessage;

  private OrderStateChangeNotification orderStateChangeNotification;

  public void setUp() {
    orderStateChangeNotificationMessage =
        TestUtils
            .readMessage("/resources/order-state-change-notification-sample.xml");

    try {
      Document doc =
          Utils.newDocumentFromString(orderStateChangeNotificationMessage);

      orderStateChangeNotification = new OrderStateChangeNotification(doc);
    } catch (CheckoutException ex) {
      fail();
    }
  }

  public void testGetNewFulfillmentOrderState() {
    FulfillmentOrderState state =
        orderStateChangeNotification.getNewFulfillmentOrderState();

    assertEquals("NEW", state.toString());
  }

  public void testGetNewFinancialOrderState() {
    FinancialOrderState state =
        orderStateChangeNotification.getNewFinancialOrderState();

    assertEquals("CHARGING", state.toString());
  }

  public void testGetPreviousFulfillmentOrderState() {
    FulfillmentOrderState state =
        orderStateChangeNotification.getPreviousFulfillmentOrderState();

    assertEquals("NEW", state.toString());
  }

  public void testGetPreviousFinancialOrderState() {
    FinancialOrderState state =
        orderStateChangeNotification.getPreviousFinancialOrderState();

    assertEquals("CHARGEABLE", state.toString());
  }

  public void testIsSerializable() throws IOException {

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(out);
    oos.writeObject(orderStateChangeNotification);
    oos.close();
    assertTrue(out.toByteArray().length > 0);
  }
}
