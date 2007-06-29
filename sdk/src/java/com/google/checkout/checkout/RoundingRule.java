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

package com.google.checkout.checkout;

/**
 * This class represents the &lt;rounding-policy&gt; &lt;rule&gt; in the
 * Checkout API.
 * 
 * @author simonjsmith
 */
public class RoundingRule {

	/**
	 * An instance of the RoundingMode class with rule: PER_LINE.
	 */
	public static final RoundingRule PER_LINE = new RoundingRule("PER_LINE");

	/**
	 * An instance of the RoundingMode class with rule: TOTAL.
	 */
	public static final RoundingRule TOTAL = new RoundingRule("TOTAL");

	private String value;

	private RoundingRule(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
