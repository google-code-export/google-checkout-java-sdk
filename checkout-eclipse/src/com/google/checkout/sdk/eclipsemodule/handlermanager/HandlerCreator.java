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

package com.google.checkout.sdk.eclipsemodule.handlermanager;

import com.google.checkout.sdk.eclipsemodule.common.CheckoutConfigManager;
import com.google.checkout.sdk.eclipsemodule.common.CheckoutFileWriter;
import com.google.checkout.sdk.eclipsemodule.common.exceptions.HandlerCreationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Handles the creation of java handler class, including loading a template
 * if necessary.
 * 
 * @author David Rubel
 */
public class HandlerCreator {
  private CheckoutConfigManager configManager;
  
  /**
   * Default constructor.  This class must be constructed in order to use
   * the getResourceAsStream() method that reads the template files.
   */
  public HandlerCreator() {
    configManager = new CheckoutConfigManager();
  }
  
  /**
   * Creates a Java file that represents a new Checkout SDK handler using
   * information from a HandlerCreationData object.
   *
   * @param handlerData Info used to create the handler
   */
  public void createHandler(HandlerCreationData handlerData) 
    throws HandlerCreationException {
    
    String errorMsg = null;
    String handler = createHandlerAsString(handlerData);
    
    if (handler == null) {
      errorMsg = "Handler is null";
    } else {
      // Write the template to a file
      try {
        File file = new File(handlerData.getHandlerLocation());
        CheckoutFileWriter.writeFileFromString(handler, file);
      } catch (IOException ex) {
        errorMsg = "Problem writing handler to file";
      }  
    }
    
    if (errorMsg != null) {
      throw new HandlerCreationException(errorMsg);
    }
  }
  
  /**
   *  Creates a string representation based on the HandlerCreationData
   *
   *  @param handlerData
   *  @return An xml formated string containing handler data
   */
  public String createHandlerAsString(HandlerCreationData handlerData) {
    // Read template
    String template = readTemplate(getImplFile(handlerData));
    
    if (template != null) {
      // Read basic handler info and get handler type
      String handlerName = handlerData.getHandlerName();
      String handlerPackage = handlerData.getHandlerPackage();
      String handlerTypePackage =
          getClassFromHandlerType(handlerData.getHandlerType());
      
      // modify the path so it maps correctly to the handler we want to import
      handlerTypePackage = replace("handlers", handlerData.getHandlerClass(), handlerTypePackage);
      handlerTypePackage = replace("Handler", "", handlerTypePackage);
      
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
    }
    
    return template;
  }
  
  /**
   * 
   * @param oldStr The old pattern to be replaced
   * @param newStr The pattern that will replace oldStr
   * @param s The string that will be manipulated
   * @return The newly manipulated string that contains instances of newStr 
   *         wherever oldStr previously existed
   */
  private String replace(String oldStr, String newStr, String str) {
    int start = str.indexOf(oldStr);
    StringBuffer sb = new StringBuffer();
    if (start > 0) {
      sb.append(str.substring(0, start));
      sb.append(newStr);
      sb.append(str.substring(start+oldStr.length()));
    }
    return sb.toString();
  }
  
  /**
   * Reads a template file at the specified location and returns the
   * contents.
   *
   * @param path The template's path
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
    } catch (IOException ex) {
      System.err.println(ex.getMessage());
      ex.printStackTrace();
    }
    
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
   * @return The Java notification class name; returns null if no class matches 
   *         the handler type 
   */
  private String getClassFromHandlerType(String type) {

    // assume 'type' is of the format xxx-xxx-notification or xxx-xxx-callback
    String handlerType = type.substring(type.lastIndexOf('-') + 1);

    if (handlerType.equals("notification")) {
      return (String)configManager.getNotificationHandler(type);
    } else {
      // assume handlerType is of type "callback"
      return (String)configManager.getCallbackHandler(type);
    }
  }
}
