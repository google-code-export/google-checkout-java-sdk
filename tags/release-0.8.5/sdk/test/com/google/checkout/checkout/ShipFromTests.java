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

import com.google.checkout.util.Utils;
import junit.framework.TestCase;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class ShipFromTests extends TestCase {
  public void testShipFromNodeNames() {
    ShipFrom shipFrom = new ShipFrom();
    shipFrom.setCity("Mountain View");
    shipFrom.setCountryCode("USA");
    shipFrom.setId("123456");
    shipFrom.setPostalCode("94043");
    shipFrom.setRegion("SW*");
    
    assertEquals(
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ship-from id=\"123456\">" + 
      "<city>Mountain View</city><country-code>USA</country-code>" + 
      "<postal-code>94043</postal-code><region>SW*</region></ship-from>", 
      Utils.nodeToString(shipFrom.getRootElement()));
  }
}
