package com.google.checkout.notification;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite =
        new TestSuite("Test for com.google.checkout.notification");
    //$JUnit-BEGIN$
    suite.addTestSuite(RefundAmountNotificationTest.class);
    suite.addTestSuite(ChargebackAmountNotificationTest.class);
    suite.addTestSuite(FulfillmentOrderStateTest.class);
    suite.addTestSuite(CompositeNotificationParserTest.class);
    suite.addTestSuite(ChargeAmountNotificationTest.class);
    suite.addTestSuite(RiskInformationNotificationTest.class);
    suite.addTestSuite(FinancialOrderStateTest.class);
    suite.addTestSuite(OrderStateChangeNotificationTest.class);
    suite.addTestSuite(AuthorizationAmountNotificationTest.class);
    suite.addTestSuite(NewOrderNotificationTest.class);
    //$JUnit-END$
    return suite;
  }

}
