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
import com.google.checkout.EnvironmentType;
import com.google.checkout.MerchantInfo;
import com.google.checkout.CheckoutSystemException;
import com.google.checkout.handlers.CompositeNotificationHandler;
import com.google.checkout.handlers.MerchantCallbackHandler;
import com.google.checkout.handlers.MessageHandler;
import com.google.checkout.handlers.NotificationHandler;
import com.google.checkout.notification.CompositeNotificationParser;
import com.google.checkout.util.CallbackXmlProcessor;
import com.google.checkout.util.NotificationXmlProcessor;
import com.google.checkout.util.Utils;

import java.io.InputStream;

import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This servlet context listener sets up attributed that need to be present in
 * the servlet context
 * 
 * @author inder
 */
public class NewConfigurationListener implements ServletContextListener {
  private HashMap messageHandlers = new HashMap();

  /** Creates a new instance of ConfigurationListener */
  public NewConfigurationListener() {
  }

  public void contextInitialized(ServletContextEvent sce) {
    ServletContext context = sce.getServletContext();
    
    String fileName = context.getInitParameter("checkout-config-file");
    InputStream is = context.getResourceAsStream(fileName);
    if (is == null) { // try default path
      fileName = "/WEB-INF/checkout-config.xml";
      is = context.getResourceAsStream(fileName);
    }
    if (is == null) {
      throw new IllegalArgumentException("web.xml must have "
          + "<checkout-config-file> init parameter!");
    }
    
    try {
      Document doc = Utils.newDocumentFromInputStream(is);
      context.setAttribute(WebConstants.MERCHANT_INFO_KEY,
        extractMerchantInfo(doc));
      
      // create xml processor for notifications
      CompositeNotificationParser notificationParser = 
        new CompositeNotificationParser();
      CompositeNotificationParser
        .registerDefaultNotificationParsers(notificationParser);
      
      context.setAttribute("notification-xml-processor", 
        new NotificationXmlProcessor(
        (MerchantInfo)context.getAttribute(WebConstants.MERCHANT_INFO_KEY), 
        notificationParser, readAndConfigureNotificationHandlers(doc), 
        messageHandlers));
      
      // create xml processor for callback
      context.setAttribute("callback-xml-processor", 
        new CallbackXmlProcessor(
        (MerchantInfo)context.getAttribute(WebConstants.MERCHANT_INFO_KEY),
        readAndConfigureCallbackHandlers(doc), messageHandlers));
    } catch (CheckoutException ex) {
      ex.printStackTrace();
      throw new CheckoutSystemException("Could not initialize context.");
    }
  }

  private MerchantInfo extractMerchantInfo(Document doc) {
    NodeList elements = doc.getElementsByTagName("merchant-info");
    if (elements.getLength() != 1) {
      throw new IllegalArgumentException(
          "checkout-config.xml must have exactly"
              + " one <merchant-info> element!");
    }
    Element miElement = (Element) elements.item(0);

    String merchantId =
        Utils.getElementStringValue(doc, miElement, "merchant-id").trim();
    String merchantKey =
        Utils.getElementStringValue(doc, miElement, "merchant-key").trim();
    String env = Utils.getElementStringValue(doc, miElement, "env").trim();
    String currencyCode =
        Utils.getElementStringValue(doc, miElement, "currency-code").trim();
    String sandboxRoot =
        Utils.getElementStringValue(doc, miElement, "sandbox-root").trim();
    String productionRoot =
        Utils.getElementStringValue(doc, miElement, "production-root").trim();
    String checkoutCommand =
        Utils.getElementStringValue(doc, miElement, "checkout-command").trim();
    String merchantCheckoutCommand =
        Utils
            .getElementStringValue(doc, miElement, "merchant-checkout-command")
            .trim();
    String requestCommand =
        Utils.getElementStringValue(doc, miElement, "request-command").trim();

    String checkoutUrl = "";
    String merchantCheckoutUrl = "";
    String requestUrl = "";

    if (EnvironmentType.Sandbox.equals(env)) {
      checkoutUrl =
          sandboxRoot + "/" + checkoutCommand + "/Merchant/" + merchantId;
      merchantCheckoutUrl =
          sandboxRoot + "/" + merchantCheckoutCommand + "/Merchant/"
              + merchantId;
      requestUrl =
          sandboxRoot + "/" + requestCommand + "/Merchant/" + merchantId;
    } else if (EnvironmentType.Production.equals(env)) {
      checkoutUrl =
          productionRoot + "/" + checkoutCommand + "/Merchant/" + merchantId;
      merchantCheckoutUrl =
          productionRoot + "/" + merchantCheckoutCommand + "/Merchant/"
              + merchantId;
      requestUrl =
          productionRoot + "/" + requestCommand + "/Merchant/" + merchantId;
    } else {
      throw new CheckoutSystemException("Env must be one of "
        + EnvironmentType.Sandbox + " or " + EnvironmentType.Production + ".");
    }
    return new MerchantInfo(merchantId, merchantKey, env, currencyCode,
        checkoutUrl, merchantCheckoutUrl, requestUrl);
  }


  public void contextDestroyed(ServletContextEvent sce) {
    // Nothing needs to be done, so ignore
  }
  
