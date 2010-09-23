/*******************************************************************************
 * Copyright (C) 2009 Google Inc.
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
package com.google.checkout.sdk.notifications;

import com.google.checkout.sdk.domain.OrderSummary;
import com.google.checkout.sdk.testing.AbstractCommandTestCase;
import com.google.checkout.sdk.testing.HistoricalNotificationDispatcher;
import com.google.checkout.sdk.testing.NullHttpServletRequest;
import com.google.checkout.sdk.testing.NullHttpServletResponse;
import com.google.checkout.sdk.testing.HistoricalNotificationDispatcher.DispatcherHistoryElement;
import com.google.checkout.sdk.util.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Test the notification dispatcher's locking behavior.
 *
 */
public class NotificationDispatcherTest extends AbstractCommandTestCase {
  private static final String SERIAL_NUMBER = "85f54628-538a-44fc-8605-ae62364f6c72";
  private static final String EXAMPLE_NEW_ORDER_NOTIFICATION =
      "<?xml version='1.0' encoding='UTF-8'?>"
    + "<new-order-notification xmlns='http://checkout.google.com/schema/2'"
    + "    serial-number='" + SERIAL_NUMBER + "'>"
    + "    <google-order-number>841171949013218</google-order-number>"
    + "    <buyer-shipping-address>"
    + "        <contact-name>Will Shipp-Toomey</contact-name>"
    + "        <email>willstoomey@example.com</email>"
    + "        <address1>10 Example Road</address1>"
    + "        <city>Sampleville</city>"
    + "        <region>CA</region>"
    + "        <postal-code>94141</postal-code>"
    + "        <country-code>US</country-code>"
    + "        <phone>5555551234</phone>"
    + "        <structured-name>"
    + "          <first-name>Will</first-name>"
    + "          <last-name>Shipp-Toomey</last-name>"
    + "        </structured-name>"
    + "    </buyer-shipping-address>"
    + "    <buyer-billing-address>"
    + "        <contact-name>Bill Hu</contact-name>"
    + "        <email>billhu@example.com</email>"
    + "        <address1>99 Credit Lane</address1>"
    + "        <city>Mountain View</city>"
    + "        <region>CA</region>"
    + "        <postal-code>94043</postal-code>"
    + "        <country-code>US</country-code>"
    + "        <phone>5555557890</phone>"
    + "        <structured-name>"
    + "          <first-name>Bill</first-name>"
    + "          <last-name>Hu</last-name>"
    + "        </structured-name>"
    + "    </buyer-billing-address>"
    + "    <buyer-id>294873009217523</buyer-id>"
    + "    <fulfillment-order-state>NEW</fulfillment-order-state>"
    + "    <financial-order-state>REVIEWING</financial-order-state>"
    + ""
    + "    <shopping-cart>"
    + "        <cart-expiration>"
    + "            <good-until-date>2007-12-31T23:59:59-05:00</good-until-date>"
    + "        </cart-expiration>"
    + "        <items>"
    + "            <item>"
    + "                <merchant-item-id>GGLAA1453</merchant-item-id>"
    + "                <item-name>Dry Food Pack</item-name>"
    + "                <item-description>One pack of nutritious dried food for emergencies.</item-description>"
    + "                <quantity>1</quantity>"
    + "                <tax-table-selector>food</tax-table-selector>"
    + "                <unit-price currency='USD'>4.99</unit-price>"
    + "            </item>"
    + "            <item>"
    + "                <merchant-item-id>MGS2GBMP3</merchant-item-id>"
    + "                <item-name>Megasound 2GB MP3 Player</item-name>"
    + "                <item-description>This portable MP3 player stores 500 songs.</item-description>"
    + "                <quantity>1</quantity>"
    + "                <unit-price currency='USD'>179.99</unit-price>"
    + "                <merchant-private-item-data>"
    + "                    <merchant-product-id>1234567890</merchant-product-id>"
    + "                </merchant-private-item-data>"
    + "            </item>"
    + "        </items>"
    + "    </shopping-cart>"
    + "    <order-adjustment>"
    + "        <merchant-calculation-successful>true</merchant-calculation-successful>"
    + "        <merchant-codes>"
    + "            <coupon-adjustment>"
    + "                <applied-amount currency='USD'>5.00</applied-amount>"
    + "                <code>FirstVisitCoupon</code>"
    + "                <calculated-amount currency='USD'>5.00</calculated-amount>"
    + "                <message>You saved $5.00 for your first visit!</message>"
    + "            </coupon-adjustment>"
    + "            <gift-certificate-adjustment>"
    + "                <applied-amount currency='USD'>10.00</applied-amount>"
    + "                <code>GiftCert12345</code>"
    + "                <calculated-amount currency='USD'>10.00</calculated-amount>"
    + "                <message>You saved $10.00 with this gift certificate!</message>"
    + "            </gift-certificate-adjustment>"
    + "        </merchant-codes>"
    + "        <total-tax currency='USD'>11.05</total-tax>"
    + "        <shipping>"
    + "            <merchant-calculated-shipping-adjustment>"
    + "                <shipping-name>SuperShip</shipping-name>"
    + "                <shipping-cost currency='USD'>9.95</shipping-cost>"
    + "            </merchant-calculated-shipping-adjustment>"
    + "        </shipping>"
    + "    </order-adjustment>"
    + "    <order-total currency='USD'>190.98</order-total>"
    + "    <buyer-marketing-preferences>"
    + "        <email-allowed>false</email-allowed>"
    + "    </buyer-marketing-preferences>"
    + "    <timestamp>2007-03-19T15:06:26.051Z</timestamp>"
    + "</new-order-notification>";

