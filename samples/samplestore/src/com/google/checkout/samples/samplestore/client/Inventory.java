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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/** 
 * Data structure for an inventory of products.
 * 
 * @author Charles Dang (cdang@google.com)
 * @author Tony Lo (tonylo@google.com)
 */

public class Inventory {
  // <Category, Collection>
  private Map categoryProducts;
  private Collection topLevelCategories;
  
  public Inventory() {
    categoryProducts = new HashMap();
    topLevelCategories = new ArrayList();
  }
  
  public Collection getAllProducts() {
    Collection products = new ArrayList();
    for (Iterator it = topLevelCategories.iterator(); it.hasNext(); ) {
      Category category = (Category) it.next();
      products.addAll(getProductsInCategory(category));
    }
    return products;
//    return getProductsInCategory(rootCategory);
  }
  
  public Collection getProductsInCategory(Category category) {
    Collection products = new ArrayList();
    
    // get products in the given category
    Collection productsToAdd = (Collection) categoryProducts.get(category);
    if (productsToAdd != null) {
      products.addAll(productsToAdd);
    }
    
    // get products in the given category's sub-categories
    if (category.hasSubCategories()) {
      Collection subCategories = category.getSubCategories();
      for (Iterator it = subCategories.iterator(); it.hasNext(); ) {
        Category subCategory = (Category) it.next();
        productsToAdd = getProductsInCategory(subCategory);
        products.addAll(productsToAdd);
      }
    }
    
    return products;
  }
  
  public void addProduct(Category category, Product product) {
    Collection products = (Collection) categoryProducts.get(category);
    if (products == null) {
      products = new ArrayList();
      categoryProducts.put(category, products);
    }
    products.add(product);
  }
  
  public Collection getTopLevelCategories() {
    return topLevelCategories;
  }
  
  public Category getTopLevelCategory(String categoryName) {
    for (Iterator it = topLevelCategories.iterator(); it.hasNext(); ) {
      Category category = (Category) it.next();
      if (category.getName().equals(categoryName)) {
        return category;
      }
    }
    return null;
  }
  
  public Category getOrAddTopLevelCategory(String categoryName) {
    Category category = getTopLevelCategory(categoryName);
    if (category == null) {
      category = new Category(categoryName);
      topLevelCategories.add(category);
    }
    return category;
  }
}
