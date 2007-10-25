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

import com.google.checkout.samples.store.model.Category;

/**
 * GUI data associated with the data model.
 * @author inder@google.com (Inderjeet Singh)
 */
public class GuiData {

  public static String getActionFor(Category category) {
    if (category.getName().equals("Turtles")) {
      return "action : { topic: '/controller', message: { value : 'turtles'}}";
    } else if (category.getName().equals("Birds 0")) {
      return "action : { topic: '/controller', message: { value : 'birds0'}}";
    } else if (category.getName().equals("Birds 1")) {
      return "action : { topic: '/controller', message: { value : 'birds1'}}";
    }
    return null;
  }

  public static boolean isSelected(Category category) {
    return category.getName().equals("Birds");
  }
}
