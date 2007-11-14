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

/**
 * This class contains methods that construct &lt;add-merchant-order-number&gt;
 * API requests.
 */
public class AddMerchantOrderNumberRequest extends AbstractOrderProcessingRequest {
  public AddMerchantOrderNumberRequest(MerchantInfo mi) {
    super(mi, "add-merchant-order-number");
  }

  /**
   * Constructor which takes an instance of mi, a Google Order Number and a
   * Merchant Order Number.
   */
  public AddMerchantOrderNumberRequest(MerchantInfo mi, String googleOrderNo,
      String merchantOrderNo) {
    this(mi);
    this.setGoogleOrderNo(googleOrderNo);
    this.setMerchantOrderNo(merchantOrderNo);
  }

  /**
   * Return the Merchant Order Number, which is the value of the
   * &lt;merchant-order-number&gt; tag.
   * 
   * @return The Merchant Order Number.
   */
  public String getMerchantOrderNo() {
    return Utils.getElementStringValue(getDocument(), getRoot(), "merchant-order-number");
  }

  /**
   * Set the Merchant Order Number, which is the value of the
   * &lt;merchant-order-number&gt; tag.
   * 
   * @param merchantOrderNo The Merchant Order Number.
   */
  public void setMerchantOrderNo(String merchantOrderNo) {
    Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(),
        "merchant-order-number", merchantOrderNo);
  }
}
