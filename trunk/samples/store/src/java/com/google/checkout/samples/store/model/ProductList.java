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

/**
 * List of products
 * @author inder@google.com (Inderjeet Singh)
 */
public class ProductList extends ArrayList<Product> {

  private final Category category;

  public ProductList(Category category) {
    this(category, (Product) null);
  }

  public ProductList(Category category, Product... products) {
    this.category = category;
    if (products != null) {
      for (Product product : products) {
        this.add(product);
      }
    }
  }

  public Category getCategory() {
    return category;
  }
}
