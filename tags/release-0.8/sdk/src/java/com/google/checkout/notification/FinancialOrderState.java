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

/**
 * This class represents the financial status of an order. The valid states are:
 * 
 * REVIEWING - Google Checkout is reviewing the order. CHARGEABLE - The order is
 * ready to be charged. CHARGING - The order is being charged; you may not
 * refund or cancel an order until is the charge is completed. CHARGED - The
 * order has been successfully charged; if the order was only partially charged,
 * the buyer's account page will reflect the partial charge. PAYMENT_DECLINED -
 * The charge attempt failed. CANCELLED - The seller canceled the order; an
 * order's financial state cannot be changed after the order is canceled.
 * CANCELLED_BY_GOOGLE - Google canceled the order.
 * 
 * @author simonjsmith
 * 
 */
public class FinancialOrderState {

	/**
	 * An instance of the FinancialOrderState class representing REVIEWING.
	 */
	public static final FinancialOrderState REVIEWING = new FinancialOrderState(
			"REVIEWING");

	/**
	 * An instance of the FinancialOrderState class representing CHARGEABLE.
	 */
	public static final FinancialOrderState CHARGEABLE = new FinancialOrderState(
			"CHARGEABLE");

	/**
	 * An instance of the FinancialOrderState class representing CHARGING.
	 */
	public static final FinancialOrderState CHARGING = new FinancialOrderState(
			"CHARGING");

	/**
	 * An instance of the FinancialOrderState class representing CHARGED.
	 */
	public static final FinancialOrderState CHARGED = new FinancialOrderState(
			"CHARGED");

	/**
	 * An instance of the FinancialOrderState class representing
	 * PAYMENT_DECLINED.
	 */
	public static final FinancialOrderState PAYMENT_DECLINED = new FinancialOrderState(
			"PAYMENT_DECLINED");

	/**
	 * An instance of the FinancialOrderState class representing
	 * CANCELLED_BY_GOOGLE.
	 */
	public static final FinancialOrderState CANCELLED_BY_GOOGLE = new FinancialOrderState(
			"CANCELLED_BY_GOOGLE");

	/**
	 * An instance of the FinancialOrderState class representing CANCELLED.
	 */
	public static final FinancialOrderState CANCELLED = new FinancialOrderState(
			"CANCELLED");

	private String value;

	private FinancialOrderState(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return value;
	}

	/**
	 * Get an instance of the FinancialOrderState based on the string value
	 * passed in.
	 * 
	 * @param state
	 *            The string value of the state.
	 * @return The FinancialOrderState instance.
	 */
	public static FinancialOrderState getState(String state) {
		if ("REVIEWING".equals(state)) {
			return REVIEWING;
		} else if ("CHARGEABLE".equals(state)) {
			return CHARGEABLE;
		} else if ("CHARGING".equals(state)) {
			return CHARGING;
		} else if ("CHARGED".equals(state)) {
			return CHARGED;
		} else if ("PAYMENT_DECLINED".equals(state)) {
			return PAYMENT_DECLINED;
		} else if ("CANCELLED_BY_GOOGLE".equals(state)) {
			return CANCELLED_BY_GOOGLE;
		} else if ("CANCELLED".equals(state)) {
			return CANCELLED;
		} else {
			throw new RuntimeException("Invalid Financial State: " + state);
		}
	}
}
