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
 * This class represents a parameter type on a URL as used by the
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
public class UrlParameterType {

	/** The city associated with the order's billing address. */
	public static UrlParameterType billingCity = new UrlParameterType(
			"billing-city");

	/**
	 * The two-letter ISO 3166 country code associated with the order's billing
	 * address.
	 */
	public static UrlParameterType billingCountryCode = new UrlParameterType(
			"billing-country-code");

	/** The five-digit U.S. zip code associated with the order's billing address. */
	public static UrlParameterType billingPostalCode = new UrlParameterType(
			"billing-postal-code");

	/** The U.S. state associated with the order's billing address. */
	public static UrlParameterType billingRegion = new UrlParameterType(
			"billing-region");

	/**
	 * A Google-assigned value that uniquely identifies a customer email
	 * address.
	 */
	public static UrlParameterType buyerID = new UrlParameterType("buyer-id");

	/** The total amount of all coupons factored into the order total. */
	public static UrlParameterType couponAmount = new UrlParameterType(
			"coupon-amount");

	/**
	 * A Google-assigned value that uniquely identifies an order. This value is
	 * displayed in the Merchant Center for each order. If you have implemented
	 * the Notification API, you will also see this value in all Google Checkout
	 * notifications.
	 */
	public static UrlParameterType OrderID = new UrlParameterType("order-id");

	/**
	 * The total cost for all of the items in the order including coupons and
	 * discounts but excluding taxes and shipping charges.
	 */
	public static UrlParameterType orderSubTotal = new UrlParameterType(
			"order-subtotal");

	/**
	 * The total cost for all of the items in the order, including shipping
	 * charges, coupons and discounts, but excluding taxes.
	 */
	public static UrlParameterType orderSubTotalPlusShipping = new UrlParameterType(
			"order-subtotal-plus-shipping");

	/**
	 * The total cost for all of the items in the order, including taxes,
	 * coupons and discounts, but excluding shipping charges.
	 */
	public static UrlParameterType orderSubTotalPlusTax = new UrlParameterType(
			"order-subtotal-plus-tax");

	/**
	 * The total cost for all of the items in the order, including taxes,
	 * shipping charges, coupons and discounts.
	 */
	public static UrlParameterType orderTotal = new UrlParameterType(
			"order-total");

	/** The shipping cost associated with an order. */
	public static UrlParameterType shippingAmount = new UrlParameterType(
			"shipping-amount");

	/** The city associated with the order's shipping address. */
	public static UrlParameterType shippingCity = new UrlParameterType(
			"shipping-city");

	/**
	 * The two-letter ISO 3166 country code associated with the order's shipping
	 * address.
	 */
	public static UrlParameterType shippingCountryCode = new UrlParameterType(
			"shipping-country-code");

	/**
	 * The five-digit U.S. zip code associated with the order's shipping
	 * address.
	 */
	public static UrlParameterType shippingPostalCode = new UrlParameterType(
			"shipping-postal-code");

	/** The U.S. state associated with the order's shipping address. */
	public static UrlParameterType shippingRegion = new UrlParameterType(
			"shipping-region");

	/** The total amount of taxes charged for an order. */
	public static UrlParameterType taxAmount = new UrlParameterType(
			"tax-amount");

	private String param;

	private UrlParameterType(String param) {
		this.param = param;
	}

	public String toString() {
		return param;
	}
}
