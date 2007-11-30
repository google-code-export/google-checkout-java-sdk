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

public class RiskInformationNotificationTest extends TestCase {
  private String riskNotificationMessage;
  
  private RiskInformationNotification riskNotification;
  
  public void setUp() {
    riskNotificationMessage = TestUtils
      .readMessage("/resources/risk-information-notification-sample.xml");
    
    try {
      Document doc = 
        Utils.newDocumentFromString(riskNotificationMessage);
      
      riskNotification = new RiskInformationNotification(doc);
    } catch (CheckoutException ex) {
      fail();
    }
  }
  
  public void testGetRiskInfo() {
    RiskInfo riskInfo = riskNotification.getRiskInfo();
    
    assertTrue(riskInfo.isEligibleForProtection());
    verifyBillingAddress(riskInfo.getBillingAddress());
    assertEquals("Y", riskInfo.getAvsResponse());
    assertEquals("M", riskInfo.getCvnResponse());
    assertEquals("3719", riskInfo.getPartialCcNumber());
    assertEquals("10.11.12.13", riskInfo.getIpAddress());
    assertEquals(6, riskInfo.getBuyerAccountAge());
  }
  
  private void verifyBillingAddress(Address billingAddress) {
    assertEquals("John Smith", billingAddress.getContactName());
    assertEquals("johnsmith@example.com", billingAddress.getEmail());
    assertEquals("10 Example Road", billingAddress.getAddress1());
    assertEquals("Sampleville", billingAddress.getCity());
    assertEquals("CA", billingAddress.getRegion());
    assertEquals("94141", billingAddress.getPostalCode());
    assertEquals("US", billingAddress.getCountryCode());
  }
}
