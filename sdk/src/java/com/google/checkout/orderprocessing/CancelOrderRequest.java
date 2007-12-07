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

import com.google.checkout.MerchantInfo;
import com.google.checkout.util.Utils;

/**
 * This class contains methods that construct &lt;cancel-order&gt; API requests.
 * 
 * @author Charles Dang (cdang@google.com)
 */
public class CancelOrderRequest extends AbstractOrderProcessingRequest {
  public static final int CANCEL_STRING_LIMIT = 140;
  
  public static final String CANCEL_ERROR_STRING = "The cancel string limits " +
    "have been exceeded. The reason and comment cannot exceed " + 
    CANCEL_STRING_LIMIT + "characters.";
  
  /**
   * Constructor which takes an instance of MerchantInfo
   * 
   * @param merchantInfo The merchant's information.
   */
  public CancelOrderRequest(MerchantInfo merchantInfo) {
    super(merchantInfo, "cancel-order");
  }

  /**
   * Constructor which takes an instance of MerchantInfo, Google order number 
   * and the reason for the cancellation.
   * 
   * @param googleOrderNumber The Google order number.
   * @param reason The reason for the cancellation.
   */
  public CancelOrderRequest(MerchantInfo merchantInfo, String googleOrderNumber, 
      String reason) {
    this(merchantInfo);
    setGoogleOrderNumber(googleOrderNumber);
    setReason(reason);
  }

  /**
   * Constructor which takes an instance of merchantInfo, Google order number, 
   * reason for cancellation and comment.
   * 
   * @param googleOrderNo The Google order number.
   * @param reason The reason for cancellation.
   * @param comment An additional comment.
   * 
   * @throws CheckoutException if merchantInfo is null.
   */
  public CancelOrderRequest(MerchantInfo merchantInfo, String googleOrderNo,
      String reason, String comment) {
    this(merchantInfo);
    setGoogleOrderNumber(googleOrderNo);
    setReason(reason);
    setComment(comment);
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
}
