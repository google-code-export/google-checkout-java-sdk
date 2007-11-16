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
import com.google.checkout.util.Utils;

/**
 * This class contains methods that construct &lt;deliver-order&gt; API
 * requests.
 */
public class DeliverOrderRequest extends AbstractOrderProcessingRequest {

  public DeliverOrderRequest(MerchantInfo mi) {
    super(mi, "deliver-order");
  }

  /**
   * Constructor which takes an instance of mi and the Google Order Number.
   */
  public DeliverOrderRequest(MerchantInfo mi, String googleOrderNo) {

    this(mi);
    setGoogleOrderNumber(googleOrderNo);
  }

  /**
   * Constructor which takes an instance of mi, the Google Order Number, the
   * Carrier, Tracking Number and the Send Email flag.
   */
  public DeliverOrderRequest(MerchantInfo mi, String googleOrderNo,
      String carrier, String trackingNo, boolean sendEmail) {

    this(mi);
    setGoogleOrderNumber(googleOrderNo);
    setCarrier(carrier);
    setTrackingNumber(trackingNo);
    setSendEmail(sendEmail);
  }

  /**
   * Return the carrier String, which is the value of the &lt;carrier&gt; tag.
   * 
   * @return The carrier String.
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
   * @deprecated
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
   * @param carrier The carrier String.
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
   * @deprecated
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
   * @param trackingNo The tracking number.
   */
  public void setTrackingNumber(String trackingNumber) {

    Element trackingDataTag =
        Utils.findContainerElseCreate(getDocument(), getRoot(), "tracking-data");
    Utils.findElementAndSetElseCreateAndSet(getDocument(), trackingDataTag,
        "tracking-number", trackingNumber);
  }
}
