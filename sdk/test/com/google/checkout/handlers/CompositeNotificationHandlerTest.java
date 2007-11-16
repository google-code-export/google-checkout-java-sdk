/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.checkout.handlers;

import com.google.checkout.MerchantInfo;
import com.google.checkout.MessageTypes;
import com.google.checkout.exceptions.CheckoutException;
import com.google.checkout.notification.CheckoutNotification;
import com.google.checkout.notification.CompositeNotificationParser;
import com.google.checkout.notification.NotificationParser;
import com.google.checkout.notification.SomeNewNotification;
import com.google.checkout.notification.UnknownNotificationException;
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
    notificationTypes.add(MessageTypes.NEW_ORDER_NOTIFICATION);
    notificationTypes.add(MessageTypes.RISK_INFORMATION_NOTIFICATION);
    notificationTypes.add(MessageTypes.ORDER_STATE_CHANGE_NOTIFICATION);
    notificationTypes.add(MessageTypes.CHARGE_AMOUNT_NOTIFICATION);
    notificationTypes.add(MessageTypes.REFUND_AMOUNT_NOTIFICATION);
    notificationTypes.add(MessageTypes.CHARGEBACK_AMOUNT_NOTIFICATION);
    notificationTypes.add(MessageTypes.AUTHORIZATION_AMOUNT_NOTIFICATION);
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
      compositeHandler.process(mi, compositeParser.parse(notificationMsg));
      
    } catch (UnknownHandlerException ex) {
      return;
    } catch (UnknownNotificationException ex) {
      fail();
    } catch (CheckoutException ex) {
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
        public void process(MerchantInfo mi, CheckoutNotification notification) throws CheckoutException {
          (new SomeNewNotificationHandler()).process(mi, notification.getXml());
        }
      });
      
      notificationMsg = TestUtils.readMessage(
        "/resources/some-new-notification-sample.xml");
      compositeHandler.process(mi, compositeParser.parse(notificationMsg));
    } catch (UnknownNotificationException ex) {
      fail();
    } catch (UnknownHandlerException ex) {
      fail();
    } catch (CheckoutException ex) {
      fail();
    }
  }
}
