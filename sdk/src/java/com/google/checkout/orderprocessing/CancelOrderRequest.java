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
 * This class contains methods that construct &lt;cancel-order&gt; API requests.
 */
public class CancelOrderRequest extends AbstractCheckoutRequest {
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
	public CancelOrderRequest(MerchantConstants merchantConstants) {
		super(merchantConstants);

		document = Utils.newEmptyDocument();
		root = (Element) document.createElementNS(Constants.checkoutNamespace,
				"cancel-order");
		root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns",
				Constants.checkoutNamespace);
		document.appendChild(root);
	}

	/**
	 * Constructor which takes an instance of MerchantConstants, Google Order
	 * Number and Reason String.
	 * 
	 * @param merchantConstants
	 *            The MerchantConstants.
	 * @param googleOrderNo
	 *            The Google Order Number.
	 * @param reason
	 *            The Reason String.
	 * 
	 * @see MerchantConstants
	 */
	public CancelOrderRequest(MerchantConstants merchantConstants,
			String googleOrderNo, String reason) {
		this(merchantConstants);
		this.setGoogleOrderNo(googleOrderNo);
		this.setReason(reason);
	}

	/**
	 * Constructor which takes an instance of MerchantConstants, Google Order
	 * Number, Reason String and Comment String.
	 * 
	 * @param merchantConstants
	 *            The MerchantConstants.
	 * @param googleOrderNo
	 *            The Google Order Number.
	 * @param reason
	 *            The Reason String.
	 * @param comment
	 *            The Comment String.
	 * 
	 * @see MerchantConstants
	 */
	public CancelOrderRequest(MerchantConstants merchantConstants,
			String googleOrderNo, String reason, String comment) {
		this(merchantConstants);
		this.setGoogleOrderNo(googleOrderNo);
		this.setReason(reason);
		this.setComment(comment);
	}

	/**
	 * Determine whether the reason and comment are within the string length
	 * limits.
	 * 
	 * @param reason
	 *            The Reason.
	 * 
	 * @param comment
	 *            The Comment.
	 * @return True or false.
	 */
	public boolean isWithinCancelStringLimits(String reason, String comment) {
		int lenStrReason = reason.length();
		int lenStrComment = comment.length();

		if (lenStrReason <= Constants.cancelStrLimit
				&& lenStrComment <= Constants.cancelStrLimit)
			return true;
		else
			return false;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.checkout.CheckoutRequest#getPostUrl()
	 */
	public String getPostUrl() {
		return merchantConstants.getRequestUrl();
	}

	/**
	 * Return the cancel order comment String, which is the value of the
	 * &lt;comment&gt; tag.
	 * 
	 * @return The cancel order comment String.
	 */
	public String getComment() {

		return Utils.getElementStringValue(document, root, "comment");
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
	 * Return the cancel order reason String, which is the value of the
	 * &lt;reason&gt; tag.
	 * 
	 * @return The cancel order reason String.
	 */
	public String getReason() {

		return Utils.getElementStringValue(document, root, "reason");
	}

	/**
	 * Set the cancel order comment String, which is the value of the
	 * &lt;comment&gt; tag.
	 * 
	 * @param comment
	 *            The cancel order comment String.
	 */
	public void setComment(String comment) {

		if (!isWithinCancelStringLimits("", comment)) {
			comment = "";
			System.err.println(Constants.cancelErrorString);
		}

		Utils.findElementAndSetElseCreateAndSet(document, root, "comment",
				comment);
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
	 * Set the cancel order reason String, which is the value of the
	 * &lt;reason&gt; tag.
	 * 
	 * @param reason
	 *            The cancel order reason String.
	 */
	public void setReason(String reason) {

		if (!isWithinCancelStringLimits(reason, "")) {
			reason = "";
			System.err.println(Constants.cancelErrorString);
		}

		Utils.findElementAndSetElseCreateAndSet(document, root, "reason",
				reason);
	}
}
