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

package com.google.checkout.samples.store.web.mvc;

import com.google.checkout.samples.store.web.*;
import com.google.checkout.samples.store.model.ModelFacade;
import com.google.checkout.samples.store.model.Category;
import com.google.checkout.samples.store.model.DummyData;
import com.google.checkout.samples.store.model.ProductList;
import com.google.checkout.samples.store.web.mvc.Action;
import com.google.checkout.samples.store.web.mvc.WebConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action that outputs the catalog data in JSON
 * @author inder@google.com (Inderjeet Singh)
 */
public class CatalogJson implements Action {

  private static final Logger logger = Logger.getLogger(CatalogJson.class.getName());
  private ServletContext context;
  private final ModelFacade mf;

  public CatalogJson(ServletContext context, ModelFacade mf) {
    this.context = context;
    this.mf = mf;
  }

  public String process(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.setHeader("Cache-Control", "no-cache");
    PrintWriter out = response.getWriter();
    try {
      String categoryString = request.getParameter(WebConstants.CATEGORY);

      Category category = Category.getCategory(categoryString);
      // TODO: remove this
      if (categoryString == null) {
        category = DummyData.BIRDS0_CATEGORY;
      }
      if (category != null) {
        ProductList products = mf.getProductsFor(category);
        String json = JsonConverter.toJson(products);
        logger.log(Level.INFO, "Json: " + json);
        out.write(json);
      }
      return null;
    } finally {
      if (out != null) {
        out.close();
      }
    }
  }
}
