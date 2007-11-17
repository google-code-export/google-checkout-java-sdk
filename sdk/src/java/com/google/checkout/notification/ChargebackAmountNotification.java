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
 * This class encapsulates the &lt;chargeback-amount-notification&gt;
 * notification.
 * 
 * @author simonjsmith
 * 
 */
public class ChargebackAmountNotification extends CheckoutNotification {

  /**
   * A constructor which takes the request as a String.
   * 
   * @param requestString The request string
   * @throws com.google.checkout.CheckoutException if there was an 
   * error processing the request string
   */
  public ChargebackAmountNotification(String requestString) throws CheckoutException {
    this(Utils.newDocumentFromString(requestString));
  }

  /**
   * A constructor which takes the request as an InputStream.
   * 
   * @param inputStream
   * @throws com.google.checkout.CheckoutException if there was an
   * error prcessing the request from the InputStream
   */
  public ChargebackAmountNotification(InputStream inputStream) throws CheckoutException {
    this(Utils.newDocumentFromInputStream(inputStream));
  }
  
  /**
   * A constructor which takes in an xml document representation of the request.
   * 
   * @param document
   */
  public ChargebackAmountNotification(Document document) {
    super(document);
  }

  /**
   * Retrieves the value of the &lt;latest-chargeback-amount&gt; tag.
   * 
   * @return The latest chargeback amount.
   */
  public float getLatestChargebackAmount() {
    return Utils.getElementFloatValue(getDocument(), getRoot(), "latest-chargeback-amount");
  }

  /**
   * Retrieves the value of the &lt;total-charge-amount&gt; tag.
   * 
   * @return The total chargeback amount.
   */
  public float getTotalChargebackAmount() {
    return Utils.getElementFloatValue(getDocument(), getRoot(), "total-chargeback-amount");
  }

  /**
   * Retrieves the currency code.
   * 
   * @return The currency code.
   */
  public String getCurrencyCode() {
    return Utils.findElementOrContainer(getDocument(), getRoot(), "latest-chargeback-amount")
      .getAttribute("currency");
  }
}
