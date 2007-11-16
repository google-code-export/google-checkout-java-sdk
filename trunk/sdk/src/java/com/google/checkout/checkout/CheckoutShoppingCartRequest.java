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

package com.google.checkout.checkout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.checkout.AbstractCheckoutRequest;
import com.google.checkout.MerchantInfo;
import com.google.checkout.exceptions.CheckoutException;
import com.google.checkout.util.Utils;

/**
 * Class used to create the structure needed by Google Checkout The class also
 * has the ability to send that request to Google or return the Xml needed to
 * place in the hidden form fields.
 * 
 * @author simonjsmith@google.com
 */
public class CheckoutShoppingCartRequest extends AbstractCheckoutRequest {

  private final Element shoppingCart;

  private final Element checkoutFlowSupport;

  /**
   * Constructor which takes an instance of mi.
   */
  public CheckoutShoppingCartRequest(MerchantInfo mi) {
    super(mi, "checkout-shopping-cart");
    
    // Document and root should be initialized after calling the super ctor
    Document document = getDocument();
    Element root = getRoot();
    
    shoppingCart = document.createElement("shopping-cart");
    checkoutFlowSupport = document.createElement("checkout-flow-support");

    Element merchantCheckoutFlowSupport =
        document.createElement("merchant-checkout-flow-support");

    root.appendChild(shoppingCart);
    root.appendChild(checkoutFlowSupport);
    checkoutFlowSupport.appendChild(merchantCheckoutFlowSupport);
  }

  /**
   * Constructor which takes an instance of mi and a cart XML.
   */
  public CheckoutShoppingCartRequest(MerchantInfo mi, String cartXml) throws CheckoutException{
    super(mi, Utils.newDocumentFromString(cartXml));
    
    // Document and root should be initialized after caller the super ctor
    Document document = getDocument();
    Element root = getRoot();
    
    shoppingCart = Utils.findContainerElseCreate(document, root, "shopping-cart");
    checkoutFlowSupport = Utils.findContainerElseCreate(document, root, "checkout-flow-support");
  }

  /**
   * Constructor which takes an instance of mi and the cart expiration.
   * 
   * @param expirationMinutesFromNow The number of minutes before the cart
   *        should expire.
   */
  public CheckoutShoppingCartRequest(MerchantInfo mi,
      int expirationMinutesFromNow) {

    this(mi);
    this.setExpirationMinutesFromNow(expirationMinutesFromNow);
  }

  /**
   * This method adds a flat-rate shipping method to an order. This method
   * handles flat-rate shipping methods that have shipping restrictions.
   * 
   * @param name The name of the shipping method. This value will be displayed
   *        on the Google Checkout order review page.
   * 
   * @param cost The cost associated with the shipping method.
   * 
   * @param restrictions A list of country, state or zip code areas where the
   *        shipping method is either available or unavailable.
   * 
   * @see ShippingRestrictions
   */
  public void addFlatRateShippingMethod(String name, float cost,
      ShippingRestrictions restrictions) {

    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Element shippingMethods =
        Utils.findContainerElseCreate(document, mcfs, "shipping-methods");

    Element newShip =
        Utils.createNewContainer(document, shippingMethods,
            "flat-rate-shipping");
    newShip.setAttribute("name", name);

    Element price =
        Utils.createNewElementAndSet(document, newShip, "price", cost);
    price.setAttribute("currency", mi.getCurrencyCode());

    if (restrictions != null) {
      Utils.importElements(document, newShip, new Element[] {restrictions
          .getRootElement()});
    }
  }

  /**
   * This method adds an item to an order. This method handles items that do not
   * have &lt;merchant-private-item-data&gt; XML blocks associated with them.
   * 
   * @param name The name of the item. This value corresponds to the value of
   *        the &lt;item-name&gt; tag in the Checkout API request.
   * 
   * @param description The description of the item. This value corresponds to
   *        the value of the &lt;item-description&gt; tag in the Checkout API
   *        request.
   * 
   * @param price The price of the item. This value corresponds to the value of
   *        the &lt;unit-price&gt; tag in the Checkout API request.</param>
   * 
   * @param quantity The number of this item that is included in the order. This
   *        value corresponds to the value of the &lt;quantity&gt; tag in the
   *        Checkout API request.
   */
  public void addItem(String name, String description, float price, int quantity) {

    addItem(name, description, price, quantity, null, null, null);
  }

  /**
   * This method adds an item to an order. This method handles items that do not
   * have &lt;merchant-private-item-data&gt; XML blocks associated with them.
   * 
   * @param name The name of the item. This value corresponds to the value of
   *        the &lt;item-name&gt; tag in the Checkout API request.
   * 
   * @param description The description of the item. This value corresponds to
   *        the value of the &lt;item-description&gt; tag in the Checkout API
   *        request.
   * 
   * @param price The price of the item. This value corresponds to the value of
   *        the &lt;unit-price&gt; tag in the Checkout API request.</param>
   * 
   * @param quantity The number of this item that is included in the order. This
   *        value corresponds to the value of the &lt;quantity&gt; tag in the
   *        Checkout API request.
   * 
   * @param merchantItemID The Merchant Item Id that uniquely identifies the
   *        product in your system. This value corresponds to the value of the
   *        &lt;merchant-item-id&gt; tag in the Checkout API request.
   */
  public void addItem(String name, String description, float price,
      int quantity, String merchantItemID) {
    addItem(name, description, price, quantity, merchantItemID, null, null);
  }

