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

package com.google.checkout.exceptions;

/**
 * This class is the base for any Checkout specific exceptions.
 * 
 * @author simonjsmith
 * 
 */
public class CheckoutException extends Exception {

  /**
   * 
   */
  public CheckoutException() {
    super();
  }
  
  /**
   * A constructor which takes a nested exception
   * 
   * @param e The exception
   * 
   * @see Exception
   */
  public CheckoutException(Exception e) {
    super(e);
  }
  
  /**
   * A constructor which takes an error message
   * 
   * @param msg The error message
   */
  public CheckoutException(String msg) {
    super(msg);
  }

}
