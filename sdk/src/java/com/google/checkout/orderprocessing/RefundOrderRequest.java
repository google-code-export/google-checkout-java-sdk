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

import org.w3c.dom.Element;

import com.google.checkout.MerchantInfo;
import com.google.checkout.util.Constants;
import com.google.checkout.util.Utils;

/**
 * This class contains methods that construct &lt;refund-order&gt; API requests.
 */
public class RefundOrderRequest extends AbstractOrderProcessingRequest {

  public RefundOrderRequest(MerchantInfo mi) {
    super(mi, "refund-order");
  }

  /**
   * Constructor which takes an instance of mi, the Google Order Number and the
   * Reason text.
   * 
   * @param mi The mi.
   * @param googleOrderNo The Google Order Number.
   * @param reason The Reason.
   */
  public RefundOrderRequest(MerchantInfo mi, String googleOrderNo, String reason) {
    this(mi);
    setGoogleOrderNumber(googleOrderNo);
    setReason(reason);
  }

  /**
   * Constructor which takes an instance of mi, the Google Order Number, the
   * Reason text, the Refund Amount and the Comment.
   * 
   * @param mi The Merchant Constants.
   * @param googleOrderNo The Google Order Number.
   * @param reason The Reason.
   * @param amount The Amount.
   * @param comment The Comment.
   */
  public RefundOrderRequest(MerchantInfo mi, String googleOrderNo,
      String reason, float amount, String comment) {
    this(mi);
    setGoogleOrderNumber(googleOrderNo);
    setReason(reason);
    setAmount(amount);
    setComment(comment);
  }

  /**
   * Determine whether the reason and comment are within the string length
   * limits.
   * 
   * @param reason The Reason.
   * 
   * @param comment The Comment.
   * @return True or false.
   */
  public boolean isWithinRefundStringLimits(String reason, String comment) {
    int lenStrReason = reason.length();
    int lenStrComment = comment.length();

    if (lenStrReason <= Constants.refundStrLimit
        && lenStrComment <= Constants.refundStrLimit)
      return true;
    else
      return false;
  }

  /**
   * Return the refund amount, which is value of the &lt;amount&gt; tag.
   * 
   * @return The refund amount.
   */
  public float getAmount() {
    return Utils.getElementFloatValue(getDocument(), getRoot(), "amount");
  }

  /**
   * Return the refund comment String, which is the value of the &lt;comment&gt;
   * tag.
   * 
   * @return The refund comment String.
   */
  public String getComment() {
    return Utils.getElementStringValue(getDocument(), getRoot(), "comment");
  }

  /**
   * Return the refund reason String, which is the value of the &lt;reason&gt;
   * tag.
   * 
   * @return The refund reason String.
   */
  public String getReason() {
    return Utils.getElementStringValue(getDocument(), getRoot(), "reason");
  }

  /**
   * Set the refund amount, which is value of the &lt;amount&gt; tag.
   * 
   * @param amount The refund amount.
   */
  public void setAmount(float amount) {
    Element e = Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(), 
        "amount", amount);
    e.setAttribute("currency", mi.getCurrencyCode());
  }

  /**
   * Set the refund comment String, which is the value of the &lt;comment&gt;
   * tag.
   * 
   * @param comment The refund comment String.
   */
  public void setComment(String comment) {
    if (!isWithinRefundStringLimits("", comment)) {
      comment = "";
      System.err.println(Constants.refundErrorString);
    }

    Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(), "comment", comment);
  }

  /**
   * Set the refund reason String, which is the value of the &lt;reason&gt; tag.
   * 
   * @param reason The refund reason String.
   */
  public void setReason(String reason) {
    if (!isWithinRefundStringLimits(reason, "")) {
      reason = "";
      System.err.println(Constants.refundErrorString);
    }
    Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(), "reason", reason);
  }
}
