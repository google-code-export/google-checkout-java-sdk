/*******************************************************************************
 * Copyright (C) 2007 Google Inc.
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

package com.google.checkout.notification;

import com.google.checkout.checkout.Item;
import com.google.checkout.CheckoutException;
import com.google.checkout.util.Utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class encapsulates the &lt;new-order-notification&gt; notification.
 * 
 * @author simonjsmith
 * 
 */
public class NewOrderNotification extends CheckoutNotification {

  /**
   * A constructor which takes the request as a String.
   * 
   * @param requestString
   * @throws com.google.checkout.CheckoutException if there was an
   * error prcessing the request string
   */
  public NewOrderNotification(String requestString) throws CheckoutException {
    this(Utils.newDocumentFromString(requestString));
  }

  /**
   * A constructor which takes the request as an InputStream.
   * 
   * @param inputStream
   * @throws com.google.checkout.CheckoutException if there was an
   * error prcessing the request from the InputStream
   */
  public NewOrderNotification(InputStream inputStream) throws CheckoutException {
    this(Utils.newDocumentFromInputStream(inputStream));
  }
  
  /**
   * A constructor which takes in an xml document representation of the request.
   * 
   * @param document
   */
  public NewOrderNotification(Document document) {
    super(document);
  }

  /**
   * Retrieves the contents of the &lt;items&gt; element as a Collection of Item
   * objects.
   * 
   * @return The Collection of Item objects.
   * @deprecated Use getShoppingCart().getItems()
   * @see Item
   */
  public Collection getItems() {
    Document document = getDocument();
    Element root = getRoot();
    
    Element shoppingCart =
        Utils.findElementOrContainer(document, root, "shopping-cart");
    Element items =
        Utils.findElementOrContainer(document, shoppingCart, "items");
    Element[] elements = Utils.getElements(document, items);
    Collection ret = new ArrayList();

    for (int i = 0; i < elements.length; i++) {
      ret.add(new Item(document, elements[i]));
    }
    return ret;
  }
  
  /**
   * Retrieves the shopping cart
   * 
   * @return A shopping cart containing buyer items
   */
  public ShoppingCart getShoppingCart() {
    Document document = getDocument();
    Element root = getRoot();
    
    Element shoppingCart =
        Utils.findElementOrContainer(document, root, "shopping-cart");
    return new ShoppingCart(document, shoppingCart);
  }

  /**
   * Retrieves the contents of the &lt;merchant-private-data&gt; element as an
   * array of Elements. 
   * 
   * @return The contents &lt;merchant-private-data&gt; element value.
   * @deprecated Use getShoppingCart().getMerchantPrivateDataNodes()
   * @see Element
   */
  public Element[] getMerchantPrivateDataNodes() {
    Document document = getDocument();
    Element root = getRoot();
    
    Element shoppingCart =
        Utils.findContainerElseCreate(document, root, "shopping-cart");
    Element mpd =
        Utils.findElementOrContainer(document, shoppingCart,
        "merchant-private-data");
    if (mpd == null) {
      return null;
    }
    return Utils.getElements(document, mpd);
  }

  /**
   * 
   * Retrieves the value of the &lt;good-until-date&gt; element.
   * 
   * @return The cart expiration.
   * @deprecated Use getShoppingCart().getCartExpiration()
   * @see Date
   * @throws com.google.checkout.CheckoutException if there was an
   * error retrieving the cart expiration date
   */
  public Date getCartExpiration() throws CheckoutException {
    Document document = getDocument();
    Element root = getRoot();   
 
    Element shoppingCart =
        Utils.findContainerElseCreate(document, root, "shopping-cart");
    Element cartExpiration = Utils
      .findContainerElseCreate(document, shoppingCart, "cart-expiration");

    return Utils.getElementDateValue(document, cartExpiration, "good-until-date");
  }

