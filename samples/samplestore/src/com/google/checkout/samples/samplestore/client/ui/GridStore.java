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

package com.google.checkout.samples.samplestore.client.ui;

import com.google.checkout.samples.samplestore.client.BaseFeedListener;
import com.google.checkout.samples.samplestore.client.BaseFeedRetriever;
import com.google.checkout.samples.samplestore.client.Category;
import com.google.checkout.samples.samplestore.client.HistoryTokenConverter;
import com.google.checkout.samples.samplestore.client.Inventory;
import com.google.checkout.samples.samplestore.client.JSONParser;
import com.google.checkout.samples.samplestore.client.ui.widgets.gwt.CategoryMenu;
import com.google.checkout.samples.samplestore.client.ui.widgets.gwt.ProductGrid;
import com.google.checkout.samples.samplestore.client.ui.widgets.gwt.TopPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This sample application demonstrates how to construct a simple storefront
 * that fetches products from Google Base and displays them in a grid display.
 * 
 * @author tonylo@google.com (Tony Lo)
 */
public class GridStore
    implements EntryPoint, BaseFeedListener, HistoryListener {
  
  private static GridStore singleton;
  
  /**
   * Gets the singleton GridStore instance.
   */
  public static GridStore get() {
    return singleton;
  }
  
  public static final int NUM_ROWS = 5;
  public static final int NUM_COLS = 3;
  
//  public static final long BASE_CUSTOMER_ID = 2828467;      // our test account
  public static final long BASE_CUSTOMER_ID = 1161353;  // buy.com
  
  public static final String STORE_NAME = "My Store";
  
  private BaseFeedRetriever feed = new BaseFeedRetriever();
  private Inventory inventory;
  private HistoryTokenConverter tokenConverter;
  
  private TopPanel topPanel = new TopPanel(STORE_NAME);
  private CategoryMenu categoryMenu = new CategoryMenu();
  private VerticalPanel rightPanel = new VerticalPanel();
  private ProductGrid productGrid = new ProductGrid(NUM_ROWS, NUM_COLS); 

  public Inventory getInventory() {
    return inventory;
  }
  
  public HistoryTokenConverter getTokenConverter() {
    return tokenConverter;
  }
  
  /**
   * This entry point method.
   */
  public void onModuleLoad() {
    singleton = this;
    
    feed.registerListener(this);
    feed.fetchProductsFromBase(BASE_CUSTOMER_ID);
    History.addHistoryListener(this);
    
    initializeMainForm();
  }

  /**
   * Initialize the main form's layout and content.
   */
  private void initializeMainForm() {
    topPanel.setWidth("100%");
    rightPanel.add(productGrid);
    
    // Create a dock panel that will contain the title and top panel at
    // the top, the category menu at the right, and the product grid taking
    // the rest.
    DockPanel outer = new DockPanel();
    outer.add(topPanel, DockPanel.NORTH);
    outer.add(categoryMenu, DockPanel.WEST);
    outer.add(rightPanel, DockPanel.WEST);
    outer.setWidth("100%");
    outer.setSpacing(4);
    outer.setCellWidth(rightPanel, "100%");
    
    Window.setMargin("0px");
    
    // Add the outer panel to the RootPanel.
    RootPanel.get().add(outer);
  }
  
  /**
   * Handles the response from the call to BaseFeedRetriever. This is currently 
   * only called once since there is only one call to Base.
   */
  public void handleResponse(JSONObject jsonObj) {
    inventory = JSONParser.parse(jsonObj);
    tokenConverter = new HistoryTokenConverter(inventory);
    
    if (inventory != null) {
      onHistoryChanged("");
    }
  }
  
  public void onHistoryChanged(String token) {
    Category categorySelected = tokenConverter.getCategoryFromToken(token);
    
    topPanel.setCategory(categorySelected);
    categoryMenu.setCategory(categorySelected);
    productGrid.setCategory(categorySelected);
  }
}
