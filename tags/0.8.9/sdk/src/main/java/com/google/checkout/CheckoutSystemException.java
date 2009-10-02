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

package com.google.checkout;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class CheckoutSystemException extends RuntimeException {

    /**
     * Creates a new instance of <code>CheckoutSystemException</code> without 
     * detail message.
     */
    public CheckoutSystemException() {
    }

    /**
     * Constructs an instance of <code>CheckoutSystemException</code> with the 
     * specified detail message.
     * 
     * @param msg The detail message.
     */
    public CheckoutSystemException(String msg) {
      super(msg);
    }
    
    /**
     * Constructs an instance of <code>CheckoutSystemException</code> with the 
     * specified nested exception.
     * 
     * @param e The nested exception.
     */
    public CheckoutSystemException(Exception e) {
      
    }
    
    /**
     * Constructs an instance of <code>CheckoutSystemException</code> with the 
     * specified nested exception and the detail message.
     * 
     * @param msg The detail message.
     * @param e The nested exception
     */
    public CheckoutSystemException(String msg, Exception e) {
      super(msg, e);
    }
}
