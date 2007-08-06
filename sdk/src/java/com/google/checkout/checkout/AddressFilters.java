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

package com.google.checkout.checkout;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.checkout.util.Utils;

/**
 * This class represents the &lt;address-filters&gt; tag in the Checkout API.
 * Similar to the shipping restrictions, filters will be applied before Google
 * Checkout sends a <merchant-calculation-callback> to the merchant.
 * 
 * @author simonjsmith@google.com
 */
public class AddressFilters {

  private final Document document;

  private final Element root;

  /**
   * The default constructor.
   */
  public AddressFilters() {

    document = Utils.newEmptyDocument();
    root = document.createElement("address-filters");
    document.appendChild(root);
  }

  /**
   * This method adds an allowed U.S. country area to a &lt;us-country-area&gt;
   * element. The &lt;us-country-area&gt; element, in turn, appears as a
   * subelement of &lt;allowed-areas&gt;.
   * 
   * @param countryArea The country area.
   * 
   * @see USArea
   */
  public void addAllowedCountryArea(USArea countryArea) {

    Element allowedAreas =
        Utils.findContainerElseCreate(document, root, "allowed-areas");
    Element usCountry =
        Utils.createNewContainer(document, allowedAreas, "us-country-area");
    usCountry.setAttribute("country-area", countryArea.toString());
  }

  /**
   * This method adds an allowed U.S. state code to a &lt;us-state-area&gt;
   * element. The &lt;us-state-area&gt; element, in turn, appears as a
   * subelement of &lt;allowed-areas&gt;.
   * 
   * @param stateCode The state code.
   */
  public void addAllowedStateCode(String stateCode) {

    Element allowedAreas =
        Utils.findContainerElseCreate(document, root, "allowed-areas");
    Element usState =
        Utils.createNewContainer(document, allowedAreas, "us-state-area");
    Utils.createNewElementAndSet(document, usState, "state", stateCode);
  }

  /**
   * This method adds an allowed zip code pattern to a &lt;us-zip-area&gt;
   * element. The &lt;us-zip-area&gt; element, in turn, appears as a subelement
   * of &lt;allowed-areas&gt;.
   * 
   * @param zipPattern The zip pattern.
   */
  public void addAllowedZipPattern(String zipPattern) {

    Element allowedAreas =
        Utils.findContainerElseCreate(document, root, "allowed-areas");
    Element usZip =
        Utils.createNewContainer(document, allowedAreas, "us-zip-area");
    Utils.createNewElementAndSet(document, usZip, "zip-pattern", zipPattern);
  }

  /**
   * This method adds an excluded U.S. country area to a &lt;us-country-area&gt;
   * element. The &lt;us-country-area&gt; element, in turn, appears as a
   * subelement of &lt;excluded-areas&gt;.
   * 
   * @param countryArea The country area.
   * 
   * @see USArea
   */
  public void addExcludedCountryArea(USArea countryArea) {

    Element excludedAreas =
        Utils.findContainerElseCreate(document, root, "excluded-areas");
    Element usCountry =
        Utils.createNewContainer(document, excludedAreas, "us-country-area");
    usCountry.setAttribute("country-area", countryArea.toString());
  }

  /**
   * This method adds an excluded U.S. state code to a &lt;us-state-area&gt;
   * element. The &lt;us-state-area&gt; element, in turn, appears as a
   * subelement of &lt;excluded-areas&gt;.
   * 
   * @param stateCode The state code.
   */
  public void addExcludedStateCode(String stateCode) {

    Element excludedAreas =
        Utils.findContainerElseCreate(document, root, "excluded-areas");
    Element usState =
        Utils.createNewContainer(document, excludedAreas, "us-state-area");
    Utils.createNewElementAndSet(document, usState, "state", stateCode);
  }

  /**
   * This method adds an excluded zip code pattern to a &lt;us-zip-area&gt;
   * element. The &lt;us-zip-area&gt; element, in turn, appears as a subelement
   * of &lt;excluded-areas&gt;.
   * 
   * @param zipPattern The zip pattern.
   */
  public void addExcludedZipPattern(String zipPattern) {

    Element excludedAreas =
        Utils.findContainerElseCreate(document, root, "excluded-areas");
    Element usZip =
        Utils.createNewContainer(document, excludedAreas, "us-zip-area");
    Utils.createNewElementAndSet(document, usZip, "zip-pattern", zipPattern);
  }

  /**
   * Get the root element, &lt;shipping-restrictions&gt;
   * 
   * @return The root element, &lt;shipping-restrictions&gt;.
   */
  public Element getRootElement() {
    return root;
  }

  /**
   * This method adds an allowed &lt;postal-area&gt; element. The
   * &lt;country-code&gt; element and optionally the &lt;postal-code-pattern&gt
   * are subelements .
   * 
   * @param countryCode The country code.
   * @param postalCodePattern The Postal Code Pattern.
   * 
   */
  public void addAllowedPostalArea(String countryCode, String postalCodePattern) {
    Element allowedAreas =
        Utils.findContainerElseCreate(document, root, "allowed-areas");
    Element pa =
        Utils.createNewContainer(document, allowedAreas, "postal-area");
    Utils.createNewElementAndSet(document, pa, "country-code", countryCode);
    Utils.createNewElementAndSet(document, pa, "postal-code-pattern",
        postalCodePattern);
  }

  /**
   * This method adds an allowed &lt;world-area&gt; element.
   * 
   */
  public void addAllowedWorldArea() {
    Element allowedAreas =
        Utils.findContainerElseCreate(document, root, "allowed-areas");
    Utils.createNewContainer(document, allowedAreas, "world-area");
  }

  /**
   * This method adds an excluded &lt;postal-area&gt; element. The
   * &lt;country-code&gt; element and optionally the &lt;postal-code-pattern&gt
   * are subelements .
   * 
   * @param countryCode The country code.
   * @param postalCodePattern The Postal Code Pattern.
   * 
   */
  public void addExcludedPostalArea(String countryCode, String postalCodePattern) {
    Element excludedAreas =
        Utils.findContainerElseCreate(document, root, "excluded-areas");
    Element pa =
        Utils.createNewContainer(document, excludedAreas, "postal-area");
    Utils.createNewElementAndSet(document, pa, "country-code", countryCode);
    Utils.createNewElementAndSet(document, pa, "postal-code-pattern",
        postalCodePattern);
  }

  /**
   * This method adds an excluded &lt;world-area&gt; element.
   * 
   */
  public void addExcludedWorldArea() {
    Element excludedAreas =
        Utils.findContainerElseCreate(document, root, "excluded-areas");
    Utils.createNewContainer(document, excludedAreas, "world-area");
  }

  /**
   * Retrieve the value of the &lt;allow-us-po-box&gt; tag.
   * 
   * @return Whether the shipping allows a US PO Box.
   */
  public boolean isAllowUsPoBox() {
    return Utils.getElementBooleanValue(document, root, "allow-us-po-box");
  }

  /**
   * Set the value of the &lt;allow-us-po-box&gt; tag.
   * 
   * @param b Whether the shipping allows a US PO Box.
   */
  public void setAllowUsPoBox(boolean b) {
    Utils.findElementAndSetElseCreateAndSet(document, root, "allow-us-po-box",
        b);
  }
}
