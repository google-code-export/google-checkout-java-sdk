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

import junit.framework.TestCase;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class PackagingTest extends TestCase {
  public void testPackagingConstants() {
    assertEquals("Box", Packaging.Box.toString());
    assertEquals("Card", Packaging.Card.toString());
    assertEquals("Carrier_Box", Packaging.Carrier_Box.toString());
    assertEquals("Carrier_Envelope", Packaging.Carrier_Envelope.toString());
    assertEquals("Carrier_Pak", Packaging.Carrier_Pak.toString());
    assertEquals("Carrier_Tube", Packaging.Carrier_Tube.toString());
    assertEquals("Letter", Packaging.Letter.toString());
  }
}
