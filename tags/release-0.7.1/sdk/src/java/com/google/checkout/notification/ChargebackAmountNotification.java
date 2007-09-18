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

import com.google.checkout.util.Utils;

/**
 * This class encapsulates the &lt;chargeback-amount-notification&gt;
 * notification.
 * 
 * @author simonjsmith
 * 
 */
public class ChargebackAmountNotification extends CheckoutNotification {

	/**
	 * A constructor which takes the request as a String.
	 * 
	 * @param requestString
	 */
	public ChargebackAmountNotification(String requestString) {
		document = Utils.newDocumentFromString(requestString);
		root = document.getDocumentElement();
	}

	/**
	 * A constructor which takes the request as an InputStream.
	 * 
	 * @param inputStream
	 */
	public ChargebackAmountNotification(InputStream inputStream) {
		document = Utils.newDocumentFromInputStream(inputStream);
		root = document.getDocumentElement();
	}

	/**
	 * Retrieves the value of the &lt;latest-chargeback-amount&gt; tag.
	 * 
	 * @return The latest chargeback amount.
	 */
	public float getLatestChargebackAmount() {
		return Utils.getElementFloatValue(document, root,
				"latest-chargeback-amount");
	}

	/**
	 * Retrieves the value of the &lt;total-charge-amount&gt; tag.
	 * 
	 * @return The total chargeback amount.
	 */
	public float getTotalChargebackAmount() {
		return Utils.getElementFloatValue(document, root,
				"total-chargeback-amount");
	}

	/**
	 * Retrieves the currency code.
	 * 
	 * @return The currency code.
	 */
	public String getCurrencyCode() {
		return Utils.findElementOrContainer(document, root,
				"latest-chargeback-amount").getAttribute("currency");
	}
}