  /**
   * Retrieves the value of the &lt;buyer-shipping-address&gt; element.
   * 
   * @return The buyer shipping address
   * 
   * @see Address
   */
  public Address getBuyerShippingAddress() {
    Document document = getDocument();
    Element root = getRoot();
    
    Element address =
        Utils.findElementOrContainer(document, root, "buyer-shipping-address");
    return new Address(document, address);
  }

  /**
   * Retrieves the value of the &lt;buyer-billing-address&gt; element.
   * 
   * @return The buyer billing address
   * 
   * @see Address
   */
  public Address getBuyerBillingAddress() {
    Document document = getDocument();
    Element root = getRoot();
    
    Element address =
        Utils.findElementOrContainer(document, root, "buyer-billing-address");
    return new Address(document, address);
  }
  
  public BuyerMarketingPreferences getBuyerMarketingPreferences() {
    Document document = getDocument();
    Element root = getRoot();
    
    Element buyerMarketingPreferences = 
      Utils.findElementOrContainer(document, root, "buyer-marketing-preferences");
    return new BuyerMarketingPreferences(document, buyerMarketingPreferences);
  }
  
  public OrderAdjustment getOrderAdjustment() {
    Document document = getDocument();
    Element root = getRoot();
    
    Element orderAdjustment = 
      Utils.findElementOrContainer(document, root, "order-adjustment");
    return new OrderAdjustment(document, orderAdjustment);
  }

  /**
   * Retrieves the value of the &lt;email-allowed&gt; element.
   * 
   * @deprecated Use getBuyerMarketingPreferences().isMarketingEmailAllowed()
   * @return The marketing preferences flag.
   */
  public boolean isMarketingEmailAllowed() {
    Document document = getDocument();
    Element root = getRoot();
    
    Element buyerMarketingPreferences =
        Utils.findElementOrContainer(document, root,
            "buyer-marketing-preferences");
    return Utils.getElementBooleanValue(document, buyerMarketingPreferences,
        "email-allowed");
  }

  /**
   * Retrieves the value of the &lt;merchant-calculation-successful&gt; element.
   * 
   * @deprecated Use getOrderAdjustment().isMerchantCalculationSuccessful()
   * @return The merchant calculation successful flag.
   */
  public boolean isMerchantCalculationSuccessful() {
    Document document = getDocument();
    Element root = getRoot();
 
    Element orderAdjustment =
        Utils.findElementOrContainer(document, root, "order-adjustment");
    return Utils.getElementBooleanValue(document, orderAdjustment,
        "merchant-calculation-successful");
  }

  /**
   * Retrieves the contents of the &lt;merchant-codes&gt; element as a
   * collection of MerchantCodes objects.
   * 
   * @return The merchant codes.
   * 
   * @deprecated Use getOrderAdjustment().getMerchantCodes()
   * 
   * @see MerchantCodes
   * @see GiftCertificateAdjustment
   * @see CouponAdjustment
   */
  public Collection getMerchantCodes() {
    Document document = getDocument();
    Element root = getRoot(); 
    
    Element oa =
        Utils.findElementOrContainer(document, root, "order-adjustment");
    Element mc = Utils.findElementOrContainer(document, oa, "merchant-codes");

    Element[] elements = Utils.getElements(document, mc);
    Collection ret = new ArrayList();

    Element e;
    String name;
    for (int i = 0; i < elements.length; i++) {
      e = elements[i];
      name = e.getNodeName();
      if ("gift-certificate-adjustment".equals(name)) {
        ret.add(new GiftCertificateAdjustment(document, e));
      } else if ("coupon-adjustment".equals(name)) {
        ret.add(new CouponAdjustment(document, e));
      }
    }
    return ret;
  }

  /**
   * Retrieves the value of the &lt;total-tax&gt; element.
   * 
   * @return The total tax.
   */
  public float getTotalTax() {
    Document document = getDocument();
    Element root = getRoot();
    
    Element orderAdjustment =
        Utils.findElementOrContainer(document, root, "order-adjustment");
    return Utils.getElementFloatValue(document, orderAdjustment, "total-tax");
  }

