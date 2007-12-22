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

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class AuthorizationAmountNotificationTest extends TestCase {
  private String authorizationNotificationMessage;
  private AuthorizationAmountNotification authorizationNotification;
  
  public void setUp() {
    authorizationNotificationMessage = TestUtils.
      readMessage("/resources/authorization-amount-notification-sample.xml"); 
   
    try {
      Document doc = Utils
        .newDocumentFromString(authorizationNotificationMessage);
      authorizationNotification = new AuthorizationAmountNotification(doc);
    } catch (CheckoutException ex) {
      fail();
    }
  }
          
  public void testGetAvsResponse() {
    assertEquals("Y", authorizationNotification.getAvsResponse());
  }
  
  public void testGetCvnResponse() {
    assertEquals("Y", authorizationNotification.getCvnResponse());
  }
  
  public void testGetAuthorizationAmount() {
    // specify a 0 delta error
    assertEquals(226.06f, authorizationNotification.getAuthorizationAmount(), 0);  
  }
  
  public void testGetCurrencyCode() {
    assertEquals("USD", authorizationNotification.getCurrencyCode());
  }
  
  public void testGetAuthorizationExpirationDateValid() {
    try {
      assertEquals(authorizationNotification.getAuthorizationExpirationDate()
          .toString(), "Sat Mar 18 20:25:31 PST 2006");
    } catch (CheckoutException ex) {
      fail();
    }
  }
  
  public void testGetAuthorizationExpirationDateInvalid() {
    String aanMessage = TestUtils
      .readMessage("/resources/authorization-amount-notification-invalid-" + 
      "expdate-sample.xml");
    
    try {
      Document doc = Utils.newDocumentFromString(aanMessage);
      authorizationNotification = new AuthorizationAmountNotification(doc);
    } catch (CheckoutException ex) {
      fail();
    }
    
    try {
      authorizationNotification.getAuthorizationExpirationDate();
      fail();
    } catch (CheckoutException ex) {
      // test case passes
    }
  }
}