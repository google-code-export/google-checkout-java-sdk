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
 * This class represents the fulfillment status of an order. The valid states
 * are:
 * 
 * NEW - The order has been received but not prepared for shipping. PROCESSING -
 * The order is being prepared for shipping. DELIVERED - The seller has shipped
 * the order. WILL_NOT_DELIVER - The seller will not ship the order; this status
 * is used for canceled orders.
 * 
 * @author simonjsmith
 * 
 */
public class FulfillmentOrderState {

	/**
	 * An instance of the FulfillmentOrderState class representing NEW.
	 */
	public static final FulfillmentOrderState NEW = new FulfillmentOrderState(
			"NEW");

	/**
	 * An instance of the FulfillmentOrderState class representing PROCESSING.
	 */
	public static final FulfillmentOrderState PROCESSING = new FulfillmentOrderState(
			"PROCESSING");

	/**
	 * An instance of the FulfillmentOrderState class representing DELIVERED.
	 */
	public static final FulfillmentOrderState DELIVERED = new FulfillmentOrderState(
			"DELIVERED");

	/**
	 * An instance of the FulfillmentOrderState class representing
	 * WILL_NOT_DELIVER.
	 */
	public static final FulfillmentOrderState WILL_NOT_DELIVER = new FulfillmentOrderState(
			"WILL_NOT_DELIVER");

	private String value;

	private FulfillmentOrderState(String value) {
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
	 * Get an instance of the FulfillmentOrderState based on the string value
	 * passed in.
	 * 
	 * @param state
	 *            The string value of the state.
	 * @return The FulfillmentOrderState instance.
	 */
	public static FulfillmentOrderState getState(String state) {
		if ("NEW".equals(state)) {
			return NEW;
		} else if ("PROCESSING".equals(state)) {
			return PROCESSING;
		} else if ("DELIVERED".equals(state)) {
			return DELIVERED;
		} else if ("WILL_NOT_DELIVER".equals(state)) {
			return WILL_NOT_DELIVER;
		} else {
			throw new RuntimeException("Invalid Fulfillment State: " + state);
		}
	}
}
