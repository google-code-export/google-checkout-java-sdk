package com.google.checkout.checkout;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.checkout.util.Utils;

/**
 * This class encapsulates the &lt;ship-from&gt; element.
 * 
 * @author simonjsmith
 */
public class ShipFrom {

  private final Document document;

  private final Element element;

  /**
   * The default constructor.
   */
  public ShipFrom() {
    document = Utils.newEmptyDocument();
    element = document.createElement("ship-from");
    document.appendChild(element);
  }

  /**
   * Set the id.
   * 
   * @param id The id of the ShipFrom instance.
   */
  public void setId(String id) {
    element.setAttribute("id", id);
  }

  /**
   * Set the city.
   * 
   * @param city The city.
   */
  public void setCity(String city) {
    Utils.findElementAndSetElseCreateAndSet(document, element, "city", city);
  }

  /**
   * Set the countryCode.
   * 
   * @param countryCode The countryCode.
   */
  public void setCountryCode(String countryCode) {
    Utils.findElementAndSetElseCreateAndSet(document, element, "country-code",
        countryCode);
  }

  /**
   * Set the postalCode.
   * 
   * @param postalCode The postalCode.
   */
  public void setPostalCode(String postalCode) {
    Utils.findElementAndSetElseCreateAndSet(document, element, "postal-code",
        postalCode);
  }

  /**
   * Set the region.
   * 
   * @param region The region.
   */
  public void setRegion(String region) {
    Utils
        .findElementAndSetElseCreateAndSet(document, element, "region", region);
  }

  /**
   * Get the root element, &lt;ship-from&gt;
   * 
   * @return The root element, &lt;ship-from&gt;.
   */
  public Element getRootElement() {
    return element;
  }
}
