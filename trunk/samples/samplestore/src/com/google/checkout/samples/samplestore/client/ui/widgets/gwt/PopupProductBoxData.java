// Copyright 2007 Google Inc. All Rights Reserved.

package com.google.checkout.samples.samplestore.client.ui.widgets.gwt;

import com.google.checkout.samples.samplestore.client.Product;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Panel that lays out the data in the popup boxes when rolling over products.
 * 
 * @author Simon Lam (simonlam@google.com)
 */
public class PopupProductBoxData extends VerticalPanel {
  private Product product;
  
  public PopupProductBoxData(Product product) {
    this.setStyleName("popupProductBox");
    this.product = product;
    if (product.getImageUrl() != "")
      this.add(new Image(product.getImageUrl()));
    
    Label title = new Label(product.getTitle());
    title.setStyleName("title");
    this.add(title);

    Label description = new Label(product.getDescription());
    description.setStyleName("item-desc");
    this.add(description);
    
    Label price = new Label(product.getPriceAsString());
    price.setStyleName("price");
    this.add(price);
    
    Button addToCart = new Button("Add to Cart");
    //Hyperlink addToCart = new Hyperlink("Add to Cart", "Recipes");
    addToCart.setStyleName("google-cart-add");
    this.add(addToCart);
  }
}
