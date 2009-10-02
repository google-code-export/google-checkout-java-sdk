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

package com.google.checkout.checkout;

import com.google.checkout.CheckoutException;
import com.google.checkout.CheckoutResponse;
import com.google.checkout.MerchantInfo;
import com.google.checkout.util.TestUtils;
import com.google.checkout.notification.CarrierCalculatedShippingAdjustment;
import com.google.checkout.notification.NewOrderNotification;

import junit.framework.TestCase;

import java.util.Collection;
import java.util.Iterator;

public class TestCarrierCalculatedShipping extends TestCase {

  public void testCarrierCalculatedShipping1() throws CheckoutException {
    MerchantInfo mi = TestUtils.createMockMerchantInfo();
    String msg =
        TestUtils.readMessage("/com/google/checkout/checkout/ccs-sample-1.xml");

    CheckoutShoppingCartRequest cart = new CheckoutShoppingCartRequest(mi);
    Item item = new Item();
    item.setMerchantItemId("5LBDOGCHOW");
    item.setItemName("5 lbs. Dog Food");
    item.setItemDescription("A 5 lb. bag of nutritious dog food.");
    item.setUnitPriceAmount(35.00f);
    item.setUnitPriceCurrency("USD");
    item.setQuantity((new Integer(1)).intValue());
    item.setItemWeight(5.5f);
    item.setItemWeightUnit("LB");
    cart.addItem(item);

    cart.addCarrierCalculatedShippingOption(10.00f, "FedEx",
        CarrierPickup.REGULAR_PICKUP, "Priority Overnight", 0, 0);

    ShipFrom shipFrom = new ShipFrom();
    shipFrom.setId("ABC");
    shipFrom.setCity("New York");
    shipFrom.setRegion("NY");
    shipFrom.setCountryCode("US");
    shipFrom.setPostalCode("10022");
    cart.addShippingPackage(null, "IN", 6, "IN", 15, "IN", 24, null, shipFrom);

    CheckoutResponse res = cart.send();
    System.out.println(cart.getXmlPretty());

    System.out.println(res.getXmlPretty());
    assertTrue(res.isValidRequest());
  }

  public void testCarrierCalculatedShipping2() throws CheckoutException {
    String msg =
        TestUtils
            .readMessage("/com/google/checkout/checkout/ccs-non-sample-1.xml");

    NewOrderNotification non = new NewOrderNotification(msg);
    Collection items = non.getItems();
    Iterator itemsIt = items.iterator();
    while (itemsIt.hasNext()) {
      Item i = (Item) itemsIt.next();
      assertEquals(i.getItemWeight(), 5.5f, 0);
      assertEquals(i.getItemWeightUnit(), "LB");
    }
    assertTrue(non.getShipping() instanceof CarrierCalculatedShippingAdjustment);
    assertEquals(non.getShipping().getShippingName(), "Carrier Shipping");
    assertEquals(non.getShipping().getShippingCost(), 9.95f, 0);
  }
}
