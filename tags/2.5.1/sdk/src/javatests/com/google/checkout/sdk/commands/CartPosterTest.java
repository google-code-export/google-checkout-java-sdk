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
package com.google.checkout.sdk.commands;

import com.google.checkout.sdk.commands.CartPoster.CheckoutShoppingCartBuilder;
import com.google.checkout.sdk.domain.CheckoutShoppingCart;
import com.google.checkout.sdk.domain.Item;
import com.google.checkout.sdk.domain.ShoppingCart;
import com.google.checkout.sdk.testing.AbstractCommandTestCase;

import java.math.BigDecimal;
import java.util.List;

/**
 * Tests for creating Google Checkout shopping carts.
 *
 */
public class CartPosterTest extends AbstractCommandTestCase {
  public void testCartPosterDoublePrice() {
     CheckoutShoppingCart checkoutShoppingCart =
       apiContext().cartPoster()
         .makeCart()
         .addItem("itemName", "itemDescription", 10.0, 1)
         .build();
     assertNull(checkoutShoppingCart.getCheckoutFlowSupport());
     assertNull(checkoutShoppingCart.getOrderProcessingSupport());
     ShoppingCart shoppingCart = checkoutShoppingCart.getShoppingCart();
     assertNull(shoppingCart.getBuyerMessages());
     assertNull(shoppingCart.getCartExpiration());
     assertNull(shoppingCart.getMerchantPrivateData());
     List<Item> items = shoppingCart.getItems().getItem();
     assertEquals(1, items.size());
     Item item = items.get(0);
     assertEquals("itemName", item.getItemName());
     assertEquals("itemDescription", item.getItemDescription());
     assertEquals("XXX", item.getUnitPrice().getCurrency());
     assertEquals(10.0, item.getUnitPrice().getValue().doubleValue());
     assertEquals(1, item.getQuantity());
  }

  public void testCartPosterBignumPrice() {
    CheckoutShoppingCart checkoutShoppingCart =
      apiContext().cartPoster()
        .makeCart()
        .addItem("itemName", "itemDescription", BigDecimal.valueOf(10.0), 1)
        .build();
    assertNull(checkoutShoppingCart.getCheckoutFlowSupport());
    assertNull(checkoutShoppingCart.getOrderProcessingSupport());
    ShoppingCart shoppingCart = checkoutShoppingCart.getShoppingCart();
    assertNull(shoppingCart.getBuyerMessages());
    assertNull(shoppingCart.getCartExpiration());
    assertNull(shoppingCart.getMerchantPrivateData());
    List<Item> items = shoppingCart.getItems().getItem();
    assertEquals(1, items.size());
    Item item = items.get(0);
    assertEquals("itemName", item.getItemName());
    assertEquals("itemDescription", item.getItemDescription());
    assertEquals("XXX", item.getUnitPrice().getCurrency());
    assertEquals(BigDecimal.valueOf(10.0).doubleValue(),
        item.getUnitPrice().getValue().doubleValue());
    assertEquals(1, item.getQuantity());
  }

  public final String REDIRECT_URL = "http://checkout.google.com/redirect";

  public void testBuildAndPost() {
    TestingApiContext apiContext = apiContext(
        "<checkout-redirect xmlns=\"http://checkout.google.com/schema/2\" serial-number=\"123456\"><redirect-url>" +
          REDIRECT_URL +
        "</redirect-url></checkout-redirect>");

    String redirectUrl = apiContext.cartPoster()
      .makeCart()
      .addItem("itemName", "itemDescription", BigDecimal.valueOf(10.0), 1)
      .buildAndPost().getRedirectUrl();

    assertEquals(REDIRECT_URL, redirectUrl);

    assertTrue(apiContext.getOutput().contains("checkout-shopping-cart"));
    assertTrue(apiContext.getOutput().contains("itemName"));
    assertTrue(apiContext.getOutput().contains("itemDescription"));
    assertTrue(apiContext.getOutput().contains("<unit-price currency=\"XXX\">10</unit-price>"));
  }

  public void testCartPosterMultipleBuilds() {
    CheckoutShoppingCartBuilder builder =
      apiContext().cartPoster()
        .makeCart()
        .addItem("itemName", "itemDescription", BigDecimal.valueOf(10.0), 1);

    builder.build();
    try {
      builder.build();
      fail();
    } catch (IllegalStateException expected) {
      // pass
    }
  }
}
