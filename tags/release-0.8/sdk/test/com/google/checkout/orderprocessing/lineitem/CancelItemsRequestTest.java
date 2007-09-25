package com.google.checkout.orderprocessing.lineitem;

import com.google.checkout.MerchantInfo;
import com.google.checkout.handlers.TestUtils;

import junit.framework.TestCase;

public class CancelItemsRequestTest extends TestCase {

  public void testGetXml() {
    MerchantInfo mi = TestUtils.createMockMerchantInfo();
    String msg = TestUtils.readMessage(
        "/com/google/checkout/orderprocessing/lineitem/cancel-items-sample.xml");
    
    CancelItemsRequest test = new CancelItemsRequest(mi, "841171949013218");
    test.addItem("A1");
    test.addItem("B2");
    test.setSendEmail(false);

    assertEquals(msg.replaceAll("\\s", ""), test.getXml().replaceAll("\\s", ""));
  }
}
