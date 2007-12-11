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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSONParser {
  // We need some sort of limit as to the number of sub-categories we allow.
  // Otherwise, the merchant can specify unlimited sub-categories
  private static final int MAX_SUBCATEGORIES = 50;
  
  /**
   * 
   * @param json
   * @return Inventory
   */
  public static Inventory parseJSON(JSONObject json) {
    if (json == null) {
      throw new IllegalArgumentException("json must not be null");
    }
    
    JSONValue feed = json.get("feed");
    
    if (feed == null || 
        feed.isObject() == null || 
        feed.isObject().get("entry") == null ||
        feed.isObject().get("entry").isArray() == null) {
      return null;
    }
    
    JSONArray items = feed.isObject().get("entry").isArray();
    
    Inventory inventory = new Inventory();
    
    for (int i = 0; i < items.size(); ++i) {
      JSONObject item = items.get(i).isObject();
      
      if (item == null) {
        continue;
      }
      
      JSONValue id = getJSONValue(item, "id");
      JSONValue name = getJSONValue(item, "title");
      JSONValue image = getJSONValue(item, "g$image_link");
      JSONValue description = getJSONValue(item, "content");
      JSONValue price = getJSONValue(item, "g$price");
      JSONValue topLevelCategory = getJSONValue(item, "g$item_type");
      
      if (id == null || name == null || price == null || 
        topLevelCategory == null) {
        continue;
      }
      
      Product p = new Product(convertToString(id), convertToString(name), 
          convertToString(image), convertToString(description), 
          parsePrice(convertToString(price)));
      
      String currCategoryName = convertToString(topLevelCategory);
      Category currCategory = 
        inventory.getRootCategory().getOrCreateSubCategory(currCategoryName);
      
//      for (int j=1; j <= MAX_SUBCATEGORIES; ++j) {
      for (char j='a'; ; j++) {
//        JSONValue subCategory = item.get("g$subCategory" + j);
        JSONValue subCategory = getJSONValue(item, "g$cat" + j);
        
        if (subCategory == null) {
          break;
        }
        
        currCategoryName = convertToString(subCategory);
        currCategory = 
          currCategory.getOrCreateSubCategory(currCategoryName);        
      }
      
      // add product to the bottom-most sub-category
      inventory.addProduct(currCategory, p);
    }
    
    return inventory;
  }
  
  private static double parsePrice(String price) {
    price = price.trim();
    
    int index = price.indexOf(" ");
    
    if (index > 0) {
      price = price.substring(0, index);
    }
    
    return Double.parseDouble(price);
  }
  
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
  
  private static String convertToString(JSONValue value) {
    if (value == null) {
      return "";
    }
    
    String ret = value.toString();
    
    if (ret.startsWith("\"") && ret.endsWith("\"") && ret.length() > 1) {
      return ret.substring(1, ret.length() - 1);
    }
    
    return ret;
  }
}
