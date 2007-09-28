/*
 * HandlerCreationException.java
 *
 * Created on September 26, 2007, 5:32 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.google.checkout.sdk.nbmodule.common.exceptions;

/**
 *
 * @author cdang
 */
public class HandlerCreationException extends java.lang.Exception {
  
  /**
   * Creates a new instance of <code>HandlerCreationException</code> without detail message.
   */
  public HandlerCreationException() {
  }
  
  
  /**
   * Constructs an instance of <code>HandlerCreationException</code> with the specified detail message.
   * @param msg the detail message.
   */
  public HandlerCreationException(String msg) {
    super(msg);
  }
}
