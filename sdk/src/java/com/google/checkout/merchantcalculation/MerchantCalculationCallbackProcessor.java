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

import com.google.checkout.CheckoutException;

/**
 * This class contains methods that parse a
 * &lt;merchant-calculation-callback&gt; request, allowing you to access items
 * in the request, shipping methods, shipping addresses, Fiax information, and
 * coupon and gift certificate codes entered by the customer.
 * 
 * @author simonjsmith@google.com
 * 
 */
public interface MerchantCalculationCallbackProcessor {

	/**
	 * Process the request and send the response back as a String.
	 * 
	 * @return The merchant calculation response String.
	 */
	public MerchantCalculationResults process(
			MerchantCalculationCallback callback) throws CheckoutException;

}
