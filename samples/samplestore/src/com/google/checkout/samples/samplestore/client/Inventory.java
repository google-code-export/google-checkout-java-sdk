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
import java.util.List;


/** 
 * Data structure for an inventory of products.
 * 
 * @author Charles Dang (cdang@google.com)
 * @author Tony Lo (tonylo@google.com)
 */

public class Inventory {
  // <Category, ArrayList>
  private HashMap categoryProducts;
//  private List categories;
  private Category rootCategory;
  
  public Inventory() {
    rootCategory =  new Category("root");
    categoryProducts = new HashMap();
  }
  
  public Category getRootCategory() {
    return rootCategory;
  }
  
  public List getProductsInCategory(Category category) {
    List productList = (List) categoryProducts.get(category);
    
    if (category.hasSubCategories()) {
      Collection subCategories = category.getSubCategories();
      Iterator it = subCategories.iterator();
      
      while (it.hasNext()) {
        Category subCategory = (Category)it.next();
        productList.addAll(getProductsInCategory(subCategory));
      }
    }
    
    return productList;
  }
  
  public void addProduct(Category category, Product product) {
    if (categoryProducts.get(category) == null) {
      List products = new ArrayList();
      products.add(product);
      
      categoryProducts.put(category, products);
    } else {
      ((List)categoryProducts.get(category)).add(product);
    }
  }
  
//  public Category getCategory(String categoryName) {
//    return categories.get(categories.indexOf(categoryName));
//  }
  
//  public void addCategory(Category category) {
//    
//  }
}
