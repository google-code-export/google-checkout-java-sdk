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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.checkout.AbstractCheckoutRequest;
import com.google.checkout.MerchantInfo;
import com.google.checkout.util.Constants;
import com.google.checkout.util.Utils;

/**
 * This class contains methods that construct &lt;ship-items&gt; API requests.
 */
public class ShipItemsRequest extends AbstractCheckoutRequest {

  private final Document document;

  private final Element root;

  public ShipItemsRequest(MerchantInfo mi) {
    super(mi);

    document = Utils.newEmptyDocument();
    root = document.createElementNS(Constants.checkoutNamespace, "ship-items");
    root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns",
        Constants.checkoutNamespace);
    document.appendChild(root);
  }

  /**
   * Constructor which takes an instance of mi and the Google Order Number.
   */
  public ShipItemsRequest(MerchantInfo mi, String googleOrderNo) {

    this(mi);
    this.setGoogleOrderNo(googleOrderNo);
  }

  /**
   * Set the Google Order Number, which is the value of the google-order-number
   * attribute on the root tag.
   * 
   * @param googleOrderNo The Google Order Number.
   */
  public void setGoogleOrderNo(String googleOrderNo) {

    root.setAttribute("google-order-number", googleOrderNo);
  }

  /**
   * Return the Google Order Number, which is the value of the
   * google-order-number attribute on the root tag.
   * 
   * @return The Google Order Number.
   */
  public String getGoogleOrderNo() {
    return root.getAttribute("google-order-number");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.checkout.CheckoutRequest#getXml()
   */
  public String getXml() {
    return Utils.documentToString(document);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.checkout.CheckoutRequest#getXmlPretty()
   */
  public String getXmlPretty() {
    return Utils.documentToStringPretty(document);

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.checkout.CheckoutRequest#getPostUrl()
   */
  public String getPostUrl() {
    return mi.getRequestUrl();
  }

  /**
   * True if an email is to be sent to the buyer. This is the value of the
   * &lt;send-email&gt; tag.
   * 
   * @return The boolean value.
   */
  public boolean isSendEmail() {
    return Utils.getElementBooleanValue(document, root, "send-email");
  }

  /**
   * True if an email is to be sent to the buyer. This is the value of the
   * &lt;send-email&gt; tag.
   * 
   * @param sendEmail The boolean value.
   */
  public void setSendEmail(boolean sendEmail) {

    Utils.findElementAndSetElseCreateAndSet(document, root, "send-email",
        sendEmail);
  }

  /**
   * Add the item shipping information.
   * 
   * @param merchantItemId
   * @param carrier
   * @param trackingNumber
   */

  public void addItemShippingInformation(String merchantItemId, String carrier,
      String trackingNumber) {
    addItemShippingInformation(merchantItemId,
        new TrackingData[] {new TrackingData(carrier, trackingNumber)});
  }

  /**
   * Add the item shipping information.
   * 
   * @param merchantItemId
   * @param trackingData
   * 
   * @see TrackingData
   */
  public void addItemShippingInformation(String merchantItemId,
      TrackingData[] trackingData) {
    Element listTag =
        Utils.findContainerElseCreate(document, root,
            "item-shipping-information-list");
    Element itemTag =
        Utils
            .createNewContainer(document, listTag, "item-shipping-information");
    Element idTag = Utils.createNewContainer(document, itemTag, "item-id");
    Utils.createNewElementAndSet(document, idTag, "merchant-item-id",
        merchantItemId);

    Element trackListTag =
        Utils.createNewContainer(document, itemTag, "tracking-data-list");
    for (int i = 0; i < trackingData.length; i++) {
      Element trackTag =
          Utils.createNewContainer(document, trackListTag, "tracking-data");
      Utils.createNewElementAndSet(document, trackTag, "carrier",
          trackingData[i].getCarrier());
      Utils.createNewElementAndSet(document, trackTag, "tracking-number",
          trackingData[i].getTrackingNumber());
    }
  }
}
