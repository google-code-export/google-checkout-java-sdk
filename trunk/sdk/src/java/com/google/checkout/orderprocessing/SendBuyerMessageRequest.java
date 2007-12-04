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

import com.google.checkout.CheckoutException;
import com.google.checkout.MerchantInfo;
import com.google.checkout.util.Utils;

/**
 * This class contains methods that construct &lt;send-buyer-message&gt; API
 * requests.
 * 
 * @author Charles Dang (cdang@google.com)
 */
public class SendBuyerMessageRequest extends AbstractOrderProcessingRequest {
  public static final int MESSAGE_STRING_LIMIT = 255;
  
  public static final String MESSAGE_ERROR_STRING = "The message string " +
    "limits have been exceeded. The message cannot exceed " + 
    MESSAGE_STRING_LIMIT + " characters.";

  /**
   * Constructor which takes an instance of MerchantInfo.
   * 
   * @param merchantInfo The merchant's information
   * 
   * @throws CheckoutException if merchantInfo is null.
   */
  public SendBuyerMessageRequest(MerchantInfo merchantInfo) 
    throws CheckoutException {
    super(merchantInfo, "send-buyer-message");
  }

  /**
   * Constructor which takes an instance of MerchantInfo, Google order number 
   * and message.
   * 
   * @param merchantInfo The merchant's information.
   * @param googleOrderNumber The Google order number
   * @param message The message to send to the buyer.
   * 
   * @throws CheckoutException if MerchantInfo is null.
   */
  public SendBuyerMessageRequest(MerchantInfo merchantInfo, 
    String googleOrderNumber, String message) throws CheckoutException {
    this(merchantInfo);
    setGoogleOrderNumber(googleOrderNumber);
    setMessage(message);
  }

  /**
   * Constructor which takes an instance of MerchantInfo, Google order number, 
   * message and SendEmail flag.
   *
   * @param merchantInfo The merchant's information.
   * @param googleOrderNumber The Google order number.
   * @param message The message to send the buyer.
   * @param sendEmail Whether to send an email to buyer.
   * 
   * @throws CheckoutException if merchantInfo is null.
   */
  public SendBuyerMessageRequest(MerchantInfo merchantInfo, String 
    googleOrderNumber, String message, boolean sendEmail) throws 
    CheckoutException {
    this(merchantInfo, googleOrderNumber, message);
    setGoogleOrderNumber(googleOrderNumber);
    setMessage(message);
    setSendEmail(sendEmail);
  }

  /**
   * Determine whether the message is within the string length limits.
   * 
   * @return True or false.
   */
  public boolean isWithinMessageStringLimits(String message) 
    throws CheckoutException {
    if (message == null) {
      throw new CheckoutException(MESSAGE_ERROR_STRING);
    }

    return (message.length() <= MESSAGE_STRING_LIMIT);
  }

  /**
   * Return the message which is to be sent to the buyer. This is the value of
   * the &lt;message&gt; tag.
   * 
   * @return The message to send to the buyer.
   */
  public String getMessage() {
    return Utils.getElementStringValue(getDocument(), getRoot(), "message");
  }

  /**
   * True if an email is to be sent to the buyer. This is the value of the
   * &lt;send-email&gt; tag.
   * 
   * @return The boolean value.
   */
  public boolean isSendEmail() {
    return Utils.getElementBooleanValue(getDocument(), getRoot(), "send-email");
  }
  
  /**
   * Set the message which is to be sent to the customer. This is the value of
   * the &lt;message&gt; tag.  Message will be truncated if it exceeds the 
   * message string limit. See SendBuyerMessageRequest.MESSAGE_STRING_LIMIT.
   * 
   * @param message The message to send to the buyer.
   * 
   * @throws CheckoutException if message is null.
   */
  public void setMessage(String message) throws CheckoutException {
    if (!isWithinMessageStringLimits(message)) {
      message = message.substring(0, MESSAGE_STRING_LIMIT);
    }

    Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(), "message", 
      message);
  }

  /**
   * Sets the flag which will determine whether an email is sent to the buyer. 
   * This is the value of the &lt;send-email&gt; tag.
   * 
   * @param sendEmail True if an email is to be sent to the buyer; otherwise 
   * false.
   */
  public void setSendEmail(boolean sendEmail) {
    Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(), 
      "send-email", sendEmail);
  }
}
