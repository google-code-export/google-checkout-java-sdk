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

import com.google.checkout.CheckoutSystemException;
import com.google.checkout.MerchantInfo;
import com.google.checkout.util.Utils;

/**
 * This class contains methods that construct &lt;add-merchant-order-number&gt;
 * API requests.
 * 
 * @author Charles Dang (cdang@google.com)
 */
public class AddMerchantOrderNumberRequest extends AbstractOrderProcessingRequest {

  /**
   * Constructor which takes an instance of MerchantInfo,
   * 
   * @param merchantInfo The merchant's information.
   */
  public AddMerchantOrderNumberRequest(MerchantInfo merchantInfo) {
    super(merchantInfo, "add-merchant-order-number");
  }

  /**
   * Constructor which takes an instance of MerchantInfo, a Google order number 
   * and a Merchant Order Number.
   * 
   * @param merchantInfo The merchant's information.
   * @param googleOrderNumber The Google order number of the request.
   * @param merchantOrderNumber The merchant's order number.
   */
  public AddMerchantOrderNumberRequest(MerchantInfo merchantInfo, 
    String googleOrderNumber, String merchantOrderNumber) {
    this(merchantInfo);
    setGoogleOrderNumber(googleOrderNumber);
    setMerchantOrderNumber(merchantOrderNumber);
  }

  /**
   * Return the Merchant Order Number, which is the value of the
   * &lt;merchant-order-number&gt; tag.
   * 
   * @deprecated Use getMerchantOrderNumber
   * 
   * @return The Merchant Order Number.
   */
  public String getMerchantOrderNo() {
    return Utils.getElementStringValue(getDocument(), getRoot(), "merchant-order-number");
  }

  /**
   * Return the Merchant Order Number, which is the value of the
   * &lt;merchant-order-number&gt; tag.
   * 
   * @return The Merchant Order Number.
   */
  public String getMerchantOrderNumber() {
    return Utils.getElementStringValue(getDocument(), getRoot(), "merchant-order-number");
  }
  
  /**
   * Set the Merchant Order Number, which is the value of the
   * &lt;merchant-order-number&gt; tag.
   * 
   * @deprecated Use setMerchantOrderNumber
   * 
   * @param merchantOrderNo The Merchant Order Number.
   */
  public void setMerchantOrderNo(String merchantOrderNo) {
    Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(),
        "merchant-order-number", merchantOrderNo);
  }
  
  /**
   * Set the Merchant Order Number, which is the value of the
   * &lt;merchant-order-number&gt; tag.
   * 
   * @param merchantOrderNumber The Merchant Order Number.
   * 
   * @throws CheckoutSystemException if merchantOrderNumber is null.
   */
  public void setMerchantOrderNumber(String merchantOrderNumber) {
    if (merchantOrderNumber == null) {
      throw new CheckoutSystemException("MerchantOrderNumber cannot be null");
    }
    Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(),
        "merchant-order-number", merchantOrderNumber);
  }
}
