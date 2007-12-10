// Copyright 2007 Google Inc. All Rights Reserved.

package com.google.checkout.samples.samplestore.client;

/**
 * A model object representing a product.
 * 
 * @author Simon Lam (simonlam@google.com)
 */
public class Product {

  private final String id;
  private final String title;
  private final String imageUrl;
  private final String description;
  private final Double price;
  //private final String category;
  
  public Product(String id, String title, String imageUrl, String description, 
      String price) {//, String category) {
    this.id = id;
    this.title = title.split("\"")[1];
    this.imageUrl = (imageUrl != "" ? imageUrl.split("\"")[1] : "images/no_image.gif");
    this.description = description.substring(1,description.length() - 1);
    this.price = (price != "" ? new Double(price.split("\"")[1].split(" ")[0]) : new Double(1.0));
    //this.category = category.split("\"")[1];
  }
  
  public String getId() {
    return id;
  }
  
  public String getTitle() {
    return title;
  }
  
  public String getImageUrl() {
    return imageUrl;
  }
  
  public String getDescription() {
    return description;
  }

  public Double getPrice() {
    return price;
  }
  
  /*public String getCategory() {
    return category;
  }*/
  
  public String toString() {
    return title + ", " + description + ", " + price;
  }
}
