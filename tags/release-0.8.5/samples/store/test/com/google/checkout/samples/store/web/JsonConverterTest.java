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

import com.google.checkout.samples.store.model.DummyData;
import com.google.checkout.samples.store.model.Product;
import com.google.checkout.samples.store.model.ProductList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for {@link JsonConverter}
 * @author inder@google.com (Inderjeet Singh)
 */
public class JsonConverterTest {

  @Test
  public void toJsonProduct() {
    Product product = DummyData.BIRD0_0;
    String expected = "{'id' : 'bird0', 'name' : 'Lovebird', " 
            + "'thumbnailURL' : 'images/lovebird-thumb.jpg', "
            + "'imageURL' : 'images/lovebird-med.jpg', 'description' : 'A princess description.'}";
    String result = JsonConverter.toJson(product);
    assertEquals(expected, result);
  }
  
  @Test
  public void toJsonProductList() {
    ProductList productList = DummyData.BIRDS0;
    String expected = "{'id' : 'Birds 0', 'items' : [" 
        + "{'id' : 'bird0', 'name' : 'Lovebird', 'thumbnailURL' : 'images/lovebird-thumb.jpg', 'imageURL' : 'images/lovebird-med.jpg', 'description' : 'A princess description.'}, "
        + "{'id' : 'bird1', 'name' : 'Peacock', 'thumbnailURL' : 'images/peacock-thumb.jpg', 'imageURL' : 'images/peacock-med.jpg', 'description' : 'A princess description.'}, "
        + "{'id' : 'bird2', 'name' : 'Dragon Iron', 'thumbnailURL' : 'images/dragon-iron-thumb.jpg', 'imageURL' : 'images/dragon-iron-med.jpg', 'description' : 'A princess description.'}, "
        + "{'id' : 'bird3', 'name' : 'Galah Parrot', 'thumbnailURL' : 'images/galah-parrot-thumb.jpg', 'imageURL' : 'images/galah-parrot-med.jpg', 'description' : 'A princess description.'}, "
        + "{'id' : 'bird4', 'name' : 'Peakcock White', 'thumbnailURL' : 'images/peacock-white-thumb.jpg', 'imageURL' : 'images/peakcock-white-med.jpg', 'description' : 'A princess description.'}, "
        + "{'id' : 'bird5', 'name' : 'Eagle Stone', 'thumbnailURL' : 'images/eagle-stone-thumb.jpg', 'imageURL' : 'images/eagle-stone-med.jpg', 'description' : 'A princess description.'}, "
        + "{'id' : 'bird6', 'name' : 'Peacock Blue', 'thumbnailURL' : 'images/peacock-blue-thumb.jpg', 'imageURL' : 'images/peacock-blue-med.jpg', 'description' : 'A princess description.'}, "
        + "{'id' : 'bird7', 'name' : 'Eclectus Female', 'thumbnailURL' : 'images/eclectus-female-thumb.jpg', 'imageURL' : 'images/eclectus-female-med.jpg', 'description' : 'A princess description.'}"
        + "]}";
    String result = JsonConverter.toJson(productList);
    assertEquals(expected, result);
  }
}