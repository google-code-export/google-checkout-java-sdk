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

import com.google.checkout.CheckoutException;
import com.google.checkout.MerchantInfo;
import com.google.checkout.util.TestUtils;
import com.google.checkout.util.Utils;

import junit.framework.TestCase;

import org.w3c.dom.Document;

/**
 * 
 * @author Charles Dang (@author cdang@google.com)
 */
public class AddTrackingDataRequestTest extends TestCase {
  private AddTrackingDataRequest request;
  private MerchantInfo mi;

  public void setUp() {
    mi = TestUtils.createMockMerchantInfo();
  }

  public void testAddTrackingDataRequestConstructor() throws CheckoutException {
    request =
        new AddTrackingDataRequest(mi, "841171949013218", "UPS MI",
            "Z5498W45987123684");
    assertEquals("Z5498W45987123684", request.getTrackingNumber());
    
    String expectedXmlMsg =
        TestUtils
            .readMessage("/com/google/checkout/orderprocessing/add-tracking-data-sample.xml");
    Document expectedDoc = Utils.newDocumentFromString(expectedXmlMsg);
    
    assertEquals(Utils.documentToString(expectedDoc).replaceAll("\\s", ""),
        request.getXml().replaceAll("\\s", ""));
  }
}
