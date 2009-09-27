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

/**
 * This class contains methods that construct &lt;archive-order&gt; API
 * requests.
 * 
 * @author Charles Dang (cdang@google.com)
 */
public class ArchiveOrderRequest extends AbstractOrderProcessingRequest {
  
  /**
   * Constructor which takes an instance of MerchantInfo.
   * 
   * @param merchantInfo The merchant's information.
   */
  public ArchiveOrderRequest(MerchantInfo merchantInfo) {
    super(merchantInfo, "archive-order");
  }

  /**
   * Constructor which takes an instance of merchantInfo and Google order number.
   * 
   * @param googleOrderNumber The Google order number.
   */
  public ArchiveOrderRequest(MerchantInfo merchantInfo, String googleOrderNumber) {
    this(merchantInfo);
    setGoogleOrderNumber(googleOrderNumber);
  }
}
