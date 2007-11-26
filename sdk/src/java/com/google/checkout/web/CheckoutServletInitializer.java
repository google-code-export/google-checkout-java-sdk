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

import com.google.checkout.util.CheckoutXmlProcessor;

import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class CheckoutServletInitializer {    
  public static final String DEFAULT_HANDLER_TYPE = "notification-handler";
  
  /**
   * 
   * @param servlet
   * @param config
   */
  public void initialize(CheckoutHandlerServlet servlet,
    ServletConfig config) {    

    // the value read from web.xml determines if the servlet handles 
    // notifications or callbacks
    String handlerType = config.getInitParameter("handler-type");
    if (handlerType == null) {
      handlerType = DEFAULT_HANDLER_TYPE;
    }
    
    InputStream is = 
      getInputStreamFromServletContext(config.getServletContext());
    CheckoutXmlProcessor xmlProcessor = 
      getXmlProcessorFromServletContext(config.getServletContext(), handlerType);
    servlet.setCheckoutXmlProcessor(xmlProcessor);
  }
  
  /**
   * 
   * @param context
   * @return
   */
  private InputStream getInputStreamFromServletContext(ServletContext context) {
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
    return is;
  }
  
  /**
   * Retrieves the corresponding XmlProcessor depending on whether the servlet
   * is handling notifications or callbacks
   * 
   * @param context The servlet context
   * @param handlerType Either 'notification-handler' or 'callback-handler'
   * @return A CheckoutXmlProcesor to process notifications if the handler type
   * is 'notification-handler'; otherwise a CheckoutXmlProcesor to handle 
   * callbacks.
   */
  private CheckoutXmlProcessor getXmlProcessorFromServletContext(ServletContext
    context, String handlerType) {    
    if (handlerType.equals(DEFAULT_HANDLER_TYPE)) {
      return (CheckoutXmlProcessor)context.getAttribute("notification-xml-processor");
    } else {
      return (CheckoutXmlProcessor)context.getAttribute("callback-xml-processor");
    }
  }
}
