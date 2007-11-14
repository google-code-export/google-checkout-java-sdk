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
import com.google.checkout.util.Constants;
import com.google.checkout.util.Utils;

/**
 * This class contains methods that construct &lt;cancel-order&gt; API requests.
 */
public class CancelOrderRequest extends AbstractOrderProcessingRequest {
  
  public CancelOrderRequest(MerchantInfo mi) {
    super(mi, "cancel-order");
  }

  /**
   * Constructor which takes an instance of mi, Google Order Number and Reason
   * String.
   * 
   * @param googleOrderNo The Google Order Number.
   * @param reason The Reason String.
   */
  public CancelOrderRequest(MerchantInfo mi, String googleOrderNo, 
      String reason) {
    this(mi);
    setGoogleOrderNumber(googleOrderNo);
    setReason(reason);
  }

  /**
   * Constructor which takes an instance of mi, Google Order Number, Reason
   * String and Comment String.
   * 
   * @param googleOrderNo The Google Order Number.
   * @param reason The Reason String.
   * @param comment The Comment String.
   */
  public CancelOrderRequest(MerchantInfo mi, String googleOrderNo,
      String reason, String comment) {
    this(mi);
    setGoogleOrderNumber(googleOrderNo);
    setReason(reason);
    setComment(comment);
  }

  /**
   * Determine whether the reason and comment are within the string length
   * limits.
   * 
   * @param reason The Reason.
   * @param comment The Comment.
   * @return True or false.
   */
  public boolean isWithinCancelStringLimits(String reason, String comment) {
    int lenStrReason = reason.length();
    int lenStrComment = comment.length();

    if (lenStrReason <= Constants.cancelStrLimit
        && lenStrComment <= Constants.cancelStrLimit)
      return true;
    else
      return false;
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
   * &lt;comment&gt; tag.
   * 
   * @param comment The cancel order comment String.
   */
  public void setComment(String comment) {
    if (!isWithinCancelStringLimits("", comment)) {
      comment = "";
      System.err.println(Constants.cancelErrorString);
    }

    Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(), "comment", comment);
  }

  /**
   * Set the cancel order reason String, which is the value of the
   * &lt;reason&gt; tag.
   * 
   * @param reason The cancel order reason String.
   */
  public void setReason(String reason) {
    if (!isWithinCancelStringLimits(reason, "")) {
      reason = "";
      System.err.println(Constants.cancelErrorString);
    }

    Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(), "reason", reason);
  }
}
