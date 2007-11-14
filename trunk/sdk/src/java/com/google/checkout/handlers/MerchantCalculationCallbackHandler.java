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

package com.google.checkout.handlers;

import com.google.checkout.exceptions.CheckoutException;
import com.google.checkout.MerchantInfo;
import com.google.checkout.merchantcalculation.AnonymousAddress;
import com.google.checkout.merchantcalculation.CouponResult;
import com.google.checkout.merchantcalculation.MerchantCalculationCallback;
import com.google.checkout.merchantcalculation.MerchantCalculationResults;
import com.google.checkout.merchantcalculation.MerchantCodeString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author simonjsmith
 * @author inder
 */
public class MerchantCalculationCallbackHandler implements MessageHandler {
  
  /**
   * 
   * @param mi
   * @param notificationMsg
   * @return
   * @throws com.google.checkout.exceptions.CheckoutException
   */
  public String process(MerchantInfo mi, String notificationMsg)
      throws CheckoutException {
    
    try {
      MerchantCalculationCallback callback = 
          new MerchantCalculationCallback(notificationMsg);
      
      Iterator addresses = callback.getAnonymousAddresses().iterator();
      Iterator shippingMethods;
      Iterator merchantCodes;
      
      MerchantCalculationResults results = new MerchantCalculationResults();
      AnonymousAddress address;
      String shipping;
      MerchantCodeString code;
      
      while (addresses.hasNext()) {
        address = (AnonymousAddress) addresses.next();
        
        shippingMethods = callback.getShippingMethods().iterator();
        while (shippingMethods.hasNext()) {
          shipping = (String) shippingMethods.next();
          
          merchantCodes = callback.getMerchantCodes().iterator();
          Collection codeResults = new ArrayList();
          while (merchantCodes.hasNext()) {
            code = (MerchantCodeString) merchantCodes.next();
            CouponResult coupon = new CouponResult(false, 0.0f,
                mi.getCurrencyCode(),
                code.getCode(), "Not supported in this example.");
            codeResults.add(coupon);
          }
          
          results.addResult(shipping, address.getId(), true, 0.0d, 0.0d,
              mi.getCurrencyCode(), codeResults);
        }
      }
      return results.getXml();
    } catch (Exception e) {
      throw new CheckoutException(e);
    }
  }
}
