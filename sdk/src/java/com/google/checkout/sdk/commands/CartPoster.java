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

import com.google.checkout.sdk.commands.EnvironmentInterface.CommandType;
import com.google.checkout.sdk.domain.CheckoutRedirect;
import com.google.checkout.sdk.domain.CheckoutShoppingCart;
import com.google.checkout.sdk.domain.Item;
import com.google.checkout.sdk.domain.OrderProcessingSupport;
import com.google.checkout.sdk.domain.ShoppingCart;
import com.google.checkout.sdk.domain.ShoppingCart.Items;

import java.math.BigDecimal;

/**
 * <p>Makes and posts Google Checkout shopping cart objects via server-to-server
 * posts.</p>
 * <p>A Google Checkout shopping cart object is a list of items, which are the
 * physical or digital goods you are selling in a given transaction, together
 * with any tax or shipping information, analytics information, and any other
 * information that affects what is displayed on the buy page to the buyer.</p>
 * <p>At the time that you post a Google Checkout shopping cart to Google Checkout,
 * you do not have specific buyer information available to you. This means that
 * tax and shipping information must be provided in a general sense using
 * tax or shipping tables, which Checkout will use to modify the cart.
 * Available on the Integration Settings page are links for Tax Setup and
 * Shipping Setup, which can store tax or shipping tables so that you do not
 * need to specify them for each cart.</p>
 * <p>After posting a Google Checkout shopping cart, you will receive a
 * {@link CheckoutRedirect} object response. This contains a
 * {@link CheckoutRedirect#getRedirectUrl()} which must be given to the buyer.
 * When they view this URL in their browser, they will be given a Google Checkout
 * buy page, where they may select their credit card, shipping address,
 * shipping options, and actually purchase the cart. You can discover their
 * purchase by listening for notifications, which
 * {@link ApiContext#handleNotification(com.google.checkout.sdk.notifications.BaseNotificationDispatcher)}
 * can help with.
 * 
*
 * @see ApiContext#cartPoster()
 */
public class CartPoster {
  
  private final ApiContext apiContext;

  CartPoster(ApiContext apiContext) {
    this.apiContext = apiContext;
  }

  /**
   * @return A Checkout shopping-cart builder.
   */
  public CheckoutShoppingCartBuilder makeCart() {
    return new CheckoutShoppingCartBuilder(this);
  }
  
  /**
   * Post Cart object to Google Checkout.
   * @param cart The Checkout Shopping Cart object.
   * @return CheckoutRedirect containing a url to which to redirect the buyer
   *   for purchase.
   * @throws CheckoutException If underlying I/O operations throw or an
   *    unsuccessful response is given. Since the buyer hasn't seen a buy page
   *    yet in this event, you do not need to do any special handling with
   *    respect to Checkout.
   */
  public CheckoutRedirect postCart(CheckoutShoppingCart cart) throws CheckoutException {
    return (CheckoutRedirect)apiContext.postCommand(CommandType.CART_POST, cart.toJAXB());
  }

