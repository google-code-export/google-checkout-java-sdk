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

package com.google.checkout.handlers;

import com.google.checkout.CheckoutException;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class CheckoutHandlerException extends CheckoutException {
    private int errorCode;
  
    /**
     * Creates a new instance of <code>CheckoutHandlerException</code> without 
     * detail message.
     */
    public CheckoutHandlerException() {
    }


    /**
     * Constructs an instance of <code>CheckoutHandlerException</code> with the 
     * specified detail message.
     * 
     * @param msg The detail message.
     */
    public CheckoutHandlerException(String msg) {
      super(msg);
    }
    
    /**
     * 
     * @param errorCode
     */
    public CheckoutHandlerException(int errorCode) {
      this.errorCode = errorCode;
    }
    
    /**
     * Constructs an instance of <code>CheckoutHandlerException</code> with the
     * specified nested exception
     * 
     * @param e The nested exception
     */
    public CheckoutHandlerException(Exception e) {
      
    }
  
    /**
     * Constructs an instance of <code>CheckoutHandlerException</code> with the 
     * specified detail message.
     * 
     * @param msg The detail message.
     * @param e The nested exception
     */
    public CheckoutHandlerException(String msg, Exception e) {
      super(msg, e);
    }
    
    /**
     * Constructs an instance of <code>CheckoutHandlerException</code> with the 
     * specified error code.
     * 
     * @return The error code associated with this exception
     */
    public int getErrorCode() {
      return this.errorCode;
    }
}
