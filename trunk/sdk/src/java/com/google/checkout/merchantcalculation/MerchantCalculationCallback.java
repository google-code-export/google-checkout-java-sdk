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

package com.google.checkout.merchantcalculation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.checkout.checkout.Item;
import com.google.checkout.util.Utils;

/**
 * This class encapsulates the &lt;merchant-calculation-callback&gt; callback.
 * 
 * @author simonjsmith@google.com
 */
public class MerchantCalculationCallback {

	private Document document;

	private Element root;

	/**
	 * A constructor which takes the request as a String.
	 * 
	 * @param requestString
	 */
	public MerchantCalculationCallback(String requestString) {
		document = Utils.newDocumentFromString(requestString);
		root = document.getDocumentElement();
	}

	/**
	 * A constructor which takes the request as an InputStream.
	 * 
	 * @param inputStream
	 */
	public MerchantCalculationCallback(InputStream inputStream) {
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
		Element shoppingCart = Utils.findContainerElseCreate(document, root,
				"shopping-cart");
		Element items = Utils.findContainerElseCreate(document, shoppingCart,
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
	 * Retrieves the value of the &lt;buyer-id&gt; element.
	 * 
	 * @return The buyer id.
	 */
	public long getBuyerId() {
		return Utils.getElementLongValue(document, root, "buyer-id");
	}

	/**
	 * Retrieves the value of the &lt;buyer-language&gt; element.
	 * 
	 * @return The buyer language.
	 */
	public String getBuyerLanguage() {
		return Utils.getElementStringValue(document, root, "buyer-language");
	}

	/**
	 * Retrieves the value of the &lt;serial-number&gt; attribute.
	 * 
	 * @return The serial number.
	 */
	public String getSerialNumber() {
		return root.getAttribute("serial-number");
	}

	/**
	 * Indicates whether tax should be calculated or not.
	 * 
	 * @return The boolean tax indicator.
	 */
	public boolean isCalculateTax() {
		Element calculate = Utils.findContainerElseCreate(document, root,
				"calculate");

		return Utils.getElementBooleanValue(document, calculate, "tax");
	}

	/**
	 * Returns a Collection of Strings representing the &lt;method&gt; tags.
	 * 
	 * @return A Collection of Strings.
	 */
	public Collection getShippingMethods() {
		Element calculate = Utils.findContainerElseCreate(document, root,
				"calculate");
		Element shipping = Utils.findContainerElseCreate(document, calculate,
				"shipping");
		Element[] elements = Utils.getElements(document, shipping);
		Collection ret = new ArrayList();

		for (int i = 0; i < elements.length; i++) {
			ret.add(elements[i].getAttribute("name"));
		}

		return ret;
	}

	/**
	 * Returns a Collection of Strings representing the
	 * &lt;merchant-code-strings&gt;.
	 * 
	 * @return A Collection of Strings.
	 */
	public Collection getMerchantCodes() {
		Element calculate = Utils.findContainerElseCreate(document, root,
				"calculate");
		Element merchantCodes = Utils.findContainerElseCreate(document,
				calculate, "merchant-code-strings");
		Element[] elements = Utils.getElements(document, merchantCodes);
		Collection ret = new ArrayList();

		for (int i = 0; i < elements.length; i++) {
			ret.add(new MerchantCodeString(elements[i].getAttribute("code"),
					elements[i].getAttribute("pin")));
		}

		return ret;
	}

	/**
	 * Returns a Collection of Strings representing the
	 * &lt;anonymous-address&gt; tags in the request.
	 * 
	 * @return A Collection of AnonymousAddress objects.
	 * 
	 * @see AnonymousAddress
	 */
	public Collection getAnonymousAddresses() {
		Element calculate = Utils.findContainerElseCreate(document, root,
				"calculate");
		Element addresses = Utils.findContainerElseCreate(document, calculate,
				"addresses");
		Element[] elements = Utils.getElements(document, addresses);
		Collection ret = new ArrayList();

		for (int i = 0; i < elements.length; i++) {
			ret.add(new AnonymousAddress(document, elements[i]));
		}

		return ret;
	}
}
