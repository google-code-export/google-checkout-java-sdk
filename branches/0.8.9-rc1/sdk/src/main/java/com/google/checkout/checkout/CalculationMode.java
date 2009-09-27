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
 * This class represents the &lt;calculation-mode&gt; tag in the Checkout API.
 *
 * @author simonjsmith
 */
public class CalculationMode {
  
  /**
   * An instance of the CalculationMode class with mode: BATCH.
   */
  public static final CalculationMode BATCH = new CalculationMode("BATCH");
  
  /**
   * An instance of the CalculationMode class with mode: SINGLE.
   */
  public static final CalculationMode SINGLE = new CalculationMode("SINGLE");
  
  private String value;
  
  private CalculationMode(String value) {
    this.value = value;
  }
  
  public String toString() {
    return value;
  }
}
