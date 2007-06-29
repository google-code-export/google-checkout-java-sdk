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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.w3c.dom.Element;

import com.google.checkout.checkout.Item;
import com.google.checkout.util.Utils;

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
	 */
	public NewOrderNotification(String requestString) {
		document = Utils.newDocumentFromString(requestString);
		root = document.getDocumentElement();
	}

	/**
	 * A constructor which takes the request as an InputStream.
	 * 
	 * @param inputStream
	 */
	public NewOrderNotification(InputStream inputStream) {
		document = Utils.newDocumentFromInputStream(inputStream);
		root = document.getDocumentElement();
	}

	/**
	 * Retrieves the contents of the &lt;items&gt; element as a Colection of
	 * Item objects.
	 * 
	 * @return The Collection of Item objects.
	 * 
	 * @see Item
	 */
	public Collection getItems() {
		Element shoppingCart = Utils.findElementOrContainer(document, root,
				"shopping-cart");
		Element items = Utils.findElementOrContainer(document, shoppingCart,
				"items");
		Element[] elements = Utils.getElements(document, items);
		Collection ret = new ArrayList();

		for (int i = 0; i < elements.length; i++) {
			ret.add(new Item(document, elements[i]));
		}
		return ret;
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
		Element shoppingCart = Utils.findContainerElseCreate(document, root,
				"shopping-cart");
		Element mpd = Utils.findElementOrContainer(document, shoppingCart,
				"merchant-private-data");
		if (mpd == null) {
			return null;
		}
		return Utils.getElements(document, mpd);
	}

	/**
	 * Retrieves the value of the &lt;good-until-date&gt; element.
	 * 
	 * @return The cart expiration.
	 * 
	 * @see Date
	 */
	public Date getCartExpiration() {
		Element shoppingCart = Utils.findContainerElseCreate(document, root,
				"shopping-cart");
		Element cartExpiration = Utils.findContainerElseCreate(document,
				shoppingCart, "cart-expiration");

		return Utils.getElementDateValue(document, cartExpiration,
				"good-until-date");
	}

	/**
	 * Retrieves the Google Order Number for this notification.
	 * 
	 * @return The Google Order Number.
	 */
	public String getGoogleOrderNo() {
		return Utils.getElementStringValue(document, root,
				"google-order-number");
	}

	/**
	 * Retrieves the value of the &lt;buyer-shipping-address&gt; element.
	 * 
	 * @return The buyer shipping address
	 * 
	 * @see Address
	 */
	public Address getBuyerShippingAddress() {
		Element address = Utils.findElementOrContainer(document, root,
				"buyer-shipping-address");
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
		Element address = Utils.findElementOrContainer(document, root,
				"buyer-billing-address");
		return new Address(document, address);
	}

	/**
	 * Retrieves the value of the &lt;email-allowed&gt; element.
	 * 
	 * @return The marketing preferences flag.
	 */
	public boolean isMarketingEmailAllowed() {
		Element buyerMarketingPreferences = Utils.findElementOrContainer(
				document, root, "buyer-marketing-preferences");
		return Utils.getElementBooleanValue(document,
				buyerMarketingPreferences, "email-allowed");
	}

	/**
	 * Retrieves the value of the &lt;merchant-calculation-successful&gt;
	 * element.
	 * 
	 * @return The merchant calculation successful flag.
	 */
	public boolean isMerchantCalculationSuccessful() {
		Element orderAdjustment = Utils.findElementOrContainer(document, root,
				"order-adjustment");
		return Utils.getElementBooleanValue(document, orderAdjustment,
				"merchant-calculation-successful");
	}

	/**
	 * Retrieves the contents of the &lt;merchant-codes&gt; element as a
	 * collection of MerchantCodes objects.
	 * 
	 * @return The merchant codes.
	 * 
	 * @see MerchantCodes
	 * @see GiftCertificateAdjustment
	 * @see CouponAdjustment
	 */
	public Collection getMerchantCodes() {
		Element oa = Utils.findElementOrContainer(document, root,
				"order-adjustment");
		Element mc = Utils.findElementOrContainer(document, oa,
				"merchant-codes");

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
		Element orderAdjustment = Utils.findElementOrContainer(document, root,
				"order-adjustment");
		return Utils.getElementFloatValue(document, orderAdjustment,
				"total-tax");
	}

	/**
	 * Retrieves the value of the &lt;adjustment-total&gt; element.
	 * 
	 * @return The adjustment total amount.
	 */
	public float getAdjustmentTotal() {
		Element orderAdjustment = Utils.findElementOrContainer(document, root,
				"order-adjustment");
		return Utils.getElementFloatValue(document, orderAdjustment,
				"adjustment-total");
	}

	/**
	 * Retrieves the value of the &lt;shipping&gt; element as a Shipping object.
	 * 
	 * @return The shipping.
	 * 
	 * @see Shipping
	 */
	public Shipping getShipping() {
		Element oa = Utils.findElementOrContainer(document, root,
				"order-adjustment");
		Element shipping = Utils.findElementOrContainer(document, oa,
				"shipping");

		Element e = Utils.findElementOrContainer(document, shipping,
				"merchant-calculated-shipping-adjustment");
		if (e != null) {
			return new MerchantCalculatedShippingAdjustment(document, e);
		}

		e = Utils.findElementOrContainer(document, shipping,
				"flat-rate-shipping-adjustment");
		if (e != null) {
			return new FlatRateShippingAdjustment(document, e);
		}

		e = Utils.findElementOrContainer(document, shipping,
				"pickup-shipping-adjustment");
		if (e != null) {
			return new PickupShippingAdjustment(document, e);
		}

		return null;
	}

	/**
	 * Retrieves the value of the &lt;order-total&gt; element.
	 * 
	 * @return The order total.
	 */
	public float getOrderTotal() {
		return Utils.getElementFloatValue(document, root, "order-total");
	}

	/**
	 * Retrieves the currency code.
	 * 
	 * @return The currency code.
	 */
	public String getOrderCurrencyCode() {
		return Utils.findElementOrContainer(document, root, "order-total")
				.getAttribute("currency");
	}

	/**
	 * Retrieves the value of the &lt;fulfillment-order-state&gt; element.
	 * 
	 * @return The fulfillment order state.
	 * 
	 * @see FulfillmentOrderState
	 */
	public FulfillmentOrderState getFulfillmentOrderState() {
		String state = Utils.getElementStringValue(document, root,
				"fulfillment-order-state");
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
		String state = Utils.getElementStringValue(document, root,
				"financial-order-state");
		return FinancialOrderState.getState(state);
	}

	/**
	 * Retrieves the value of the &lt;buyer-id&gt; element.
	 * 
	 * @return The buyer id.
	 */
	public long getBuyerId() {
		return Utils.getElementLongValue(document, root, "buyer-id");
	}

}
