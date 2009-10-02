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

package com.google.checkout.samples.samplestore.client.ui.widgets.gwt;

import com.google.checkout.samples.samplestore.client.Product;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LoadListener;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Container for a product in the display grid.
 * 
 * @author Simon Lam (simonlam@google.com)
 */
public class ProductBox extends Composite {
  private static final int MAX_IMAGE_WIDTH = 200;
  private static final int MAX_IMAGE_HEIGHT = 125;
  
  // Absolute position offset of a popup product box 
  // relative its associated product box.
  private static final int POPUP_OFFSET_LEFT = 25;
  private static final int POPUP_OFFSET_TOP = 25;
  
  private PopupPanel popup = new PopupPanel();
  private FocusPanel popupFocusPanel = new FocusPanel();
  
  private VerticalPanel panel = new VerticalPanel();
  private FocusPanel outer = new FocusPanel();
  
  private Image productImage;
  private Image popupProductImage;
  
  public ProductBox(Product product) {
    if (product == null) {
      return;
    }
    
    // The product image in the pop-up box. We initialize it here
    // because getProductImage() may use it.
    popupProductImage = new Image(product.getImageUrl());
    
    // The product image.
    productImage = getProductImage(product);
    if (productImage != null) {
      panel.add(productImage);
      panel.setCellHorizontalAlignment(productImage, HorizontalPanel.ALIGN_CENTER);
    }
    
    // The product title.
    HTML titleLink = 
      new HTML("<a href='javascript:;'>" + product.getTitle() + "</a>");
    panel.add(titleLink);

    // The product price.
    Label price = new Label(product.getPriceAsString());
    price.setStyleName("gridstore-ProductBoxPrice");
    panel.add(price);

    // The "Add to Cart" link.
    HTML cartLink = 
      new HTML("<a href='javascript:;'>Add to Cart</a>");
    cartLink.setStyleName("gridstore-ProductBoxCartLink");
    panel.add(cartLink);

    panel.setStyleName("gridstore-ProductBoxPanel");
    panel.setSpacing(2);

    popupProductImage.setStyleName("gridstore-ProductBoxImage");
    popupFocusPanel.add(new PopupProductBox(product, popupProductImage));
    
    // The pop-up panel that appears when hovering over
    // the product box.
    popup.add(popupFocusPanel);
    
    outer.addMouseListener(new MouseListenerAdapter() {
      public void onMouseEnter(final Widget sender) {
        popup.setPopupPosition(sender.getAbsoluteLeft() - POPUP_OFFSET_LEFT, 
            sender.getAbsoluteTop() - POPUP_OFFSET_TOP);
        popup.show();
      }
    });

    popupFocusPanel.addMouseListener(new MouseListenerAdapter() {
      public void onMouseLeave(Widget sender) {
        popup.hide();
      }
    });
    
    outer.add(panel);
    initWidget(outer);
  }

  /*
   * Helper method for retrieving the product image and resizing it for
   * proper display.
   */
  private Image getProductImage(Product p) {
    if (p.getImageUrl() == "") {
      return null;
    }
    
    Image image = new Image(p.getImageUrl());
    image.setStyleName("gridstore-ProductBoxImage");
    
    image.setVisible(false);
    
    // A LoadListener is used because the image size
    // is only retrievable after the image loads.
    image.addLoadListener(new LoadListener() {
    
      public void onLoad(Widget widget) {
        Image image = (Image) widget;
        
        // Scale down image size.
        int width = image.getWidth();
        int height = image.getHeight();
        if (width > MAX_IMAGE_WIDTH) {
          double scaleFactor = ((double) MAX_IMAGE_WIDTH / width);
          height *= scaleFactor;
          width = MAX_IMAGE_WIDTH;
        }
        if (height > MAX_IMAGE_HEIGHT) {
          double scaleFactor = ((double) MAX_IMAGE_HEIGHT / height);
          width *= scaleFactor;
          height = MAX_IMAGE_HEIGHT;
        }
        image.setWidth(width + "px");
        image.setHeight(height + "px");
        image.setVisible(true);

        // Set the popup image's size to be same as this image.
        if (popupProductImage != null) {
          popupProductImage.setWidth(width + "px");
          popupProductImage.setHeight(height + "px");
        }
      }

      public void onError(Widget widget) {
        widget.setVisible(false);
      }
      
    });
    
    return image;
  }
}
