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
import com.google.checkout.checkout.Item;
import com.google.checkout.util.TestUtils;
import com.google.checkout.util.Utils;

import junit.framework.TestCase;

import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class NewOrderNotificationTest extends TestCase {
  private String newOrderNotificationMessage;

  private NewOrderNotification newOrderNotification;

  public void setUp() {
    newOrderNotificationMessage =
        TestUtils.readMessage("/resources/new-order-notification-sample.xml");

    try {
      Document doc = Utils.newDocumentFromString(newOrderNotificationMessage);

      newOrderNotification = new NewOrderNotification(doc);
    } catch (CheckoutException ex) {
      fail();
    }
  }

  public void testGetShoppingCart() {
    ShoppingCart cart = newOrderNotification.getShoppingCart();
    try {
      Date cartExpDate = cart.getCartExpiration();
      assertEquals("2007-12-31T23:59:59.000Z", Utils.getDateString(cartExpDate));

      verfifyShoppingCartItems(cart.getItems());
    } catch (CheckoutException ex) {
      fail();
    }
  }

  public void testGetBuyerShippingAddress() {
    Address shippingAddress = newOrderNotification.getBuyerShippingAddress();

    assertEquals("John Smith", shippingAddress.getContactName());
    assertEquals("johnsmith@example.com", shippingAddress.getEmail());
    assertEquals("10 Example Road", shippingAddress.getAddress1());
    assertEquals("Sampleville", shippingAddress.getCity());
    assertEquals("CA", shippingAddress.getRegion());
    assertEquals("94141", shippingAddress.getPostalCode());
    assertEquals("US", shippingAddress.getCountryCode());
  }

  public void testGetBuyerBillingAddress() {
    Address billingAddress = newOrderNotification.getBuyerBillingAddress();

    assertEquals("Bill Hu", billingAddress.getContactName());
    assertEquals("billhu@example.com", billingAddress.getEmail());
    assertEquals("99 Credit Lane", billingAddress.getAddress1());
    assertEquals("Mountain View", billingAddress.getCity());
    assertEquals("CA", billingAddress.getRegion());
    assertEquals("94043", billingAddress.getPostalCode());
    assertEquals("US", billingAddress.getCountryCode());
  }

  public void testGetBuyerMarketingPreferences() {
    BuyerMarketingPreferences preferences =
        newOrderNotification.getBuyerMarketingPreferences();
    assertFalse(preferences.isMarketingEmailAllowed());
  }

  public void testGetOrderAdjustment() {
    OrderAdjustment adjustment = newOrderNotification.getOrderAdjustment();
    assertTrue(adjustment.isMerchantCalculationSuccessful());

    verifyMerchantCodes(adjustment.getMerchantCodes());

    Shipping shipping = adjustment.getShipping();
    assertEquals(9.95f, shipping.getShippingCost(), 0);
    assertEquals("SuperShip", shipping.getShippingName());

    assertEquals(11.05f, adjustment.getTotalTax(), 0);
  }

  public void testGetOrderTotal() {
    assertEquals(190.98f, newOrderNotification.getOrderTotal(), 0);
  }

  public void testGetOrderCurrencyCode() {
    assertEquals("USD", newOrderNotification.getOrderCurrencyCode());
  }

  public void testGetFulfillmentOrderState() {
    FulfillmentOrderState state =
        newOrderNotification.getFulfillmentOrderState();
    assertEquals("NEW", state.toString());
  }

  public void testGetFinancialOrderState() {
    FinancialOrderState state = newOrderNotification.getFinancialOrderState();
    assertEquals("REVIEWING", state.toString());
  }

  public void testGetBuyerId() {
    assertEquals(294873009217523L, newOrderNotification.getBuyerId());
  }

  private void verfifyShoppingCartItems(Collection items) {
    Iterator it = items.iterator();

    Item item1 = (Item) it.next();
    assertEquals("GGLAA1453", item1.getMerchantItemId());
    assertEquals("Dry Food Pack", item1.getItemName());
    assertEquals("One pack of nutritious dried food for emergencies.", item1
        .getItemDescription());
    assertEquals(1, item1.getQuantity());
    assertEquals("food", item1.getTaxTableSelector());
    assertEquals(4.99f, item1.getUnitPriceAmount(), 0);
    assertEquals("USD", item1.getUnitPriceCurrency());

    Item item2 = (Item) it.next();
    assertEquals("MGS2GBMP3", item2.getMerchantItemId());
    assertEquals("Megasound 2GB MP3 Player", item2.getItemName());
    assertEquals("This portable MP3 player stores 500 songs.", item2
        .getItemDescription());
    assertEquals(1, item2.getQuantity());
    assertEquals(179.99f, item2.getUnitPriceAmount(), 0);
    assertEquals("USD", item2.getUnitPriceCurrency());
  }

  private void verifyMerchantCodes(Collection merchantCodes) {
    Iterator it = merchantCodes.iterator();

    CouponAdjustment couponAdjustment = (CouponAdjustment) it.next();
    assertEquals(5.00f, couponAdjustment.getAppliedAmount(), 0);
    assertEquals("FirstVisitCoupon", couponAdjustment.getCode());
    assertEquals(5.00f, couponAdjustment.getCalculatedAmount(), 0);
    assertEquals("You saved $5.00 for your first visit!", couponAdjustment
        .getMessage());

    GiftCertificateAdjustment giftAdjustment =
        (GiftCertificateAdjustment) it.next();
    assertEquals(10.00f, giftAdjustment.getAppliedAmount(), 0);
    assertEquals("GiftCert12345", giftAdjustment.getCode());
    assertEquals(10.00f, giftAdjustment.getCalculatedAmount(), 0);
    assertEquals("You saved $10.00 with this gift certificate!", giftAdjustment
        .getMessage());

  }

  public void testIsSerializable() throws IOException {

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(out);
    oos.writeObject(newOrderNotification);
    oos.close();
    assertTrue(out.toByteArray().length > 0);
  }
}