  /**
   * Retrieves the value of the &lt;adjustment-total&gt; element.
   * 
   * @deprecated Use getOrderAdjustment().getAdjustmentTotal();
   * @return The adjustment total amount.
   */
  public float getAdjustmentTotal() {
    Document document = getDocument();
    Element root = getRoot();
    
    Element orderAdjustment =
        Utils.findElementOrContainer(document, root, "order-adjustment");
    return Utils.getElementFloatValue(document, orderAdjustment,
        "adjustment-total");
  }

  /**
   * Retrieves the value of the &lt;shipping&gt; element as a Shipping object.
   * 
   * @return The shipping.
   * 
   * @deprecated Use getOrderAdjustment().getShipping()
   * 
   * @see Shipping
   */
  public Shipping getShipping() {
    Document document = getDocument();
    Element root = getRoot();
    
    Element oa =
        Utils.findElementOrContainer(document, root, "order-adjustment");
    Element shipping = Utils.findElementOrContainer(document, oa, "shipping");

    Element e =
        Utils.findElementOrContainer(document, shipping,
            "merchant-calculated-shipping-adjustment");
    if (e != null) {
      return new MerchantCalculatedShippingAdjustment(document, e);
    }

    e =
        Utils.findElementOrContainer(document, shipping,
            "flat-rate-shipping-adjustment");
    if (e != null) {
      return new FlatRateShippingAdjustment(document, e);
    }

    e =
        Utils.findElementOrContainer(document, shipping,
            "pickup-shipping-adjustment");
    if (e != null) {
      return new PickupShippingAdjustment(document, e);
    }

    e =
        Utils.findElementOrContainer(document, shipping,
            "carrier-calculated-shipping-adjustment");
    if (e != null) {
      return new CarrierCalculatedShippingAdjustment(document, e);
    }

    return null;
  }

  /**
   * Retrieves the value of the &lt;order-total&gt; element.
   * 
   * @return The order total.
   */
  public float getOrderTotal() {
    return Utils.getElementFloatValue(getDocument(), getRoot(), "order-total");
  }

  /**
   * Retrieves the currency code.
   * 
   * @return The currency code.
   */
  public String getOrderCurrencyCode() {
    return Utils.findElementOrContainer(getDocument(), getRoot(), "order-total")
        .getAttribute("currency");
  }
  
  /**
   * Retrieves the promotions.
   * 
   * @return The promotions associated with this NewOrderNotification
   */
  public Element[] getPromotions() {
    Document document = getDocument();
    Element root = getRoot();
    
    Element promotions =
        Utils.findElementOrContainer(document, root, "promotions");
    if (promotions == null) {
      return null;
    }
    return Utils.getElements(document, promotions); 
  }

  /**
   * Retrieves the value of the &lt;fulfillment-order-state&gt; element.
   * 
   * @return The fulfillment order state.
   * 
   * @see FulfillmentOrderState
   */
  public FulfillmentOrderState getFulfillmentOrderState() {
    String state =
        Utils.getElementStringValue(getDocument(), getRoot(), "fulfillment-order-state");
    return FulfillmentOrderState.getState(state);
  }

  /**
   * Retrieves the value of the &lt;financial-order-state&gt; element.
   * 
   * @return The financial order state.
   * 
   * @see FinancialOrderState
   */
  public FinancialOrderState getFinancialOrderState() {
    String state =
        Utils.getElementStringValue(getDocument(), getRoot(), "financial-order-state");
    return FinancialOrderState.getState(state);
  }

  /**
   * Retrieves the value of the &lt;buyer-id&gt; element.
   * 
   * @return The buyer id.
   */
  public long getBuyerId() {
    return Utils.getElementLongValue(getDocument(), getRoot(), "buyer-id");
  }

}
