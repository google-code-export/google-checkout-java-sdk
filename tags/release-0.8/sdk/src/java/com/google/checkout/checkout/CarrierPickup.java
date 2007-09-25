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
 * This class represents the &lt;carrier-pickup&gt; tag in the Checkout API.
 * 
 * @author simonjsmith
 */
public class CarrierPickup {

  /**
   * An instance of the CarrierPickup class with value: REGULAR_PICKUP.
   */
  public static final CarrierPickup REGULAR_PICKUP =
      new CarrierPickup("REGULAR_PICKUP");

  /**
   * An instance of the CarrierPickup class with value: SPECIAL_PICKUP.
   */
  public static final CarrierPickup SPECIAL_PICKUP =
      new CarrierPickup("SPECIAL_PICKUP");

  /**
   * An instance of the CarrierPickup class with value: DROP_OFF.
   */
  public static final CarrierPickup DROP_OFF = new CarrierPickup("DROP_OFF");

  private final String value;

  private CarrierPickup(String value) {
    this.value = value;
  }

  public String toString() {
    return value;
  }
}
