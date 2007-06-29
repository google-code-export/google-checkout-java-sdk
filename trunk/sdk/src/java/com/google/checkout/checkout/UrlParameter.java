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
 * This class represents a parameter on a URL as used by the
 * CheckoutShoppingCartRequest clas s for 3rd party conversion tracking
 * purposes.
 * 
 * @see CheckoutShoppingCartRequest
 * 
 * @author simonjsmith@google.com
 * 
 * See
 * http://code.google.com/apis/checkout/developer/checkout_pixel_tracking.html
 * For additional information on third party tracking
 */
public class UrlParameter {

	private String name;

	private UrlParameterType type;

	/**
	 * Constructor which takes the parameter name and type.
	 * 
	 * @param name
	 *            The parameter name.
	 * @param type
	 *            The parameter type.
	 * 
	 * @see UrlParameterType
	 */
	public UrlParameter(String name, UrlParameterType type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * Get the URL parameter name.
	 * 
	 * @return The URL parameter name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the URL parameter type.
	 * 
	 * @return The URL parameter type.
	 * 
	 * @see UrlParameterType
	 */
	public UrlParameterType getParamType() {
		return type;
	}
}