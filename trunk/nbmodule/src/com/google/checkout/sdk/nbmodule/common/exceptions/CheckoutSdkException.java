/*
 * CopyCheckoutSDKException.java
 *
 * Created on September 26, 2007, 1:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.google.checkout.sdk.nbmodule.common.exceptions;

/**
 *
 * @author cdang
 */
public class CheckoutSdkException extends java.lang.Exception {
  
  /**
   * Creates a new instance of <code>CopyCheckoutSDKException</code> without detail message.
   */
  public CheckoutSdkException() {
  }
  
  
  /**
   * Constructs an instance of <code>CopyCheckoutSDKException</code> with the specified detail message.
   * @param msg the detail message.
   */
  public CheckoutSdkException(String msg) {
    super(msg);
  }
}
