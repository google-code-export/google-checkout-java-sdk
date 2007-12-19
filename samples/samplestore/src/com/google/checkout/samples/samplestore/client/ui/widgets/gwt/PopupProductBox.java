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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Panel that lays out the data in the popup boxes when rolling over products.
 * 
 * @author Simon Lam (simonlam@google.com)
 */
public class PopupProductBox extends Composite {

  private VerticalPanel panel = new VerticalPanel();
  
  public PopupProductBox(Product product, Image image) {
    if (product == null) {
      return;
    }

    if (image != null) {
      panel.add(image);
      panel.setCellHorizontalAlignment(image, HorizontalPanel.ALIGN_CENTER);
    }
    
    HTML titleLink = 
      new HTML("<a href='javascript:;'>" + product.getTitle() + "</a>");
    
    SimplePanel titlePanel = new SimplePanel();
    titlePanel.add(titleLink);
    titlePanel.setStyleName("gridstore-PopupProductBoxTitlePanel");
    titlePanel.setWidth("100%");
    panel.add(titlePanel);
    
    Label price = new Label(product.getPriceAsString());
    price.setStyleName("gridstore-PopupProductBoxPrice");
    panel.add(price);

    Label description = new Label(product.getDescription());
    description.setStyleName("gridstore-PopupProductBoxDescription");
    panel.add(description);
    
    HTML cartLink = 
      new HTML("<a href='javascript:;'>Add to Cart</a>");
    cartLink.setStyleName("gridstore-PopupProductBoxCartLink");
    panel.add(cartLink);

    panel.setStyleName("gridstore-PopupProductBoxPanel");
    panel.setSpacing(2);
    initWidget(panel);
  }
}
