/*
 * WriteWebXmlException.java
 *
 * Created on September 26, 2007, 1:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.google.checkout.sdk.nbmodule.common.exceptions;

/**
 *
 * @author cdang
 */
public class WebXmlException extends java.lang.Exception {
  
  /**
   * Creates a new instance of <code>WriteWebXmlException</code> without detail message.
   */
  public WebXmlException() {
  }
  
  
  /**
   * Constructs an instance of <code>WriteWebXmlException</code> with the specified detail message.
   * @param msg the detail message.
   */
  public WebXmlException(String msg) {
    super(msg);
  }
}
