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

import com.google.checkout.CheckoutException;
import com.google.checkout.checkout.Item;
import com.google.checkout.util.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class ShoppingCart {
  private final Document document;
  private final Element element;

  public ShoppingCart(Document document, Element element) {
    this.document = document;
    this.element = element;
  }
  
  /**
   * 
   * Retrieves the value of the &lt;good-until-date&gt; element.
   * 
   * @return The cart expiration.
   * @see Date
   * @throws com.google.checkout.exceptions.CheckoutException if there was an
   * error retrieving the cart expiration date
   */
  public Date getCartExpiration() throws CheckoutException { 
    Element cartExpiration = 
      Utils.findContainerElseCreate(document, element, "cart-expiration");

    return Utils.getElementDateValue(document, cartExpiration, "good-until-date");
  }
  
    /**
   * Retrieves the contents of the &lt;items&gt; element as a Collection of Item
   * objects.
   * 
   * @return The Collection of Item objects.
   * @see Item
   */
  public Collection getItems() {
    Element items =
        Utils.findElementOrContainer(document, element, "items");
    Element[] elements = Utils.getElements(document, items);
    Collection ret = new ArrayList();

    for (int i = 0; i < elements.length; i++) {
      ret.add(new Item(document, elements[i]));
    }
    return ret;
  }
  
    /**
   * Retrieves the contents of the &lt;merchant-private-data&gt; element as an
   * array of Elements. 
   * 
   * @return The contents &lt;merchant-private-data&gt; element value.
   * 
   * @see Element
   */
  public Element[] getMerchantPrivateDataNodes() {    
    Element mpd = Utils.findElementOrContainer(document, element,
      "merchant-private-data");
    if (mpd == null) {
      return null;
    }
    return Utils.getElements(document, mpd);
  }
}
