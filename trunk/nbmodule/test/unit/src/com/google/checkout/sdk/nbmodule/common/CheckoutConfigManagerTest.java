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

import java.io.BufferedReader;
import junit.framework.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 * Unit tests for CheckoutConfigManager.
 *
 * @author David Rubel
 */
public class CheckoutConfigManagerTest extends TestCase {
  
  public CheckoutConfigManagerTest(String testName) {
    super(testName);
  }

  /**
   * Tests that readFile() properly reads the file and fills in the fields
   * of CheckoutConfigManager.
   */
  public void testReadFile() {
    System.out.println("readFile");
    
    // Setup instance with a file name
    CheckoutConfigManager instance = new CheckoutConfigManager();
    URL url = getClass().getResource("/resources/test-checkout-config.xml");
    if (url == null) {
      fail("Could not find test-checkout-config.xml.");
    }
    File testXml = new File(url.getFile());
    instance.setFile(testXml);
    
    // Verify that the read succeeded
    boolean expResult = true;
    boolean result = instance.readFile();
    assertEquals(expResult, result);
    
    // Verify that all fields were read correctly
    assertEquals(instance.getMerchantId(), "1234");
    assertEquals(instance.getMerchantKey(), "5678");
    assertEquals(instance.getEnv(), "Sandbox");
    assertEquals(instance.getCurrencyCode(), "USD");
    assertEquals(instance.getSandboxRoot(), 
        "https://sandbox.google.com/checkout/cws/v2/Merchant");
    assertEquals(instance.getProductionRoot(), 
        "https://checkout.google.com/cws/v2/Merchant");
    assertEquals(instance.getCheckoutSuffix(), "checkout");
    assertEquals(instance.getMerchantCheckoutSuffix(), "merchantCheckout");
    assertEquals(instance.getRequestSuffix(), "request");
    
    // Verify that handlers were read correctly
    assertEquals(instance.getNotificationHandler("new-order-notification"), 
        "com.google.checkout.sdk.NewOrderNotificationHandler");
    assertEquals(instance.getCallbackHandler("merchant-calculation-callback"),
        "com.google.checkout.sdk.MerchantCalculationCallbackHandler");
  }

  /**
   * Tests that the getBody() method returns the correct string.
   */
  public void testGetBody() {
    System.out.println("getBody");
    
    // Setup instance with a file name
    CheckoutConfigManager instance = new CheckoutConfigManager();
    URL url = getClass().getResource("/resources/test-checkout-config.xml");
    if (url == null) {
      fail("Could not find test-checkout-config.xml.");
    }
    File textXml = new File(url.getFile());
    instance.setFile(textXml);
    
    // Setup expected result
    url = getClass().getResource("/resources/expected-checkout-config.xml");
    if (url == null) {
      fail("Could not find expected-checkout-config.xml.");
    }
    File expXml = new File(url.getFile());
    String expResult = null;
    try {
      expResult = CheckoutFileReader.readFileAsString(expXml);
    } catch (IOException ex) {
      fail("Could not read expected-checkout-config.xml.");
    }
    
    // Verify that the expected result is returned by getBody()
    String result = instance.getBody();
    assertEquals(expResult, result);
  }
}
