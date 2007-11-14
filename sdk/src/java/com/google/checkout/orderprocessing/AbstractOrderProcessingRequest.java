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
import com.google.checkout.MerchantInfo;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public abstract class AbstractOrderProcessingRequest extends AbstractCheckoutRequest {
  
  public AbstractOrderProcessingRequest(MerchantInfo info, String requestType) {
    super(info, requestType);
  }
  
  /**
   * Return the Google Order Number, which is the value of the
   * google-order-number attribute on the root tag.
   * 
   * @return The Google Order Number.
   */
  @Deprecated
  public String getGoogleOrderNo() {
    return getRoot().getAttribute("google-order-number");
  }
  
    /**
   * Return the Google Order Number, which is the value of the
   * google-order-number attribute on the root tag.
   * 
   * @return The Google Order Number.
   */
  public String getGoogleOrderNumber() {
    return getRoot().getAttribute("google-order-number");
  }
  
  /**
   * Set the Google Order Number, which is the value of the google-order-number
   * attribute on the root tag.
   * 
   * @param googleOrderNo The Google Order Number.
   */
  @Deprecated
  public void setGoogleOrderNo(String googleOrderNo) {
    getRoot().setAttribute("google-order-number", googleOrderNo);
  }
  
    /**
   * Set the Google Order Number, which is the value of the google-order-number
   * attribute on the root tag.
   * 
   * @param googleOrderNo The Google Order Number.
   */
  public void setGoogleOrderNumber(String googleOrderNo) {
    getRoot().setAttribute("google-order-number", googleOrderNo);
  }
}
