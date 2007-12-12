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
import com.google.checkout.samples.samplestore.client.Category;
import com.google.checkout.samples.samplestore.client.BaseFeedRetriever;
import com.google.checkout.samples.samplestore.client.Inventory;
import com.google.checkout.samples.samplestore.client.JSONParser;
import com.google.checkout.samples.samplestore.client.Product;
import com.google.checkout.samples.samplestore.client.ui.widgets.gwt.ProductBox;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GridStore implements EntryPoint, BaseFeedListener, 
HistoryListener {
  public static final int NUM_ROWS = 4;
  public static final int NUM_COLS = 4;
  
  private BaseFeedRetriever feed = new BaseFeedRetriever();
  private VerticalPanel mainPanel = new VerticalPanel();
  private HorizontalPanel navBar = new HorizontalPanel();
  private HorizontalPanel horzPanel = new HorizontalPanel();
  private VerticalPanel categoryPanel = new VerticalPanel();
  private Inventory inventory;
  private Grid grid = new Grid(NUM_ROWS, NUM_COLS);
  
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    feed.registerListener(this);
    feed.fetchProductsFromBase("2828467");      // our test account
    //feed.fetchProductsFromBase("1161353");    // buy.com
    History.addHistoryListener(this);
    initializeMainForm();
  }

  /**
   * Initialize the main form's layout and content.
   */
  public void initializeMainForm() {
    RootPanel.get().add(mainPanel);
    mainPanel.add(navBar);
    mainPanel.add(horzPanel);
    horzPanel.add(categoryPanel);
    horzPanel.add(grid);
  }
  
  public void handleResponse(JSONObject jsonObj) {
    Inventory inventory = JSONParser.parseJSON(jsonObj);
    
    if (inventory != null) {
      this.inventory = inventory;
      
      //navBar.add(new Hyperlink("Home", ""));
      onHistoryChanged("");
      
//      Collection topLevelCategories = 
//        inventory.getRootCategory().getSubCategories();
//      
//      Iterator it = topLevelCategories.iterator();
//      while (it.hasNext()) {
//        Category category = (Category) it.next();
//        Hyperlink link = new Hyperlink(category.getName(), category.getName());
//        categoryPanel.add(link);
//      }
    }
  }
  
  public void onHistoryChanged(String categorySelected) {
    navBar.clear();
    grid.clear();
    categoryPanel.clear();
    
    navBar.add(new Hyperlink("Home", ""));
    Category currCategory = inventory.getRootCategory();
    String linkCategory = "";
    
    // refresh navbar
    if (!categorySelected.equals("")) {
      String[] categoryHierarchy = categorySelected.split(";");
      
      for (int i=0; i < categoryHierarchy.length; i++) {
        linkCategory += categoryHierarchy[i] + ";";
        Hyperlink link = new Hyperlink(categoryHierarchy[i], linkCategory);
        navBar.add(new Label(">"));
        navBar.add(link);
        
        currCategory = currCategory.getSubCategory(categoryHierarchy[i]);        
      }
    }
    
    // refresh side menu
    Collection menuCategories = currCategory.getSubCategories();
    if (menuCategories == null) {
      // just display the grid, don't display the left menu bar
    } else {
      Iterator it = menuCategories.iterator();
      while (it.hasNext()) {
        Category category = (Category) it.next();
        Hyperlink link = new Hyperlink(category.getName(), 
            linkCategory + category.getName());
        categoryPanel.add(link);
      }
    }
    
    // refresh products
    List products = inventory.getProductsInCategory(currCategory);
    for (int i = 0; i < products.size(); i++) {
      ProductBox productBox = new ProductBox((Product) products.get(i));
      grid.setWidget(i / NUM_ROWS, i % NUM_COLS, productBox);
      if ((i + 1) == NUM_ROWS*NUM_COLS) {
        break;
      }
    }
  }
}
