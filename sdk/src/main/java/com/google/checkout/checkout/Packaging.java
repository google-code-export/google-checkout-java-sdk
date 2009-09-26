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
 * This class represents the &lt;packaging&gt; tag in the Checkout API.
 * 
 * @author simonjsmith
 */
public class Packaging {

  /**
   * An instance of the Packaging class with value: Box.
   */
  public static final Packaging Box = new Packaging("Box");

  /**
   * An instance of the Packaging class with value: Card.
   */
  public static final Packaging Card = new Packaging("Card");
  
  /**
   * An instance of the Packaging class with value: Carrier_Box.
   */
  public static final Packaging Carrier_Box = new Packaging("Carrier_Box");

  /**
   * An instance of the Packaging class with value: Carrier_Envelope.
   */
  public static final Packaging Carrier_Envelope = 
    new Packaging("Carrier_Envelope");
  
  /**
   * An instance of the Packaging class with value: Carrier_Pak.
   */
  public static final Packaging Carrier_Pak = new Packaging("Carrier_Pak");

  /**
   * An instance of the Packaging class with value: Carrier_Tube.
   */
  public static final Packaging Carrier_Tube = new Packaging("Carrier_Tube");

  /**
   * An instance of the Packaging class with value: Letter.
   */
  public static final Packaging Letter = new Packaging("Letter");


  private final String value;

  private Packaging(String value) {
    this.value = value;
  }

  public String toString() {
    return value;
  }
}
