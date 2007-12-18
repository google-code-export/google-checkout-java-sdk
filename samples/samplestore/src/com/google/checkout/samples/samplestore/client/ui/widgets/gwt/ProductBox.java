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
import com.google.gwt.user.client.Timer;
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

  private static final int LOAD_DELAY = 200;
  private static final int MAX_IMAGE_WIDTH = 200;
  private static final int MAX_IMAGE_HEIGHT = 125;
  
  private PopupPanel popup = new PopupPanel();
  private FocusPanel popupFocusPanel = new FocusPanel();
  
  private VerticalPanel panel = new VerticalPanel();
  private FocusPanel outer = new FocusPanel();
  
  private Timer loadTimer;
  
  public ProductBox(Product product) {
    if (product == null) {
      return;
    }
    
    Image image = getProductImage(product);
    if (image != null) {
      panel.add(image);
      panel.setCellHorizontalAlignment(image, HorizontalPanel.ALIGN_CENTER);
    }
    
    HTML titleLink = 
      new HTML("<a href='javascript:;'>" + product.getTitle() + "</a>");
    panel.add(titleLink);

    Label price = new Label(product.getPriceAsString());
    price.setStyleName("gridstore-ProductBoxPrice");
    panel.add(price);

    HTML cartLink = 
      new HTML("<a href='javascript:;'>Add to Cart</a>");
    cartLink.setStyleName("gridstore-ProductBoxCartLink");
    panel.add(cartLink);

    panel.setStyleName("gridstore-ProductBoxPanel");
    panel.setSpacing(2);
    
    popupFocusPanel.add(new PopupProductBox(product));
    
    // The pop-up panel that appears when hovering over
    // the product box.
    popup.add(popupFocusPanel);
    
    outer.addMouseListener(new MouseListenerAdapter() {
      public void onMouseEnter(final Widget sender) {
        loadTimer = new Timer() {
          public void run() {
            popup.setPopupPosition(sender.getAbsoluteLeft() - 25, sender
                .getAbsoluteTop() - 25);
            popup.show();
          }
        };
        loadTimer.schedule(LOAD_DELAY);
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
          //height = (new Double(height * scaleFactor)).intValue();
          width = MAX_IMAGE_WIDTH;
        }
        if (height > MAX_IMAGE_HEIGHT) {
          double scaleFactor = ((double) MAX_IMAGE_HEIGHT / height);
          //width = (new Double(width * scaleFactor)).intValue();
          width *= scaleFactor;
          height = MAX_IMAGE_HEIGHT;
        }
        image.setWidth(width + "px");
        image.setHeight(height + "px");
        image.setVisible(true);
      }
    
      public void onError(Widget image) {
        //image.setVisible(false);
      }
      
    });
    
    return image;
  }
}
