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

package com.google.checkout.merchantcalculation;

import java.util.ArrayList;
import java.util.Collection;
import junit.framework.TestCase;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class MerchantCalculationResultsTest extends TestCase {
  
  public void testMerchantCalculationResultsWithMerchantCodeResults() {

    MerchantCalculationResults merchantResult = new MerchantCalculationResults();
    
    Collection merchantCodeResults = new ArrayList();

    CouponResult tempCoupon = new CouponResult(true, 5.00f, "USD", 
      "FirstVisitCoupon",
      "Congratulations! You saved $5.00 on your first visit!");
    merchantCodeResults.add(tempCoupon); 

    GiftCertificateResult tempCertificate = new GiftCertificateResult(true, 
      10.00f, "USD", "GiftCert012345", "You used your Gift Certificate!");
    merchantCodeResults.add(tempCertificate);

    // first result
    merchantResult.addResult("SuperShip", "739030698069958", true, 7.03, 14.67, 
      "USD", merchantCodeResults);

    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + 
      "<merchant-calculation-results xmlns=\"http://checkout.google.com/schema/2\">" + 
      "<results><result address-id=\"739030698069958\" shipping-name=\"SuperShip\">" + 
      "<shippable>true</shippable><total-tax currency=\"USD\">7.03</total-tax>" + 
      "<shipping-rate currency=\"USD\">14.67</shipping-rate>" + 
      "<merchant-code-results><coupon-result><valid>true</valid><calculated-amount>" + 
      "5.0</calculated-amount><code>FirstVisitCoupon</code><message>" + 
      "Congratulations! You saved $5.00 on your first visit!</message>" + 
      "</coupon-result><gift-certificate-result><valid>true</valid>" + 
      "<calculated-amount>10.0</calculated-amount><code>GiftCert012345</code>" + 
      "<message>You used your Gift Certificate!</message></gift-certificate-result>" + 
      "</merchant-code-results></result></results></merchant-calculation-results>", 
      merchantResult.getXml());
 
  }
  
  public void testAddResultWithNullMerchantCodeResults() {
    MerchantCalculationResults merchantResult = 
     new MerchantCalculationResults();
        
    merchantResult.addResult("SuperShip", "739030698069958", true, 5.5, 7.25, 
      "USD", null);
    merchantResult.addResult("SuperShip", "739030698069958", true, 5.5, "USD", 
      null);
  }
}
