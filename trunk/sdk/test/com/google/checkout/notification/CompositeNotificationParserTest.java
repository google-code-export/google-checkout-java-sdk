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

import com.google.checkout.handlers.TestUtils;

import java.util.ArrayList;
import junit.framework.TestCase;
import org.w3c.dom.Document;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class CompositeNotificationParserTest extends TestCase {
  private CompositeNotificationParser compositeNotificationParser;
  private String notificationMsg;
  private CheckoutNotification notification;
  private ArrayList notificationTypes;
  
  public void setUp() {
    compositeNotificationParser = new CompositeNotificationParser();
    notificationMsg = "";
    notificationTypes = new ArrayList();
    
    addNotificationTypes();
  }
  
  private void addNotificationTypes() {
    notificationTypes.add("new-order-notification");
    notificationTypes.add("risk-information-notification");
    notificationTypes.add("order-state-change-notification");
    notificationTypes.add("charge-amount-notification");
    notificationTypes.add("refund-amount-notification");
    notificationTypes.add("chargeback-amount-notification");
    notificationTypes.add("authorization-amount-notification");
  }
  
  /**
   * Testing parse() for NotificationParsers of existing types
   */
  public void testParseExistingNotifications() {
    CompositeNotificationParser.registerDefaultNotificationParsers(compositeNotificationParser);
    try {      
      for (int i=0; i < notificationTypes.size(); ++i) {
        String type = (String)notificationTypes.get(i);
        notificationMsg = TestUtils.readMessage("/resources/" + type + "-sample.xml");
        notification = compositeNotificationParser.parse(notificationMsg);
        assertEquals(notification.getType(), type);
      }
    } catch (CheckoutNotificationException ex) {
      fail();
    }
  }
  
  /**
   * Testing parse() for NotificationParsers of non-existing type
   */
  public void testParseNonExistingNotification() {
    // test some-new-notification will cause factory to throw exception since a
    // parser has not been registered with it.
    try {
      notificationMsg = TestUtils.readMessage(
        "/resources/some-new-notification-sample.xml");
      notification = compositeNotificationParser.parse(notificationMsg);
    } catch (CheckoutNotificationException ex) {
      // parse correctly threw an UnknownNotificationException
      return;
    }
    
    fail("Test did not throw an UnknownNotificationException");
  }

  /**
   * Testing register() correctly registers a NotificationParser
   */
  public void testRegister() {
    try {
      compositeNotificationParser.register("some-new-notification", new NotificationParser() {
        public CheckoutNotification parse(Document document) {
           return new SomeNewNotification(document);
        }
      });
      
      notificationMsg = TestUtils.readMessage(
        "/resources/some-new-notification-sample.xml");
      notification = compositeNotificationParser.parse(notificationMsg);
      assertEquals(notification.getType(), "some-new-notification");
    } catch (CheckoutNotificationException ex) {
      fail();
    }
  }
}
