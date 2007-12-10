/*******************************************************************************
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliancem with the License. You may obtain a copy of
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
import java.util.Iterator;
import java.util.List;

/**
 * List of products
 * @author inder@google.com (Inderjeet Singh)
 */
public class ProductList extends ArrayList {

  private final Category category;

  public ProductList(Category category) {
    this(category, new ArrayList());
  }

  public ProductList(Category category, Product product) {
    this.category = category;
    if (product != null)
      this.add(product);
  }
  
  public ProductList(Category category, List products) {
    this.category = category;
    if (products != null) {
      Iterator iter = products.iterator();
      while (iter.hasNext()) {
        Product product = (Product) iter.next();
        if (product != null)
          this.add(product);
      }
    }
  }

  public Category getCategory() {
    return category;
  }
}
