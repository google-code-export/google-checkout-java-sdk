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

package com.google.checkout.samples.store.web;

import com.google.checkout.samples.store.model.Product;
import com.google.checkout.samples.store.model.ProductList;
import java.util.Iterator;

/**
 * Provides conversions of objects to their representation in Json
 * @author inder@google.com (Inderjeet Singh)
 */
public class JsonConverter {

  public static String toJson(Product product) {
    return "{'id' : '" + product.getId() + "', 'name' : '" + product.getName() 
            + "', 'thumbnailURL' : '" + product.getThumbnailUrl()
            + "', 'imageURL' : '" + product.getImageUrl() 
            + "', 'description' : '" + product.getDescription() + "'}";    
  }
  
  public static String toJson(ProductList list) {
    StringBuilder sb = new StringBuilder("{");
    if (list != null) {
      sb.append("'id' : '");
      sb.append(list.getCategory().getName());
      sb.append("', 'items' : [");
      for (Iterator<Product> iterator = list.iterator(); iterator.hasNext(); ) {
        Product product = iterator.next();
        sb.append(toJson(product));
        if (iterator.hasNext()) {
          sb.append(", ");
        }
      }
      sb.append("]");
    }
    sb.append("}");
    return sb.toString();
  }

}