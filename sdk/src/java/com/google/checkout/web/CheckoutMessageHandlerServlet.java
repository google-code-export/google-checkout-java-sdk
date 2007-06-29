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
import com.google.checkout.handlers.MessageHandler;
import com.google.checkout.MerchantConstants;
import com.google.checkout.MerchantConstantsFactory;
import com.google.checkout.util.Utils;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * @author simonjsmith
 * @author Inderjeet Singh (inder@google.com)
 */
public class CheckoutMessageHandlerServlet extends javax.servlet.http.HttpServlet {
  
    private static final String DEFAULT_HANDLER_TYPE = "notification-handler";
    
  /** Overrides servlet method to load notification processor configuration from web.xml */
  public void init(ServletConfig config) throws ServletException {
    ServletContext context = config.getServletContext();
    String fileName = context.getInitParameter("checkout-config-file");
    InputStream is = context.getResourceAsStream(fileName);
    String handlerType = config.getInitParameter("handler-type");
    if (handlerType == null) {
        handlerType = DEFAULT_HANDLER_TYPE;
    }
    if (is == null) { // try default path
      fileName = "/WEB-INF/checkout-config.xml";
      is = context.getResourceAsStream(fileName);
    }
    if (is != null) {
      readAndConfigure(is, handlerType);
    }
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, 
        "This REST Web service accepts request only through the HTTP POST method. Your request was denied because it was sent through HTTP GET!");
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    
    MerchantConstants mc = MerchantConstantsFactory.getMerchantConstants();
    
    try {
      String auth = request.getHeader("Authorization");
      if (auth == null || !auth.equals("Basic " + mc.getHttpAuth())) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
            "Authentication Failed.");
        return;
      }
      
      String notification = getNotificationBody(request.getInputStream());
      String ret = dispatch(notification);
      
      PrintWriter out = response.getWriter();
      out.print(ret);
      
    } catch (Exception ex) {
      ex.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex
          .getMessage());
    }
  }
  
  
  /** Overrides base class method to load the configuration from web.xml deployment descriptor */
  protected String dispatch(String message) throws Exception {
    MessageHandler mh = getMessageHandler(message);
    if (mh != null) {
      return mh.process(MerchantConstantsFactory.getMerchantConstants(),
          message);
    }
    return null;
  }
  
  private MessageHandler getMessageHandler(String message) {
    for (Iterator it=mhTable.keySet().iterator(); it.hasNext(); ) {
      String key = (String) it.next();
      if (message.indexOf(key) > -1) {
        return (MessageHandler) mhTable.get(key);
      }
    }
    return null;
  }
  private void readAndConfigure(InputStream is, String handlerType) {
    Document doc = Utils.newDocumentFromInputStream(is);
    NodeList elements = doc.getElementsByTagName(handlerType);
    for (int i = 0; i < elements.getLength(); ++i) {
      try {
        Element element = (Element) elements.item(i);
        String className = Utils.getElementStringValue(doc, element, "handler-class").trim();
        String target = Utils.getElementStringValue(doc, element, "message-type").trim();
        Class c = Class.forName(className);
        Object obj = c.newInstance();
        mhTable.put(target, obj);
      } catch (ClassNotFoundException e) {
        e.printStackTrace(); // TBD: Fix
      } catch (SecurityException e) {
        e.printStackTrace(); // TBD: Fix
      } catch (InstantiationException e) {
        e.printStackTrace(); // TBD: Fix
      } catch (IllegalAccessException e) {
        e.printStackTrace(); // TBD: Fix
      } catch (IllegalArgumentException e) {
        e.printStackTrace(); // TBD: Fix
      }
    }
  }
  private String getNotificationBody(InputStream inputStream)
  throws IOException {
    
    BufferedReader reader = new BufferedReader(new InputStreamReader(
        inputStream));
    StringBuffer xml = new StringBuffer();
    String line;
    
    while ((line = reader.readLine()) != null) {
      xml.append(line + "\n");
    }
    reader.close();
    
    return xml.toString();
  }
  
  private HashMap mhTable = new HashMap();
}
