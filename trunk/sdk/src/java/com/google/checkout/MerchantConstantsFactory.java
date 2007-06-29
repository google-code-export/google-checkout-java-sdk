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

import java.util.ResourceBundle;

/**
 * This class loads the merchant constants from a properties file. You can
 * change this to load from another source if you wish.
 *
 * @author simonjsmith
 *
 */
public class MerchantConstantsFactory {
  
  private static MerchantConstants mc;
  
  /**
   * Return an instance of the MerchantConstants.
   *
   * @return The MerchantConstants.
   *
   * @see MerchantConstants
   */
  public static MerchantConstants getMerchantConstants() {
    
    if (mc == null) {
      ResourceBundle bundle = ResourceBundle
          .getBundle("com_google_checkout_example_settings");
      String merchantId = bundle.getString("merchantid");
      String merchantKey = bundle.getString("merchantkey");
      String env = bundle.getString("env");
      String currencyCode = bundle.getString("currencycode");
      
      String sandboxRoot = bundle.getString("sandbox.root");
      String prodRoot = bundle.getString("prod.root");
      String checkoutSuffix = bundle.getString("checkout.suffix");
      String merchantCheckoutSuffix = bundle
          .getString("merchantCheckout.suffix");
      String requestSuffix = bundle.getString("request.suffix");
      
      String checkoutUrl = "";
      String merchantCheckoutUrl = "";
      String requestUrl = "";
      
      if (EnvironmentType.Sandbox.equals(env)) {
        checkoutUrl = sandboxRoot + "/" + merchantId + "/"
            + checkoutSuffix;
        merchantCheckoutUrl = sandboxRoot + "/" + merchantId + "/"
            + merchantCheckoutSuffix;
        requestUrl = sandboxRoot + "/" + merchantId + "/"
            + requestSuffix;
      } else if (EnvironmentType.Production.equals(env)) {
        checkoutUrl = prodRoot + "/" + merchantId + "/"
            + checkoutSuffix;
        merchantCheckoutUrl = prodRoot + "/" + merchantId + "/"
            + merchantCheckoutSuffix;
        requestUrl = prodRoot + "/" + merchantId + "/" + requestSuffix;
      } else {
        throw new RuntimeException("Env must be one of "
            + EnvironmentType.Sandbox + " or "
            + EnvironmentType.Production + ".");
      }
      
      mc = new MerchantConstants(merchantId, merchantKey, env,
          currencyCode, checkoutUrl, merchantCheckoutUrl, requestUrl);
    }
    return mc;
  }
  
}
