/*
 * SamplesJspException.java
 *
 * Created on September 26, 2007, 1:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.google.checkout.sdk.nbmodule.common.exceptions;

/**
 *
 * @author cdang
 */
public class SamplesJspException extends java.lang.Exception {
  
  /**
   * Creates a new instance of <code>SamplesJspException</code> without detail message.
   */
  public SamplesJspException() {
  }
  
  
  /**
   * Constructs an instance of <code>SamplesJspException</code> with the specified detail message.
   * @param msg the detail message.
   */
  public SamplesJspException(String msg) {
    super(msg);
  }
}
