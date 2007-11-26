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

package com.google.checkout.web;

import com.google.checkout.CheckoutException;
import com.google.checkout.CheckoutSystemException;
import com.google.checkout.MerchantInfo;

import com.google.checkout.util.CheckoutXmlProcessor;
import com.google.checkout.util.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class CheckoutHandlerServlet extends HttpServlet {
    protected MerchantInfo merchantInfo;
    
    private CheckoutXmlProcessor processor;
    private CheckoutServletInitializer initializer;
    
    public void init(ServletConfig config) throws ServletException {
      super.init(config);
      initializer = new CheckoutServletInitializer();
      initializer.initialize(this, config);
    }
      
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
        "This REST Web service accepts request only through the HTTP POST "
            + "method. Your request was denied because it was sent through "
            + "HTTP GET!");
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      try {
        String auth = request.getHeader("Authorization");
        merchantInfo =
          (MerchantInfo) getServletContext().getAttribute(
              WebConstants.MERCHANT_INFO_KEY);
        if (auth == null || !auth.equals("Basic " + merchantInfo.getHttpAuth())) {
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
              "Authentication Failed.");
          return;
        } 

        String messageBody = getMessageBody(request.getInputStream());
        Document xmlDocument = Utils.newDocumentFromString(messageBody);
        dispatch(xmlDocument, response);
      } catch (CheckoutException ex) {
        ex.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          ex.getMessage());
      } catch (CheckoutSystemException ex) {
        ex.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
          ex.getMessage());        
      }
    }
    
    /**
     * Retrieves the xml string from the request
     * 
     * @param inputStream
     * @return
     * @throws java.io.IOException
     */
    private String getMessageBody(InputStream inputStream)
        throws IOException {

      BufferedReader reader =
          new BufferedReader(new InputStreamReader(inputStream));
      StringBuffer xml = new StringBuffer();
      String line;

      while ((line = reader.readLine()) != null) {
        xml.append(line + "\n");
      }
      reader.close();

      return xml.toString();
    }
    
    public void dispatch(Document xmlDocument, HttpServletResponse response) {      
      try {
        Document resultDocument = processor.process(xmlDocument);
        
        PrintWriter out = response.getWriter();
        out.print(Utils.documentToString(resultDocument));
      } catch (IOException ex) {
        throw new CheckoutSystemException(ex);
      } catch (CheckoutException ex) {
        throw new CheckoutSystemException(ex);
      }
    }
    
    public void setCheckoutXmlProcessor(CheckoutXmlProcessor processor) {
      this.processor = processor;
    }
}
