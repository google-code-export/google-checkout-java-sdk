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

package com.google.checkout.merchantcalculation;

import com.google.checkout.CheckoutException;
import com.google.checkout.util.TestUtils;
import com.google.checkout.notification.ShoppingCart;
import com.google.checkout.util.Utils;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import junit.framework.TestCase;
import org.w3c.dom.Document;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class MerchantCalculationCallbackTest extends TestCase {
  private String merchantCalculationCallbackMessage;
  private MerchantCalculationCallback merchantCallback;
  
  public void setUp() {
    merchantCalculationCallbackMessage = TestUtils
      .readMessage("/resources/merchant-calculation-callback-sample.xml");
    
    try {
      Document doc = Utils
        .newDocumentFromString(merchantCalculationCallbackMessage);
      merchantCallback = new MerchantCalculationCallback(doc);
    } catch (CheckoutException ex) {
      fail();
    }
  }
  
  public void testAnonymousAddresses() {
    Collection anonymousAddresses = merchantCallback.getAnonymousAddresses();
    Iterator it = anonymousAddresses.iterator();
    
    AnonymousAddress address1 = (AnonymousAddress)it.next();
    
    // verify the first anonymous address
    assertEquals("Mountain View", address1.getCity());
    assertEquals("US", address1.getCountryCode());
    assertEquals("739030698069958", address1.getId());
    assertEquals("94043", address1.getPostalCode());
    assertEquals("CA", address1.getRegion());
    
    // verify the second anonymous address
    AnonymousAddress address2 = (AnonymousAddress)it.next();
    assertEquals("New York", address2.getCity());
    assertEquals("US", address1.getCountryCode());
    assertEquals("421273450774618", address1.getId());
    assertEquals("10022", address1.getPostalCode());
    assertEquals("NY", address1.getRegion());
  }
  
  public void testBuyerId() {
    assertEquals(294873009217523L, merchantCallback.getBuyerId());
  }
  
  public void testBuyerLanguage() {
    assertEquals("en_US", merchantCallback.getBuyerLanguage());
  }
  
  public void testShippingMethods() {
    Collection shippingMethods = merchantCallback.getShippingMethods();
    Iterator it = shippingMethods.iterator();
    
    String method1 = (String)it.next();
    assertEquals("SuperShip", method1);
    
    String method2 = (String)it.next();
    assertEquals("UPS Ground", method2);
  }
}
