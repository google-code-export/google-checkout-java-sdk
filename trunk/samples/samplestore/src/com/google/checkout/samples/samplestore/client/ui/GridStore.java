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
import com.google.checkout.samples.samplestore.client.Product;
import com.google.checkout.samples.samplestore.client.ui.widgets.gwt.ProductBox;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GridStore 
    implements EntryPoint, BaseFeedListener, HistoryListener {
  public static final int NUM_ROWS = 4;
  public static final int NUM_COLS = 4;
  
  public static final long BASE_CUSTOMER_ID = 2828467;      // our test account
//  public static final long BASE_CUSTOMER_ID = "1161353";  // buy.com
  
  private BaseFeedRetriever feed = new BaseFeedRetriever();
  private Inventory inventory;
  private HistoryTokenConverter tokenConverter;
  
  private VerticalPanel mainPanel = new VerticalPanel();
  private HorizontalPanel navBar = new HorizontalPanel();
  private HorizontalPanel horzPanel = new HorizontalPanel();
  private VerticalPanel categoryPanel = new VerticalPanel();
  private Grid grid = new Grid(NUM_ROWS, NUM_COLS);
  
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    feed.registerListener(this);
    feed.fetchProductsFromBase(BASE_CUSTOMER_ID);
    History.addHistoryListener(this);
    initializeMainForm();
  }

  /**
   * Initialize the main form's layout and content.
   */
  private void initializeMainForm() {
    RootPanel.get().add(mainPanel);
    mainPanel.add(navBar);
    mainPanel.add(horzPanel);
    horzPanel.add(categoryPanel);
    horzPanel.add(grid);
  }
  
  /**
   * Handles the response from the call to BaseFeedRetriever.
   * (currently only called once because there is only one call to Base)
   */
  public void handleResponse(JSONObject jsonObj) {
    inventory = JSONParser.parse(jsonObj);
    tokenConverter = new HistoryTokenConverter(inventory);
    
    if (inventory != null) {
      onHistoryChanged("");
    }
  }
  
  public void onHistoryChanged(String historyToken) {
    Category categorySelected = 
      tokenConverter.getCategoryFromToken(historyToken);
    
    // refresh navigation bar
    loadNavBar(categorySelected);

    // refresh menu
    loadCategoriesMenu(categorySelected);
    
    // refresh products
    loadProductsGrid(categorySelected);
  }
  
  /**
   * Refresh the navigation bar
   */
  private void loadNavBar(Category category) {
    navBar.clear();
    navBar.add(new Hyperlink("All Products", ""));
    
    Stack categoryStack = new Stack();
    while (category != null) {
      categoryStack.add(category);
      category = category.getParent();
    }
    
    while (!categoryStack.empty()) {
      category = (Category) categoryStack.pop();
      navBar.add(new Label(">"));
      navBar.add(new Hyperlink(category.getName(), 
          tokenConverter.getTokenFromCategory(category)));
    }
  }
  
  /**
   * Refresh the menu with the sub-categories of the given category
   */
  private void loadCategoriesMenu(Category category) {
    categoryPanel.clear();
    
    Collection menuCategories;
    if (category == null) {
      menuCategories = inventory.getTopLevelCategories();
    } else {
      menuCategories = category.getSubCategories();
    }
    
    if (menuCategories != null) {
      for (Iterator it = menuCategories.iterator(); it.hasNext(); ) {
        Category subCategory = (Category) it.next();
        categoryPanel.add(new Hyperlink(subCategory.getName(), 
            tokenConverter.getTokenFromCategory(subCategory)));
      }
    }
  }
  
  /**
   * Refresh the products grid
   */
  private void loadProductsGrid(Category category) {
    grid.clear();
    
    List products = new ArrayList();
    if (category == null) {
      products.addAll(inventory.getAllProducts());
    } else {
      products.addAll(inventory.getProductsInCategory(category));
    }
    
    for (int i = 0; i < products.size(); i++) {
      ProductBox productBox = new ProductBox((Product) products.get(i));
      grid.setWidget(i / NUM_ROWS, i % NUM_COLS, productBox);
      if ((i + 1) == NUM_ROWS*NUM_COLS) {
        break;
      }
    }
  }
}
