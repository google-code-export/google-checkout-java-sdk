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
import java.util.Date;

import com.google.checkout.util.Utils;

/**
 * This class encapsulates the &lt;authorization-amount-notification&gt;
 * notification.
 * 
 * @author simonjsmith
 * 
 */
public class AuthorizationAmountNotification extends CheckoutNotification {

	/**
	 * A constructor which takes the request as a String.
	 * 
	 * @param requestString
	 */
	public AuthorizationAmountNotification(String requestString) {
		document = Utils.newDocumentFromString(requestString);
		root = document.getDocumentElement();
	}

	/**
	 * A constructor which takes the request as an InputStream.
	 * 
	 * @param inputStream
	 */
	public AuthorizationAmountNotification(InputStream inputStream) {
		document = Utils.newDocumentFromInputStream(inputStream);
		root = document.getDocumentElement();
	}

	/**
	 * Retrieves the value of the &lt;avs-response&gt; tag.
	 * 
	 * @return The AVS response code.
	 */
	public String getAvsResponse() {
		return Utils.getElementStringValue(document, root, "avs-response");
	}

	/**
	 * Retrieves the value of the &lt;cvn-response&gt; tag.
	 * 
	 * @return The CVN response code.
	 */
	public String getCvnResponse() {
		return Utils.getElementStringValue(document, root, "cvn-response");
	}

	/**
	 * Retrieves the value of the &lt;authorization-amount&gt; tag.
	 * 
	 * @return The authorization amount.
	 */
	public float getAuthorizationAmount() {
		return Utils.getElementFloatValue(document, root,
				"authorization-amount");
	}

	/**
	 * Retrieves the currency code.
	 * 
	 * @return The currency code.
	 */
	public String getCurrentyCode() {
		return Utils.findElementOrContainer(document, root,
				"authorization-amount").getAttribute("currency");
	}

	/**
	 * Retrieves the value of the &lt;authorization-expiration-date&gt; tag.
	 * 
	 * @return The authorization expiration date.
	 */
	public Date getAuthorizationExpirationDate() {

		return Utils.getElementDateValue(document, root,
				"authorization-expiration-date");
	}

}
