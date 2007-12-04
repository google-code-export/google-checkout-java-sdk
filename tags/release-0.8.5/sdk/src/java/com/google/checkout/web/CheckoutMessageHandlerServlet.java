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
import com.google.checkout.MerchantInfo;
import com.google.checkout.CheckoutSystemException;
import com.google.checkout.handlers.MessageHandler;
import com.google.checkout.util.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author simonjsmith
 * @author Inderjeet Singh (inder@google.com)
 */
public class CheckoutMessageHandlerServlet extends
    javax.servlet.http.HttpServlet {

  private static final String DEFAULT_HANDLER_TYPE = "notification-handler";

  private final HashMap mhTable = new HashMap();
  
  /**
   * Overrides servlet method to load notification processor configuration from
   * web.xml
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    String handlerType = config.getInitParameter("handler-type");
    if (handlerType == null) {
      handlerType = DEFAULT_HANDLER_TYPE;
    }

    ServletContext context = config.getServletContext();
    String fileName = context.getInitParameter("checkout-config-file");
    InputStream is = context.getResourceAsStream(fileName);
    if (is == null) { // try default path
      fileName = "/WEB-INF/checkout-config.xml";
      is = context.getResourceAsStream(fileName);
    }
    if (is == null) {
      throw new IllegalArgumentException(
          "web.xml must have <checkout-config-file> init parameter!");
    }
     
    try {
      Document doc = Utils.newDocumentFromInputStream(is);
      readAndConfigureHandlers(doc, handlerType);
    } catch (CheckoutException ex) {
      throw new CheckoutSystemException("Unable to initialize servlet.");
    }
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
        "This REST Web service accepts request only through the HTTP POST "
            + "method. Your request was denied because it was sent through "
            + "HTTP GET!");
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    try {
      String auth = request.getHeader("Authorization");
      MerchantInfo mi =
          (MerchantInfo) getServletContext().getAttribute(
              WebConstants.MERCHANT_INFO_KEY);
      if (auth == null || !auth.equals("Basic " + mi.getHttpAuth())) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
            "Authentication Failed.");
        return;
      } 

      String notification = getNotificationBody(request.getInputStream());
      String ret = dispatch(mi, notification);

      PrintWriter out = response.getWriter();
      out.print(ret);

    } catch (CheckoutException ex) {
      ex.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  /**
   * Overrides base class method to load the configuration from web.xml
   * deployment descriptor
   * 
   * @param mi The merchant information
   * @param message The checkout message to process
   * @return The string result from processing the checkout message
   * @throws CheckoutException if there was an
   * error dispatching the processing the merchant's information
   */
  protected String dispatch(MerchantInfo mi, String message) throws CheckoutException {
    MessageHandler mh = getMessageHandler(message);
    if (mh != null) {
      return mh.process(mi, message);
    }
    return null;
  }

  private MessageHandler getMessageHandler(String message) {
    for (Iterator it = mhTable.keySet().iterator(); it.hasNext();) {
      String key = (String) it.next();
      if (message.indexOf(key) > -1) {
        return (MessageHandler) mhTable.get(key);
      }
    }
    return null;
  }

  private void readAndConfigureHandlers(Document doc, String handlerType) 
    throws CheckoutException {
    NodeList elements = doc.getElementsByTagName(handlerType);
    
    String errorMsg = "";
    
    for (int i = 0; i < elements.getLength(); ++i) {
      try {
        Element element = (Element) elements.item(i);
        String className =
            Utils.getElementStringValue(doc, element, "handler-class").trim();
        String target =
            Utils.getElementStringValue(doc, element, "message-type").trim();
        Class c = Class.forName(className);
        Object obj = c.newInstance();
        mhTable.put(target, obj);
      } catch (ClassNotFoundException ex) {
        errorMsg = ex.getMessage();
      } catch (SecurityException ex) {
        errorMsg = ex.getMessage();
      } catch (InstantiationException ex) {
        errorMsg = ex.getMessage();
      } catch (IllegalAccessException ex) {
        errorMsg = ex.getMessage();
      } catch (IllegalArgumentException ex) {
        errorMsg = ex.getMessage();
      }
    }
    
    if (!errorMsg.equals("")) {
      throw new CheckoutException(errorMsg);
    }
  }

  private String getNotificationBody(InputStream inputStream)
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
}
