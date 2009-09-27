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

/**
 * A product type
 * @author inder@google.com (Inderjeet Singh)
 */
public class Product {

  private final String id;
  private final String name;
  private final String thumbnailUrl;
  private final String imageUrl;
  private final String description;
  
  public Product(String id, String name, String thumbnailUrl, String imageUrl, String description) {
    this.id = id;
    this.name = name;
    this.thumbnailUrl = thumbnailUrl;
    this.imageUrl = imageUrl;
    this.description = description;
  }
  
  public String getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }
  
  public String getThumbnailUrl() {
    return thumbnailUrl;
  }
  
  public String getImageUrl() {
    return imageUrl;
  }
  
  public String getDescription() {
    return description;
  }
}
