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

package com.google.checkout.sdk.module.exceptions;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class WebXmlException extends java.lang.Exception {
  
  /**
   * Creates a new instance of <code>WebXmlException</code> without detail 
   * message.
   */
  public WebXmlException() {}

  /**
   * Constructs an instance of <code>WebXmlException</code> with the specified 
   * detail message.
   * 
   * @param msg The detail message.
   */
  public WebXmlException(String msg) {
    super(msg);
  }
  
  /**
   * Constructs an instance of <code>WebXmlException</code> with the specified
   * detail message.
   * 
   * @param msg The detail message.
   * @param cause The original cause of the exception.
   */
  public WebXmlException(String msg, Throwable cause) {
    super(msg, cause);
  }
  
  /**
   * Constructs an instance of <code>WebXmlException</code> with the original
   * cause of the exception.
   * 
   * @param cause The original cause of the exception.
   */
  public WebXmlException(Throwable cause) {
    super(cause);
  }
}