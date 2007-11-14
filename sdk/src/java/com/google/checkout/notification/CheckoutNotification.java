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

import com.google.checkout.exceptions.CheckoutException;
import com.google.checkout.util.Utils;

import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class is the parent for all the notification classes.
 * 
 * @author simonjsmith
 * 
 */
public abstract class CheckoutNotification {

  private Document document;
  private Element root;

  /**
   * Takes a document that contains info on the notification
   * 
   * @param document The notification document
   */
  public CheckoutNotification(Document document) {
    this.document = document;
    this.root = document.getDocumentElement();
  }
  
  public Document getDocument() {
    return document;
  }
  
  public Element getRoot() {
    return root;
  }
  
  /**
   * Return the Google Order Number for this notification.
   * 
   * @return The Google Order Number.
   */
  public String getGoogleOrderNo() {
    return Utils.getElementStringValue(document, root, "google-order-number");
  }
  
  /**
   * Retrieves the value of the &lt;timestamp&gt; tag.
   * 
   * @return The timestamp.
   */
  public Date getTimestamp() throws CheckoutException {
    return Utils.getElementDateValue(document, root, "timestamp");
  }
  
  /**
   * Returns the type of the notification 
   * 
   * @return Notification type
   */
  public String getType() {
    return getRootNodeName();
  }

  /**
   * Retrieves the value of the serial-number attribute.
   * 
   * @return The serial number.
   */
  public String getSerialNumber() {
    return root.getAttribute("serial-number");
  }

  /**
   * Return the XML request String.
   * 
   * @return The XML request String.
   */
  public String getXml() {    
    return Utils.documentToString(document);
  }

  /**
   * Return the nicely formatted XML request String.
   * 
   * @return The nicely formatted XML request String.
   */
  public String getXmlPretty() {    
    return Utils.documentToStringPretty(document);
  }

  /**
   * Return the name of the root node of the notification.
   * 
   * @return The root node name.
   */
  public String getRootNodeName() {
    return document.getDocumentElement().getNodeName();
  }
}
