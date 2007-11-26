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

package com.google.checkout.notification;

import com.google.checkout.util.Utils;
import java.util.ArrayList;
import java.util.Collection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class OrderAdjustment {
  private Document document;
  private Element element;

  public OrderAdjustment(Document document, Element element) {
    this.document = document;
    this.element = element;
  }
  
  /**
   * Retrieves the value of the &lt;merchant-calculation-successful&gt; element.
   * 
   * @return The merchant calculation successful flag.
   */
  public boolean isMerchantCalculationSuccessful() { 
    Element orderAdjustment =
        Utils.findElementOrContainer(document, element, "order-adjustment");
    return Utils.getElementBooleanValue(document, orderAdjustment,
        "merchant-calculation-successful");
  }
  
  /**
   * Retrieves the value of the &lt;adjustment-total&gt; element.
   * 
   * @return The adjustment total amount.
   */
  public float getAdjustmentTotal() {    
    Element orderAdjustment =
        Utils.findElementOrContainer(document, element, "order-adjustment");
    return Utils.getElementFloatValue(document, orderAdjustment,
        "adjustment-total");
  }
  
  /**
   * Retrieves the contents of the &lt;merchant-codes&gt; element as a
   * collection of MerchantCodes objects.
   * 
   * @return The merchant codes.
   * 
   * @see MerchantCodes
   * @see GiftCertificateAdjustment
   * @see CouponAdjustment
   */
  public Collection getMerchantCodes() {
    Element oa =
        Utils.findElementOrContainer(document, element, "order-adjustment");
    Element mc = Utils.findElementOrContainer(document, oa, "merchant-codes");

    Element[] elements = Utils.getElements(document, mc);
    Collection ret = new ArrayList();

    Element e;
    String name;
    for (int i = 0; i < elements.length; i++) {
      e = elements[i];
      name = e.getNodeName();
      if ("gift-certificate-adjustment".equals(name)) {
        ret.add(new GiftCertificateAdjustment(document, e));
      } else if ("coupon-adjustment".equals(name)) {
        ret.add(new CouponAdjustment(document, e));
      }
    }
    return ret;
  }
  
  /**
   * Retrieves the value of the &lt;shipping&gt; element as a Shipping object.
   * 
   * @return The shipping amount
   * 
   * @see Shipping
   */
  public Shipping getShipping() {    
    Element oa = Utils.findElementOrContainer(document, element, 
      "order-adjustment");
    Element shipping = Utils.findElementOrContainer(document, oa, "shipping");

    Element e = Utils.findElementOrContainer(document, shipping, 
      "merchant-calculated-shipping-adjustment");
    if (e != null) {
      return new MerchantCalculatedShippingAdjustment(document, e);
    }

    e = Utils.findElementOrContainer(document, shipping,
      "flat-rate-shipping-adjustment");
    if (e != null) {
      return new FlatRateShippingAdjustment(document, e);
    }

    e = Utils.findElementOrContainer(document, shipping,
      "pickup-shipping-adjustment");
    if (e != null) {
      return new PickupShippingAdjustment(document, e);
    }

    e = Utils.findElementOrContainer(document, shipping,
      "carrier-calculated-shipping-adjustment");
    if (e != null) {
      return new CarrierCalculatedShippingAdjustment(document, e);
    }

    return null;
  }
}
