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

import com.google.checkout.CheckoutException;
import com.google.checkout.MerchantInfo;
import com.google.checkout.util.TestUtils;

import junit.framework.TestCase;

/**
 * small test for ChargeAmountNotificationHandler
 * @author inder
 */
public class ChargeAmountNotificationHandlerTest extends TestCase {

  public void testProcess() throws CheckoutException {
    MessageHandler handler = new ChargeAmountNotificationHandler();
    MerchantInfo mi = TestUtils.createMockMerchantInfo();
    String notificationMsg = TestUtils.readMessage(
        "/charge-amount-notification-sample.xml");
    String response = handler.process(mi, notificationMsg);
    //System.out.println(response);
    assertTrue(response.contains("notification-acknowledgment"));
  }

}