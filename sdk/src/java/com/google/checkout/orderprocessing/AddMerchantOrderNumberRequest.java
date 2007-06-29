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
 * This class contains methods that construct &lt;add-merchant-order-number&gt;
 * API requests.
 */
public class AddMerchantOrderNumberRequest extends AbstractCheckoutRequest {

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
	public AddMerchantOrderNumberRequest(MerchantConstants merchantConstants) {
		super(merchantConstants);

		document = Utils.newEmptyDocument();
		root = (Element) document.createElementNS(Constants.checkoutNamespace,
				"add-merchant-order-number");
		root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns",
				Constants.checkoutNamespace);
		document.appendChild(root);
	}

	/**
	 * Constructor which takes an instance of MerchantConstants, a Google Order
	 * Number and a Merchant Order Number.
	 * 
	 * @param merchantConstants
	 *            The MerchantConstants.
	 * @param googleOrderNo
	 *            The Google Order Number.
	 * @param merchantOrderNo
	 *            The Merchant Order Number.
	 */
	public AddMerchantOrderNumberRequest(MerchantConstants merchantConstants,
			String googleOrderNo, String merchantOrderNo) {
		this(merchantConstants);
		this.setGoogleOrderNo(googleOrderNo);
		this.setMerchantOrderNo(merchantOrderNo);
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
	 * Return the Google Order Number, which is the value of the
	 * google-order-number attribute on the root tag.
	 * 
	 * @return The Google Order Number.
	 */
	public String getGoogleOrderNo() {
		return root.getAttribute("google-order-number");
	}

	/**
	 * Return the Merchant Order Number, which is the value of the
	 * &lt;merchant-order-number&gt; tag.
	 * 
	 * @return The Merchant Order Number.
	 */
	public String getMerchantOrderNo() {
		return Utils.getElementStringValue(document, root,
				"merchant-order-number");
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

	/**
	 * Set the Merchant Order Number, which is the value of the
	 * &lt;merchant-order-number&gt; tag.
	 * 
	 * @param merchantOrderNo
	 *            The Merchant Order Number.
	 */
	public void setMerchantOrderNo(String merchantOrderNo) {
		Utils.findElementAndSetElseCreateAndSet(document, root,
				"merchant-order-number", merchantOrderNo);
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
