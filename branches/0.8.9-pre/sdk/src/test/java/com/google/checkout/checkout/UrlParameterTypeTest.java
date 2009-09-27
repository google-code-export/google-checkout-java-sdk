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

import junit.framework.TestCase;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class UrlParameterTypeTest extends TestCase {
  public void testUrlParameterTypeConstants() {
    assertEquals("order-id", UrlParameterType.OrderID.toString());
    assertEquals("billing-city", UrlParameterType.billingCity.toString());
    assertEquals("billing-country-code", 
      UrlParameterType.billingCountryCode.toString());
    assertEquals("billing-postal-code", 
      UrlParameterType.billingPostalCode.toString());
    assertEquals("billing-region", UrlParameterType.billingRegion.toString());
    assertEquals("buyer-id", UrlParameterType.buyerID.toString());
    assertEquals("coupon-amount", UrlParameterType.couponAmount.toString());
    assertEquals("order-subtotal", UrlParameterType.orderSubTotal.toString());
    assertEquals("order-subtotal-plus-shipping", 
      UrlParameterType.orderSubTotalPlusShipping.toString());
    assertEquals("order-subtotal-plus-tax", 
      UrlParameterType.orderSubTotalPlusTax.toString());
    assertEquals("order-total", UrlParameterType.orderTotal.toString());
    assertEquals("shipping-amount", UrlParameterType.shippingAmount.toString());
    assertEquals("shipping-city", UrlParameterType.shippingCity.toString());
    assertEquals("shipping-country-code", 
      UrlParameterType.shippingCountryCode.toString());
    assertEquals("shipping-postal-code", 
      UrlParameterType.shippingPostalCode.toString());
    assertEquals("shipping-region", UrlParameterType.shippingRegion.toString());
    assertEquals("tax-amount", UrlParameterType.taxAmount.toString());
  }
}
