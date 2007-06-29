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

package com.google.checkout.orderprocessing;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.checkout.AbstractCheckoutRequest;
import com.google.checkout.MerchantConstants;
import com.google.checkout.util.Constants;
import com.google.checkout.util.Utils;

/**
 * This class contains methods that construct &lt;charge-order&gt; API requests.
 */
public class ChargeOrderRequest extends AbstractCheckoutRequest {

	private Document document;

	private Element root;

	/**
	 * Constructor which takes an instance of MerchantConstants.
	 * 
	 * @param merchantConstants
	 *            The MerchantConstants.
	 * 
	 * @see MerchantConstants
	 */
	public ChargeOrderRequest(MerchantConstants merchantConstants) {
		super(merchantConstants);

		document = Utils.newEmptyDocument();
		root = (Element) document.createElementNS(Constants.checkoutNamespace,
				"charge-order");
		root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns",
				Constants.checkoutNamespace);
		document.appendChild(root);
	}

	/**
	 * Constructor which takes an instance of MerchantConstants and the Google
	 * Order Number.
	 * 
	 * @param merchantConstants
	 *            The MerchantConstants.
	 * @param googleOrderNo
	 *            The Google Order Number.
	 * 
	 * @see MerchantConstants
	 */
	public ChargeOrderRequest(MerchantConstants merchantConstants,
			String googleOrderNo) {

		this(merchantConstants);
		this.setGoogleOrderNo(googleOrderNo);
	}

	/**
	 * Constructor which takes an instance of MerchantConstants, the Google
	 * Order Number and the ammount to be charged.
	 * 
	 * @param merchantConstants
	 *            The MerchantConstants.
	 * @param googleOrderNo
	 *            The Google Order Number.
	 * @param amt
	 *            The amount to be charged.
	 * 
	 * @see MerchantConstants
	 */
	public ChargeOrderRequest(MerchantConstants merchantConstants,
			String googleOrderNo, float amt) {

		this(merchantConstants);
		this.setGoogleOrderNo(googleOrderNo);
		this.setAmount(amt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.checkout.CheckoutRequest#getXml()
	 */
	public String getXml() {
		return Utils.documentToString(document);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.checkout.CheckoutRequest#getXmlPretty()
	 */
	public String getXmlPretty() {
		return Utils.documentToStringPretty(document);
	}

	/**
	 * Return the charge amount, which is the value of the &lt;amount&gt; tag.
	 * 
	 * @return The charge amount.
	 */
	public float getAmount() {
		return Utils.getElementFloatValue(document, root, "amount");
	}

	/**
	 * Return the Google Order Number, which is the value of the
	 * google-order-number attribute on the root tag.
	 * 
	 * @return The Google Order Number.
	 */
	public String getGoogleOrderNo() {
		return root.getAttribute("google-order-number");
	}

	/**
	 * Set the charge amount, which is the value of the &lt;amount&gt; tag.
	 * 
	 * @param amount
	 *            The charge amount.
	 */
	public void setAmount(float amount) {
		Element e = Utils.findElementAndSetElseCreateAndSet(document, root,
				"amount", amount);
		e.setAttribute("currency", merchantConstants.getCurrencyCode());
	}

	/**
	 * Set the Google Order Number, which is the value of the
	 * google-order-number attribute on the root tag.
	 * 
	 * @param googleOrderNo
	 *            The Google Order Number.
	 */
	public void setGoogleOrderNo(String googleOrderNo) {
		root.setAttribute("google-order-number", googleOrderNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.checkout.CheckoutRequest#getPostUrl()
	 */
	public String getPostUrl() {
		return merchantConstants.getRequestUrl();
	}
}
