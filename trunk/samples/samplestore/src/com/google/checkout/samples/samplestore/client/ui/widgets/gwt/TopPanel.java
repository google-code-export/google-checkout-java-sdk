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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.Stack;

/** 
 * The top panel, which contains the page header and navigation links. 
 * 
 * @author Tony Lo (tonylo@google.com)
 */
public class TopPanel extends Composite {
  private HorizontalPanel navBar = new HorizontalPanel();
  private HorizontalPanel navLinks = new HorizontalPanel();
  
  private Hyperlink topLink = new Hyperlink("All Products", "");
  
  public TopPanel(String title) {
    // The page header containing the name of the store.
    Label header = new Label(title);
    header.setStyleName("gridstore-TopPanelHeader");
    
    // The header bar containing the header.
    HorizontalPanel headerBar = new HorizontalPanel();
    headerBar.setStyleName("gridstore-TopPanelHeaderBar");
    headerBar.add(header);

    // The navigation links for navigating to other category levels.
    navLinks.setStyleName("gridstore-TopPanelNavLinks");
    
    // The navigation bar containing the navigation links.
    navBar.add(navLinks);
    navBar.setCellHorizontalAlignment(navLinks, HorizontalPanel.ALIGN_LEFT);
    navBar.setStyleName("gridstore-TopPanelNavBar");
    
    // The outer panel containing the header bar and the navigation bar.
    VerticalPanel outer = new VerticalPanel();
    outer.add(headerBar);
    outer.add(navBar);
    
    initWidget(outer);
  }
  
  public void setCategory(Category category) {
    navLinks.clear();
    
    // Add top-level "All Products" link.
    addLink(null);
    
    Stack categoryStack = new Stack();
    while (category != null) {
      categoryStack.add(category);
      category = category.getParent();
    }
    
    while (!categoryStack.empty()) {
      category = (Category) categoryStack.pop();
      addLinkSeparator();
      addLink(category);
    }
  }
  
  private void addLink(Category category) {
    if (category == null) {
      navLinks.add(topLink);
    } else {
      Hyperlink link = new Hyperlink(category.getName(), 
          GridStore.get().getTokenConverter().getTokenFromCategory(category));
      navLinks.add(link);
    }
  }
  
  private void addLinkSeparator() {
    Label arrow = new Label(">");
    
    SimplePanel panel = new SimplePanel();
    panel.setStyleName("gridstore-TopPanelNavLinkPanel");
    panel.add(arrow);
    
    navLinks.add(panel);
  }
}
