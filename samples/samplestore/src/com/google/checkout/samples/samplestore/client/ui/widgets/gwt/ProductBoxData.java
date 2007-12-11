// Copyright 2007 Google Inc. All Rights Reserved.

package com.google.checkout.samples.samplestore.client.ui.widgets.gwt;

import com.google.checkout.samples.samplestore.client.Product;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Hyperlink;

/** 
 * Panel that lays out the data in the grid boxes.
 * 
 * @author Simon Lam (simonlam@google.com)
 */
public class ProductBoxData extends VerticalPanel {
  
  private Product product;
  
  public ProductBoxData(Product product) {
    try {
      this.setStyleName("item");
      this.product = product;
      if (product.getImageUrl() != "") {
        Image image = new Image(product.getImageUrl());
        image.setStyleName("image");
        this.add(image);
      }
      
      Label title = new Label(product.getTitle());
      title.setStyleName("title");
      this.add(title);

      Label price = new Label(product.getPriceAsString());
      price.setStyleName("price");
      this.add(price);
      
      Button addToCart = new Button("Add to Cart");
      //Hyperlink addToCart = new Hyperlink("Add to Cart", "Recipes");
      addToCart.setStyleName("google-cart-add");
      this.add(addToCart);
   
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
