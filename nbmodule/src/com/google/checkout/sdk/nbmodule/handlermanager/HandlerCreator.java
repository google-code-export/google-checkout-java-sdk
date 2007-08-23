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

package com.google.checkout.sdk.nbmodule.handlermanager;

import com.google.checkout.sdk.nbmodule.common.CheckoutFileWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Handles the creation of java handler class, including loading a template
 * if necessary.
 * 
 * @author David Rubel
 */
public class HandlerCreator {
  
  /**
   * Default constructor.  This class must be constructed in order to use
   * the getResourceAsStream() method that reads the template files.
   */
  public HandlerCreator() {
  }
  
  /**
   * Creates a Java file that represents a new Checkout SDK handler using
   * information from a HandlerCreationData object.
   *
   * @param handlerData Info used to create the handler
   * @return True if successfully created and written
   */
  public boolean createHandler(HandlerCreationData handlerData) {
    boolean success = true;
    
    // Read template
    String template = readTemplate(getImplFile(handlerData));
    
    if (template != null) {
      // Read basic handler info and get handler type
      String handlerName = handlerData.getHandlerName();
      String handlerPackage = handlerData.getHandlerPackage();
      String handlerTypePackage =
          getNotificationClassFromType(handlerData.getHandlerType());
      
      // Build handler type from the handler type package name
      String handlerType = handlerTypePackage;
      int loc = handlerType.lastIndexOf(".") + 1;
      if (loc >= 0) {
        handlerType = handlerType.substring(loc);
      }
      
      // Replace things in the template
      template = template.replace("<name>", handlerName);
      template = template.replace("<package>", handlerPackage);
      template = template.replace("<type-package>", handlerTypePackage);
      template = template.replace("<type>", handlerType);
      
      // Write the template to a file
      try {
        File file = new File(handlerData.getHandlerLocation());
        CheckoutFileWriter.writeFileFromString(template, file);
      } catch (IOException ex) {
        success = false;
      }
    } else {
      success = false;
    }
    
    return success;
  }
  
  /**
   * Reads a template file at the specified location and returns the
   * contents.
   *
   * @param filename The template's path
   * @return The contents of the file
   */
  private String readTemplate(String path) {
    StringBuilder builder = new StringBuilder();
    InputStream source = getClass().getResourceAsStream(path);
    
    try {
      // Read from the input stream and write to the string builder
      int ch;
      while ((ch = source.read()) != -1) {
        builder.append((char)ch);
      }
      
      // Close streams
      source.close();
    } catch (IOException ex) {}
    
    return builder.toString();
  }
  
  /**
   * Gets the template's file path associated with the implementation of a
   * HandlerCreationData object.
   * 
   * @param handlerData The implementation data
   * @return The path to the corresponding template
   */
  private static String getImplFile(HandlerCreationData handlerData) {
    // TODO: Look this up from a config file
    String fileName = null;
    
    if (handlerData.getHandlerImpl().equals(HandlerCreationData.EMPTY_CLASS)) {
      fileName = "/resources/EmptyHandler.txt";
    }
    
    return fileName;
  }
  
  /**
   * Gets the name of the Java notification class that corresponds to a given
   * message type.
   *
   * @param type The message type
   * @return The Java notification class name
   */
  private String getNotificationClassFromType(String type) {
    // TODO: Look this up from a config file
    HashMap types = new HashMap();
    types.put("new-order-notification", 
        "com.google.checkout.notification.NewOrderNotification");
    types.put("risk-information-notification", 
        "com.google.checkout.notification.RiskInformationNotification");
    types.put("order-state-change-notification", 
        "com.google.checkout.notification.OrderStateChangeNotification");
    types.put("charge-amount-notification", 
        "com.google.checkout.notification.ChargeAmountNotification");
    types.put("refund-amount-notification", 
        "com.google.checkout.notification.RefundAmountNotification");
    types.put("chargeback-amount-notification", 
        "com.google.checkout.notification.ChargebackAmountNotification");
    types.put("authorization-amount-notification", 
        "com.google.checkout.notification.AuthorizationAmountNotification");
    types.put("merchant-calculation-callback", 
        "com.google.checkout.merchantcalculation.MerchantCalculationCallback");
    
    return (String) types.get(type);
  }
}
