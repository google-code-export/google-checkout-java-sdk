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
 * @author cdang
 */
public class SamplesJspException extends java.lang.Exception {
 
  /**
   * Creates a new instance of <code>SamplesJspException</code> without detail 
   * message.
   */
  public SamplesJspException() {}

  /**
   * Constructs an instance of <code>SamplesJspException</code> with the 
   * specified detail message.
   * 
   * @param msg The detail message.
   */
  public SamplesJspException(String msg) {
    super(msg);
  }
  
  /**
   * Constructs an instance of <code>SamplesJspException</code> with the
   * specified detail message and the original cause of the exception.
   * 
   * @param msg The detail message.
   * @param cause The original cause if the exception.
   */
  public SamplesJspException(String msg, Throwable cause) {
    super(msg, cause);
  }
  
  /**
   * Constructs an instance of <code>SamplesJspException</code>
   * 
   * @param cause The original cause of the exception.
   */
  public SamplesJspException(Throwable cause) {
    super(cause);
  }
}