/*
 * CheckoutConfigException.java
 *
 * Created on September 26, 2007, 1:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.google.checkout.sdk.nbmodule.common.exceptions;

/**
 *
 * @author cdang
 */
public class CheckoutConfigException extends java.lang.Exception {
  
  /**
   * Creates a new instance of <code>CheckoutConfigException</code> without detail message.
   */
  public CheckoutConfigException() {
  }
  
  
  /**
   * Constructs an instance of <code>CheckoutConfigException</code> with the specified detail message.
   * @param msg the detail message.
   */
  public CheckoutConfigException(String msg) {
    super(msg);
  }
}