  /**
   * This method adds an item to an order. This method handles items that have
   * &lt;merchant-private-item-data&gt; XML blocks associated with them.
   * 
   * @param name The name of the item. This value corresponds to the value of
   *        the &lt;item-name&gt; tag in the Checkout API request.
   * 
   * @param description The description of the item. This value corresponds to
   *        the value of the &lt;item-description&gt; tag in the Checkout API
   *        request.
   * 
   * @param price The price of the item. This value corresponds to the value of
   *        the &lt;unit-price&gt; tag in the Checkout API request.</param>
   * 
   * @param quantity The number of this item that is included in the order. This
   *        value corresponds to the value of the &lt;quantity&gt; tag in the
   *        Checkout API request.
   * 
   * @param merchantPrivateItemData An array of XML nodes that should be
   *        associated with the item in the Checkout API request. This value
   *        corresponds to the value of the value of the
   *        &lt;merchant-private-item-data&gt; tag in the Checkout API request.
   */
  public void addItem(String name, String description, float price,
      int quantity, Element[] merchantPrivateItemData) {
    addItem(name, description, price, quantity, null, merchantPrivateItemData,
        null);
  }

  /**
   * This method adds an item to an order. This method handles items that have
   * &lt;merchant-private-item-data&gt; XML blocks associated with them.
   * 
   * @param name The name of the item. This value corresponds to the value of
   *        the &lt;item-name&gt; tag in the Checkout API request.
   * 
   * @param description The description of the item. This value corresponds to
   *        the value of the &lt;item-description&gt; tag in the Checkout API
   *        request.
   * 
   * @param price The price of the item. This value corresponds to the value of
   *        the &lt;unit-price&gt; tag in the Checkout API request.</param>
   * 
   * @param quantity The number of this item that is included in the order. This
   *        value corresponds to the value of the &lt;quantity&gt; tag in the
   *        Checkout API request.
   * 
   * @param merchantItemID The Merchant Item Id that uniquely identifies the
   *        product in your system. This value corresponds to the value of the
   *        &lt;merchant-item-id&gt; tag in the Checkout API request.
   * 
   * @param merchantPrivateItemData An array of XML nodes that should be
   *        associated with the item in the Checkout API request. This value
   *        corresponds to the value of the value of the
   *        &lt;merchant-private-item-data&gt; tag in the Checkout API request.
   * 
   * @param taxTableSelector The name of the alternate tax table that should be
   *        used to calculate tax for the item. This value corresponds to the
   *        value of the value of the &lt;tax-table-selector&gt; tag in the
   *        Checkout API request.
   * 
   */
  public void addItem(String name, String description, float price,
      int quantity, String merchantItemID, Element[] merchantPrivateItemData,
      String taxTableSelector) {

    Document document = getDocument();
    
    Element items =
        Utils.findContainerElseCreate(document, shoppingCart, "items");
    Element item = Utils.createNewContainer(document, items, "item");
    Utils.createNewElementAndSet(document, item, "item-name", name);
    Utils.createNewElementAndSet(document, item, "item-description",
        description);
    Element ePrice =
        Utils.createNewElementAndSet(document, item, "unit-price", price);
    ePrice.setAttribute("currency", mi.getCurrencyCode());
    Utils.createNewElementAndSet(document, item, "quantity", quantity);
    if (merchantItemID != null) {
      Utils.createNewElementAndSet(document, item, "merchant-item-id",
          merchantItemID);
    }
    if (merchantPrivateItemData != null) {
      Element privateItemData =
          Utils
              .createNewContainer(document, item, "merchant-private-item-data");
      Utils.importElements(document, privateItemData, merchantPrivateItemData);
    }
    if (taxTableSelector != null) {
      Utils.createNewElementAndSet(document, item, "tax-table-selector",
          taxTableSelector);
    }
  }

  /**
   * This method adds an item to an order. This method handles items that have
   * &lt;merchant-private-item-data&gt; XML blocks associated with them.
   * 
   * @param item The Item object.
   * 
   * @see Item
   */
  public void addItem(Item item) {
    Document document = getDocument();
    
    Element items =
        Utils.findContainerElseCreate(document, shoppingCart, "items");

    Utils
        .importElements(document, items, new Element[] {item.getRootElement()});
  }

  /**
   * This method adds a merchant-calculated shipping method to an order. This
   * method handles merchant-calculated shipping methods that do not have
   * shipping restrictions.
   * 
   * @param name The name of the shipping method. This value will be displayed
   *        on the Google Checkout order review page.
   * 
   * @param defaultCost The default cost associated with the shipping method.
   *        This value is the amount that Gogle Checkout will charge for
   *        shipping if the merchant calculation callback request fails.
   */
  public void addMerchantCalculatedShippingMethod(String name, float defaultCost) {
    addMerchantCalculatedShippingMethod(name, defaultCost, null);
  }

