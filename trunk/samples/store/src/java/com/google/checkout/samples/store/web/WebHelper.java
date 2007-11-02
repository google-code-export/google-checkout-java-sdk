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

package com.google.checkout.samples.store.web;

import  com.google.checkout.samples.store.model.ModelFacade;
import  com.google.checkout.samples.store.model.Category;
import java.util.Collection;
import java.util.Iterator;

/**
 * Helper class for JSP pages
 * @author inder@google.com (Inderjeet Singh)
 */
public class WebHelper {
  private final ModelFacade mf;
  public WebHelper() {
    this.mf = new ModelFacade();
  }
  public WebHelper(ModelFacade mf) {
    this.mf = mf;
  }
  
  StringBuilder appendMenuJsonForCategory(Category category, StringBuilder sb) {
    sb.append("{label : '").append(category.getName()).append("'");
    if (GuiData.isSelected(category)) {
      sb.append(", selected : true");
    }
    if (category.hasSubCategories()) {
      sb.append(", ");
      appendMenuJsonForCategories(category.getSubCategories(), sb);      
    }
    String action = GuiData.getActionFor(category);
    if (action != null) {
      sb.append(", ").append(action);
    }
    sb.append("}");
    return sb;
  }
  
  StringBuilder appendMenuJsonForCategories(Collection<Category> categories, StringBuilder sb) {
    if (categories != null && categories.size() > 0) {
      sb.append("menu : [");
      for (Iterator<Category> iterator = categories.iterator(); iterator.hasNext(); ) {
        Category category = iterator.next();
        appendMenuJsonForCategory(category, sb);
        if (iterator.hasNext()) {
          sb.append(", ");
        }
      }
      sb.append("]");
    }
    return sb;
  }
  
  public String getJsonForAccordianMenu() {
    String value = getJsonForAccordianMenu(mf.getCategories());
    return value;
  }
  
  String getJsonForAccordianMenu(Collection<Category> categories) {
    StringBuilder sb = new StringBuilder("{");
    appendMenuJsonForCategories(categories, sb);
    sb.append("}");
    return sb.toString();
  }
}
