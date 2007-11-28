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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class ItemTest extends TestCase {

  public void testSetMerchantPrivateDataNodesWithEmptyDocument() {
    Item tempItem = new Item();
    Document doc = Utils.newEmptyDocument();
    Element [] itemData = new Element[1];
    itemData[0] = doc.createElement("item-data");
 
    itemData[0].setTextContent("Popular item - order more if needed");
    tempItem.setMerchantPrivateItemData(itemData);

    Element [] ret = tempItem.getMerchantPrivateItemData();
    assertNotNull(ret);
    assertEquals(1, ret.length);
  }
  
  public void testItemNodeNames() {
    Item item = new Item();
    item.setDigitalContent(new DigitalContent());
    item.setItemDescription("description");
    item.setItemName("itemName");
    item.setItemWeight(5.5f);
    item.setItemWeightUnit("lbs");
    item.setMerchantItemId("0123456abcd");
    item.setQuantity(1);
    item.setTaxTableSelector("taxTableSelector");
    item.setUnitPriceAmount(2.3f);
    item.setUnitPriceCurrency("USD");
    
    assertEquals(
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?><item><digital-content/>" + 
      "<item-description>description</item-description><item-name>itemName" + 
      "</item-name><item-weight unit=\"lbs\" value=\"5.5\"/>" + 
      "<merchant-item-id>0123456abcd</merchant-item-id><quantity>1</quantity>" + 
      "<tax-table-selector>taxTableSelector</tax-table-selector>" + 
      "<unit-price currency=\"USD\">2.3</unit-price></item>", 
      Utils.nodeToString(item.getRootElement()));
  }
}
