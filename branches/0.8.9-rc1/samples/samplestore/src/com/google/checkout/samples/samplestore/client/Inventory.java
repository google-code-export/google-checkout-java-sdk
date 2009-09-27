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
 * An inventory of products within a set of product categories.
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
  
  /**
   * Retrieves all the products in the inventory of the Google Base customer.
   * 
   * @return All the products in the current inventory.
   */
  public Collection getAllProducts() {
    Collection products = new ArrayList();
    for (Iterator it = topLevelCategories.iterator(); it.hasNext(); ) {
      Category category = (Category) it.next();
      products.addAll(getProductsInCategory(category));
    }
    return products;
//    return getProductsInCategory(rootCategory);
  }
  
  /**
   * Retrieves all the products in the inventory of the Google Base customer
   * corresponding to the given category.
   * 
   * @param category The category with the items that will be retrieved if it
   * exists. Otherwise, will return a Collection with size of 0.
   * 
   * @return A collection containing the products corresponding to the given
   * Category.
   */
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
  
  /**
   * Adds a product corresponding to the bottom-most category. 
   * 
   * @param category The bottom-most category that is associated with the 
   * product.
   * @param product The product to be added.
   */
  public void addProduct(Category category, Product product) {
    Collection products = (Collection) categoryProducts.get(category);
    if (products == null) {
      products = new ArrayList();
      categoryProducts.put(category, products);
    }
    products.add(product);
  }
  
  /**
   * Retrieve all the top level categories in the inventory.
   * 
   * @return The top-level categories in the inventory.
   */
  public Collection getTopLevelCategories() {
    return topLevelCategories;
  }
  
  /**
   * Retrieves a top-level category in the inventory.
   * 
   * @param categoryName The top-level category we want to retrieve.
   * 
   * @return The top-level Category corresponding to the given categoryName. 
   * Otherwise, null.
   */
  public Category getTopLevelCategory(String categoryName) {
    for (Iterator it = topLevelCategories.iterator(); it.hasNext(); ) {
      Category category = (Category) it.next();
      if (category.getName().equals(categoryName)) {
        return category;
      }
    }
    return null;
  }
  
  /**
   * 
   * @param categoryName The name of the category we would like to retrieve or 
   * add.
   * @return The Category (existing or newly created) corresponding to the 
   * categoryName we have passed in.
   */
  public Category getOrAddTopLevelCategory(String categoryName) {
    Category category = getTopLevelCategory(categoryName);
    if (category == null) {
      category = new Category(categoryName);
      topLevelCategories.add(category);
    }
    return category;
  }
}
