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

import org.w3c.dom.Element;

/**
 * This class contains methods that construct &lt;deliver-order&gt; API
 * requests.
 * 
 * @author Charles Dang (cdang@google.com)
 */
public class DeliverOrderRequest extends AbstractOrderProcessingRequest {

  /**
   * Constructor which takes an instance of MerchantInfo.
   * 
   * @param merchantInfo The merchant's information.
   * 
   * @throws CheckoutException if merchantInfo is null.
   */
  public DeliverOrderRequest(MerchantInfo merchantInfo) 
    throws CheckoutException {
    super(merchantInfo, "deliver-order");
  }

  /**
   * Constructor which takes an instance of MerchantInfo and the Google order 
   * number.
   * 
   * @param merchantInfo The merchant's information.
   * @param googleOrderNumber The Google order number.
   * 
   * @throws CheckoutException if merchantInfo is null.
   */
  public DeliverOrderRequest(MerchantInfo merchantInfo, 
    String googleOrderNumber) throws CheckoutException {
    this(merchantInfo);
    setGoogleOrderNumber(googleOrderNumber);
  }


  /**
   * Constructor which takes an instance of merchantInfo, the Google order 
   * number, the carrier, tracking number and the whether to send email.
   *
   * @param merchantInfo The merchant's information.
   * @param googleOrderNumber The Google order number.
   * @param carrier The carrier that will make the delivery.
   * @param trackingNumber The tracking number of the delivery.
   * @param sendEmail Whether to notify the buyer.
   * 
   * @throws CheckoutException if merchantInfo is null.
   */
  public DeliverOrderRequest(MerchantInfo merchantInfo, String googleOrderNumber,
    String carrier, String trackingNumber, boolean sendEmail) 
    throws CheckoutException {
    this(merchantInfo);
    setGoogleOrderNumber(googleOrderNumber);
    setCarrier(carrier);
    setTrackingNumber(trackingNumber);
    setSendEmail(sendEmail);
  }

  /**
   * Return the carrier String, which is the value of the &lt;carrier&gt; tag.
   * 
   * @return The carrier that will make the delivery.
   */
  public String getCarrier() {
    Element trackingDataTag =
        Utils.findContainerElseCreate(getDocument(), getRoot(), "tracking-data");
    return Utils.getElementStringValue(getDocument(), trackingDataTag, "carrier");
  }

  /**
   * Return the tracking number, which is the value of the
   * &lt;tracking-number&gt; tag.
   * 
   * @deprecated Use getTrackingNumber()
   * 
   * @return The tracking number.
   */
  public String getTrackingNo() {
    Element trackingDataTag =
        Utils.findContainerElseCreate(getDocument(), getRoot(), "tracking-data");
    return Utils.getElementStringValue(getDocument(), trackingDataTag,
        "tracking-number");
  }
  
  /**
   * Return the tracking number, which is the value of the
   * &lt;tracking-number&gt; tag.
   * 
   * @return The tracking number.
   */
  public String getTrackingNumber() {
    Element trackingDataTag =
        Utils.findContainerElseCreate(getDocument(), getRoot(), "tracking-data");
    return Utils.getElementStringValue(getDocument(), trackingDataTag,
        "tracking-number");
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
   * Set the carrier String, which is the value of the &lt;carrier&gt; tag.
   * 
   * @param carrier The carrier that will make the delivery.
   */
  public void setCarrier(String carrier) {

    Element trackingDataTag =
        Utils.findContainerElseCreate(getDocument(), getRoot(), "tracking-data");
    Utils.findElementAndSetElseCreateAndSet(getDocument(), trackingDataTag,
        "carrier", carrier);
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
   * Set the tracking number, which is the value of the &lt;tracking-number&gt;
   * tag.
   * 
   * @deprecated Use setTrackingNumber()
   * 
   * @param trackingNo The tracking number.
   */
  public void setTrackingNo(String trackingNo) {

    Element trackingDataTag =
        Utils.findContainerElseCreate(getDocument(), getRoot(), "tracking-data");
    Utils.findElementAndSetElseCreateAndSet(getDocument(), trackingDataTag,
        "tracking-number", trackingNo);
  }
  
  /**
   * Set the tracking number, which is the value of the &lt;tracking-number&gt;
   * tag.
   * 
   * @param trackingNumber The tracking number.
   * 
   * @throws CheckoutException if tracking number is null.
   */
  public void setTrackingNumber(String trackingNumber) 
    throws CheckoutException {
    if (trackingNumber == null) {
      throw new CheckoutException("Tracking number cannot be null.");
    }
    
    Element trackingDataTag =
        Utils.findContainerElseCreate(getDocument(), getRoot(), "tracking-data");
    Utils.findElementAndSetElseCreateAndSet(getDocument(), trackingDataTag,
        "tracking-number", trackingNumber);
  }
}
