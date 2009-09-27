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

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public class JSONParser {
  
  // Maximum number of nested sub-category levels allowed.
  private static final int MAX_SUBCATEGORY_LEVELS = 50;
  
  /**
   * Parses a product feed and returns an inventory
   * containing all the products and product categories.
   * 
   * @param jsonFeed The product feed.
   * @return The inventory containing all the products and categories.
   */
  public static Inventory parse(JSONObject jsonFeed) {
    if (jsonFeed == null) {
      throw new IllegalArgumentException("jsonObj cannot be null.");
    }
    
    JSONValue feed = jsonFeed.get("feed");
    if (!isFeedValid(feed)) {
      return null;
    }

    Inventory inventory = new Inventory();
    JSONArray items = feed.isObject().get("entry").isArray();
    
    // Loop through the set of items (products).
    for (int i = 0; i < items.size(); ++i) {
      JSONObject item = items.get(i).isObject();
      
      // Skip item if it's null.
      if (item == null) {
        continue;
      }
      
      JSONValue id = getJSONValue(item, "id");
      JSONValue name = getJSONValue(item, "title");
      JSONValue image = getJSONValue(item, "g$image_link");
      JSONValue description = getJSONValue(item, "content");
      JSONValue price = getJSONValue(item, "g$price");
      JSONValue topLevelCategory = getJSONValue(item, "g$item_type");
      
      // Skip item if any required field is missing.
      if (id == null || name == null || price == null || 
        topLevelCategory == null) {
        continue;
      }
      
      Product p = new Product(convertToString(id), convertToString(name), 
          convertToString(image), convertToString(description), 
          parsePrice(convertToString(price)));
      
      String currCategoryName = convertToString(topLevelCategory);
      Category currCategory = inventory.getOrAddTopLevelCategory(currCategoryName);
      
      // Parse sub-categories
      for (int j = 1; j <= MAX_SUBCATEGORY_LEVELS; j++) {
        JSONValue subCategory = getJSONValue(item, "g$sub-category-" + j);
        
        // Break when there are no more sub-categories.
        if (subCategory == null) {
          break;
        }
        
        currCategoryName = convertToString(subCategory);
        // Add the sub-category as a child of the previous sub-category
        currCategory = 
          currCategory.getOrAddSubCategory(currCategoryName);        
      }
      
      // Add product to the bottom-most sub-category.
      inventory.addProduct(currCategory, p);
    }
    
    return inventory;
  }
  
  /**
   * Helper method that parses a price string and returns its
   * numeric value.
   * 
   * NOTE: We are not dealing with currency at this point.
   */
  private static double parsePrice(String price) {
    price = price.trim();
    int index = price.indexOf(" ");
    if (index > 0) {
      price = price.substring(0, index);
    }
    return Double.parseDouble(price);
  }
  
  private static boolean isFeedValid(JSONValue feed) {
    return !(feed == null || 
        feed.isObject() == null || 
        feed.isObject().get("entry") == null ||
        feed.isObject().get("entry").isArray() == null);
  }
  
  /**
   * Helper method that parses the given JSON object and returns the
   * value for the given tag name.
   */
  private static JSONValue getJSONValue(JSONObject obj, String tagName) {
    JSONValue tempValue = obj.get(tagName);
    if (tempValue == null) {
      return null;
    }
    
    JSONObject tempObj = tempValue.isObject();
    if (tempObj == null) {
      JSONArray tempArray = tempValue.isArray();
      if (tempArray == null) {
        return null;
      }
      return tempArray.get(0).isObject().get("$t");
    }
    
    return tempObj.get("$t");
  }
  
  /**
   * Helper method that converts a JSON value to a string by removing
   * surrounding whitespace and quotes.
   */
  private static String convertToString(JSONValue jsonVal) {
    if (jsonVal == null) {
      return "";
    }
    String str = jsonVal.toString().trim();

    // strip quotes
    if (str.startsWith("\"") && str.endsWith("\"") && str.length() > 1) {
      return str.substring(1, str.length() - 1);
    }
    return str;
  }
}
