/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.checkout.notification;

import com.google.checkout.exceptions.CheckoutException;
import com.google.checkout.util.Utils;
import org.w3c.dom.Document;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class SomeNewNotification extends CheckoutNotification {
  
  /**
   * A constructor which takes in an xml document representation of the request.
   * 
   * @param document
   */
  public SomeNewNotification(Document document) {
    super(document);
  }
  
  public SomeNewNotification(String requestString) throws CheckoutException {
    this(Utils.newDocumentFromString(requestString));
  }
}
