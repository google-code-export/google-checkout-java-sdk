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

import org.w3c.dom.Element;

import com.google.checkout.CheckoutException;
import com.google.checkout.MerchantInfo;
import com.google.checkout.orderprocessing.AbstractOrderProcessingRequest;
import com.google.checkout.util.Utils;
import org.w3c.dom.Document;

/**
 * This class contains methods that construct
 * &lt;reset-items-shipping-information&gt; API requests.
 * 
 * @author Charles Dang (cdang@google.com)
 */
public class ResetItemsShippingInformationRequest extends AbstractOrderProcessingRequest {

  /**
   * Constructor which takes an instance of MerchantInfo.
   * 
   * @param merchantInfo The merchant's information.
   */
  public ResetItemsShippingInformationRequest(MerchantInfo merchantInfo) {
    super(merchantInfo, "reset-items-shipping-information");
  }

  /**
   * Constructor which takes an instance of MerchantInfo and the Google order 
   * number.
   *    
   * @param merchantInfo The merchant's information.
   * @param googleOrderNumber The Google order number
   */
  public ResetItemsShippingInformationRequest(MerchantInfo merchantInfo,
      String googleOrderNumber) {

    this(merchantInfo);
    setGoogleOrderNumber(googleOrderNumber);
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
   * Add the merchantItemId which is to be reset.
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
}
