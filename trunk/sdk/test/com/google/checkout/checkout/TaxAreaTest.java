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
public class TaxAreaTest extends TestCase {

  public void setUp() {
    
  }
  
  public void testTaxAreaNodeNames() {
    TaxArea taxArea = new TaxArea();
    
    taxArea.addCountryArea(USArea.ALL);
    taxArea.addPostalArea("USA", "SW*");
    taxArea.addStateCode("CA");
    taxArea.addWorldArea();
    taxArea.addZipPattern("94043");
    
    assertEquals(
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
      "<tax-areas><us-country-area country-area=\"ALL\"/><postal-area>" + 
      "<country-code>USA</country-code><postal-code-pattern>SW*" + 
      "</postal-code-pattern></postal-area><us-state-area><state>CA" + 
      "</state></us-state-area><world-area/><us-zip-area><zip-pattern>" + 
      "94043</zip-pattern></us-zip-area></tax-areas>", 
      Utils.nodeToString(taxArea.getRootElement()));
  }
}
