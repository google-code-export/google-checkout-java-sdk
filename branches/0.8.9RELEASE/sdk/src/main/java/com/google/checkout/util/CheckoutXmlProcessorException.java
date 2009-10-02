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

package com.google.checkout.util;

import com.google.checkout.CheckoutException;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class CheckoutXmlProcessorException extends CheckoutException {

    /**
     * Creates a new instance of <code>CheckoutXmlProcessorException</code> 
     * without detail message.
     */
    public CheckoutXmlProcessorException() {
    }


    /**
     * Constructs an instance of <code>CheckoutXmlProcessorException</code> with 
     * the specified detail message.
     * @param msg the detail message.
     */
    public CheckoutXmlProcessorException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>CheckoutXmlProcessorException</code> with 
     * the wrapped exception
     * @param ex The wrapped exception
     */
    public CheckoutXmlProcessorException(Exception ex) {
      super(ex);
    }
}
