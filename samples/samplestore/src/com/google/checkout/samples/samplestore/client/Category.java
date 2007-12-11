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
import java.util.Iterator;
import java.util.List;

/**
 * A product category. This data structure supports arbitrary levels of subcategories
 * @author inder@google.com (Inderjeet Singh)
 */
public class Category {
  private final String name;
  private final Collection subCategories;
  
  public Category(String name) {
    this(name, null);
  }
  
  public Category(String name, List subCategories) {
    this.name = name;
    this.subCategories = new ArrayList();
    if (subCategories != null && subCategories.size() > 0) {
      Iterator iter = subCategories.iterator();
      while (iter.hasNext()) {
        Category category = (Category) iter.next();
        if (category != null) {
          this.subCategories.add(category);
        }
      }
    }
  }
  
  public String getName() {
    return name;
  }
  
  public boolean hasSubCategories() {
    return subCategories != null && subCategories.size() > 0;
  }
  
  public Category getSubCategory(String subCategoryName) {
    Iterator it = subCategories.iterator();
    
    while (it.hasNext()) {
      Category tempCategory = (Category) it.next();
      if (tempCategory.getName().equals(subCategoryName)) {
        return tempCategory;
      }
    }
    
    return null;
  }
  
  public Collection getSubCategories() {
    return subCategories;
  }
    
  public int hashCode() {
    return name.hashCode();
  }
  
  public boolean equals(Object o) {
    if (o == null || !(o instanceof Category)) {
      return false;
    }
    Category other = (Category) o;
    if (!name.equals(other.getName())) {
      return false;
    } 
    if (subCategories.size() != other.subCategories.size()) {
      return false;
    }
    Iterator iter = subCategories.iterator();
    while (iter.hasNext()) {
      if (!other.subCategories.contains(iter.next())) {
        return false;
      }
    }
    return true;
  }
  
  /**
   * Adds a sub-category if this category does not contain a sub-category with 
   * the same name. Otherwise, subCategory will replace the existing sub-category
   * with the same name.
   * 
   * @param subCategory the sub-category to be added
   */
  public void addSubCategory(Category subCategory) {
    Iterator it = subCategories.iterator();
    
    boolean subCategoryExists = false;
    
    Category tempCategory = null;
    while (it.hasNext()) {
      tempCategory = (Category)it.next();
      
      if (tempCategory.getName().equals(subCategory.getName())) {
        subCategoryExists = true;
        break;
      }
    }
    
    if (!subCategoryExists) {
      subCategories.add(subCategory);
    } else {
      subCategories.remove(tempCategory);
      subCategories.add(subCategory);
    }
  }
  
  public Category getOrCreateSubCategory(String subCategoryName) {
    Category tempCategory = getSubCategory(subCategoryName);
    if (tempCategory == null) {
      tempCategory = new Category(subCategoryName);
      subCategories.add(tempCategory);
    }
    
    return tempCategory;
  }
  
//  private static List list = new ArrayList();
//  
//  private static Category intern(Category category) {
//      int index = list.indexOf(category);
//      if (index >= 0) {
//        return (Category) list.get(index);
//      } else {
//        list.add(category);
//        return category;
//      }
//    }
//
//  public static Category getCategory(String name) {
//    return getCategory(name, null);
//  }
//  
//  public static Category getCategory(String name, List subCategories) {
//    if (name == null || name.trim().equals("")) {
//      return null;
//    }
//    return intern(new Category(name, subCategories));
//  }
}
