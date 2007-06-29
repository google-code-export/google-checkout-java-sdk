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
 * This class encapsulates the &lt;coupon-result&gt; tag used as part of the
 * Merchant Calculation API.
 * 
 * @author simonjsmith
 * 
 * @see MerchantCodeResult
 */
public class CouponResult extends MerchantCodeResult {

	/**
	 * A constructor which takes all the relevant parameters.
	 * 
	 * @param valid
	 *            Whether the code is valid.
	 * @param calculatedAmount
	 *            The discount amount.
	 * @param currency
	 *            The currency.
	 * @param code
	 *            The coupon code.
	 * @param message
	 *            The message to display to the user.
	 */
	public CouponResult(boolean valid, float calculatedAmount, String currency,
			String code, String message) {
		this.setType("coupon-result");
		this.setValid(valid);
		this.setCalculatedAmount(calculatedAmount);
		this.setCurrency(currency);
		this.setCode(code);
		this.setMessage(message);
	}

}
