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
 * This class represents the &lt;delivery-address-category&gt; tag in the
 * Checkout API.
 * 
 * @author simonjsmith
 */
public class DeliveryAddressCategory {

  /**
   * An instance of the DeliveryAddressCategory class with value: RESIDENTIAL.
   */
  public static final DeliveryAddressCategory RESIDENTIAL =
      new DeliveryAddressCategory("RESIDENTIAL");

  /**
   * An instance of the DeliveryAddressCategory class with value: COMMERCIAL..
   */
  public static final DeliveryAddressCategory COMMERCIAL =
      new DeliveryAddressCategory("COMMERCIAL.");

  private final String value;

  private DeliveryAddressCategory(String value) {
    this.value = value;
  }

  public String toString() {
    return value;
  }

}
