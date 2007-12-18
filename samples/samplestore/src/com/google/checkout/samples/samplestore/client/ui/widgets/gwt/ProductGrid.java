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

import com.google.checkout.samples.samplestore.client.Category;
import com.google.checkout.samples.samplestore.client.Product;
import com.google.checkout.samples.samplestore.client.ui.GridStore;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.List;

/** 
 * The products grid, which contains a grid display of products
 * from a certain category.
 * 
 * @author Tony Lo (tonylo@google.com)
 */
public class ProductGrid extends Composite {
  private VerticalPanel panel = new VerticalPanel();
  
  private Grid grid;
  private DockPanel pageLinks = new DockPanel();
  private HTML prevLink = new HTML("<a href='javascript:;'>< Previous</a>");
  private HTML nextLink = new HTML("<a href='javascript:;'>Next ></a>");
  
//  private int numRows;
  private int numCols;
  private int itemsPerPage;
  private int currPage;
  
  private List products;
  
  public ProductGrid(int rows, int columns) {
//    numRows = rows;
    numCols = columns;
    itemsPerPage = rows * columns;
    
    grid = new Grid(rows, columns);
    grid.setStyleName("gridstore-ProductGridGrid");
    grid.setCellSpacing(0);
    grid.setCellPadding(0);
    
    // Add click listeners for pagination links.
    prevLink.addClickListener(new ClickListener() {
      public void onClick(Widget arg0) {
        setPage(currPage - 1);
      }
    });
    nextLink.addClickListener(new ClickListener() {
      public void onClick(Widget arg0) {
        setPage(currPage + 1);
      }
    });
    
    pageLinks.setStyleName("gridstore-ProductGridPageLinks");
    pageLinks.setWidth("100%");
    
    panel.setStyleName("gridstore-ProductGridPanel");
    panel.setWidth("100%");
    panel.add(grid);
    panel.add(pageLinks);
    panel.setCellHorizontalAlignment(grid, HorizontalPanel.ALIGN_LEFT);
    panel.setCellHorizontalAlignment(pageLinks, HorizontalPanel.ALIGN_CENTER);
    initWidget(panel);
  }
  
  public void setCategory(Category category) {products = new ArrayList();
    if (category == null) {
      products.addAll(GridStore.get().getInventory().getAllProducts());
    } else {
      products.addAll(
          GridStore.get().getInventory().getProductsInCategory(category));
    }
    
    // Display the first page of products
    setPage(0);
  }
  
  private void setPage(int page) {
    grid.clear();
    pageLinks.clear();

    currPage = page;
    int startNum = page * itemsPerPage;
    
    for (int i = 0; i < itemsPerPage; i++) {
      // Break when last product is reached.
      if (startNum + i >= products.size()) {
        break;
      }
      Product product = (Product) products.get(startNum + i);
      addProduct(product, i);
    }
    
    if (startNum > 0) {
      pageLinks.add(prevLink, DockPanel.WEST);
      pageLinks.setCellHorizontalAlignment(
          prevLink, HorizontalPanel.ALIGN_LEFT);
    }
    
    if (startNum + itemsPerPage < products.size()) {
      pageLinks.add(nextLink, DockPanel.EAST);
      pageLinks.setCellHorizontalAlignment(
          nextLink, HorizontalPanel.ALIGN_RIGHT);
    }
  }
  
  private void addProduct(Product product, int productNum) {
    ProductBox productBox = new ProductBox(product);
    
    int row = productNum / numCols;
    int col = productNum % numCols;
    grid.setWidget(row, col, productBox);
  }
}
