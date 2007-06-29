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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.checkout.util.Utils;

/**
 * This class encapsulates the data describing an Address.
 * 
 * @author simonjsmith
 */
public class Address {

	private Document document;

	private Element element;

	public Address(Document document, Element element) {
		this.document = document;
		this.element = element;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return Utils.getElementStringValue(document, element, "address1");
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return Utils.getElementStringValue(document, element, "address2");
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return Utils.getElementStringValue(document, element, "city");
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return Utils.getElementStringValue(document, element, "company-name");
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return Utils.getElementStringValue(document, element, "contact-name");
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return Utils.getElementStringValue(document, element, "country-code");
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return Utils.getElementStringValue(document, element, "email");
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return Utils.getElementStringValue(document, element, "fax");
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return Utils.getElementStringValue(document, element, "phone");
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return Utils.getElementStringValue(document, element, "postal-code");
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return Utils.getElementStringValue(document, element, "region");
	}
}
