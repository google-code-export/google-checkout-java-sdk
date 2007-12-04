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

package com.google.checkout.orderprocessing;

import com.google.checkout.AbstractCheckoutRequest;
import com.google.checkout.CheckoutException;
import com.google.checkout.CheckoutSystemException;
import com.google.checkout.MerchantInfo;

/**
 * The parent for all order processing requests.
 *
 * @author Charles Dang (cdang@google.com)
 */
public abstract class AbstractOrderProcessingRequest extends 
  AbstractCheckoutRequest {
  
  /**
   * Constructor which takes in an instance of MerchantInfo.
   * 
   * @param merchantInfo The merchant's information.
   * @param requestType The request type.
   * 
   * @throws CheckoutException if merchantInfo is null.
   */
  public AbstractOrderProcessingRequest(MerchantInfo merchantInfo, 
    String requestType) throws CheckoutException {
    super(merchantInfo, requestType);
  }
  
  /**
   * Return the Google order number, which is the value of the
   * google-order-number attribute on the root tag.
   * 
   * @deprecated Use getGoogleOrderNumber().
   * 
   * @return The Google order number.
   */
  public String getGoogleOrderNo() {
    return getRoot().getAttribute("google-order-number");
  }
  
  /**
   * Return the Google order number, which is the value of the
   * google-order-number attribute on the root tag.
   * 
   * @return The Google order number.
   */
  public String getGoogleOrderNumber() {
    return getRoot().getAttribute("google-order-number");
  }
  
  /**
   * Set the Google order number, which is the value of the google-order-number
   * attribute on the root tag.
   * 
   * @deprecated Use setGoogleOrderNumber().
   * 
   * @param googleOrderNo The Google order number.
   */
  public void setGoogleOrderNo(String googleOrderNo) {
    getRoot().setAttribute("google-order-number", googleOrderNo);
  }
  
  /**
   * Set the Google order number, which is the value of the google-order-number
   * attribute on the root tag.
   * 
   * @param googleOrderNumber The Google order number.
   * 
   * @throws CheckoutSystemException if the googleOrderNumber is null.
   */
  public void setGoogleOrderNumber(String googleOrderNumber) {
    if (googleOrderNumber == null) {
      throw new CheckoutSystemException("GoogleOrderNumber cannot be null");
    }
    getRoot().setAttribute("google-order-number", googleOrderNumber);
  }
}
