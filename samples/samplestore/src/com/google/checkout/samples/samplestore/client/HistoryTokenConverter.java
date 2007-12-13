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

package com.google.checkout.samples.samplestore.client;

/** 
 * Class for generating and interpreting history tokens.
 * 
 * @author Tony Lo (tonylo@google.com)
 */
public class HistoryTokenConverter {
  private final Inventory inventory;
  
  public HistoryTokenConverter(Inventory inventory) {
    this.inventory = inventory;
  }
  
  public String getTokenFromCategory(Category category) {
    String token = "";
    
    while (category != null) {
      token = category.getName() + ";" + token;
      category = category.getParent();
    }
    
    return token;
  }
  
  public Category getCategoryFromToken(String token) {
    String[] categoryHierarchy = token.split(";");
    
    if (categoryHierarchy.length == 0) {
      return null;
    }
    
    Category category = inventory.getTopLevelCategory(categoryHierarchy[0]);
    for (int i = 1; i < categoryHierarchy.length; i++) {
      if (category == null) {
        return null;
      }
      category = category.getSubCategory(categoryHierarchy[i]);     
    }
    
    return category;
  }
}
