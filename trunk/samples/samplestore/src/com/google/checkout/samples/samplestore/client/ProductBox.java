// Copyright 2007 Google Inc. All Rights Reserved.

package com.google.checkout.samples.samplestore.client;

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
