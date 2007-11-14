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

import org.w3c.dom.Element;

import com.google.checkout.MerchantInfo;
import com.google.checkout.util.Utils;

/**
 * This class contains methods that construct &lt;charge-order&gt; API requests.
 */
public class ChargeOrderRequest extends AbstractOrderProcessingRequest {

  public ChargeOrderRequest(MerchantInfo mi) {
    super(mi, "charge-order");
  }

  /**
   * Constructor which takes an instance of mi and the Google Order Number.
   */
  public ChargeOrderRequest(MerchantInfo mi, String googleOrderNo) {

    this(mi);
    setGoogleOrderNumber(googleOrderNo);
  }

  /**
   * Constructor which takes an instance of mi, the Google Order Number and the
   * amount to be charged.
   */
  public ChargeOrderRequest(MerchantInfo mi, String googleOrderNo, float amt) {

    this(mi);
    setGoogleOrderNumber(googleOrderNo);
    setAmount(amt);
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
    Element e = Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(),
        "amount", amount);
    e.setAttribute("currency", mi.getCurrencyCode());
  }
}
