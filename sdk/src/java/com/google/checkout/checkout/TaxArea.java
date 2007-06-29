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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.checkout.util.Utils;

/**
 * This class represents the &lt;tax-area&gt; tag in the Checkout API.
 * 
 * @author simonjsmith
 */
public class TaxArea {

	Document document;

	Element root;

	/**
	 * Default constructor.
	 */
	public TaxArea() {

		document = Utils.newEmptyDocument();
		root = (Element) document.createElement("tax-area");
		document.appendChild(root);
	}

	/**
	 * Add a &lt;us-country-area&gt; tag to the &lt;tax-area&gt; tag.
	 * 
	 * @param countryArea
	 *            The US country area.
	 * 
	 * @see USArea
	 */
	public void addCountryArea(USArea countryArea) {

		Element usCountry = Utils.createNewContainer(document, root,
				"us-country-area");
		usCountry.setAttribute("country-area", countryArea.toString());
	}

	/**
	 * Add a &lt;state&gt; and encolsing &lt;us-state-area&gt; to the
	 * &lt;tax-area&gt; tag.
	 * 
	 * @param stateCode
	 *            The state code.
	 */
	public void addStateCode(String stateCode) {

		Element usState = Utils.createNewContainer(document, root,
				"us-state-area");
		Utils.createNewElementAndSet(document, usState, "state", stateCode);
	}

	/**
	 * Add a &lt;zip-pattern&gt; and encolsing &lt;us-zip-area&gt; to the
	 * &lt;tax-area&gt; tag.
	 * 
	 * @param zipPattern
	 *            The Zip Pattern.
	 */
	public void addZipPattern(String zipPattern) {

		Element usZip = Utils.createNewContainer(document, root, "us-zip-area");
		Utils
				.createNewElementAndSet(document, usZip, "zip-pattern",
						zipPattern);
	}

	/**
	 * Get the root element, &lt;tax-area&gt;
	 * 
	 * @return The root element, &lt;tax-atea&gt;.
	 */
	public Element getRootElement() {
		return root;
	}

	/**
	 * This method adds a &lt;postal-area&gt; element. The &lt;country-code&gt;
	 * element and optionally the &lt;postal-code-pattern&gt are subelements .
	 * 
	 * @param countryCode
	 *            The country code.
	 * @param postalCodePattern
	 *            The Postal Code Pattern.
	 * 
	 */
	public void addPostalArea(String countryCode, String postalCodePattern) {
		Element pa = Utils.createNewContainer(document, root, "postal-area");
		Utils.createNewElementAndSet(document, pa, "country-code", countryCode);

		if (postalCodePattern != null)
		{
		  Utils.createNewElementAndSet(document, pa, "postal-code-pattern",
				postalCodePattern);
		}
	}

	/**
	 * This method adds a &lt;postal-area&gt; element.
	 * 
	 * @param countryCode
	 *            The country code.
	 */
	public void addPostalArea(String countryCode) {
		addPostalArea(countryCode, null);
	}
	
	/**
	 * This method adds a &lt;world-area&gt; element.
	 * 
	 */
	public void addWorldArea() {
		Utils.createNewContainer(document, root, "world-area");
	}

}
