/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.checkout.notification;

import org.w3c.dom.Document;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class SomeNewNotification extends CheckoutNotification {
  
  /**
   * A constructor which takes in an xml document representation of the request.
   * 
   * @param xmlDocument
   */
  public SomeNewNotification(Document xmlDocument) {
    document = xmlDocument;
    root = document.getDocumentElement();
  }
}
