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

import java.io.InputStream;

import org.w3c.dom.Document;

/**
 * This class encapsulates the &lt;charge-amount-notification&gt; notification.
 * 
 * @author simonjsmith
 * 
 */
public class ChargeAmountNotification extends CheckoutNotification {

  /**
   * A constructor which takes the request as a String.
   * 
   * @param requestString
   * @throws com.google.checkout.exceptions.CheckoutException if there was an
   * error prcessing the request string
   */
  public ChargeAmountNotification(String requestString) throws CheckoutException {
    this(Utils.newDocumentFromString(requestString));
  }

  /**
   * A constructor which takes the request as an InputStream.
   * 
   * @param inputStream
   * @throws com.google.checkout.exceptions.CheckoutException if there was an
   * error prcessing the request from the InputStream
   */
  public ChargeAmountNotification(InputStream inputStream) throws CheckoutException {
    this(Utils.newDocumentFromInputStream(inputStream));
  }
  
  /**
   * A constructor which takes in an xml document representation of the request.
   * 
   * @param document
   */
  public ChargeAmountNotification(Document document) {
    super(document);
  }

  /**
   * Retrieves the value of the &lt;latest-charge-amount&gt; tag.
   * 
   * @return The latest charge amount.
   */
  public float getLatestChargeAmount() {
    return Utils.getElementFloatValue(getDocument(), getRoot(), "latest-charge-amount");
  }

  /**
   * Retrieves the value of the &lt;total-charge-amount&gt; tag.
   * 
   * @return The total charge amount.
   */
  public float getTotalChargeAmount() {
    return Utils.getElementFloatValue(getDocument(), getRoot(), "total-charge-amount");
  }

  /**
   * Retrieves the currency code.
   * 
   * @return The currency code.
   */
  public String getCurrencyCode() {
    return Utils.findElementOrContainer(getDocument(), getRoot(), "latest-charge-amount")
        .getAttribute("currency");
  }

}
