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
 * properly parameterise the API requests.
 *
 * @author simonjsmith
 */
public class MerchantConstants {
  
  private String merchantId;
  
  private String merchantKey;
  
  private String env;
  
  private String currencyCode;
  
  private String checkoutUrl;
  
  private String merchantCheckoutUrl;
  
  private String requestUrl;
  
  /**
   * The constructor.
   *
   * @param merchantId
   *            The Merchant Id.
   * @param merchantKey
   *            The Merchant Key.
   * @param env
   *            The Environment Type.
   * @param currencyCode
   *            The Currency Code.
   * @param checkoutUrl
   *            The Checkout Url
   * @param merchantCheckoutUrl
   *            The Merchant Checkout Url
   * @param requestUrl
   *            The Request Url
   */
  public MerchantConstants(String merchantId, String merchantKey, String env,
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
  
  /**
   * Return the Currency Code.
   *
   * @return The Currency Code.
   */
  public String getCurrencyCode() {
    return this.currencyCode;
  }
  
  /**
   * Return the Environment Type value.
   *
   * @return The Environment Type.
   *
   * @see EnvironmentType
   */
  public String getEnv() {
    return this.env;
  }
  
  /**
   * Return the Merchant Id.
   *
   * @return The Merchant Id.
   */
  public String getMerchantId() {
    return this.merchantId;
  }
  
  /**
   * Return the Merchant Key.
   *
   * @return The Merchant Key.
   */
  public String getMerchantKey() {
    return this.merchantKey;
  }
  
  /**
   * Set the Currency Code.
   *
   * @param currencyCode
   *            The Currency Code.
   */
  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
    
  }
  
  /**
   * Set the Environment Type value.
   *
   * @param env
   *            The Environment Type.
   *
   * @see EnvironmentType
   */
  public void setEnv(String env) {
    this.env = env;
    
  }
  
  /**
   * Set the Merchant Id.
   *
   * @param merchantId
   *            The Merchant Id.
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
    
  }
  
  /**
   * Set the Merchant Key.
   *
   * @param merchantKey
   *            The Merchant Key.
   */
  public void setMerchantKey(String merchantKey) {
    this.merchantKey = merchantKey;
    
  }
  
  /**
   * Return the Checkout URL.
   *
   * @return The Checkout URL.
   */
  public String getCheckoutUrl() {
    return checkoutUrl;
  }
  
  /**
   * Return the Request URL.
   *
   * @return The Request URL.
   */
  public String getRequestUrl() {
    return requestUrl;
  }
  
  /**
   * Return the HTTP Auth value.
   *
   * @return The HTTP Auth value.
   */
  public String getHttpAuth() {
    return Base64Coder.encode(new StringBuffer(getMerchantId()).append(":")
        .append(getMerchantKey()).toString());
  }
  
  /**
   * Return the Merchant Checkout URL.
   *
   * @return The Checkout URL.
   */
  public String getMerchantCheckoutUrl() {
    return merchantCheckoutUrl;
  }
  
  /**
   * Set the Merchant Checkout URL.
   *
   * @param merchantCheckoutUrl
   *            The Merchant Checkout URL.
   */
  public void setMerchantCheckoutUrl(String merchantCheckoutUrl) {
    this.merchantCheckoutUrl = merchantCheckoutUrl;
  }
  
  /**
   * Set the Checkout URL.
   *
   * @param checkoutUrl
   *            The Checkout URL.
   */
  public void setCheckoutUrl(String checkoutUrl) {
    this.checkoutUrl = checkoutUrl;
  }
  
  /**
   * Set the Request URL.
   *
   * @param requestUrl
   *            The Checkout URL.
   */
  public void setRequestUrl(String requestUrl) {
    this.requestUrl = requestUrl;
  }
  
}
