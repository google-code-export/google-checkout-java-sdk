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

/**
 * Facade to provide data model 
 * @author inder@google.com (Inderjeet Singh)
 */
public class ModelFacade {
  
  private static BaseSnippetPuller puller = new BaseSnippetPuller();
    
  public ModelFacade() {
    
  }
  
  public ProductList getProductsFor(Category category) {
    return BaseSnippetPuller.getBaseData().get(category);
  }
  
  public Collection<Category> getCategories() {
    Collection<Category> categories = new ArrayList<Category>();
    puller.loadBaseData();
    categories.add(BaseSnippetPuller.BASE_CATEGORY);
    return categories;
  }
}
