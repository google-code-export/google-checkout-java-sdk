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

import org.w3c.dom.Element;

import com.google.checkout.util.Utils;

/**
 * This class encapsulates the &lt;risk-information-notification&gt;
 * notification.
 * 
 * @author simonjsmith
 * 
 */
public class RiskInformationNotification extends CheckoutNotification {

	Element riskInfo;

	/**
	 * A constructor which takes the request as a String.
	 * 
	 * @param requestString
	 */
	public RiskInformationNotification(String requestString) {
		document = Utils.newDocumentFromString(requestString);
		root = document.getDocumentElement();
		riskInfo = Utils.findElementOrContainer(document, root,
				"risk-information");
	}

	/**
	 * A constructor which takes the request as an InputStream.
	 * 
	 * @param inputStream
	 */
	public RiskInformationNotification(InputStream inputStream) {
		document = Utils.newDocumentFromInputStream(inputStream);
		root = document.getDocumentElement();
		riskInfo = Utils.findElementOrContainer(document, root,
				"risk-information");
	}

	/**
	 * Retrieves the value of the &lt;eligible-for-protection&gt; tag.
	 * 
	 * @return The eligible for protection flag.
	 */
	public boolean isEligibleForProtection() {
		return Utils.getElementBooleanValue(document, riskInfo,
				"eligible-for-protection");
	}

	/**
	 * Retrieves the contents of the &lt;billing-address&gt; tag as an Address
	 * object.
	 * 
	 * @return The billing address.
	 * 
	 * @see Address
	 */
	public Address getBillingAddress() {
		Element address = Utils.findElementOrContainer(document, riskInfo,
				"billing-address");
		return new Address(document, address);
	}

	/**
	 * Retrieves the value of the &lt;avs-response&gt; tag.
	 * 
	 * @return The AVS response.
	 */
	public String getAvsResponse() {
		return Utils.getElementStringValue(document, riskInfo, "avs-response");
	}

	/**
	 * Retrieves the value of the &lt;cvn-response&gt; tag.
	 * 
	 * @return The CVN response.
	 */
	public String getCvnResponse() {
		return Utils.getElementStringValue(document, riskInfo, "cvn-response");
	}

	/**
	 * Retrieves the value of the &lt;partial-cc-number&gt; tag.
	 * 
	 * @return The partial credit card number.
	 */
	public String getPartialCcNumber() {
		return Utils.getElementStringValue(document, riskInfo,
				"partial-cc-number");
	}

	/**
	 * Retrieves the value of the &lt;buyer-account-age&gt; tag.
	 * 
	 * @return The buyer account age.
	 */
	public int getBuyerAccountAge() {
		return Utils
				.getElementIntValue(document, riskInfo, "buyer-account-age");
	}

	/**
	 * Retrieves the value of the &lt;ip-address&gt; tag.
	 * 
	 * @return The IP address.
	 */
	public String getIpAddress() {
		return Utils.getElementStringValue(document, riskInfo, "ip-address");
	}
}
