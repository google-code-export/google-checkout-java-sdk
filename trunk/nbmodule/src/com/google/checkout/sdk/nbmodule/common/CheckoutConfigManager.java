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

package com.google.checkout.sdk.nbmodule.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Represents a physical checkout-config.xml file, storing information about
 * a merchant and keeping maps of notification and callback handlers.
 */
public class CheckoutConfigManager {
  
  // The actual config file be read from and written to
  private String outputLocation;
  
  // Basic merchant info
  private String merchantId;
  private String merchantKey;
  private String env;
  private String currencyCode;
  private String sandboxRoot;
  private String productionRoot;
  private String checkoutSuffix;
  private String merchantCheckoutSuffix;
  private String requestSuffix;
  
  // The maps which store handlers
  private HashMap notificationHandlers;
  private HashMap callbackHandlers;
  
  public static final String CHECKOUT_CONFIG_LOCATION = "/resources/checkout-config.xml";
  
  /**
   * Creates a new instance of CheckoutConfigManager that loads the data from the
   * default checkout-config.xml
   */
  public CheckoutConfigManager() {
    this(CheckoutConfigManager.class.getResourceAsStream(CHECKOUT_CONFIG_LOCATION));
  }
  
  /**
   * Creates a new instance of CheckoutConfigManager that loads the data from the 
   * specified InputStream of an xml file.
   */
  public CheckoutConfigManager(InputStream input) {
    readContent(input);
  }
  
  /*************************************************************************/
  /*                             FIELD ACCESSORS                           */
  /*************************************************************************/
  
  public String getOutputLocation() {
    return outputLocation;
  }
  
  public void setOutputLocation(String outputLocation) {
    this.outputLocation = outputLocation;
  }
  
