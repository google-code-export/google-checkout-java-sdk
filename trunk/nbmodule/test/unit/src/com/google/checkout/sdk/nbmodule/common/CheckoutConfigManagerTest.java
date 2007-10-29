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
package com.google.checkout.sdk.nbmodule.common;

import com.google.checkout.sdk.module.common.CheckoutConfigManager;
import junit.framework.TestCase;

/**
 * Unit tests for CheckoutConfigManager.
 *
 * @author David Rubel
 */
public class CheckoutConfigManagerTest extends TestCase {
  
  public CheckoutConfigManagerTest(String testName) {
    super(testName);
  }

  public void testEmptyCtor() {
    CheckoutConfigManager c = new CheckoutConfigManager();
    
    // verify the merchant info
    assertEquals("812318588721976", c.getMerchantId());
    assertEquals("c1YAeK6wMizfJ6BmZJG9Fg", c.getMerchantKey());
    assertEquals("Sandbox", c.getEnv());
    assertEquals("USD", c.getCurrencyCode());
    assertEquals("https://sandbox.google.com/checkout/cws/v2/Merchant", c.getSandboxRoot());
    assertEquals("https://checkout.google.com/cws/v2/Merchant", c.getProductionRoot());
    assertEquals("checkout", c.getCheckoutSuffix());
    assertEquals("merchantCheckout", c.getMerchantCheckoutSuffix());
    assertEquals("request", c.getRequestSuffix());

    // verify the notification handlers
    assertEquals("com.google.checkout.handlers.NewOrderNotificationHandler", 
        (String)c.getNotificationHandler("new-order-notification"));
    
    assertEquals("com.google.checkout.handlers.RiskInformationNotificationHandler", 
        (String)c.getNotificationHandler("risk-information-notification"));
    
    assertEquals("com.google.checkout.handlers.OrderStateChangeNotificationHandler", 
        (String)c.getNotificationHandler("order-state-change-notification"));
    
    assertEquals("com.google.checkout.handlers.ChargeAmountNotificationHandler", 
        (String)c.getNotificationHandler("charge-amount-notification"));
    
    assertEquals("com.google.checkout.handlers.RefundAmountNotificationHandler", 
        (String)c.getNotificationHandler("refund-amount-notification"));
    
    assertEquals("com.google.checkout.handlers.ChargebackAmountNotificationHandler", 
        (String)c.getNotificationHandler("chargeback-amount-notification"));
    
    assertEquals("com.google.checkout.handlers.AuthorizationAmountNotificationHandler", 
        (String)c.getNotificationHandler("authorization-amount-notification"));   

    // verify the callback handlers
    assertEquals("com.google.checkout.handlers.MerchantCalculationCallbackHandler",
        (String)c.getCallbackHandler("merchant-calculation-callback"));
  }
  
  public void testFileCtor_ExistingFile() {
    CheckoutConfigManager c = new CheckoutConfigManager( 
        getClass().getResourceAsStream("/resources/test-checkout-config.xml"));
    
    // verify the merchant info
    assertEquals("1234", c.getMerchantId());
    assertEquals("5678", c.getMerchantKey());
    assertEquals("Sandbox", c.getEnv());
    assertEquals("USD", c.getCurrencyCode());
    assertEquals("https://sandbox.google.com/checkout/cws/v2/Merchant", c.getSandboxRoot());
    assertEquals("https://checkout.google.com/cws/v2/Merchant", c.getProductionRoot());
    assertEquals("checkout", c.getCheckoutSuffix());
    assertEquals("merchantCheckout", c.getMerchantCheckoutSuffix());
    assertEquals("request", c.getRequestSuffix());
    
    // verify the notification handlers
    assertEquals("com.google.checkout.handlers.NewOrderNotificationHandler", 
        (String)c.getNotificationHandler("new-order-notification"));
    assertEquals(null, (String)c.getNotificationHandler("risk-information-notification"));
    assertEquals(null, (String)c.getNotificationHandler("order-state-change-notification"));
    assertEquals(null, (String)c.getNotificationHandler("charge-amount-notification"));
    assertEquals(null, (String)c.getNotificationHandler("refund-amount-notification"));
    assertEquals(null, (String)c.getNotificationHandler("chargeback-amount-notification"));
    assertEquals(null, (String)c.getNotificationHandler("authorization-amount-notification"));
    
    // verify the callback handlers
    assertEquals("com.google.checkout.handlers.MerchantCalculationCallbackHandler", 
        (String)c.getCallbackHandler("merchant-calculation-callback"));
  }
  
  public void testFileCtor_NonExistentFile() {    
    CheckoutConfigManager c = new CheckoutConfigManager(
        getClass().getResourceAsStream("/resources/non-existent-checkout-config.xml"));
    
    // verify the merchant info
    assertEquals("", c.getMerchantId());
    assertEquals("", c.getMerchantKey());
    assertEquals("Sandbox", c.getEnv());
    assertEquals("USD", c.getCurrencyCode());
    assertEquals("https://sandbox.google.com/checkout/cws/v2/Merchant", c.getSandboxRoot());
    assertEquals("https://checkout.google.com/cws/v2/Merchant", c.getProductionRoot());
    assertEquals("checkout", c.getCheckoutSuffix());
    assertEquals("merchantCheckout", c.getMerchantCheckoutSuffix());
    assertEquals("request", c.getRequestSuffix());
    
    // verify the notification handlers
    assertEquals("com.google.checkout.handlers.NewOrderNotificationHandler", 
        (String)c.getNotificationHandler("new-order-notification"));
    
    assertEquals("com.google.checkout.handlers.RiskInformationNotificationHandler", 
        (String)c.getNotificationHandler("risk-information-notification"));
    
    assertEquals("com.google.checkout.handlers.OrderStateChangeNotificationHandler", 
        (String)c.getNotificationHandler("order-state-change-notification"));
    
    assertEquals("com.google.checkout.handlers.ChargeAmountNotificationHandler", 
        (String)c.getNotificationHandler("charge-amount-notification"));
    
    assertEquals("com.google.checkout.handlers.RefundAmountNotificationHandler", 
        (String)c.getNotificationHandler("refund-amount-notification"));
    
    assertEquals("com.google.checkout.handlers.ChargebackAmountNotificationHandler", 
        (String)c.getNotificationHandler("chargeback-amount-notification"));
    
    assertEquals("com.google.checkout.handlers.AuthorizationAmountNotificationHandler", 
        (String)c.getNotificationHandler("authorization-amount-notification"));

    // verify the callback handlers
    assertEquals("com.google.checkout.handlers.MerchantCalculationCallbackHandler", 
        (String)c.getCallbackHandler("merchant-calculation-callback"));
  }
  
  public void testGetBody() {
    // Setup instance with a file name  
    CheckoutConfigManager c1 = new CheckoutConfigManager(
        getClass().getResourceAsStream( "/resources/test-checkout-config.xml"));
    
    String result = c1.getBody();
    
    // Get the expected result
    CheckoutConfigManager c2 = new CheckoutConfigManager(
        getClass().getResourceAsStream("/resources/expected-checkout-config.xml"));
        
    String expResult = c2.getBody();
    
    // Verify that the expected result is returned by getBody()
    assertEquals(expResult, result);
  }
}
