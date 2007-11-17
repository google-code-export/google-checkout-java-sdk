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


import com.google.checkout.CheckoutException;
import com.google.checkout.CheckoutSystemException;
import com.google.checkout.util.Constants;
import com.google.checkout.util.Utils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The parent for all Checkout requests.
 *
 * @author simonjsmith@google.com
 */
public abstract class AbstractCheckoutRequest {
  
  protected MerchantInfo mi;

  private final Document document;
  private final Element root;
  
  /**
   * @param merchantInfo The merchant's information
   * @param requestType The request type
   */
  public AbstractCheckoutRequest(MerchantInfo merchantInfo, String requestType) {
    this.mi = merchantInfo;
    
    document = Utils.newEmptyDocument();
    
    root = document.createElementNS(Constants.checkoutNamespace, requestType);
    root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns",
      Constants.checkoutNamespace);
    document.appendChild(root);
  }
  
  /**
   * @param merchantInfo The merchant's info
   * @param document A document that has already been constructed
   */
  public AbstractCheckoutRequest(MerchantInfo merchantInfo, Document document) {
    this.mi = merchantInfo;
    this.document = document;
    this.root = document.getDocumentElement();   
  }
  
  /**
   * @return The document for this CheckoutRequest
   */
  protected Document getDocument() {
    return document;
  }
  
  /**
   * @return The root of the document for this CheckoutRequest
   */
  protected Element getRoot() {
    return root;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see com.google.checkout.CheckoutRequest#getPostUrl()
   */
  public String getPostUrl() {
    return mi.getRequestUrl();
  }
  
   /**
   * Return the XML request String.
   * 
   * @return The XML request String.
   */
  public String getXml() {
    return Utils.documentToString(document);
  }

  /**
   * Return the nicely formatted XML request String.
   *
   * @return The nicely formatted XML request String.
   */
  public String getXmlPretty() {
    return Utils.documentToStringPretty(document);
  }

  /**
   * Submit the request to the POST URL and return a CheckoutResponse.
   * 
   * @return The CheckoutResponse object.
   * 
   * @see CheckoutResponse
   * 
   * @throws com.google.checkout.exceptions.CheckoutException if the post URL
   * for the merchant info was invalid.
   */
  public CheckoutResponse send() throws CheckoutException {
    try {
      URL url = new URL(getPostUrl());
      HttpURLConnection connection = (HttpURLConnection) url
          .openConnection();
      
      connection.setDoInput(true);
      connection.setDoOutput(true);
      connection.setUseCaches(false);
      connection.setInstanceFollowRedirects(true);
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Authorization", "Basic "
          + mi.getHttpAuth());
      connection
          .setRequestProperty("Host", connection.getURL().getHost());
      
      // Changed to allow i18n character sets to be processed properly
      connection.setRequestProperty("content-type",
          "application/xml; charset=UTF-8");
      connection.setRequestProperty("accept", "application/xml");
      
      PrintWriter output = new PrintWriter(new OutputStreamWriter(
          connection.getOutputStream(), "UTF8"));      
      output.print(getXml());
      output.flush();
      output.close();
      
      int responseCode = connection.getResponseCode();
      InputStream inputStream;
      
      if (responseCode == HttpURLConnection.HTTP_OK) {
        inputStream = connection.getInputStream();
      } else {
        inputStream = connection.getErrorStream();
      }
      
      return new CheckoutResponse(inputStream);
    }
    catch (MalformedURLException ex) {
      throw new CheckoutException(
        "MalformedURLException encountered.  URL was: " + getPostUrl(), ex);
    } catch (IOException ex) {
      throw new CheckoutSystemException("IOException encountered.");
    }
  }
}