  /**
   * <p>A helper for constructing and sending Checkout Shopping Carts to Google
   * Checkout. An example of use:
   * <code>
   * ApiContext apiContext = new ApiContext(...); 
   * CheckoutRedirect redirect =
   *    apiContext.cartPoster().makeCart() // creates a CheckoutShoppingCartBuilder
   *        .addItem(
   *            "Red Shoes", "A pair of size 10 trainers", 50.0, 1) // with an item
   *        .addItem(
   *            "Awesome Laces", "A pair of incredibly cool laces", 10.0, 4) // another item
   *        .buildAndPost();
   * ... // show the redirect to the user, somehow
   * </code>
   * This will offer a pair of shoes (at 50 dollars each, if the ApiContext we
   * created said we were selling in dollars) and 4 sets of laces (10 dollars
   * each) to a buyer.</p>
   * <p>If you need to modify a cart before posting it (this Builder is
   * intended to be quick and easy, not exhaustive!), you can either use the
   * builder partially, as in:
   * <code>
   * ApiContext apiContext = new ApiContext(...);
   * CheckoutShoppingCart checkoutShoppingCart =
   *    apiContext.cartPoster().makeCart() // creates a CheckoutShoppingCartBuilder
   *        .addItem(
   *            "Red Shoes", "A pair of size 10 trainers", 50.0, 1) // with an item
   *        .addItem(
   *            "Awesome Laces", "A pair of incredibly cool laces", 10.0, 4) // another item
   *        .build();
   * MerchantCheckoutFlowSupport flowSupport = new MerchanTCheckoutFlowSupport();
   * ... // modify the flow support -- add some taxes or shipping
   * checkoutShoppingCart.setCheckoutFlowSupport(flowSupport);
   * CheckoutRedirect redirect = apiContext.cartPoster().postCart(checkoutShoppingCart);
   * ... // show the redirect to the user, somehow
   * </code>
   * Or dispense with the builder entirely, and perform all operations on the
   * {@link CheckoutShoppingCart} manually, ignoring this class.</p>
   * <p>It is an error to invoke any methods on this class after {@link #build()}
   * or {@link #buildAndPost()} have been invoked.</p>
   * 
   * @see CartPoster#makeCart()
   */
  public static final class CheckoutShoppingCartBuilder {
    private final CartPoster cartPoster;
    private ShoppingCart shoppingCart;
    private OrderProcessingSupport orderProcessingSupport;
    private boolean hasBeenBuilt;
    
    CheckoutShoppingCartBuilder(CartPoster cartPoster) {
      this.cartPoster = cartPoster;
      this.shoppingCart = new ShoppingCart();
      this.hasBeenBuilt = false;
      shoppingCart.setItems(new Items());
    }
    
    /**
     * Adds a line-item to be purchased.
     * @return This builder.
     */
    public CheckoutShoppingCartBuilder addItem(String name,
        String description, double unitPrice, int quantity) {
      return addItem(name, description, BigDecimal.valueOf(unitPrice), quantity);
    }

    /**
     * Adds a line-item to be purchased.
     * @return This builder.
     */
    public CheckoutShoppingCartBuilder addItem(String name,
        String description, BigDecimal unitPrice, int quantity) {

      Item newItem = new Item();
      newItem.setItemName(name);
      newItem.setItemDescription(description);
      newItem.setUnitPrice(cartPoster.apiContext.makeMoney(unitPrice));
      newItem.setQuantity(quantity);

      return addItem(newItem);
    }

    /**
     * Adds a line-item to be purchased.
     * @return This builder.
     */
    public CheckoutShoppingCartBuilder addItem(Item item) {
      if (hasBeenBuilt) {
        throw new IllegalStateException();
      }
      shoppingCart.getItems().getItem().add(item);
      return this;
    }
    
    /**
     * Create -- but do not post! -- the constructed cart. After this method is
     * invoked, you may not use this Builder anymore.
     * @return The java domain checkout shopping cart object.
     */
    public CheckoutShoppingCart build() {
      if (hasBeenBuilt) {
        throw new IllegalStateException();
      }
      this.hasBeenBuilt = true;
      CheckoutShoppingCart checkoutShoppingCart = new CheckoutShoppingCart();
      checkoutShoppingCart.setShoppingCart(shoppingCart);
      checkoutShoppingCart.setOrderProcessingSupport(orderProcessingSupport);
      return checkoutShoppingCart;
    }
    

    /**
     * Posts the constructed cart to Google Checkout. This is implemented as
     * a call to {@link CartPoster#postCart(CheckoutShoppingCart)} and so all
     * the caveats there apply here. After this method is invoked, you may not
     * use this Builder anymore.
     * @return CheckoutRedirect containing a url to which to redirect the buyer
     *   for purchase.
     * @throws CheckoutException if
     *    {@link CartPoster#postCart(CheckoutShoppingCart)} throws an exception.
     */
    public CheckoutRedirect buildAndPost() throws CheckoutException {
      return cartPoster.postCart(build());
    }
  }
}
