/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.checkout.handlers;

import com.google.checkout.MerchantInfo;
import com.google.checkout.notification.NotificationTypes;
import com.google.checkout.CheckoutException;
import com.google.checkout.notification.CheckoutNotification;
import com.google.checkout.notification.CheckoutParserException;
import com.google.checkout.notification.CompositeNotificationParser;
import com.google.checkout.notification.NotificationParser;
import com.google.checkout.notification.SomeNewNotification;
import java.util.ArrayList;
import junit.framework.TestCase;
import org.w3c.dom.Document;

/**
 *
 * @author Charles Dang
 */
public class CompositeNotificationHandlerTest extends TestCase {
  private CompositeNotificationHandler compositeHandler; 
  private CompositeNotificationParser compositeParser;
  private String notificationMsg;
  private String response;
  private ArrayList notificationTypes;
  private MerchantInfo mi;
  
  public void setUp() {
    compositeHandler = new CompositeNotificationHandler();
    compositeParser = new CompositeNotificationParser();
    
    notificationMsg = "";
    notificationTypes = new ArrayList();
    
    mi = TestUtils.createMockMerchantInfo();
    
    addNotificationTypes();
  }
  
  public void addNotificationTypes() {
    notificationTypes.add(NotificationTypes.NEW_ORDER_NOTIFICATION);
    notificationTypes.add(NotificationTypes.RISK_INFORMATION_NOTIFICATION);
    notificationTypes.add(NotificationTypes.ORDER_STATE_CHANGE_NOTIFICATION);
    notificationTypes.add(NotificationTypes.CHARGE_AMOUNT_NOTIFICATION);
    notificationTypes.add(NotificationTypes.REFUND_AMOUNT_NOTIFICATION);
    notificationTypes.add(NotificationTypes.CHARGEBACK_AMOUNT_NOTIFICATION);
    notificationTypes.add(NotificationTypes.AUTHORIZATION_AMOUNT_NOTIFICATION);
  }
  
  public void testNonExistingHandlers() {
    try {
      compositeParser.register("some-new-notification", new NotificationParser() {
        public CheckoutNotification parse(Document document) {
           return new SomeNewNotification(document);
        }
      });
      
      notificationMsg = TestUtils.readMessage(
        "/resources/some-new-notification-sample.xml");
      
      CheckoutNotification notification = compositeParser.parse(notificationMsg);
              
      compositeHandler.handle(mi, notification);
      
    } catch (CheckoutHandlerException ex) {
      return;
    } catch (CheckoutParserException ex) {
      fail();
    }
    
    fail("Test did not throw a CheckoutException");
  }
  
  public void testRegister() {
    try {
      compositeParser.register("some-new-notification", new NotificationParser() {
        public CheckoutNotification parse(Document document) {
           return new SomeNewNotification(document);
        }
      });
      
      compositeHandler.register("some-new-notification", new NotificationHandler() {
        public void handle(MerchantInfo mi, CheckoutNotification notification) {
            String msg = notification.getXml();
            SomeNewNotificationHandler newHandler = new SomeNewNotificationHandler();
            try {
              newHandler.process(mi, msg);
            } catch (CheckoutException ex) {
              fail();
            }
        }
      });
      
      notificationMsg = TestUtils.readMessage(
        "/resources/some-new-notification-sample.xml");
      compositeHandler.handle(mi, compositeParser.parse(notificationMsg));
    } catch (CheckoutHandlerException ex) {
      fail();
    } catch (CheckoutParserException ex) {
      fail();
    }
  }
}
