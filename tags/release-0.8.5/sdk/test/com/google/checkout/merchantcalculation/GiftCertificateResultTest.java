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

import junit.framework.TestCase;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class GiftCertificateResultTest extends TestCase {
  public void testDefaultConstructor() {
    GiftCertificateResult result = 
      new GiftCertificateResult(true, 100.50f, "USD", "GiftCert012345", 
      "Congratulations! You saved $5.00 on your first visit!");
    
    assertEquals("gift-certificate-result", result.getType());
    assertTrue(result.isValid());
    assertEquals(100.50f, result.getCalculatedAmount(), 0); // delta = 0
    assertEquals("USD", result.getCurrency());
    assertEquals("GiftCert012345", result.getCode());
    assertEquals("Congratulations! You saved $5.00 on your first visit!", result.getMessage());
  }
}
