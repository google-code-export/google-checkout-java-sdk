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

import com.google.checkout.samples.store.model.ModelFacade;
import java.io.*;
import java.net.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.checkout.samples.store.web.CatalogJson;
import com.google.checkout.samples.store.web.WebHelper;

/**
 * Main controller servlet for implementing MVC
 * @author inder@google.com (Inderjeet Singh)
 */
public class ControllerServlet extends HttpServlet {

  private static final Logger logger = Logger.getLogger(ControllerServlet.class.getName());
  private ActionMap actionMap = new ActionMap(WebConstants.CONTROL_URL_PREFIX);
  private ModelFacade mf;

  @Override
  public void destroy() {
    actionMap = null;
  }

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setUpHelperBeans(request);
    Action action = actionMap.get(request);
    if (action != null) {
      String responseUrl = action.process(request, response);
      if (responseUrl != null) {
        logger.log(Level.INFO, " Invoking response URL: " + responseUrl);
        getServletContext().getRequestDispatcher(responseUrl).forward(request, response);
      }
    } else {
      logger.log(Level.SEVERE, "Action for '" + request.getRequestURI() 
              + "' not registered in ControllerServlet!!");
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  private void setUpHelperBeans(HttpServletRequest request) {
    request.setAttribute(WebConstants.REQUEST_ATTR_WEB_HELPER, new WebHelper(mf));
  }
  
  @Override
    public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ServletContext context = getServletContext();
    this.mf = new ModelFacade();

    actionMap.put("/catalog-json.action", new CatalogJson(context, mf));
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  /** 
    * Returns a short description of the servlet.
    */
  public String getServletInfo() {
    return "Short description";
  }
  // </editor-fold>

}
