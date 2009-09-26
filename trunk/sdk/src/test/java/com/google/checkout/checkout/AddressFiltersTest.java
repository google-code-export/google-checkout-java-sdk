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
public class AddressFiltersTest extends TestCase {
  
  public void testAddressFiltersNodeNames() {
    AddressFilters addressFilters = new AddressFilters();
    addressFilters.addAllowedCountryArea(USArea.ALL);
    addressFilters.addAllowedPostalArea("USA", "SW*");
    addressFilters.addAllowedStateCode("CA");
    addressFilters.addAllowedWorldArea();
    addressFilters.addAllowedZipPattern("94043");
    addressFilters.addExcludedCountryArea(USArea.CONTINENTAL_48);
    addressFilters.addExcludedPostalArea("USA", "NE*");
    addressFilters.addExcludedWorldArea();
    addressFilters.addExcludedZipPattern("90210");
    
    assertEquals(
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?><address-filters>" + 
      "<allowed-areas><us-country-area country-area=\"ALL\"/><postal-area>" + 
      "<country-code>USA</country-code><postal-code-pattern>SW*" + 
      "</postal-code-pattern></postal-area><us-state-area><state>CA</state>" + 
      "</us-state-area><world-area/><us-zip-area><zip-pattern>94043" + 
      "</zip-pattern></us-zip-area></allowed-areas><excluded-areas>" + 
      "<us-country-area country-area=\"CONTINENTAL_48\"/><postal-area>" + 
      "<country-code>USA</country-code><postal-code-pattern>NE*" + 
      "</postal-code-pattern></postal-area><world-area/><us-zip-area>" + 
      "<zip-pattern>90210</zip-pattern></us-zip-area></excluded-areas>" + 
      "</address-filters>", Utils.nodeToString(addressFilters.getRootElement()));
  }
}
