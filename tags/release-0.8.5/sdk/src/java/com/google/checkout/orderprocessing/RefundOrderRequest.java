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

import com.google.checkout.CheckoutException;
import com.google.checkout.MerchantInfo;
import com.google.checkout.util.Utils;

/**
 * This class contains methods that construct &lt;refund-order&gt; API requests.
 *  
 * @author Charles Dang (cdang@google.com)
 */
public class RefundOrderRequest extends AbstractOrderProcessingRequest {
  public static final int REFUND_STRING_LIMIT = 140;
  
  public static final String REFUND_ERROR_STRING = "The refund string limits " +
    "have been exceeded. The reason and comment cannot exceed " + 
    REFUND_STRING_LIMIT + " characters.";
  
  /**
   * Constructor which takes an instance of MerchantInfo.
   * 
   * @param merchantInfo The merchant's information.
   * 
   * @throws CheckoutException if merchantInfo is null.
   */
  public RefundOrderRequest(MerchantInfo merchantInfo) 
    throws CheckoutException {
    super(merchantInfo, "refund-order");
  }

  /**
   * Constructor which takes an instance of MerchantInfo, the Google order 
   * number and the reason for the refund.
   * 
   * @param merchantInfo The merchant's information.
   * @param googleOrderNumber The Google order number.
   * @param reason The reason for the refund.
   * 
   * @throws CheckoutException if merchantInfo or reason is null.
   */
  public RefundOrderRequest(MerchantInfo merchantInfo, String googleOrderNumber, 
    String reason) throws CheckoutException {
    this(merchantInfo);
    setGoogleOrderNumber(googleOrderNumber);
    setReason(reason);
  }

  /**
   * Constructor which takes an instance of MerchantInfo, the Google order 
   * number, the reason for the refund, the refund amount and any additional 
   * comment.
   * 
   * @param merchantInfo The merchant's information.
   * @param googleOrderNumber The Google order number.
   * @param reason The reason for the refund.
   * @param amount The amount to refund the buyer.
   * @param comment Any additional comment needed.
   * 
   * @throws CheckoutException if merchantInfo, reason or comment is null.
   */
  public RefundOrderRequest(MerchantInfo merchantInfo, String googleOrderNumber,
    String reason, float amount, String comment) throws CheckoutException {
    this(merchantInfo);
    setGoogleOrderNumber(googleOrderNumber);
    setReason(reason);
    setAmount(amount);
    setComment(comment);
  }

  /**
   * Determine whether the reason and comment are within the string length
   * limits. See REFUND_STRING_LIMIT for the maximum length allowed.
   * 
   * @param reason The reason for the refund.
   * @param comment Any additional comment needed.
   * 
   * @return True if the reason and the comment strings are within the allowable
   * limit; otherwise false.
   * 
   * @throws CheckoutException if reason or comment is null.
   */
  public boolean isWithinRefundStringLimits(String reason, String comment) 
    throws CheckoutException {
    if (reason == null || comment == null) {
      throw new CheckoutException(REFUND_ERROR_STRING);
    }
    
    return ((reason.length() <= REFUND_STRING_LIMIT) 
      && (comment.length() <= REFUND_STRING_LIMIT));
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
   * 
   * @throws CheckoutException if amount <= 0.
   */
  public void setAmount(float amount) throws CheckoutException {
    if (amount <= 0) {
      throw new CheckoutException("Refund amount must be greater than 0.");
    }
    
    Element e = Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(), 
      "amount", amount);
    e.setAttribute("currency", merchantInfo.getCurrencyCode());
  }

  /**
   * Set the refund order comment String, which is the value of the
   * &lt;comment&gt; tag. Comment will be truncated if it exceeds the refund
   * string limit. See RefundOrderRequest.REFUND_STRING_LIMIT.
   * 
   * @param comment The refund comment String.
   * 
   * @throws CheckoutException if comment is null.
   */
  public void setComment(String comment) throws CheckoutException {
    if (!isWithinRefundStringLimits("", comment)) {
      comment = comment.substring(0, REFUND_STRING_LIMIT);
    }

    Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(), "comment", 
      comment);
  }

  /**
   * Set the refund reason String, which is the value of the &lt;reason&gt; tag.
   * Reason will be truncated if it exceeds the refund string limit. See 
   * RefundOrderRequest.REFUND_STRING_LIMIT.
   * 
   * @param reason The refund reason String.
   * 
   * @throws CheckoutException if reason is null.
   */
  public void setReason(String reason) throws CheckoutException {
    if (!isWithinRefundStringLimits(reason, "")) {
      reason = reason.substring(0, REFUND_STRING_LIMIT);
    }
    Utils.findElementAndSetElseCreateAndSet(getDocument(), getRoot(), "reason", 
      reason);
  }
}
