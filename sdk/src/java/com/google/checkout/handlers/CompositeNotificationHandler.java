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
   * @throws com.google.checkout.notification.exceptions.CheckoutException if 
   * there was an error parsing the request string
   */

package com.google.checkout.handlers;

import com.google.checkout.MerchantInfo;
import com.google.checkout.exceptions.CheckoutException;
import com.google.checkout.notification.CheckoutNotification;
import java.util.HashMap;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class CompositeNotificationHandler implements NotificationHandler {
  private HashMap notificationHandlers;
  
  public CompositeNotificationHandler() {
    notificationHandlers = new HashMap();
//    registerDefaultHandlers();
  }
  
  public void process(MerchantInfo mi, CheckoutNotification notification) 
    throws CheckoutException, UnknownHandlerException {
   
    NotificationHandler handler = (NotificationHandler)notificationHandlers.get(notification.getType());
    
    if (handler == null) {
      throw new UnknownHandlerException();
    }
    
    handler.process(mi, notification);
  }
  
  public void register(String notificationType, NotificationHandler nh) {
    notificationHandlers.put(notificationType, nh);
  }
}

