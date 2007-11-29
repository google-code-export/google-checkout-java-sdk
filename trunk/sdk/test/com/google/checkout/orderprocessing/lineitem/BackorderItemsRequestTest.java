package com.google.checkout.orderprocessing.lineitem;

import com.google.checkout.MerchantInfo;

import com.google.checkout.util.TestUtils;
import junit.framework.TestCase;

public class BackorderItemsRequestTest extends TestCase {

  public void testGetXml() {
    MerchantInfo mi = TestUtils.createMockMerchantInfo();
    String msg = TestUtils.readMessage(
        "/com/google/checkout/orderprocessing/lineitem/backorder-items-sample.xml");
    
    BackorderItemsRequest test = new BackorderItemsRequest(mi, "841171949013218");
    test.addItem("A1");
    test.addItem("B2");
    test.setSendEmail(false);

    assertEquals(msg.replaceAll("\\s", ""), test.getXml().replaceAll("\\s", ""));
  }
}
