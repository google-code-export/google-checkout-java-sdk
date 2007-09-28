/*
 * NoMainProjectException.java
 *
 * Created on September 25, 2007, 9:51 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.google.checkout.sdk.nbmodule.common.exceptions;

/**
 *
 * @author cdang
 */
public class MainProjectException extends java.lang.Exception {
  
  /**
   * Creates a new instance of <code>NoMainProjectException</code> without detail message.
   */
  public MainProjectException() {
  }
  
  
  /**
   * Constructs an instance of <code>NoMainProjectException</code> with the specified detail message.
   * @param msg the detail message.
   */
  public MainProjectException(String msg) {
    super(msg);
  }
}
