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
 * This class contains methods that construct &lt;refund-order&gt; API requests.
 */
public class RefundOrderRequest extends AbstractCheckoutRequest {
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
	public RefundOrderRequest(MerchantConstants merchantConstants) {
		super(merchantConstants);
		document = Utils.newEmptyDocument();
		root = (Element) document.createElementNS(Constants.checkoutNamespace,
				"refund-order");
		root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns",
				Constants.checkoutNamespace);
		document.appendChild(root);
	}

	/**
	 * Constructor which takes an instance of MerchantConstants, the Google
	 * Order Number and the Reason text.
	 * 
	 * @param merchantConstants
	 *            The MerchantConstants.
	 * @param googleOrderNo
	 *            The Google Order Number.
	 * @param reason
	 *            The Reason.
	 */
	public RefundOrderRequest(MerchantConstants merchantConstants,
			String googleOrderNo, String reason) {
		this(merchantConstants);
		this.setGoogleOrderNo(googleOrderNo);
		this.setReason(reason);
	}

	/**
	 * Constructor which takes an instance of MerchantConstants, the Google
	 * Order Number, the Reason text, the Refund Amount and the Comment.
	 * 
	 * @param merchantConstants
	 *            The Merchant Constants.
	 * @param googleOrderNo
	 *            The Google Order Number.
	 * @param reason
	 *            The Reason.
	 * @param amount
	 *            The Amount.
	 * @param comment
	 *            The Comment.
	 */
	public RefundOrderRequest(MerchantConstants merchantConstants,
			String googleOrderNo, String reason, float amount, String comment) {
		this(merchantConstants);
		this.setGoogleOrderNo(googleOrderNo);
		this.setReason(reason);
		this.setAmount(amount);
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
	public boolean isWithinRefundStringLimits(String reason, String comment) {
		int lenStrReason = reason.length();
		int lenStrComment = comment.length();

		if (lenStrReason <= Constants.refundStrLimit
				&& lenStrComment <= Constants.refundStrLimit)
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
	 * Return the refund amount, which is value of the &lt;amount&gt; tag.
	 * 
	 * @return The refund amount.
	 */
	public float getAmount() {
		return Utils.getElementFloatValue(document, root, "amount");
	}

	/**
	 * Return the refund comment String, which is the value of the
	 * &lt;comment&gt; tag.
	 * 
	 * @return The refund comment String.
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
		return Utils.getElementStringValue(document, root,
				"google-order-number");
	}

	/**
	 * Return the refund reason String, which is the value of the &lt;reason&gt;
	 * tag.
	 * 
	 * @return The refund reason String.
	 */
	public String getReason() {
		return Utils.getElementStringValue(document, root, "reason");
	}

	/**
	 * Set the refund amount, which is value of the &lt;amount&gt; tag.
	 * 
	 * @param amount
	 *            The refund amount.
	 */
	public void setAmount(float amount) {
		Element e = Utils.findElementAndSetElseCreateAndSet(document, root,
				"amount", amount);
		e.setAttribute("currency", merchantConstants.getCurrencyCode());
	}

	/**
	 * Set the refund comment String, which is the value of the &lt;comment&gt;
	 * tag.
	 * 
	 * @param comment
	 *            The refund comment String.
	 */
	public void setComment(String comment) {
		if (!isWithinRefundStringLimits("", comment)) {
			comment = "";
			System.err.println(Constants.refundErrorString);
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
	 * Set the refund reason String, which is the value of the &lt;reason&gt;
	 * tag.
	 * 
	 * @param reason
	 *            The refund reason String.
	 */
	public void setReason(String reason) {
		if (!isWithinRefundStringLimits(reason, "")) {
			reason = "";
			System.err.println(Constants.refundErrorString);
		}
		Utils.findElementAndSetElseCreateAndSet(document, root, "reason",
				reason);
	}
}
