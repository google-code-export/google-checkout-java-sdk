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

package com.google.checkout.orderprocessing.lineitem;

import com.google.checkout.CheckoutException;
import com.google.checkout.MerchantInfo;
import com.google.checkout.orderprocessing.AbstractOrderProcessingRequest;
import com.google.checkout.util.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class contains methods that construct &lt;cancel-items&gt; API requests.
 * 
 * @author Charles Dang (cdang@google.com)
 */
public class CancelItemsRequest extends AbstractOrderProcessingRequest {

  public static final int CANCEL_STRING_LIMIT = 140;
  
  public static final String CANCEL_ERROR_STRING = "The cancel string limits " +
    "have been exceeded. The reason and comment cannot exceed " + 
    CANCEL_STRING_LIMIT + "characters.";
  
  /**
   * Constructor which takes an instance of MerchantInfo.
   * 
   * @param merchantInfo The merchant's information.
   */
  public CancelItemsRequest(MerchantInfo merchantInfo) {
    super(merchantInfo, "cancel-items");
  }

  /**
   * Constructor which takes an instance of MerchantInfo and the Google order 
   * number.
   *
   * @param merchantInfo The merchant's information
   * @param googleOrderNumber The Google order number.
   * 
   */
  public CancelItemsRequest(MerchantInfo merchantInfo, String googleOrderNumber) {
    this(merchantInfo);
    setGoogleOrderNumber(googleOrderNumber);
  }

  /**
   * Constructor which takes an instance of MerchantInfo, Google order number 
   * and the reason for the cancellation.
   * 
   * @param merchantInfo The merchant's information
   * @param googleOrderNumber The Google order number.
   * @param reason The reason for the cancellation.
   */
  public CancelItemsRequest(MerchantInfo merchantInfo, String googleOrderNumber, 
      String reason) {
    this(merchantInfo);
    setGoogleOrderNumber(googleOrderNumber);
    setReason(reason);
  }

  /**
   * Constructor which takes an instance of merchantInfo, Google order number, 
   * reason for cancellation and comment.
   * 
   * @param merchantInfo The merchant's information
   * @param googleOrderNo The Google order number.
   * @param reason The reason for cancellation.
   * @param comment An additional comment.
   * 
   * @throws CheckoutException if merchantInfo is null.
   */
  public CancelItemsRequest(MerchantInfo merchantInfo, String googleOrderNo,
      String reason, String comment) {
    this(merchantInfo);
    setGoogleOrderNumber(googleOrderNo);
    setReason(reason);
    setComment(comment);
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
   * True if an email is to be sent to the buyer. This is the value of the
   * &lt;send-email&gt; tag.
   * 
   * @param sendEmail The boolean value.
   */
  public void setSendEmail(boolean sendEmail) {
    Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(), "send-email",
        sendEmail);
  }

  /**
   * Add the merchantItemId which is to be canceled.
   * 
   * @param merchantItemId
   */
  public void addItem(String merchantItemId) {
    Document document = getDocument();
    Element root = getRoot();
    
    Element idsTag = Utils.findContainerElseCreate(document, root, "item-ids");
    Element idTag = Utils.createNewContainer(document, idsTag, "item-id");
    Utils.createNewElementAndSet(document, idTag, "merchant-item-id",
        merchantItemId);
  }

  /**
   * Return the cancel order comment String, which is the value of the
   * &lt;comment&gt; tag.
   * 
   * @return The cancel order comment String.
   */
  public String getComment() {
    return Utils.getElementStringValue(getDocument(), getRoot(), "comment");
  }

  /**
   * Return the cancel order reason String, which is the value of the
   * &lt;reason&gt; tag.
   * 
   * @return The cancel order reason String.
   */
  public String getReason() {
    return Utils.getElementStringValue(getDocument(), getRoot(), "reason");
  }

  /**
   * Set the cancel order comment String, which is the value of the
   * &lt;comment&gt; tag. Comment will be truncated if it exceeds the cancel 
   * string limit. See CancelOrderRequest.CANCEL_STRING_LIMIT.
   * 
   * @param comment A comment associated with the cancellation.
   */
  public void setComment(String comment) {
    if (!isWithinCancelStringLimits("", comment)) {
      comment = comment.substring(0, CANCEL_STRING_LIMIT);
    }

    Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(), "comment", 
      comment);
  }

  /**
   * Set the cancel order reason String, which is the value of the
   * &lt;reason&gt; tag. Reason will be truncated if it exceeds the reason 
   * string limit. See CancelOrderRequest.CANCEL_STRING_LIMIT.
   * 
   * @param reason The reason for cancellation.
   */
  public void setReason(String reason) {
    if (!isWithinCancelStringLimits(reason, "")) {
      reason = reason.substring(0, CANCEL_STRING_LIMIT);
    }

    Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(), "reason", 
      reason);
  } 
  
  /**
   * Determine whether the reason and comment are within the string length
   * limits. See CancelOrderRequest.CANCEL_STRING_LIMIT for the maximum allowed 
   * length for reason and comment.
   * 
   * @param reason The reason for cancellation.
   * @param comment An additional comment.
   * 
   * @return True if the cancellation reason and the additional comment are
   * within the allowed limits.
   * 
   * @throws CheckoutException if reason is null or if comment if null.
   */
  public boolean isWithinCancelStringLimits(String reason, String comment) {
    if (reason == null || comment == null) {
      throw new IllegalArgumentException(CANCEL_ERROR_STRING);
    }

    return ((reason.length() <= CANCEL_STRING_LIMIT) && 
      (comment.length() <= CANCEL_STRING_LIMIT));
  }
}
