package com.google.checkout.orderprocessing.lineitem;

import com.google.checkout.MerchantInfo;

import com.google.checkout.util.TestUtils;
import junit.framework.TestCase;

public class ShipItemsRequestTest extends TestCase {

  public void testGetXml() {
    MerchantInfo mi = TestUtils.createMockMerchantInfo();
    String msg = TestUtils.readMessage(
        "/com/google/checkout/orderprocessing/lineitem/ship-items-sample.xml");
    
    ShipItemsRequest test = new ShipItemsRequest(mi, "841171949013218");
    test.addItemShippingInformation("A1", new TrackingData[] {new TrackingData("UPS", "55555555")});
    test.addItemShippingInformation("B2", "UPS", "77777777");
    test.setSendEmail(true);

    assertEquals(msg.replaceAll("\\s", ""), test.getXml().replaceAll("\\s", ""));
  }
}
