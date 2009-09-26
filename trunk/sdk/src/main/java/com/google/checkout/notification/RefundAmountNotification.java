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

import java.io.InputStream;

import org.w3c.dom.Document;

/**
 * This class encapsulates the &lt;refund-amount-notification&gt; notification.
 * 
 * @author simonjsmith
 * 
 */
public class RefundAmountNotification extends CheckoutNotification {

  /**
   * A constructor which takes the request as a String.
   * 
   * @param requestString The request string
   * @throws com.google.checkout.CheckoutException if there was an 
   * error reading the request string
   */
  public RefundAmountNotification(String requestString) throws CheckoutException {
    this(Utils.newDocumentFromString(requestString));
  }

  /**
   * A constructor which takes the request as an InputStream.
   * 
   * @param inputStream
   * @throws com.google.checkout.CheckoutException if there was an 
   * error reading the request from the InputStream
   */
  public RefundAmountNotification(InputStream inputStream) throws CheckoutException {
    this(Utils.newDocumentFromInputStream(inputStream));
  }
  
  /**
   * A constructor which takes in an xml document representation of the request.
   * 
   * @param document
   */
  public RefundAmountNotification(Document document) {
    super(document);
  }
  
  /**
   * Retrieves the value of the &lt;latest-promotion-chargeback-amount&gt; tag if 
   * it exists; otherwise null.
   * 
   * @return The latest promotion refund amount
   */
  public float getLatestPromotionRefundAmount() {
    return Utils.getElementFloatValue(getDocument(), getRoot(), 
      "latest-promotion-chargeback-amount");
  }

  /**
   * Retrieves the value of the &lt;latest-refund-amount&gt; tag.
   * 
   * @return The latest refund amount.
   */
  public float getLatestRefundAmount() {
    return Utils.getElementFloatValue(getDocument(), getRoot(), 
      "latest-refund-amount");
  }

  /**
   * Retrieves the value of the &lt;total-refund-amount&gt; tag.
   * 
   * @return The total refund amount.
   */
  public float getTotalRefundAmount() {
    return Utils.getElementFloatValue(getDocument(), getRoot(), 
      "total-refund-amount");
  }

  /**
   * Retrieves the currency code.
   * 
   * @return The currency code.
   */
  public String getCurrencyCode() {
    return Utils.findElementOrContainer(getDocument(), getRoot(), 
      "latest-refund-amount").getAttribute("currency");
  }
}
