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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.checkout.util.Utils;

/**
 * This class encapsulates the &lt;anonymous-address&gt; element, used as part
 * of the Merchant Calculations API.
 * 
 * @author simonjsmith
 * 
 */
public class AnonymousAddress {

	private Document document;

	private Element element;

	/**
	 * A constructor which takes the document and element pointing to the
	 * &lt;anonymous-address&gt; tag.
	 * 
	 * @param document
	 *            The document.
	 * @param element
	 *            The element.
	 */
	public AnonymousAddress(Document document, Element element) {
		this.document = document;
		this.element = element;
	}

	/**
	 * Retrive the contents of the &lt;city&gt; tag.
	 * 
	 * @return The city.
	 */
	public String getCity() {
		return Utils.getElementStringValue(document, element, "city");
	}

	/**
	 * Retrive the contents of the &lt;country-code&gt; tag.
	 * 
	 * @return The Country Code.
	 */
	public String getCountryCode() {
		return Utils.getElementStringValue(document, element, "country-code");
	}

	/**
	 * Retrive the contents of the &lt;id&gt; tag.
	 * 
	 * @return The id.
	 */
	public String getId() {
		return element.getAttribute("id");
	}

	/**
	 * Retrive the contents of the &lt;postal-code&gt; tag.
	 * 
	 * @return The Postal Code.
	 */
	public String getPostalCode() {
		return Utils.getElementStringValue(document, element, "postal-code");
	}

	/**
	 * Retrive the contents of the &lt;region&gt; tag.
	 * 
	 * @return The Region.
	 */
	public String getRegion() {
		return Utils.getElementStringValue(document, element, "region");
	}
}
