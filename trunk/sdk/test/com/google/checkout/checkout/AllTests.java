package com.google.checkout.checkout;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for com.google.checkout.checkout");
    // $JUnit-BEGIN$
    suite.addTestSuite(TestDigitalDelivery.class);
    suite.addTestSuite(TestCarrierCalculatedShipping.class);
    // $JUnit-END$
    return suite;
  }

}
