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

import com.google.checkout.CheckoutResponse;
import com.google.checkout.MerchantInfo;
import com.google.checkout.util.TestUtils;

import junit.framework.TestCase;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author Charles Dang (cdang@google.com)
 */
public class CheckoutShoppingCartRequestTest extends TestCase {
  public void testCheckoutShoppingCartRequestNodeNames() {
    MerchantInfo mi = TestUtils.createMockMerchantInfo();
    
    try {
      CheckoutShoppingCartRequest cartRequest = 
        new CheckoutShoppingCartRequest(mi);
      
      cartRequest.addAlternateTaxRule("tableName", true, 8.25, new TaxArea());
      cartRequest
        .addCarrierCalculatedShippingOption(7.5f, "shippingCompany", 
        CarrierPickup.REGULAR_PICKUP, "FAST", 1.1f, 2.5f);
      cartRequest.addDefaultTaxRule(2.2, true, new TaxArea());
      cartRequest.addFlatRateShippingMethod("name", 2.5f, 
        new ShippingRestrictions());
      cartRequest.addItem(new Item());
      cartRequest.addMerchantCalculatedShippingMethod("name", 3.3f, 
        new ShippingRestrictions(), new AddressFilters());
      try {
        cartRequest.addParameterizedUrl("http://checkout.google.com", true);
      } catch (UnsupportedEncodingException ex) {
        fail();
      }
      
      cartRequest.addPickupShippingMethod("name", 2.3f);
      cartRequest.addShippingPackage(DeliveryAddressCategory.RESIDENTIAL, "m", 
        5.4f, "cm", 400f, "mm", 3000f, Packaging.Box, new ShipFrom());
      
      System.out.println(cartRequest.getXml());
    } catch (IllegalArgumentException ex) {
      fail();
    }
  }
  
  public void testCheckoutShoppingCartRequestWithNullMerchantInfo() {
    MerchantInfo mi = null;
    
    try {
      CheckoutShoppingCartRequest cartRequest = 
        new CheckoutShoppingCartRequest(mi);
      fail();
    } catch (IllegalArgumentException ex) {
      
    }
  }
  
  //Make sure issue 50 is fixed.
  //http://code.google.com/p/google-checkout-java-sdk/issues/detail?id=50
  public void testCheckoutShoppingCartWithLargeNumberOfItems() {
      MerchantInfo mi = TestUtils.createMockMerchantInfo();
      
      try {
        CheckoutShoppingCartRequest cartRequest = 
          new CheckoutShoppingCartRequest(mi);
        
        cartRequest.addItem("Test Item", "Test Item", 10f, 10000);
        
        CheckoutResponse res = cartRequest.send();
        assertTrue(res.isValidRequest());
        
        System.out.println(cartRequest.getXml());
      } catch (Exception ex) {
        fail();
      }
    }
  
  //Make sure issue 52 is fixed.
  //http://code.google.com/p/google-checkout-java-sdk/issues/detail?id=52
  public void testAddParameterizedUrl() {
    MerchantInfo mi = TestUtils.createMockMerchantInfo();
    
    try {
      CheckoutShoppingCartRequest cartRequest = 
        new CheckoutShoppingCartRequest(mi);
      
      cartRequest.addItem("Test Item", "Test Item", 10f, 1);
      cartRequest.addParameterizedUrl("http://www.google.com");
      
      CheckoutResponse res = cartRequest.send();
      assertTrue(res.isValidRequest());
      
      System.out.println(cartRequest.getXml());
    } catch (Exception ex) {
      fail();
    }
  }  
  
  public void testGoodUntilDate() {    MerchantInfo mi = TestUtils.createMockMerchantInfo();
  
  try {
    CheckoutShoppingCartRequest cartRequest = 
      new CheckoutShoppingCartRequest(mi);
    
    cartRequest.addItem("Test Item", "Test Item", 10f, 1);
    cartRequest.setExpirationMinutesFromNow(10);
    System.out.println(cartRequest.getXml());
    
    CheckoutResponse res = cartRequest.send();
    System.out.println(res.getXmlPretty());
    
    assertTrue(res.isValidRequest());
  } catch (Exception ex) {
    ex.printStackTrace();
    fail();
  }
    
  }
}
