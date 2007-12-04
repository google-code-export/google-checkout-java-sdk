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

public class RefundAmountNotificationTest extends TestCase {
  private String refundNotificationMessage;
  
  private RefundAmountNotification refundNotification;
  
  public void setUp() {
    refundNotificationMessage = TestUtils
      .readMessage("/resources/refund-amount-notification-sample.xml");
    
    try {
      Document doc = 
        Utils.newDocumentFromString(refundNotificationMessage);
      
      refundNotification = new RefundAmountNotification(doc);
    } catch (CheckoutException ex) {
      fail();
    }
  }
  
  public void testGetLatestRefundAmount() {
    assertEquals(226.06f, refundNotification.getLatestRefundAmount(), 0);
  }
  
  public void testGetTotalRefundAmount() {
    assertEquals(226.06f, refundNotification.getTotalRefundAmount(), 0);
  }
  
  public void testGetCurrencyCode() {
    assertEquals("USD", refundNotification.getCurrencyCode());
  }
}
