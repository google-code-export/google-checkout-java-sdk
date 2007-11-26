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
import com.google.checkout.util.Utils;

import java.util.HashMap;

import org.w3c.dom.Document;

/**
 * @author Charles Dang (cdang@google.com)
 */
public class CompositeNotificationParser implements NotificationParser {
  private HashMap notificationParsers;
  
  /**
   * Default constructor
   */
  public CompositeNotificationParser() {
    notificationParsers = new HashMap();
  }
  
  /**
   * Parses an xml string and returns a CheckoutNotification object of the given
   * type.
   * 
   * Assumes the root element contains the notification type. If the notification
   * type is not one of the following types, an UnknownNotificationException is thrown:
   * new-order-notification, risk-information-notification, 
   * order-state-change-notification, refund-amount-notification, 
   * chargeback-amount-notification or authorization-amount-notification.
   * 
   * @param xmlString
   * @return A notification object of the specified type
   * @throws com.google.checkout.notification.UnknownNotificationException if the
   * notification type was not recognized
   * @throws com.google.checkout.notification.CheckoutException if 
   * there was an error parsing the request string
   */
  public CheckoutNotification parse(String xmlString) 
    throws CheckoutParserException {
      CheckoutNotification notification;
      try { 
        notification = parse(Utils.newDocumentFromString(xmlString));
      } catch (CheckoutException ex) {
        throw new CheckoutParserException(ex);
      }
  
      return notification;
  }

  /**
   * Parses the xmlDocment and returns a CheckoutNotification object of the 
   * specified type.
   * 
   * @param xmlDocument
   * @return CheckoutNotification of the type specified in xmlDocument
   * @throws com.google.checkout.notification.UnknownNotificationException
   */
  public CheckoutNotification parse(Document xmlDocument) 
    throws CheckoutParserException {
      String type = xmlDocument.getDocumentElement().getNodeName();
      
      NotificationParser parser = (NotificationParser)notificationParsers.get(type);

      if (parser == null) {
        throw new CheckoutParserException("Parser for type (" + type + ") " +
          "could not be found.");
      }
      
      return parser.parse(xmlDocument);
  }

  /**
   * Registers a NotificationParser with the associated notification type
   * 
   * @param notificationType The notification type
   * @param parser The parser associated with the notification type
   */
  public void register(String notificationType, NotificationParser parser) {
    notificationParsers.put(notificationType, parser);
  }
  
  /**
   * Registers the default notification parsers.
   */
  public static void registerDefaultNotificationParsers(CompositeNotificationParser parser) {
    parser.register(NotificationTypes.NEW_ORDER_NOTIFICATION, new NotificationParser() {
      public CheckoutNotification parse(Document xmlDocument) {
         return new NewOrderNotification(xmlDocument);
      }
    });
    
    parser.register(NotificationTypes.RISK_INFORMATION_NOTIFICATION, new NotificationParser() {
      public CheckoutNotification parse(Document xmlDocument) {
        return new RiskInformationNotification(xmlDocument);
      }
    });
    
    parser.register(NotificationTypes.ORDER_STATE_CHANGE_NOTIFICATION, new NotificationParser() {
      public CheckoutNotification parse(Document xmlDocument) {
        return new OrderStateChangeNotification(xmlDocument);
      }
    });
    
    parser.register(NotificationTypes.CHARGE_AMOUNT_NOTIFICATION, new NotificationParser() {
      public CheckoutNotification parse(Document xmlDocument) {
        return new ChargeAmountNotification(xmlDocument);
      }
    });
    
    parser.register(NotificationTypes.REFUND_AMOUNT_NOTIFICATION, new NotificationParser() {
      public CheckoutNotification parse(Document xmlDocument) {
        return new RefundAmountNotification(xmlDocument);
      }
    });
    
    parser.register(NotificationTypes.CHARGEBACK_AMOUNT_NOTIFICATION, new NotificationParser() {
      public CheckoutNotification parse(Document xmlDocument) {
        return new ChargebackAmountNotification(xmlDocument);
      }
    });
    
    parser.register(NotificationTypes.AUTHORIZATION_AMOUNT_NOTIFICATION, new NotificationParser() {
      public CheckoutNotification parse(Document xmlDocument) {
        return new AuthorizationAmountNotification(xmlDocument);
      }
    });
  }
}
