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

package com.google.checkout.sdk.module.handlermanager;

/**
 * Stores all of the information needed to create a new handler.
 *
 * @author David Rubel
 */
public class HandlerCreationData {
  
  /*************************************************************************/
  /*                           SPECIAL VALUES                              */
  /*************************************************************************/
  
  // Handlers classes
  public static String NOTIFICATION = "notification";
  public static String CALLBACK = "callback";

  // Implementation types
  public static String EMPTY_CLASS = "empty_Class";
  
  /*************************************************************************/
  /*                           PRIVATE FIELDS                              */
  /*************************************************************************/
  
  private String handlerName;
  private String handlerPackage;
  private String handlerLocation;
  private String handlerClass;
  private String handlerType;
  private String handlerImpl;
  
  /*************************************************************************/
  /*                           FIELD ACCESSORS                             */
  /*************************************************************************/

  public String getHandlerName() {
    return handlerName;
  }

  public void setHandlerName(String handlerName) {
    this.handlerName = handlerName;
  }

  public String getHandlerPackage() {
    return handlerPackage;
  }

  public void setHandlerPackage(String handlerPackage) {
    this.handlerPackage = handlerPackage;
  }

  public String getHandlerLocation() {
    return handlerLocation;
  }

  public void setHandlerLocation(String handlerLocation) {
    this.handlerLocation = handlerLocation;
  }
  
  public String getHandlerClass() {
    return handlerClass;
  }

  public void setHandlerClass(String handlerClass) {
    this.handlerClass = handlerClass;
  }

  public String getHandlerType() {
    return handlerType;
  }

  public void setHandlerType(String handlerType) {
    this.handlerType = handlerType;
  }

  public String getHandlerImpl() {
    return handlerImpl;
  }

  public void setHandlerImpl(String handlerImpl) {
    this.handlerImpl = handlerImpl;
  }
}
