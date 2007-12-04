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
 * This class represents the &lt;rounding-policy&gt; &lt;mode&gt; in the
 * Checkout API.
 *
 * @author simonjsmith
 */
public class RoundingMode {
  
  /**
   * An instance of the RoundingMode class with mode: CEILING.
   */
  public static final RoundingMode CEILING = new RoundingMode("CEILING");
  
  /**
   * An instance of the RoundingMode class with mode: DOWN.
   */
  public static final RoundingMode DOWN = new RoundingMode("DOWN");
  
  /**
   * An instance of the RoundingMode class with mode: FLOOR.
   */
  public static final RoundingMode FLOOR = new RoundingMode("FLOOR");
  
  /**
   * An instance of the RoundingMode class with mode: HALF_DOWN.
   */
  public static final RoundingMode HALF_DOWN = new RoundingMode("HALF_DOWN");
  
  /**
   * An instance of the RoundingMode class with mode: HALF_EVEN.
   */
  public static final RoundingMode HALF_EVEN = new RoundingMode("HALF_EVEN");
  
  /**
   * An instance of the RoundingMode class with mode: HALF_UP.
   */
  public static final RoundingMode HALF_UP = new RoundingMode("HALF_UP");
 
  /**
   * An instance of the RoundingMode class with mode: UNNECESSARY.
   */
  public static final RoundingMode UNNECESSARY = 
    new RoundingMode("UNNECESSARY");
 
  /**
   * An instance of the RoundingMode class with mode: UP.
   */
  public static final RoundingMode UP = new RoundingMode("UP");
    
  private String value;
  
  private RoundingMode(String value) {
    this.value = value;
  }
  
  public String toString() {
    return value;
  }
}
