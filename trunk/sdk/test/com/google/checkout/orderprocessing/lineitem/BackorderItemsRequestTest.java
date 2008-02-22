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

import com.google.checkout.MerchantInfo;
import com.google.checkout.util.TestUtils;

import junit.framework.TestCase;

/**
 * 
 * @author Charles Dang (cdang@google.com)
 */
public class BackorderItemsRequestTest extends TestCase {
  private MerchantInfo mi;
  private BackorderItemsRequest request;

  public void setUp() {
    mi = TestUtils.createMockMerchantInfo();

    request = new BackorderItemsRequest(mi, "841171949013218");
  }

  public void testGetXml() {
    String msg =
        TestUtils
            .readMessage("/com/google/checkout/orderprocessing/lineitem/backorder-items-sample.xml");

    request.addItem("A1");
    request.addItem("B2");
    request.setSendEmail(false);

    assertEquals(msg.replaceAll("\\s", ""), request.getXml().replaceAll("\\s",
        ""));
  }
}