  /**
   * Extract the callback handler (the one for MerchantCalculationCallback). This
   * callback will either implement the MessageHandler interface or the 
   * MerchantCallbackInterface. If the class handling the merchant calculation 
   * callback implements the MessageHandler interface, this function will return
   * null and the member variable, oldTypeCallbackHandlers, will be populated
   * (i.e. oldTypeCallbackHandlers.size() == 1).
   * 
   * If the class handling the merchant calculation callback implements the
   * MerchantCallbackInterface, this function will return it.
   * 
   * @param doc The xml document representation of the checkout-config.xml
   * @return The MerchantCallbackHandler if any; otherwise null.
   * @throws com.google.checkout.CheckoutException if there was an error 
   * processing the callback handler.
   */
  private MerchantCallbackHandler readAndConfigureCallbackHandlers(Document doc) 
    throws CheckoutException {  
    MerchantCallbackHandler callbackHandler  = null;
    
    NodeList elements = doc.getElementsByTagName("callback-handler");
    
    for (int i=0; i < elements.getLength(); ++i) {
      try {
        Element element = (Element)elements.item(i);
        
        String target = 
          Utils.getElementStringValue(doc, element, "message-type");
        String className = 
          Utils.getElementStringValue(doc, element, "handler-class");
        
        Class c = Class.forName(className);
        Object obj = c.newInstance();
        
        Class[] interfaces = c.getInterfaces();
        
        // flags to indicate whether a handler class has implemented both the
        // MessageHandler interface and the NotificationHandler/MerchantCalback
        boolean messageHandlerInterface = false;
        boolean callbackHandlerInterface = false;
        
        for (int j=0; j < interfaces.length; ++j) {
          Class tempInterface = interfaces[j];
          if (tempInterface.equals(MessageHandler.class)) {
            messageHandlers.put(target, (MessageHandler)obj);
            messageHandlerInterface = true;
          } else if (tempInterface.equals(MerchantCallbackHandler.class)){
            callbackHandler = (MerchantCallbackHandler)obj;
            callbackHandlerInterface = true;
          }
        }
        
        if (messageHandlerInterface && callbackHandlerInterface) {
          throw new CheckoutSystemException("Handler cannot implement both " + 
          "MessageHandler and MerchantCallbackHandler " + 
          "interfaces");
        }
        
      } catch (ClassNotFoundException ex) {
        throw new CheckoutException(ex);
      } catch (SecurityException ex) {
        throw new CheckoutException(ex);
      } catch (InstantiationException ex) {
        throw new CheckoutException(ex);
      } catch (IllegalAccessException ex) {
        throw new CheckoutException(ex);
      } catch (IllegalArgumentException ex) {
        throw new CheckoutException(ex);
      }
    }

    return callbackHandler;
  }
  
  /**
   * Extract the notification handlers. Each notification handler will either
   * implement the MessageHandler interface or the NotificationHandler interface.
   * 
   * If the notification class implements the MessageHandler class, then it
   * will be added to the oldTypeNotificationHandlers HashMap. Otherwise, it will
   * be added as part of the CompositeNotificationHandler.
   * 
   * @param doc The xml document representation of the checkout-config.xml
   * @return The CompositeNotificationHandler if any NotificationHandlers were
   * found; otherwise null.
   * @throws com.google.checkout.CheckoutException if there was an error 
   * processing the notification handlers.
   */
  private NotificationHandler readAndConfigureNotificationHandlers(Document doc) 
    throws CheckoutException {
    CompositeNotificationHandler newTypeNotificationHandlers = 
      new CompositeNotificationHandler();
            
    NodeList elements = doc.getElementsByTagName("notification-handler");

    for (int i=0; i < elements.getLength(); ++i) {
      try {
        Element element = (Element)elements.item(i);

        String target =
          Utils.getElementStringValue(doc, element, "message-type").trim();
        String className =
          Utils.getElementStringValue(doc, element, "handler-class").trim();

        Class c = Class.forName(className);
        Object obj = c.newInstance();

        Class[] interfaces = c.getInterfaces();

        boolean messageHandlerInterface = false;
        boolean notificationHandlerInterface = false;
        
        for (int j=0; j < interfaces.length; ++j) {
          Class tempInterface = interfaces[j];
          if (tempInterface.equals(MessageHandler.class)) {
            messageHandlers.put(target, obj);
            messageHandlerInterface = true;
          } else if (tempInterface.equals(NotificationHandler.class)) {
            newTypeNotificationHandlers.register(target, (NotificationHandler)obj);
            notificationHandlerInterface = true;
          }
        }
        
        if (messageHandlerInterface && notificationHandlerInterface) {
          throw new CheckoutSystemException("Handler cannot implement both " + 
          "MessageHandler and MerchantCallbackHandler " + 
          "interfaces");
        }
        
      } catch (ClassNotFoundException ex) {
        throw new CheckoutException(ex);
      } catch (SecurityException ex) {
        throw new CheckoutException(ex);
      } catch (InstantiationException ex) {
        throw new CheckoutException(ex);
      } catch (IllegalAccessException ex) {
        throw new CheckoutException(ex);
      } catch (IllegalArgumentException ex) {
        throw new CheckoutException(ex);
      }  
    }
    
    return newTypeNotificationHandlers;
  }
}
