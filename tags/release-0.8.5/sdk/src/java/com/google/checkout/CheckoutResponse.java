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

import com.google.checkout.util.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.InputStream;

/**
 * The default implementation of the CheckoutResponse interface.
 *
 * @author ksim
 * @version 1.0 - ksim - March 7th, 2007 - Initial Version to separate interface
 *          and implementation
 */
public class CheckoutResponse {
  
  private final Document document;
  
  private final Element root;
  
  public CheckoutResponse() throws CheckoutException {
    this("");
  }
  
  /**
   * A constructor which takes the response String.
   * 
   * @param response The response string
   * @throws com.google.checkout.CheckoutException
   */
  public CheckoutResponse(String response) throws CheckoutException {
    document = Utils.newDocumentFromString(response);
    root = document.getDocumentElement();
  }
            
  /**
   * A constructor which takes the response as an InputStream.
   * 
   * @param response The response as an InputStream
   * @throws com.google.checkout.CheckoutException
   */
  public CheckoutResponse(InputStream response) throws CheckoutException {
    document = Utils.newDocumentFromInputStream(response);
    root = document.getDocumentElement();
  }
  
  /**
   * Gets a value indicating whether Google returned an error code or not.
   * true if the response did not indicate an error; otherwise, false.
   *
   * @return The boolean value.
   */
  public boolean isValidRequest() {
    String nodeName = root.getNodeName();
    if ("checkout-redirect".equals(nodeName)
        || "request-received".equals(nodeName)) {
      return true;
    }
    return false;
  }
  
  /**
   * Gets the serial number. Google attaches a unique serial number to every
   * response. The serial number, for example
   * 58ea39d3-025b-4d52-a697-418f0be74bf9.
   *
   * @return The serial number.
   */
  public String getSerialNumber() {
    return root.getAttribute("serial-number");
  }
  
  /**
   * If Google responded with an error (isGood = false) then this property
   * will contain the human-readable error message. The error message returned
   * by Google, or an empty string if there was no error.
   *
   * @return The error message.
   */
  public String getErrorMessage() {
    return Utils.getElementStringValue(document, root, "error-message");
  }
  
  /**
   * If Google indicated a redirect URL in the response, this property will
   * contain the URL string. The redirect URL, or the empty string if Google
   * didn't send a redirect URL.
   *
   * @return The redirect URL.
   */
  public String getRedirectUrl() {
    return Utils.getElementStringValue(document, root, "redirect-url");
  }
  
  /**
   * Return the XML response String.
   *
   * @return The XML response String.
   */
  public String getXml() {    
    return Utils.documentToString(document);
  }
  
  /**
   * Return the nicely formatted XML response String.
   *
   * @return The nicely formatted XML response String.
   */
  public String getXmlPretty() {    
    return Utils.documentToStringPretty(document);
  }
}
