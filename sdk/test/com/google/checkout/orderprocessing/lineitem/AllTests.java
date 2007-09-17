package com.google.checkout.orderprocessing.lineitem;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite =
        new TestSuite("Test for com.google.checkout.orderprocessing.lineitem");
    //$JUnit-BEGIN$
    suite.addTestSuite(ShipItemsRequestTest.class);
    suite.addTestSuite(BackorderItemsRequestTest.class);
    suite.addTestSuite(ResetItemsShippingInformationRequestTest.class);
    suite.addTestSuite(ReturnItemsRequestTest.class);
    suite.addTestSuite(CancelItemsRequestTest.class);
    //$JUnit-END$
    return suite;
  }

}
