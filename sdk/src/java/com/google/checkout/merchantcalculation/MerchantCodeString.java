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

/**
 * This class encapsulates the &lt;merchant-code-string&gt; element, used as
 * part of the Merchant Calculations API.
 * 
 * @author simonjsmith
 * 
 */
public class MerchantCodeString {

	private String code;

	private String pin;

	/**
	 * A constructor which takes the code and pin.
	 * 
	 * @param code
	 *            The code.
	 * @param pin
	 *            The pin.
	 */
	public MerchantCodeString(String code, String pin) {
		this.code = code;
		this.pin = pin;
	}

	/**
	 * Retrive the contents of the code attribute.
	 * 
	 * @return The code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set the contents of the code attribute.
	 * 
	 * @param code
	 *            The code.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Retrive the contents of the pin attribute.
	 * 
	 * @return The pin.
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * Set the contents of the pin attribute.
	 * 
	 * @param pin
	 *            The pin.
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}

}
