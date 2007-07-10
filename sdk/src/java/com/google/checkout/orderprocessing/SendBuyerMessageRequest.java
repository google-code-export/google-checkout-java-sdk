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

package com.google.checkout.orderprocessing;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.checkout.AbstractCheckoutRequest;
import com.google.checkout.MerchantInfo;
import com.google.checkout.util.Constants;
import com.google.checkout.util.Utils;

/**
 * This class contains methods that construct &lt;send-buyer-message&gt; API
 * requests.
 */
public class SendBuyerMessageRequest extends AbstractCheckoutRequest {

  private Document document;

  private Element root;

  public SendBuyerMessageRequest(MerchantInfo mi) {
    super(mi);
    document = Utils.newEmptyDocument();
    root = document.createElementNS(Constants.checkoutNamespace,
        "send-buyer-message");
    root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns",
        Constants.checkoutNamespace);
    document.appendChild(root);
  }

  /**
   * Constructor which takes an instance of mi, Google Order Number and Message.
   */
  public SendBuyerMessageRequest(MerchantInfo mi, String googleOrderNo,
      String message) {
    this(mi);
    this.setGoogleOrderNo(googleOrderNo);
    this.setMessage(message);
  }

  /**
   * Constructor which takes an instance of mi, Google Order Number, Message and
   * Send Email flag.
   */
  public SendBuyerMessageRequest(MerchantInfo mi, String googleOrderNo,
      String message, boolean sendEmail) {
    this(mi, googleOrderNo, message);
    this.setGoogleOrderNo(googleOrderNo);
    this.setMessage(message);
    this.setSendEmail(sendEmail);
  }

  /**
   * Determine whether the message is within the string length limits.
   * 
   * @return True or false.
   */
  public boolean isWithinMessageStringLimits(String message) {
    int lenStrMessage = message.length();

    if (lenStrMessage <= Constants.messageStrLimit)
      return true;
    else
      return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.checkout.CheckoutRequest#getXml()
   */
  public String getXml() {
    return Utils.documentToString(document);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.checkout.CheckoutRequest#getXmlPretty()
   */
  public String getXmlPretty() {
    return Utils.documentToStringPretty(document);

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
   * Return the Google Order Number, which is the value of the
   * google-order-number attribute on the root tag.
   * 
   * @return The Google Order Number.
   */
  public String getGoogleOrderNo() {
    return root.getAttribute("google-order-number");
  }

  /**
   * Return the message which is to be sent to the buyer. This is the value of
   * the &lt;message&gt; tag.
   * 
   * @return The message.
   */
  public String getMessage() {
    return Utils.getElementStringValue(document, root, "message");
  }

  /**
   * True if an email is to be sent to the buyer. This is the value of the
   * &lt;send-email&gt; tag.
   * 
   * @return The boolean value.
   */
  public boolean isSendEmail() {
    return Utils.getElementBooleanValue(document, root, "send-email");
  }

  /**
   * Set the Google Order Number, which is the value of the google-order-number
   * attribute on the root tag.
   * 
   * @param googleOrderNo The Google Order Number.
   */
  public void setGoogleOrderNo(String googleOrderNo) {
    root.setAttribute("google-order-number", googleOrderNo);
  }

  /**
   * Set the message which is to be sent to the customer. This is the value of
   * the &lt;message&gt; tag.
   * 
   * @param message The message.
   */
  public void setMessage(String message) {
    if (!isWithinMessageStringLimits(message)) {
      message = "";
      System.err.println(Constants.messageErrorString);
    }

    Utils.findElementAndSetElseCreateAndSet(document, root, "message", message);
  }

  /**
   * True if an email is to be sent to the buyer. This is the value of the
   * &lt;send-email&gt; tag.
   * 
   * @param sendEmail The boolean value.
   */
  public void setSendEmail(boolean sendEmail) {
    Utils.findElementAndSetElseCreateAndSet(document, root, "send-email",
        sendEmail);
  }
}
