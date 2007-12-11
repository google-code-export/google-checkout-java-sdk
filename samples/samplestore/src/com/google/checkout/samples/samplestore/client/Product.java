// Copyright 2007 Google Inc. All Rights Reserved.

package com.google.checkout.samples.samplestore.client;

/**
 * A model object representing a product.
 * 
 * @author Simon Lam (simonlam@google.com)
 */
public class Product {

  private static final String DEFAULT_IMAGE = "images/no_image.gif";
  
  private final String id;
  private final String title;
  private final String imageUrl;
  private final String description;
  private final Double price;
  //private final String category;
  
  public Product(String id, String title, String imageUrl, String description, 
      String price) {//, String category) {
    this.id = removeSurroundingQuotes(id);
    this.title = removeSurroundingQuotes(title);
    this.imageUrl = (imageUrl != "" ? removeSurroundingQuotes(imageUrl) : 
        DEFAULT_IMAGE);
    this.description = removeSurroundingQuotes(description);
    this.price = (price != "" ? new Double(removeSurroundingQuotes(price)
        .split(" ")[0]) : new Double(1.0));
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
  
  public String getPriceAsString() {
    if (price.intValue() == price.doubleValue())
      return "$" + price + "0";
    return "$" + price;
  }
  
  /*public String getCategory() {
    return category;
  }*/
  
  public String toString() {
    return title + ", " + description + ", " + price;
  }
  
  private String removeSurroundingQuotes(String element) {
    if (element.charAt(0) == '\"' && element.charAt(element.length() - 1) == '\"')
      return element.substring(1, element.length() - 1);
    return element;
  }
}
