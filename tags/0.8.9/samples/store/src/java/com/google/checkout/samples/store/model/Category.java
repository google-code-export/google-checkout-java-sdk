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

package com.google.checkout.samples.store.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A product category. This data structure supports arbitrary levels of subcategories
 * @author inder@google.com (Inderjeet Singh)
 */
public class Category {
  private final String name;
  private final Collection<Category> subCategories;
  
  private Category(String name) {
    this(name, (Category) null);
  }
  
  private Category(String name, Category... subCategories) {
    this.name = name;
    this.subCategories = new ArrayList<Category>();
    if (subCategories != null && subCategories.length > 0) {
      for (Category category : subCategories) {
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
  
  public Collection<Category> getSubCategories() {
    return subCategories;
  }
    
  @Override 
  public int hashCode() {
    return name.hashCode();
  }
  
  @Override
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
    for (Category category : subCategories) {
      if (!other.subCategories.contains(category)) {
        return false;
      }
    }
    return true;
  }
  
  private static List<Category> list = new ArrayList<Category>();
  private static Category intern(Category category) {
      int index = list.indexOf(category);
      if (index >= 0) {
        return list.get(index);
      } else {
        list.add(category);
        return category;
      }
    }

  public static Category getCategory(String name) {
    return getCategory(name, (Category) null);
  }
  
  public static Category getCategory(String name, Category... subCategories) {
    if (name == null || name.trim().equals("")) {
      return null;
    }
    return intern(new Category(name, subCategories));
  }
}
