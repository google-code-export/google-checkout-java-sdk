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

import com.google.checkout.MerchantConstantsFactory;
import com.google.checkout.MerchantConstants;
/**
 *
 * @author inder
 */
public class MerchantInfo {
  
  /** Creates a new instance of MerchantInfo */
  public MerchantInfo() {
    this(MerchantConstantsFactory.getMerchantConstants());
  }
  
  private MerchantInfo(MerchantConstants mc) {
    this.mc = mc;
  }
  
  public String getCurrencyCode() {
    return mc.getCurrencyCode();
  }
  
  /**
   * Return the Environment Type value.
   *
   * @return The Environment Type.
   *
   * @see EnvironmentType
   */
  public String getEnv() {
    return mc.getEnv();
  }
  
  /**
   * Return the Merchant Id.
   *
   * @return The Merchant Id.
   */
  public String getMerchantId() {
    return mc.getMerchantId();
  }
  
  /**
   * Return the Merchant Key.
   *
   * @return The Merchant Key.
   */
  public String getMerchantKey() {
    return mc.getMerchantKey();
  }
  
  /**
   * Return the Checkout URL.
   *
   * @return The Checkout URL.
   */
  public String getCheckoutUrl() {
    return mc.getCheckoutUrl();
  }
  
  /**
   * Return the Request URL.
   *
   * @return The Request URL.
   */
  public String getRequestUrl() {
    return mc.getRequestUrl();
  }
  
  /**
   * Return the HTTP Auth value.
   *
   * @return The HTTP Auth value.
   */
  public String getHttpAuth() {
    return mc.getHttpAuth();
  }
  
  /**
   * Return the Merchant Checkout URL.
   *
   * @return The Checkout URL.
   */
  public String getMerchantCheckoutUrl() {
    return mc.getMerchantCheckoutUrl();
  }
  
  private MerchantConstants mc;
}
