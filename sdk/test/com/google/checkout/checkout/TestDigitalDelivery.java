package com.google.checkout.checkout;

import com.google.checkout.CheckoutException;
import com.google.checkout.CheckoutResponse;
import com.google.checkout.MerchantInfo;
import com.google.checkout.handlers.TestUtils;
import com.google.checkout.notification.NewOrderNotification;

import junit.framework.TestCase;

import java.util.Collection;
import java.util.Iterator;

public class TestDigitalDelivery extends TestCase {

  public void testDigitalSubmit1() throws CheckoutException {
    MerchantInfo mi = TestUtils.createMockMerchantInfo();
    String msg =
        TestUtils
            .readMessage("/com/google/checkout/checkout/digital-sample-1.xml");

    CheckoutShoppingCartRequest cart = new CheckoutShoppingCartRequest(mi);
    Item item = new Item();
    item.setItemName("Super Software 5000");
    item.setItemDescription("Super Software 5000 improves download speeds.");
    item.setUnitPriceAmount(1.00f);
    item.setUnitPriceCurrency(mi.getCurrencyCode());
    item.setQuantity((new Integer(1)).intValue());
    DigitalContent dc = new DigitalContent();
    dc.setEmailDelivery(false);
    item.setDigitalContent(dc);
    dc.setEmailDelivery(true);
    item.setDigitalContent(dc);
    cart.addItem(item);

    CheckoutResponse res = cart.send();
    System.out.println(res.getXmlPretty());

    assertTrue(res.isValidRequest());
    assertEquals(msg.replaceAll("\\s", ""), cart.getXml().replaceAll("\\s", ""));
  }

  public void testDigitalSubmit2() throws CheckoutException {
    MerchantInfo mi = TestUtils.createMockMerchantInfo();
    String msg =
        TestUtils
            .readMessage("/com/google/checkout/checkout/digital-sample-2.xml");

    CheckoutShoppingCartRequest cart = new CheckoutShoppingCartRequest(mi);
    Item item = new Item();
    item.setItemName("Super Software 5000");
    item.setItemDescription("Super Software 5000 improves download speeds.");
    item.setUnitPriceAmount(1.00f);
    item.setUnitPriceCurrency(mi.getCurrencyCode());
    item.setQuantity((new Integer(1)).intValue());
    DigitalContent dc = new DigitalContent();
    dc
        .setDescription("Please go to <a href=\"http://supersoft.example.com\">our website</a>, and enter your access key so that you can download our software.");
    dc.setKey("1456-1514-3657-2198");
    dc.setUrl("http://supersoft.example.com");
    item.setDigitalContent(dc);
    cart.addItem(item);

    CheckoutResponse res = cart.send();
    System.out.println(res.getXmlPretty());

    assertTrue(res.isValidRequest());
    assertEquals(msg.replaceAll("\\s", ""), cart.getXml().replaceAll("\\s", ""));
  }

  public void testDigitalNon1() throws CheckoutException {
    String msg =
        TestUtils
            .readMessage("/com/google/checkout/checkout/digital-non-sample-1.xml");

    NewOrderNotification non = new NewOrderNotification(msg);
    Collection items = non.getItems();
    Iterator itemsIt = items.iterator();
    while (itemsIt.hasNext()) {
      Item i = (Item) itemsIt.next();
      assertTrue(i.getDigitalContent().isEmailDelivery());
    }
  }

  public void testDigitalNon2() throws CheckoutException {
    String msg =
        TestUtils
            .readMessage("/com/google/checkout/checkout/digital-non-sample-2.xml");

    NewOrderNotification non = new NewOrderNotification(msg);
    Collection items = non.getItems();
    Iterator itemsIt = items.iterator();
    while (itemsIt.hasNext()) {
      Item i = (Item) itemsIt.next();
      assertEquals(
          i.getDigitalContent().getDescription().replaceAll("\\s", ""),
          "Please go to <a href=\"http://supersoft.example.com\">our website</a>,and enter your access key so that you can download our software."
              .replaceAll("\\s", ""));
      assertEquals(i.getDigitalContent().getKey(), "1456-1514-3657-2198");
      assertEquals(i.getDigitalContent().getUrl(),
          "http://supersoft.example.com");

    }
  }


}