  public String getMerchantId() {
    return merchantId;
  }
  
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }
  
  public String getMerchantKey() {
    return merchantKey;
  }
  
  public void setMerchantKey(String merchantKey) {
    this.merchantKey = merchantKey;
  }
  
  public String getEnv() {
    return env;
  }
  
  public void setEnv(String env) {
    this.env = env;
  }
  
  public String getCurrencyCode() {
    return currencyCode;
  }
  
  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getSandboxRoot() {
    return sandboxRoot;
  }

  public String getProductionRoot() {
    return productionRoot;
  }

  public String getCheckoutSuffix() {
    return checkoutSuffix;
  }

  public String getMerchantCheckoutSuffix() {
    return merchantCheckoutSuffix;
  }

  public String getRequestSuffix() {
    return requestSuffix;
  }
  
  /*************************************************************************/
  /*                           MAP ACCESSORS                               */
  /*************************************************************************/
  
  /**
   * @returns The notification handler matched to the specified type; otherwise
   *          null no matching handler is found  
   */
  public Object getNotificationHandler(String type) {
    return notificationHandlers.get(type);
  }
  
  public void setNotificationHandler(String type, String name) {
    if (name.trim().equals("")) {
      name = null;
    }
    notificationHandlers.put(type,name);
  }

  /**
   * @returns The callback handler matched to the specified type; otherwise
   *          null no matching handler is found  
   */
  public Object getCallbackHandler(String type) {
    return callbackHandlers.get(type);
  }
  
  public void setCallbackHandler(String type, String name) {
    if (name.trim().equals("")) {
      name = null;
    }
    callbackHandlers.put(type,name);
  }
  
  /*************************************************************************/
  /*                         UTILITY METHODS                               */
  /*************************************************************************/
  
  private void readMerchantInfo(Element merchantInfo) {
    if (merchantInfo != null) {
      merchantId = read(merchantInfo, "merchant-id");
      merchantKey = read(merchantInfo, "merchant-key");
      env = read(merchantInfo, "env");
      currencyCode = read(merchantInfo, "currency-code");
      sandboxRoot = read(merchantInfo, "sandbox-root");
      productionRoot = read(merchantInfo, "production-root");
      checkoutSuffix = read(merchantInfo, "checkout-suffix");
      merchantCheckoutSuffix = read(merchantInfo, 
          "merchant-checkout-suffix");
      requestSuffix = read(merchantInfo, "request-suffix");
    }
  }
  
  private void readNotificationHandlers(Element notificationRoot) {
    if (notificationHandlers == null) {
      notificationHandlers = new HashMap();
    }
    
    if (notificationRoot != null) {
      NodeList nodes = 
          notificationRoot.getElementsByTagName("notification-handler");
      for( int i=0; i<nodes.getLength(); i++) {
        Element elem = (Element) nodes.item(i);
        String type = read(elem, "message-type");
        String name = read(elem, "handler-class");
        setNotificationHandler(type, name);
      }
    }
  }
  
  private void readCallbackHandlers(Element callbackRoot) {
    if (callbackHandlers == null) {
      callbackHandlers = new HashMap();
    }
    
    if (callbackRoot != null) {
      NodeList nodes = 
          callbackRoot.getElementsByTagName("callback-handler");
      for( int i=0; i<nodes.getLength(); i++) {
        Element elem = (Element) nodes.item(i);
        String type = read(elem, "message-type");
        String name = read(elem, "handler-class");
        setCallbackHandler(type, name);
      }
    } 
  }

  private void initDefaultMerchantInfo() {
    merchantId = "";
    merchantKey = "";
    env = "Sandbox";
    currencyCode = "USD";
    sandboxRoot = "https://sandbox.google.com/checkout/cws/v2/Merchant";
    productionRoot = "https://checkout.google.com/cws/v2/Merchant";
    checkoutSuffix = "checkout";
    merchantCheckoutSuffix = "merchantCheckout";
    requestSuffix = "request";
  }
  
  /**
   * Creates the default standard batch of notification handlers
   */
  private void initDefaultNotificationHandlers() {
    if (notificationHandlers == null) {
      notificationHandlers = new HashMap();
    }
    
    // Insert a key-value pair for each message type
    notificationHandlers.put("new-order-notification",
        "com.google.checkout.handlers.NewOrderNotificationHandler");
    notificationHandlers.put("risk-information-notification",
        "com.google.checkout.handlers.RiskInformationNotificationHandler");
    notificationHandlers.put("order-state-change-notification",
        "com.google.checkout.handlers.OrderStateChangeNotificationHandler");
    notificationHandlers.put("charge-amount-notification",
        "com.google.checkout.handlers.ChargeAmountNotificationHandler");
    notificationHandlers.put("refund-amount-notification",
        "com.google.checkout.handlers.RefundAmountNotificationHandler");
    notificationHandlers.put("chargeback-amount-notification",
        "com.google.checkout.handlers.ChargebackAmountNotificationHandler");
    notificationHandlers.put("authorization-amount-notification",
        "com.google.checkout.handlers.AuthorizationAmountNotificationHandler");
  }
  
  /**
   * Creates the default standard batch of callback handlers
   */
  private void initDefaultCallbackHandlers() {
    if (callbackHandlers == null) {
      callbackHandlers = new HashMap();
    }
    
    // Insert a key-value pair for each message type
    callbackHandlers.put("merchant-calculation-callback",
        "com.google.checkout.handlers.MerchantCalculationCallbackHandler");
  }

  /**
   * Gets an array of notification message types.
   *
   * @return Array of notification message types
   */
  public String[] getNotificationTypes() {
    Object[] keys = notificationHandlers.keySet().toArray();
    String[] types = new String[keys.length];
    
    for (int i=0; i<types.length; i++) {
      types[i] = (String) keys[i];
    }
    
    return types;
  }
  
  /**
   * Gets an array of callback message types.
   *
   * @return Array of callback message types
   */
  public String[] getCallbackTypes() {
    Object[] keys = callbackHandlers.keySet().toArray();
    String[] types = new String[keys.length];
    
    for (int i=0; i<types.length; i++) {
      types[i] = (String) keys[i];
    }
    
    return types;
  }
  
  /**
   * Reads the value of an XML element from a parent element.
   *
   * @param parent The parent element to read from
   * @param name The name of the element to look for
   * @return The value of the element
   */
  private String read(Element parent, String name) {
    Element elem = (Element) parent.getElementsByTagName(name).item(0);
    Node value = (Node)elem.getChildNodes().item(0);
    
    if (value == null) {
      return "";
    } else {
      return value.getNodeValue().trim();
    }
  }

  /**
   * Populates the Google Checkout configuration fields. If the InputStream is 
   * null or there is an error parsing the xml file, the configuration fields
   * will be populated with default values.
   */
  private void readContent(final InputStream tempStream) {

    boolean success = true;
    
    if (tempStream != null) {
      try {
        // Get the document
        DocumentBuilderFactory docBuilderFactory = 
            DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = null;
        try { 
          doc = docBuilder.parse(tempStream);
        } catch (FileNotFoundException ex) {
          success = false;
        }
        if (success) {
          doc.getDocumentElement().normalize();

          // Read merchant information
          readMerchantInfo(
              (Element)doc.getElementsByTagName("merchant-info").item(0));

          // Read notification handlers
          readNotificationHandlers(
              (Element)doc.getElementsByTagName("notification-handlers").item(0));

          // Read callback handlers
          readCallbackHandlers(
              (Element)doc.getElementsByTagName("callback-handlers").item(0));
        }
      } catch (ParserConfigurationException ex) {
          success = false;
          ex.printStackTrace();
      } catch (IOException ex) {
          success = false;
          ex.printStackTrace();
      } catch (SAXException ex) {
          success = false;
          ex.printStackTrace();
      }   
    } else {
      success = false;
    }
    
    if (!success) {
      // read from default
      initDefaultMerchantInfo();
      initDefaultNotificationHandlers();
      initDefaultCallbackHandlers();
    }
  }
  
  /*************************************************************************/
  /*                            FILE METHODS                               */
  /*************************************************************************/
  
  /**
   * Generates the new body of checkout-config.xml based on all of this
   * class's fields.
   *
   * @return The new body
   */
  public String getBody() {
    String body = "";
    
    // Begin
    body += "<checkout-config>\n";
    
    // Merchant info
    body += "    <merchant-info>\n"
        + "        <merchant-id>" + merchantId + "</merchant-id>\n"
        + "        <merchant-key>" + merchantKey + "</merchant-key>\n"
        + "        <env>" + env + "</env>\n"
        + "        <currency-code>" + currencyCode + "</currency-code>\n"
        + "        <sandbox-root>" + getSandboxRoot() + "</sandbox-root>\n"
        + "        <production-root>" + getProductionRoot() + "</production-root>\n"
        + "        <checkout-suffix>" + getCheckoutSuffix() + "</checkout-suffix>\n"
        + "        <merchant-checkout-suffix>" + getMerchantCheckoutSuffix()
        + "</merchant-checkout-suffix>\n"
        + "        <request-suffix>" + getRequestSuffix() + "</request-suffix>\n"
        + "    </merchant-info>\n";
    
    // Notification handlers
    body += "    <notification-handlers>\n";
    Object[] keys = notificationHandlers.keySet().toArray();
    for (int i=0; i<keys.length; i++) {
      String key = (String) keys[i];
      String value = (String) notificationHandlers.get(key);
      if (value != null) {
        body += "        <notification-handler>\n"
            + "            <message-type>" + key + "</message-type>\n"
            + "            <handler-class>" + value + "</handler-class>\n"
            + "        </notification-handler>\n";
      }
    }
    body += "    </notification-handlers>\n";
    
    // Callback handlers
    body += "    <callback-handlers>\n";
    keys = callbackHandlers.keySet().toArray();
    for (int i=0; i<keys.length; i++) {
      String key = (String) keys[i];
      String value = (String) callbackHandlers.get(key);
      if (value != null) {
        body += "        <callback-handler>\n"
            + "            <message-type>" + key + "</message-type>\n"
            + "            <handler-class>" + value + "</handler-class>\n"
            + "        </callback-handler>\n";
      }
    }
    body += "    </callback-handlers>\n";
    
    // End
    body += "</checkout-config>";
    
    return body;
  }
}
