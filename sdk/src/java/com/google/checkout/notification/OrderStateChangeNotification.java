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
 * This class encapsulates the &lt;order-state-change-notification&gt;
 * notification.
 * 
 * @author simonjsmith
 * 
 */
public class OrderStateChangeNotification extends CheckoutNotification {

  /**
   * A constructor which takes the request as a String.
   * 
   * @param requestString
   * @throws com.google.checkout.exceptions.CheckoutException if there was an
   * error prcessing the request string
   */
  public OrderStateChangeNotification(String requestString) throws CheckoutException {
    this(Utils.newDocumentFromString(requestString));
  }

  /**
   * A constructor which takes the request as an InputStream.
   * 
   * @param inputStream
   * @throws com.google.checkout.exceptions.CheckoutException if there was an
   * error prcessing the request from the InputStream
   */
  public OrderStateChangeNotification(InputStream inputStream) throws CheckoutException {
    this(Utils.newDocumentFromInputStream(inputStream));
  }
  
  /**
   * A constructor which takes in an xml document representation of the request.
   * 
   * @param document
   */
  public OrderStateChangeNotification(Document document) {
    super(document);
  }

  /**
   * Retrieves the value of the &lt;new-fulfillment-order-state&gt; element.
   * 
   * @return The new fulfillment order state.
   * 
   * @see FulfillmentOrderState
   */
  public FulfillmentOrderState getNewFulfillmentOrderState() {
    String state = Utils.getElementStringValue(getDocument(), getRoot(),
      "new-fulfillment-order-state");
    return FulfillmentOrderState.getState(state);
  }

  /**
   * Retrieves the value of the &lt;new-financial-order-state&gt; element.
   * 
   * @return The new financial order state.
   * 
   * @see FinancialOrderState
   */
  public FinancialOrderState getNewFinancialOrderState() {
    String state = Utils
      .getElementStringValue(getDocument(), getRoot(), "new-financial-order-state");
    return FinancialOrderState.getState(state);
  }

  /**
   * Retrieves the value of the &lt;previous-fulfillment-order-state&gt;
   * element.
   * 
   * @return The previous fulfillment order state.
   * 
   * @see FulfillmentOrderState
   */
  public FulfillmentOrderState getPreviousFulfillmentOrderState() {
    String state = Utils.getElementStringValue(getDocument(), getRoot(),
      "previous-fulfillment-order-state");
    return FulfillmentOrderState.getState(state);
  }

  /**
   * Retrieves the value of the &lt;previous-financial-order-state&gt; element.
   * 
   * @return The previous financial order state.
   * 
   * @see FinancialOrderState
   */
  public FinancialOrderState getPreviousFinancialOrderState() {
    String state = Utils.getElementStringValue(getDocument(), getRoot(),
      "previous-financial-order-state");
    return FinancialOrderState.getState(state);
  }

}
