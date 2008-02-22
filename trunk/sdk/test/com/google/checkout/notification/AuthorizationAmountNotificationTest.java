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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 
 * @author Charles Dang (cdang@google.com)
 */
public class AuthorizationAmountNotificationTest extends TestCase {
  private String authorizationNotificationMessage;
  private AuthorizationAmountNotification authorizationNotification;

  public void setUp() {
    authorizationNotificationMessage =
        TestUtils
            .readMessage("/resources/authorization-amount-notification-sample.xml");

    try {
      Document doc =
          Utils.newDocumentFromString(authorizationNotificationMessage);
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
      assertEquals(Utils.getDateString(authorizationNotification
          .getAuthorizationExpirationDate()), "2006-03-18T20:25:31.000Z");
    } catch (CheckoutException ex) {
      fail();
    }
  }

  public void testGetAuthorizationExpirationDateInvalid() {
    String aanMessage =
        TestUtils
            .readMessage("/resources/authorization-amount-notification-invalid-"
                + "expdate-sample.xml");

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

  public void testIsSerializable() throws IOException {

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(out);
    oos.writeObject(authorizationNotification);
    oos.close();
    assertTrue(out.toByteArray().length > 0);
  }
}
