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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

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
  private int numRows;
  private int numCols;
  
  public ProductGrid(int rows, int columns) {
    numRows = rows;
    numCols = columns;
    
    grid = new Grid(rows, columns);
    grid.setStyleName("gridstore-ProductGridGrid");
    grid.setCellSpacing(0);
    grid.setCellPadding(0);
    
    panel.setStyleName("gridstore-ProductGridPanel");
    panel.add(grid);
    panel.setCellHorizontalAlignment(grid, HorizontalPanel.ALIGN_LEFT);
    initWidget(panel);
  }
  
  public void setCategory(Category category) {
    grid.clear();
    
    List products = new ArrayList();
    if (category == null) {
      products.addAll(GridStore.get().getInventory().getAllProducts());
    } else {
      products.addAll(GridStore.get().getInventory().getProductsInCategory(category));
    }
    
    for (int i = 0; i < products.size(); i++) {
      // Break when grid capacity is reached.
      if (i >= numRows * numCols) {
        break;
      }
      
      Product product = (Product) products.get(i);
      addProduct(product, i);
    }
  }
  
  private void addProduct(Product product, int productNum) {
    ProductBox productBox = new ProductBox(product);
    
    int row = productNum / numCols;
    int col = productNum % numCols;
    grid.setWidget(row, col, productBox);
  }
}
