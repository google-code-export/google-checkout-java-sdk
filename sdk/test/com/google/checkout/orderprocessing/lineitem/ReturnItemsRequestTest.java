package com.google.checkout.orderprocessing.lineitem;

import com.google.checkout.MerchantInfo;

import com.google.checkout.util.TestUtils;
import junit.framework.TestCase;

public class ReturnItemsRequestTest extends TestCase {

  public void testGetXml() {
    MerchantInfo mi = TestUtils.createMockMerchantInfo();
    String msg = TestUtils.readMessage(
        "/com/google/checkout/orderprocessing/lineitem/return-items-sample.xml");
    
    ReturnItemsRequest test = new ReturnItemsRequest(mi, "841171949013218");
    test.addItem("A1");
    test.addItem("B2");
    test.setSendEmail(false);

    assertEquals(msg.replaceAll("\\s", ""), test.getXml().replaceAll("\\s", ""));
  }
}
