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

import com.google.checkout.MerchantInfo;
import com.google.checkout.util.Utils;

import org.w3c.dom.Element;

/**
 * This class contains methods that construct &lt;charge-order&gt; API requests.
 * 
 * @author Charles Dang (cdang@google.com)
 */
public class ChargeOrderRequest extends AbstractOrderProcessingRequest {

  /**
   * Constructor which takes an instance of MechantInfo and the Google order
   * 
   * @param merchantInfo The merchant's information.
   */
  public ChargeOrderRequest(MerchantInfo merchantInfo) {
    super(merchantInfo, "charge-order");
  }

  /**
   * Constructor which takes an instance of merchantInfo and the Google order 
   * number.
   * 
   * @param merchantInfo The merchant's information.
   * @param googleOrderNumber The Google order number.
   */
  public ChargeOrderRequest(MerchantInfo merchantInfo, String googleOrderNumber) {
    this(merchantInfo);
    setGoogleOrderNumber(googleOrderNumber);
  }

  /**
   * Constructor which takes an instance of MerchantInfo, the Google order 
   * number and the amount to be charged.
   * 
   * @param merchantInfo The merchant's information.
   * @param googleOrderNumber The Google order number.
   * @param amount The amount to charge.
   */
  public ChargeOrderRequest(MerchantInfo merchantInfo, String googleOrderNumber, 
    float amount) {
    this(merchantInfo);
    setGoogleOrderNumber(googleOrderNumber);
    setAmount(amount);
  }

  /**
   * Return the charge amount, which is the value of the &lt;amount&gt; tag.
   * 
   * @return The charge amount.
   */
  public float getAmount() {
    return Utils.getElementFloatValue(getDocument(), getRoot(), "amount");
  }

  /**
   * Set the charge amount, which is the value of the &lt;amount&gt; tag.
   * 
   * @param amount The charge amount.
   */
  public void setAmount(float amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Charge amount must be greater than 0");
    }
    
    Element e = Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(),
        "amount", amount);
    e.setAttribute("currency", merchantInfo.getCurrencyCode());
  }
}
