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
package com.google.checkout.handlers;

import com.google.checkout.util.TestUtils;
import com.google.checkout.CheckoutException;
import com.google.checkout.MerchantInfo;
import com.google.checkout.util.Utils;

import junit.framework.TestCase;

import org.w3c.dom.Document;

/**
 * small test for MerchantCalculationCallbackHandler
 * @author inder
 */
public class MerchantCalculationCallbackHandlerTest extends TestCase {

  public void testProcess() throws CheckoutException {
    MessageHandler handler = new MerchantCalculationCallbackHandler();
    MerchantInfo mi = TestUtils.createMockMerchantInfo();
    String notificationMsg = TestUtils.readMessage(
        "/resources/merchant-calculation-callback-sample.xml");
    String response = handler.process(mi, notificationMsg);
    //System.out.println(response);
    assertTrue(response.contains("merchant-calculation-results"));
    assertTrue(response.contains("results"));
    assertTrue(response.contains("total-tax"));
    assertTrue(response.contains("shipping-rate"));
    assertTrue(response.contains("merchant-code-results"));
    assertTrue(response.contains("calculated-amount"));

    Document responseDoc = Utils.newDocumentFromString(response);
    responseDoc.normalizeDocument();
    
    String expectedResponse = TestUtils.readMessage(
    "/resources/merchant-calculation-callback-response-sample.xml");
    Document expectedDoc = Utils.newDocumentFromString(expectedResponse);
    expectedDoc.normalizeDocument();
    
    assertEquals(expectedDoc.toString(), responseDoc.toString());
  }
}
