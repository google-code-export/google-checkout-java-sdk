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
 * This class represents the &lt;us-country-area&gt; in the Checkout API.
 * 
 * @author simonjsmith
 */
public class USArea {

	/**
	 * An instance of the USArea class with the country-area attribute as:
	 * CONTINENTAL_48.
	 */
	public static final USArea CONTINENTAL_48 = new USArea("CONTINENTAL_48");

	/**
	 * An instance of the USArea class with the country-area attribute as:
	 * FULL_50_STATES.
	 */
	public static final USArea FULL_50_STATES = new USArea("FULL_50_STATES");

	/**
	 * An instance of the USArea class with the country-area attribute as: ALL.
	 */
	public static final USArea ALL = new USArea("ALL");

	private String value;

	private USArea(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