  public void testSimpleParseAndTransactionality() throws Exception {
    HttpServletRequest request = new NullHttpServletRequest() {
      @Override
      public String getHeader(String name) {
        if (name.equals("Authorization")) {
          return apiContext().getHttpAuth();
        } else if (name.equals(Utils.CONTENT_TYPE)) {
          return "application/xml";
        }
        fail();
        return null;
      }

      @Override
      public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(
            EXAMPLE_NEW_ORDER_NOTIFICATION.getBytes("utf-8"));
        return new ServletInputStream() {
          @Override
          public int read() {
            return bais.read();
          }
        };
      }
    };
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    HttpServletResponse response = new NullHttpServletResponse() {
      @Override
      public ServletOutputStream getOutputStream() {
        return new ServletOutputStream() {
          @Override
          public void write(int b) {
            baos.write(b);
          }
        };
      }
    };
    HistoricalNotificationDispatcher dispatcher =
        new HistoricalNotificationDispatcher(request, response) {
      @Override
      public void onAllNotifications(
          OrderSummary orderSummary, Notification notification)
          throws Exception {
        List<DispatcherHistoryElement> history = this.getHistory();
        assertEquals(
            new DispatcherHistoryElement(NotificationDispatcherTest.SERIAL_NUMBER,
            "startTransaction", false, false),
            history.get(0));
        assertEquals(
            new DispatcherHistoryElement(NotificationDispatcherTest.SERIAL_NUMBER,
            "hasAlreadyHandled", false, false),
            history.get(1));
        assertEquals(2, history.size());
      }
    };
    apiContext().handleNotification(dispatcher);
    List<DispatcherHistoryElement> history = dispatcher.getHistory();
    assertEquals(
        new DispatcherHistoryElement(SERIAL_NUMBER,
            "startTransaction", false, false),
        history.get(0));
    assertEquals(
        new DispatcherHistoryElement(SERIAL_NUMBER,
        "hasAlreadyHandled", false, false),
        history.get(1));
    assertEquals(
        new DispatcherHistoryElement(SERIAL_NUMBER,
        "rememberSerialNumber", false, true),
        history.get(2));
    assertEquals(
        new DispatcherHistoryElement(SERIAL_NUMBER,
        "commitTransaction", true, true),
        history.get(3));
    assertEquals(4, history.size());
    assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
        + "<notification-acknowledgment "
        + "xmlns=\"http://checkout.google.com/schema/2\" "
        + "serial-number=\"" + SERIAL_NUMBER + "\"/>\n",
        baos.toString("utf-8"));
  }
}