  /**
   * This method adds a merchant-calculated shipping method to an order. This
   * method handles merchant-calculated shipping methods that have shipping
   * restrictions.
   * 
   * @param name The name of the shipping method. This value will be displayed
   *        on the Google Checkout order review page.
   * 
   * @param defaultCost The default cost associated with the shipping method.
   *        This value is the amount that Gogle Checkout will charge for
   *        shipping if the merchant calculation callback request fails.
   * @param restrictions A list of country, state or zip code areas where the
   *        shipping method is either available or unavailable.
   * 
   * @see ShippingRestrictions
   */
  public void addMerchantCalculatedShippingMethod(String name,
      float defaultCost, ShippingRestrictions restrictions) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Element shippingMethods =
        Utils.findContainerElseCreate(document, mcfs, "shipping-methods");

    Element newShip =
        Utils.createNewContainer(document, shippingMethods,
            "merchant-calculated-shipping");
    newShip.setAttribute("name", name);

    Element price =
        Utils.createNewElementAndSet(document, newShip, "price", defaultCost);
    price.setAttribute("currency", mi.getCurrencyCode());

    if (restrictions != null) {
      Utils.importElements(document, newShip, new Element[] {restrictions
          .getRootElement()});
    }
  }

  /**
   * This method adds a merchant-calculated shipping method to an order. This
   * method handles merchant-calculated shipping methods that have shipping
   * restrictions.
   * 
   * @param name The name of the shipping method. This value will be displayed
   *        on the Google Checkout order review page.
   * 
   * @param defaultCost The default cost associated with the shipping method.
   *        This value is the amount that Gogle Checkout will charge for
   *        shipping if the merchant calculation callback request fails.
   * @param restrictions A list of country, state or zip code areas where the
   *        shipping method is either available or unavailable.
   * 
   * @param filters Similar to the shipping restrictions, filters will be
   *        applied before Google Checkout sends a
   *        <merchant-calculation-callback> to the merchant.
   * 
   * @see ShippingRestrictions
   */
  public void addMerchantCalculatedShippingMethod(String name,
      float defaultCost, ShippingRestrictions restrictions,
      AddressFilters filters) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Element shippingMethods =
        Utils.findContainerElseCreate(document, mcfs, "shipping-methods");

    Element newShip =
        Utils.createNewContainer(document, shippingMethods,
            "merchant-calculated-shipping");
    newShip.setAttribute("name", name);

    Element price =
        Utils.createNewElementAndSet(document, newShip, "price", defaultCost);
    price.setAttribute("currency", mi.getCurrencyCode());

    if (restrictions != null) {
      Utils.importElements(document, newShip, new Element[] {restrictions
          .getRootElement()});
    }

    if (filters != null) {
      Utils.importElements(document, newShip, new Element[] {filters
          .getRootElement()});
    }
  }

  /**
   * This method adds an instore-pickup shipping option to an order.
   * 
   * @param name The name of the shipping method. This value will be displayed
   *        on the Google Checkout order review page.
   * 
   * @param cost The cost associated with the shipping method.
   */
  public void addPickupShippingMethod(String name, float cost) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Element shippingMethods =
        Utils.findContainerElseCreate(document, mcfs, "shipping-methods");

    Element newShip =
        Utils.createNewContainer(document, shippingMethods, "pickup");
    newShip.setAttribute("name", name);

    Element price =
        Utils.createNewElementAndSet(document, newShip, "price", cost);
    price.setAttribute("currency", mi.getCurrencyCode());
  }

  /**
   * Retrieves the value of the &lt;analytics-data&gt; element. Google Analytics
   * uses this to Track Google Checkout Orders.
   * 
   * Please read
   * http://code.google.com/apis/checkout/developer/checkout_analytics_integration.html"
   * for more information.
   * 
   * @return The &lt;analytics-data&gt; element value.
   */
  public String getAnalyticsData() {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findElementOrContainer(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    if (mcfs == null) {
      return "";
    }
    return Utils.getElementStringValue(document, mcfs, "analytics-data");
  }

  /**
   * Retrieves the value of the &lt;good-until-date&gt; element.
   * 
   * @return The cart expiration.
   * 
   * @see Date
   */
  public Date getCartExpiration() throws CheckoutException {
    Document document = getDocument();
    
    Element expiration =
        Utils.findElementOrContainer(document, shoppingCart, "cart-expiration");
    if (expiration == null) {
      return null;
    }
    
    Date goodUntilDate = 
        Utils.getElementDateValue(document, expiration, "good-until-date");
  
    return goodUntilDate;
  }

  /**
   * Retrieves the value of the &lt;continue-shopping-url&gt; element. Google
   * Checkout will display a link to this URL on the page that the customer sees
   * after completing her purchase.
   * 
   * @return The &lt;continue-shopping-url&gt; element value.
   */
  public String getContinueShoppingUrl() {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findElementOrContainer(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    if (mcfs == null) {
      return "";
    }
    return Utils.getElementStringValue(document, mcfs, "continue-shopping-url");
  }

  /**
   * Retrieves the value of the &lt;edit-cart-url&gt; element. Google Checkout
   * will display a link to this URL on the Google Checkout order confirmation
   * page. The customer can click this link to edit the shopping cart contents
   * before completing a purchase.
   * 
   * @return The &lt;edit-cart-url&gt; element value.
   */
  public String getEditCartUrl() {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findElementOrContainer(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    if (mcfs == null) {
      return "";
    }
    return Utils.getElementStringValue(document, mcfs, "edit-cart-url");
  }

  /**
   * Retrieves the value of the &lt;merchant-calculations-url&gt; element. This
   * value is the URL to which Google Checkout will send
   * &lt;merchant-calculation-callback&gt; requests. This property is only
   * relevant for merchants who are implementing the Merchant Calculations API.
   * 
   * @return The &lt;merchant-calculations-url&gt; element value.
   */
  public String getMerchantCalculationsUrl() {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findElementOrContainer(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    if (mcfs == null) {
      return "";
    }
    Element merchantCalcs =
        Utils.findElementOrContainer(document, mcfs, "merchant-calculations");
    if (merchantCalcs == null) {
      return "";
    }
    return Utils.getElementStringValue(document, merchantCalcs,
        "merchant-calculations-url");
  }

  /**
   * Retrieves the value of the &lt;platform-id&gt; element.
   * 
   * The &lt;platform-id&gt; tag should only be used by eCommerce providers who
   * make API requests on behalf of a merchant. The tag's value contains a
   * Google Checkout merchant ID that identifies the eCommerce provider.
   * 
   * @return The &lt;analytics-data&gt; element value.
   */
  public long getPlatformID() {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findElementOrContainer(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    if (mcfs == null) {
      return 0l;
    }
    return Utils.getElementLongValue(document, mcfs, "platform-id");
  }

  /**
   * Retrieves the value that indicates whether the merchant accepts coupons. If
   * this value is set to <b>true</b>, the Google Checkout order confirmation
   * page will display a text field where the customer can enter a coupon code.
   * 
   * This value of this property is a Boolean value that indicates whether the
   * merchant accepts coupons. This value should only be set to <b>true</b> if
   * the merchant has implemented the Merchant Calculations API.
   * 
   * @return The boolean value.
   */
  public boolean isAcceptMerchantCoupons() {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findElementOrContainer(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    if (mcfs == null) {
      return false;
    }
    Element merchantCalcs =
        Utils.findElementOrContainer(document, mcfs, "merchant-calculations");
    if (merchantCalcs == null) {
      return false;
    }
    return Utils.getElementBooleanValue(document, merchantCalcs,
        "accept-merchant-coupons");
  }

  /**
   * Retrieves the value that indicates whether the merchant accepts gift
   * certificates. If this value is set to <b>true</b>, the Google Checkout
   * order confirmation page will display a text field where the customer can
   * enter a gift certificate code.
   * 
   * This value of this property is a Boolean value that indicates whether the
   * merchant accepts gift certificates. This value should only be set to
   * <b>true</b> if the merchant has implemented the Merchant Calculations API.
   * 
   * @return The boolean value.
   */
  public boolean isAcceptMerchantGiftCertificates() {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findElementOrContainer(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    if (mcfs == null) {
      return false;
    }
    Element merchantCalcs =
        Utils.findElementOrContainer(document, mcfs, "merchant-calculations");
    if (merchantCalcs == null) {
      return false;
    }
    return Utils.getElementBooleanValue(document, merchantCalcs,
        "accept-gift-certificates");
  }

  /**
   * Retrieves the value that indicates whether the merchant is responsible for
   * calculating taxes for the default tax table.
   * 
   * @return The value of this property should be <b>true</b> if the merchant
   *         will calculate taxes for the order. Otherwise, this value should be
   *         <b>false</b>. The value should only be <b>true</b> if the
   *         merchant has implemented the Merchant Calculations API. </value>
   */
  public boolean isMerchantCalculatedTax() {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findElementOrContainer(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    if (mcfs == null) {
      return false;
    }
    Element taxTables =
        Utils.findContainerElseCreate(document, mcfs, "tax-tables");
    if (taxTables == null) {
      return false;
    }
    return Boolean.valueOf(taxTables.getAttribute("merchant-calculated"))
        .booleanValue();
  }

  /**
   * Retrieves the value of the &lt;request-buyer-phone-number&gt; element. If
   * this value is true, the buyer must enter a phone number to complete a
   * purchase.
   * 
   * @return <c>true</c> if the Google should send the buyer's phone number to
   *         the merchant, otherwise <c>false</c>.
   */
  public boolean isRequestBuyerPhoneNumber() {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findElementOrContainer(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    if (mcfs == null) {
      return false;
    }
    return Utils.getElementBooleanValue(document, mcfs,
        "request-buyer-phone-number");
  }

  /**
   * Sets the value that indicates whether the merchant accepts coupons. If this
   * value is set to <b>true</b>, the Google Checkout order confirmation page
   * will display a text field where the customer can enter a coupon code.
   * 
   * This value of this property is a Boolean value that indicates whether the
   * merchant accepts coupons. This value should only be set to <b>true</b> if
   * the merchant has implemented the Merchant Calculations API.
   * 
   * @param b The boolean value.
   */
  public void setAcceptMerchantCoupons(boolean b) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Element merchantCalcs =
        Utils.findContainerElseCreate(document, mcfs, "merchant-calculations");
    Utils.findElementAndSetElseCreateAndSet(document, merchantCalcs,
        "accept-merchant-coupons", b);
  }

  /**
   * Sets the value that indicates whether the merchant accepts gift
   * certificates. If this value is set to <b>true</b>, the Google Checkout
   * order confirmation page will display a text field where the customer can
   * enter a gift certificate code.
   * 
   * This value of this property is a Boolean value that indicates whether the
   * merchant accepts gift certificates. This value should only be set to
   * <b>true</b> if the merchant has implemented the Merchant Calculations API.
   * 
   * @param b The boolean value.
   */
  public void setAcceptMerchantGiftCertificates(boolean b) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Element merchantCalcs =
        Utils.findContainerElseCreate(document, mcfs, "merchant-calculations");
    Utils.findElementAndSetElseCreateAndSet(document, merchantCalcs,
        "accept-gift-certificates", b);
  }

  /**
   * Set the value of the &lt;calculation-mode&gt; element. The value of this
   * element determines whether Google will send the addresses one at a time or
   * in batch to your merchant calculations handler.
   * 
   * @param calcMode The CalculationMode.
   * 
   * @see CalculationMode
   */
  public void setCalculationMode(CalculationMode calcMode) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Element merchantCalcs =
        Utils.findContainerElseCreate(document, mcfs, "merchant-calculations");
    Utils.findElementAndSetElseCreateAndSet(document, merchantCalcs,
        "calculation-mode", calcMode.toString());
  }

  /**
   * Sets the value of the &lt;analytics-data&gt; element. Google Analytics uses
   * this to Track Google Checkout Orders.
   * 
   * Please read
   * http://code.google.com/apis/checkout/developer/checkout_analytics_integration.html"
   * for more information.
   * 
   * @param data The &lt;analytics-data&gt; element value.
   */
  public void setAnalyticsData(String data) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Utils.findElementAndSetElseCreateAndSet(document, mcfs, "analytics-data",
        data);
  }

  /**
   * Sets the value of the &lt;good-until-date&gt; element.
   * 
   * @param date The cart expiration.
   * 
   * @see Date
   */
  public void setCartExpiration(Date date) {
    Document document = getDocument();
    
    Element expiration =
        Utils.findContainerElseCreate(document, shoppingCart, "cart-expiration");
    Utils.findElementAndSetElseCreateAndSet(document, expiration,
        "good-until-date", date);
  }

  /**
   * Sets the value of the &lt;continue-shopping-url&gt; element. Google
   * Checkout will display a link to this URL on the page that the customer sees
   * after completing her purchase.
   * 
   * @param url The &lt;continue-shopping-url&gt; element value.
   */
  public void setContinueShoppingUrl(String url) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Utils.findElementAndSetElseCreateAndSet(document, mcfs,
        "continue-shopping-url", url);
  }

  /**
   * Sets the value of the &lt;edit-cart-url&gt; element. Google Checkout will
   * display a link to this URL on the Google Checkout order confirmation page.
   * The customer can click this link to edit the shopping cart contents before
   * completing a purchase.
   * 
   * @param url The &lt;edit-cart-url&gt; element value.
   */
  public void setEditCartUrl(String url) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Utils.findElementAndSetElseCreateAndSet(document, mcfs, "edit-cart-url",
        url);
  }

  /**
   * This method sets the value of the &lt;good-until-date&gt; using the value
   * of the <b>cartExpirationMinutes</b> parameter. This method converts that
   * value into Coordinated Universal Time (UTC).
   * 
   * @param expirationMinutesFromNow The length of time, in minutes, after which
   *        the shopping cart should expire.
   */
  public void setExpirationMinutesFromNow(int expirationMinutesFromNow) {

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MINUTE, expirationMinutesFromNow);
    setCartExpiration(cal.getTime());
  }

  /**
   * Sets the value that indicates whether the merchant is responsible for
   * calculating taxes for the default tax table.
   * 
   * @param b The value of this property should be <b>true</b> if the merchant
   *        will calculate taxes for the order. Otherwise, this value should be
   *        <b>false</b>. The value should only be <b>true</b> if the merchant
   *        has implemented the Merchant Calculations API. </value>
   */
  public void setMerchantCalculatedTax(boolean b) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Element taxTables =
        Utils.findContainerElseCreate(document, mcfs, "tax-tables");
    taxTables.setAttribute("merchant-calculated", "" + b);
  }

  /**
   * Sets the value of the &lt;merchant-calculations-url&gt; element. This value
   * is the URL to which Google Checkout will send
   * &lt;merchant-calculation-callback&gt; requests. This property is only
   * relevant for merchants who are implementing the Merchant Calculations API.
   * 
   * @param url The &lt;merchant-calculations-url&gt; element value.
   */
  public void setMerchantCalculationsUrl(String url) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Element merchantCalcs =
        Utils.findContainerElseCreate(document, mcfs, "merchant-calculations");
    Utils.findElementAndSetElseCreateAndSet(document, merchantCalcs,
        "merchant-calculations-url", url);
  }

  /**
   * Sets the value of the &lt;platform-id&gt; element.
   * 
   * The &lt;platform-id&gt; tag should only be used by eCommerce providers who
   * make API requests on behalf of a merchant. The tag's value contains a
   * Google Checkout merchant ID that identifies the eCommerce provider.
   * 
   * @param platformId The &lt;analytics-data&gt; element value.</value>
   */
  public void setPlatformID(long platformId) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Utils.findElementAndSetElseCreateAndSet(document, mcfs, "platform-id",
        platformId);
  }

  /**
   * Sets the value of the &lt;request-buyer-phone-number&gt; element. If this
   * value is true, the buyer must enter a phone number to complete a purchase.
   * 
   * @param b <c>true</c> if the Google should send the buyer's phone number to
   *        the merchant, otherwise <c>false</c>.
   */
  public void setRequestBuyerPhoneNumber(boolean b) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Utils.findElementAndSetElseCreateAndSet(document, mcfs,
        "request-buyer-phone-number", b);
  }

  /**
   * Retrieves the contents of the &lt;merchant-private-data&gt; element as an
   * array of Elements.
   * 
   * @return The contents &lt;merchant-private-data&gt; element value.
   * 
   * @see Element
   */
  public Element[] getMerchantPrivateDataNodes() {
    Document document = getDocument();
    
    Element mpd =
        Utils.findElementOrContainer(document, shoppingCart,
            "merchant-private-data");
    if (mpd == null) {
      return null;
    }
    return Utils.getElements(document, mpd);
  }

  /**
   * Sets the contents of the &lt;merchant-private-data&gt; element.
   * 
   * @param nodes The &lt;merchant-private-data&gt; element value.
   * 
   * @see Element
   */
  public void setMerchantPrivateDataNodes(Element[] nodes) {
    Document document = getDocument();
    
    Element mpd =
        Utils.findContainerElseCreate(document, shoppingCart,
            "merchant-private-data");
    Utils.importElements(document, mpd, nodes);
  }

  /**
   * This method creates a new &lt;alternate-tax-rule&gt;.
   * 
   * @param tableName The name for the tax table.
   * @param standalone The standalone attribute indicates how taxes should be
   *        calculated if there is no matching alternate-tax-rule for the given
   *        state, zip code or country area. If this attribute's value is TRUE
   *        and there is no matching alternate-tax-rule, the tax amount will be
   *        zero. If the attribute's value is FALSE and there is no matching
   *        alternate-tax-rule, the tax amount will be calculated using the
   *        default tax table.
   * @param taxRate The tax rate to be applied.
   * @param taxArea The TaxArea for which this rule applies.
   * 
   * @see TaxArea
   */
  public void addAlternateTaxRule(String tableName, boolean standalone,
      double taxRate, TaxArea taxArea) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Element taxTables =
        Utils.findContainerElseCreate(document, mcfs, "tax-tables");
    Element alternateTaxTables =
        Utils.findContainerElseCreate(document, taxTables,
            "alternate-tax-tables");

    Element newTaxTable =
        Utils.findContainerWithAttributeValueElseCreate(document,
            alternateTaxTables, "alternate-tax-table", "name", tableName);
    newTaxTable.setAttribute("standalone", "" + standalone);

    Element alternateTaxRules =
        Utils.findContainerElseCreate(document, newTaxTable,
            "alternate-tax-rules");

    Element newRule =
        Utils.createNewContainer(document, alternateTaxRules,
            "alternate-tax-rule");
    Utils.createNewElementAndSet(document, newRule, "rate", taxRate);
    Utils.importElements(document, newRule, new Element[] {taxArea
        .getRootElement()});
  }

  /**
   * This method creates a new &lt;default-tax-rule&gt;.
   * 
   * @param taxRate The tax rate to be applied.
   * @param shippingTaxed Whether the shipping is taxed or not.
   * @param taxArea The TaxArea for which this rule applies.
   * 
   * @see TaxArea
   */
  public void addDefaultTaxRule(double taxRate, boolean shippingTaxed,
      TaxArea taxArea) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Element taxTables =
        Utils.findContainerElseCreate(document, mcfs, "tax-tables");
    Element defaultTaxTable =
        Utils.findContainerElseCreate(document, taxTables, "default-tax-table");
    Element taxRules =
        Utils.findContainerElseCreate(document, defaultTaxTable, "tax-rules");

    Element newRule =
        Utils.createNewContainer(document, taxRules, "default-tax-rule");
    Utils.createNewElementAndSet(document, newRule, "shipping-taxed",
        shippingTaxed);
    Utils.createNewElementAndSet(document, newRule, "rate", taxRate);
    Utils.importElements(document, newRule, new Element[] {taxArea
        .getRootElement()});
  }

  /**
   * Add a URL which has already been URL encoded to the
   * &lt;parameterized-urls&gt; collection. These tags are used to support third
   * party conversion tracking.
   * 
   * @param url The UrlEncoded &lt;parameterized-url&gt; to add to the
   *        collection.
   */
  public void addParameterizedUrl(String url) throws UnsupportedEncodingException {
    addParameterizedUrl(url, false);
  }

  /**
   * Add a URL to the &lt;parameterized-urls&gt; collection. These tags are used
   * to support third party conversion tracking.
   * 
   * @param url The UrlEncoded &lt;parameterized-url&gt; to add to the
   *        collection.
   * 
   * @param urlEncode Set to true if you need the url to be URL encoded.
   */
  public void addParameterizedUrl(String url, boolean urlEncode)
    throws UnsupportedEncodingException {
    addParameterizedUrl(url, urlEncode, null);
  }

  /**
   * Add a collection of URLs which have already been URL encoded to the
   * &lt;parameterized-urls&gt; collection. These tags are used to support third
   * party conversion tracking.
   * 
   * @param url The UrlEncoded &lt;parameterized-url&gt; to add to the
   *        collection.
   * 
   * @param parameters A collection of UrlParameter objects which define the
   *        parameters to be added to the URL.
   * 
   * @see UrlParameter
   */
  public void addParameterizedUrl(String url, Collection parameters) 
    throws UnsupportedEncodingException {
    addParameterizedUrl(url, false, parameters);

  }

  /**
   * Add a collection of URLs to the &lt;parameterized-urls&gt; collection.
   * These tags are used to support third party conversion tracking.
   * 
   * @param url The UrlEncoded &lt;parameterized-url&gt; to add to the
   *        collection.
   * 
   * @param urlEncode Set to true if you need the url to be URL encoded.
   * 
   * @param parameters A collection of UrlParameter objects which define the
   *        parameters to be added to the URL.
   * 
   * @see UrlParameter
   */
  public void addParameterizedUrl(String url, boolean urlEncode, Collection parameters) 
    throws UnsupportedEncodingException {
    if (urlEncode) {
      try {
        url = URLEncoder.encode(url, "UTF-8");
      } catch (UnsupportedEncodingException ex) {
        throw ex;
      }
    }
    Document document = getDocument();

    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Element pUrls =
        Utils.findContainerElseCreate(document, mcfs, "parameterized-urls");
    Element pUrl =
        Utils.createNewContainer(document, pUrls, "parameterized-url");
    pUrl.setAttribute("url", url);

    Element eParams = Utils.createNewContainer(document, pUrl, "parameters");

    Iterator it = parameters.iterator();
    UrlParameter param;
    while (it.hasNext()) {
      param = (UrlParameter) it.next();

      Element eParam =
          Utils.createNewContainer(document, eParams, "url-parameter");
      eParam.setAttribute("name", param.getName());
      eParam.setAttribute("type", param.getParamType().toString());
    }
  }

  /*
   * Must override getPostUrl() functionality of AbstractCheckoutRequest
   * 
   * @see com.google.checkout.CheckoutRequest#getPostUrl()
   */
  public String getPostUrl() {
    return mi.getMerchantCheckoutUrl();
  }

  /**
   * This method adds a flat-rate shipping method to an order. This method
   * handles flat-rate shipping methods that do not have shipping restrictions.
   * 
   * @param name The name of the shipping method. This value will be displayed
   *        on the Google Checkout order review page.
   * 
   * @param cost The cost associated with the shipping method.
   */
  public void addFlatRateShippingMethod(String name, float cost) {
    this.addFlatRateShippingMethod(name, cost, null);
  }

  /**
   * This method adds an Xml node to the &lt;merchant-private-data&gt; element.
   * 
   * @param node The &lt;merchant-private-data&gt; element.
   */
  public void addMerchantPrivateDataNode(Element node) {
    Document document = getDocument();
    
    Element mpd =
        Utils.findContainerElseCreate(document, shoppingCart,
            "merchant-private-data");
    Utils.importElements(document, mpd, new Element[] {node});
  }

  /**
   * Retrieves the value of the &lt;request-initial-auth-details&gt; element.
   * 
   * The &lt;request-initial-auth-details&gt; tag indicates whether Google
   * should send an &lt;authorization-amount-notification&gt; when a credit card
   * is authorized for a new order.
   * 
   * @return The &lt;request-initial-auth-details&gt; element value.
   */
  public boolean isRequestInitialAuthDetails() {
    Document document = getDocument();
    Element root = getRoot();
    
    Element ops =
        Utils
            .findElementOrContainer(document, root, "order-processing-support");
    if (ops == null) {
      return false;
    }
    return Utils.getElementBooleanValue(document, ops,
        "request-initial-auth-details");
  }

  /**
   * Sets the value of the &lt;request-initial-auth-details&gt; element.
   * 
   * The &lt;request-initial-auth-details&gt; tag indicates whether Google
   * should send an &lt;authorization-amount-notification&gt; when a credit card
   * is authorized for a new order.
   * 
   * @param b The boolean value.
   */
  public void setRequestInitialAuthDetails(boolean b) {
    Document document = getDocument();
    Element root = getRoot();
    
    Element ops =
        Utils.findContainerElseCreate(document, root,
            "order-processing-support");
    Utils.findElementAndSetElseCreateAndSet(document, ops,
        "request-initial-auth-details", b);
  }

  /**
   * Sets the value of the &lt;rounding-policy&gt; element and sub elements.
   * 
   * @param rule The RoundingRule.
   * @param mode The RoundingMode.
   * 
   * @see RoundingRule
   * @see RoundingMode
   */
  public void setRoundingPolicy(RoundingRule rule, RoundingMode mode) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Element policy =
        Utils.findContainerElseCreate(document, mcfs, "rounding-policy");
    Utils.findElementAndSetElseCreateAndSet(document, policy, "rule", rule
        .toString());
    Utils.findElementAndSetElseCreateAndSet(document, policy, "mode", mode
        .toString());
  }

  /**
   * Add a &lt;carrier-calculated-shipping&gt; element. See the carrier
   * calculated shipping docs for more information on this feature.
   * 
   * @param price The default price.
   * @param shippingCompany The shipping company, see docs for supported
   *        companies.
   * @param carrierPickup The carrier pickup type.
   * @param shippingType The shipping type, see docs for supported types.
   * @param additionalFixedCharge An additional fixed charge.
   * @param additionalVariableChargePercent A variable charge percent.
   * 
   * @see CarrierPickup
   */
  public void addCarrierCalculatedShippingOption(float price,
      String shippingCompany, CarrierPickup carrierPickup, String shippingType,
      float additionalFixedCharge, float additionalVariableChargePercent) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Element shippingMethods =
        Utils.findContainerElseCreate(document, mcfs, "shipping-methods");

    Element ccShip =
        Utils.findContainerElseCreate(document, shippingMethods,
            "carrier-calculated-shipping");

    Element ccShipOps =
        Utils.findContainerElseCreate(document, ccShip,
            "carrier-calculated-shipping-options");

    Element newShip =
        Utils.createNewContainer(document, ccShipOps,
            "carrier-calculated-shipping-option");

    Element priceElement =
        Utils.createNewElementAndSet(document, newShip, "price", price);
    priceElement.setAttribute("currency", mi.getCurrencyCode());

    Utils.createNewElementAndSet(document, newShip, "shipping-company",
        shippingCompany);

    if (carrierPickup != null) {
      Utils.createNewElementAndSet(document, newShip, "carrier-pickup",
          carrierPickup.toString());
    }

    Utils.createNewElementAndSet(document, newShip, "shipping-type",
        shippingType);

    Element fixedCharge =
        Utils.createNewElementAndSet(document, newShip,
            "additional-fixed-charge", additionalFixedCharge);
    fixedCharge.setAttribute("currency", mi.getCurrencyCode());

    Utils.createNewElementAndSet(document, newShip,
        "additional-variable-charge-percent", additionalVariableChargePercent);
  }

  /**
   * Add a &lt;shipping-package&gt; element. See the carrier calculated shipping
   * docs for more information on this feature.
   * 
   * @param deliveryAddressCategory The delivery address category.
   * @param heightUnit The height unit.
   * @param heightValue The height value.
   * @param widthUnit The width unit.
   * @param widthValue The width value.
   * @param lengthUnit The length unit.
   * @param lengthValue The length value.
   * @param packaging The packaging.
   * @param shipFrom The ship from details.
   * 
   * @see DeliveryAddressCategory
   * @see Packaging
   * @see ShipFrom
   */
  public void addShippingPackage(
      DeliveryAddressCategory deliveryAddressCategory, String heightUnit,
      float heightValue, String widthUnit, float widthValue, String lengthUnit,
      float lengthValue, Packaging packaging, ShipFrom shipFrom) {
    Document document = getDocument();
    
    Element mcfs =
        Utils.findContainerElseCreate(document, checkoutFlowSupport,
            "merchant-checkout-flow-support");
    Element shippingMethods =
        Utils.findContainerElseCreate(document, mcfs, "shipping-methods");

    Element ccShip =
        Utils.findContainerElseCreate(document, shippingMethods,
            "carrier-calculated-shipping");

    Element shipPackages =
        Utils.findContainerElseCreate(document, ccShip, "shipping-packages");

    Element newPack =
        Utils.createNewContainer(document, shipPackages, "shipping-package");

    if (deliveryAddressCategory != null) {
      Utils.createNewElementAndSet(document, newPack,
          "delivery-address-category", deliveryAddressCategory.toString());
    }

    Element height = Utils.createNewContainer(document, newPack, "height");
    height.setAttribute("unit", heightUnit);
    height.setAttribute("value", "" + heightValue);

    Element length = Utils.createNewContainer(document, newPack, "length");
    length.setAttribute("unit", lengthUnit);
    length.setAttribute("value", "" + lengthValue);

    Element width = Utils.createNewContainer(document, newPack, "width");
    width.setAttribute("unit", widthUnit);
    width.setAttribute("value", "" + widthValue);

    if (packaging != null) {
      Utils.createNewElementAndSet(document, newPack, "packaging", packaging
          .toString());
    }

    Utils.importElements(document, newPack, new Element[] {shipFrom
        .getRootElement()});
  }
}
