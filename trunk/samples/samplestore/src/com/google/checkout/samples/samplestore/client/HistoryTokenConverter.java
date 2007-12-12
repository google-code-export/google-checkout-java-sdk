// Copyright 2007 Google Inc. All Rights Reserved.

package com.google.checkout.samples.samplestore.client;

/**
 * @author tonylo@google.com (Tony Lo)
 *
 */
public class HistoryTokenConverter {
  private final Inventory inventory;
  
  public HistoryTokenConverter(Inventory inventory) {
    this.inventory = inventory;
  }
  
  public String getTokenFromCategory(Category category) {
    String token = "";
    
    while (category != null) {
      token += category.getName() + ";";
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
