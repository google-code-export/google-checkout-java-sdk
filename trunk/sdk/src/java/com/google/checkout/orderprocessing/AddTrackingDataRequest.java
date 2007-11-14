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
 * This class contains methods that construct &lt;add-tracking-data&gt; API
 * requests.
 */
public class AddTrackingDataRequest extends AbstractOrderProcessingRequest {
  public AddTrackingDataRequest(MerchantInfo mi) {
    super(mi, "add-tracking-data");
  }

  /**
   * Constructor which takes an instance of mi, the Google Order Number, the
   * Carrier and the Tracking Number
   * 
   * @param googleOrderNo The Google Order Number.
   * @param carrier The Carrier.
   * @param trackingNo The Tracking Number.
   */
  public AddTrackingDataRequest(MerchantInfo mi, String googleOrderNo,
      String carrier, String trackingNo) {
    this(mi);
    setGoogleOrderNumber(googleOrderNo);
    setCarrier(carrier);
    setTrackingNumber(trackingNo);
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
   * @return The tracking number.
   */
  @Deprecated
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
   * Set the tracking number, which is the value of the &lt;tracking-number&gt;
   * tag.
   * 
   * @param trackingNo The tracking number.
   */
  @Deprecated
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
  public void setTrackingNumber(String trackingNo) {
    Element trackingDataTag =
        Utils.findContainerElseCreate(getDocument(), getRoot(), "tracking-data");
    Utils.findElementAndSetElseCreateAndSet(getDocument(), trackingDataTag,
        "tracking-number", trackingNo);
  }
}
