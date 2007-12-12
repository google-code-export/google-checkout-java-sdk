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
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Container for a product in the display grid which listens for mouse rollovers.
 * 
 * @author Simon Lam (simonlam@google.com)
 */
public class ProductBox extends FocusPanel {

  private static final int LOAD_DELAY = 200;
  private Product product;
  private PopupPanel popup = new PopupPanel();
  private FocusPanel popupFocusPanel = new FocusPanel();
  //private Timer loadTimer;
  
  public ProductBox(Product product) {
    this.add(new ProductBoxData(product));
    popup.add(popupFocusPanel);
    popupFocusPanel.add(new PopupProductBoxData(product));
    this.addMouseListener(new MouseListenerAdapter() {
      
      public void onMouseEnter(Widget sender) {
        popup.setPopupPosition(sender.getAbsoluteLeft() - 25, sender.getAbsoluteTop() - 25);
        popup.show();
        /**loadTimer = new Timer() {
          public void run() {
              
          }
        };
        loadTimer.schedule(LOAD_DELAY);
        */
      }
    });
    
    popupFocusPanel.addMouseListener(new MouseListenerAdapter() {
      
      public void onMouseLeave(Widget sender) {
        popup.hide();
      }
    });

  }

}
