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

import org.w3c.dom.Element;

/**
 * This class contains methods that construct &lt;add-tracking-data&gt; API
 * requests.
 * 
 * @author Charles Dang (cdang@google.com)
 */
public class AddTrackingDataRequest extends AbstractOrderProcessingRequest {
  
  /**
   * Constructor which takes an instance of MerchantInfo
   * 
   * @param merchantInfo The merchant's information
   */
  public AddTrackingDataRequest(MerchantInfo merchantInfo) {
    super(merchantInfo, "add-tracking-data");
  }

  /**
   * Constructor which takes an instance of MerchantInfo, the Google order 
   * number, the carrier and the tracking number
   * 
   * @param googleOrderNumber The Google order number.
   * @param carrier The carrier.
   * @param trackingNumber The tracking number.
   */
  public AddTrackingDataRequest(MerchantInfo merchantInfo, String googleOrderNumber,
      String carrier, String trackingNumber) {
    this(merchantInfo);
    setGoogleOrderNumber(googleOrderNumber);
    setCarrier(carrier);
    setTrackingNumber(trackingNumber);
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
   * Set the carrier string, which is the value of the &lt;carrier&gt; tag.
   * 
   * @param carrier The carrier string.
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
   */
  public void setTrackingNumber(String trackingNumber) {
    Element trackingDataTag =
        Utils.findContainerElseCreate(getDocument(), getRoot(), "tracking-data");
    Utils.findElementAndSetElseCreateAndSet(getDocument(), trackingDataTag,
        "tracking-number", trackingNumber);
  }
}
