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

package com.google.checkout.orderprocessing.lineitem;

import com.google.checkout.CheckoutException;
import com.google.checkout.MerchantInfo;
import com.google.checkout.util.TestUtils;
import com.google.checkout.util.Utils;

import junit.framework.TestCase;

import org.w3c.dom.Document;

public class ResetItemsShippingInformationRequestTest extends TestCase {

  public void testGetXml() throws CheckoutException {
    MerchantInfo mi = TestUtils.createMockMerchantInfo();
    ResetItemsShippingInformationRequest test = 
      new ResetItemsShippingInformationRequest(mi, "841171949013218");
    test.addItem("A1");
    test.addItem("B2");
    test.setSendEmail(false);

    String expectedXmlMsg = TestUtils.readMessage(
      "/com/google/checkout/orderprocessing/lineitem/" + 
      "reset-items-shipping-information-sample.xml");
    Document expectedDoc = Utils.newDocumentFromString(expectedXmlMsg);
    
    assertEquals(Utils.documentToString(expectedDoc).replaceAll("\\s", ""),
        test.getXml().replaceAll("\\s", ""));    
  }
}
