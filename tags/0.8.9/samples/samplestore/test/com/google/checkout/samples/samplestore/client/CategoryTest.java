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

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collection;

public class CategoryTest extends TestCase {
  private Category category;
  
  public void testConstructorWithString() {
    category = new Category("New Category");
    
    assertEquals("New Category", category.getName());
    assertNull(category.getParent());
    assertEquals(0, category.getSubCategories().size());
    assertFalse(category.hasSubCategories());
  }
  
  public void testGetOrAddSubCategory() {
    category = new Category("New Category");
    
    category.getOrAddSubCategory("New subcategory");
    assertTrue(category.hasSubCategories());
    
    Collection subCategories = category.getSubCategories();
    assertEquals(1, subCategories.size());
    
    Category subCategory = (Category)subCategories.iterator().next();
    assertEquals("New subcategory", subCategory.getName());
    
    assertEquals(category, subCategory.getParent());
  }
  
  public void testConstructorWithNullParent() {
    category = new Category("New Category", null);
    
    assertEquals("New Category", category.getName());
    assertEquals(0, category.getSubCategories().size());
    assertFalse(category.hasSubCategories());
  }
  
  public void testConstructorWithCollection() {
    Collection subCategories = createSubCategories();
    
    category = new Category("New Category", null, subCategories);
    
    assertNull(category.getParent());
    assertEquals(2, category.getSubCategories().size());
  }
  
  public void testConstructorWithNullParentAndNullCollection() {
    category = new Category("New Category", null, null);
  }
  
  public void testConstructorWithParentAndCollection() {
    Collection subCategories = createSubCategories();
    Category parent = new Category("Parent");
    
    Category category = new Category("New Category", parent, subCategories);
    
    assertEquals(2, category.getSubCategories().size());
    assertNotNull(category.getParent());
    
    assertEquals(1, parent.getSubCategories().size());
    
    Category c1 = (Category)parent.getSubCategories().iterator().next();
    assertEquals("New Category", c1.getName());
  }
  
  private Collection createSubCategories() {
    Collection subCategories = new ArrayList();
    
    subCategories.add(new Category("Sub category 1"));
    subCategories.add(new Category("Sub category 2"));
    
    return subCategories;
  }
}
