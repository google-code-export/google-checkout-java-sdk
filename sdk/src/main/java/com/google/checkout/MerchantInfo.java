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
package com.google.checkout;

import com.google.checkout.util.Base64Coder;

/**
 * This class encapsulates the various merchant specific settings. An instance
 * of this class will be required by most of the other classes in order to
 * properly parameterize the API requests.
 *
 * @author simonjsmith
 * @author inder
 */
public class MerchantInfo implements java.io.Serializable {
  
  private final String merchantId;
  private final String merchantKey;
  private final String env;
  private final String currencyCode;
  private final String checkoutUrl;
  private final String merchantCheckoutUrl;
  private final String requestUrl;
  
   /**
    * 
    * 
    * @param merchantId The merchant id.
    * @param merchantKey The merchant key.
    * @param env The environment type.
    * @param currencyCode The currency code.
    * @param checkoutUrl The checkout url
    * @param merchantCheckoutUrl The merchant checkout url
    * @param requestUrl The request url
    */
  public MerchantInfo(String merchantId, String merchantKey, String env,
      String currencyCode, String checkoutUrl,
      String merchantCheckoutUrl, String requestUrl) {
    this.merchantId = merchantId;
    this.merchantKey = merchantKey;
    this.env = env;
    this.currencyCode = currencyCode;
    this.checkoutUrl = checkoutUrl;
    this.merchantCheckoutUrl = merchantCheckoutUrl;
    this.requestUrl = requestUrl;
  }
  
  public String getCurrencyCode() {
    return currencyCode;
  }
  
  public String getEnv() {
    return env;
  }
  
  public String getMerchantId() {
    return merchantId;
  }
  
  public String getMerchantKey() {
    return merchantKey;
  }
  
  public String getCheckoutUrl() {
    return checkoutUrl;
  }
  
  public String getRequestUrl() {
    return requestUrl;
  }
  
  public String getHttpAuth() {
    return Base64Coder.encode(new StringBuffer(getMerchantId()).append(":")
        .append(getMerchantKey()).toString());
  }
  
  public String getMerchantCheckoutUrl() {
    return merchantCheckoutUrl;
  }
}
