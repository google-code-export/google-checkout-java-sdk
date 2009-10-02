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
import com.google.checkout.samples.samplestore.client.ui.GridStore;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.Collection;
import java.util.Iterator;

/** 
 * The category menu, which contains links to sub-categories of the selected
 * category.
 * 
 * @author Tony Lo (tonylo@google.com)
 */
public class CategoryMenu extends Composite {
  private VerticalPanel panel = new VerticalPanel();
  
  public CategoryMenu() {
    panel.setStyleName("gridstore-CategoryMenuPanel");
    initWidget(panel);
  }
  
  public void setCategory(Category category) {
    panel.clear();
    
    addMenuHeader(category);
    
    Collection menuCategories;
    if (category == null) {
      menuCategories = GridStore.get().getInventory().getTopLevelCategories();
    } else {
      menuCategories = category.getSubCategories();
    }
    
    if (menuCategories != null) {
      for (Iterator it = menuCategories.iterator(); it.hasNext(); ) {
        Category subCategory = (Category) it.next();
        addMenuLink(subCategory);
      }
    }
  }
  
  private void addMenuHeader(Category category) {
    String categoryName = 
      (category != null ? category.getName() : "All Products");
      
    Label label = new Label("Shop " + categoryName);
    label.setStyleName("gridstore-CategoryMenuHeader");
    
    panel.add(label);
  }
  
  private void addMenuLink(Category category) {
    if (category == null) {
      return;
    }
    
    Hyperlink link = new Hyperlink(category.getName(), 
        GridStore.get().getTokenConverter().getTokenFromCategory(category));

    // We need to wrap the link with another panel. Otherwise, GWT includes
    // the entire table row in the link.
    HorizontalPanel linkPanel = new HorizontalPanel();
    linkPanel.setStyleName("gridstore-CategoryMenuLinkPanel");
    linkPanel.add(link);
    
    panel.add(linkPanel);
  }
}
