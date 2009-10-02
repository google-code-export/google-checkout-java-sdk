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

package com.google.checkout.merchantcalculation;

import com.google.checkout.util.Constants;
import com.google.checkout.util.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Collection;
import java.util.Iterator;

/**
 * This class encapsulates the &lt;merchant-calculation-callback&gt; message
 * which is part of the Merchant Calculation API.
 * 
 * @author simonjsmith
 * 
 */
public class MerchantCalculationResults {

  private final Document document;

  private final Element root;

  private final Element results;

  public MerchantCalculationResults() {
    document = Utils.newEmptyDocument();
    root = document.createElementNS(Constants.checkoutNamespace, 
        "merchant-calculation-results");
    root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns",
        Constants.checkoutNamespace);
    document.appendChild(root);
    results = document.createElement("results");
    root.appendChild(results);
}

/**
 * Add a result.
 * 
 * @param shippingName The shipping name.
 * @param addressId The address id.
 * @param shippable The shippable flag.
 * @param totalTaxAmount The total tax amount.
 * @param shippingRate The shipping rate.
 * @param currency The currency code.
 * @param merchantCodeResults A collection of MerchantCodeResult objects.
 * 
 * @see MerchantCodeResult
 */
  public void addResult(String shippingName, String addressId, 
      boolean shippable, double totalTaxAmount, double shippingRate, 
      String currency, Collection merchantCodeResults) {
    
//    Element result = Utils.createNewContainer(document, results, "result");
//    
//    result.setAttribute("shipping-name", shippingName);
//    result.setAttribute("address-id", addressId);
//    
//    Utils.createNewElementAndSet(document, result, "shippable", shippable);
//    
//    Element tax = Utils.createNewElementAndSet(document, result, "total-tax", 
//        totalTaxAmount);
//
//    tax.setAttribute("currency", currency);
//    
//    Element shipping = Utils.createNewElementAndSet(document, result, 
//        "shipping-rate", shippingRate);
//    
//    shipping.setAttribute("currency", currency);
//
//    if (merchantCodeResults != null) {
//      Element codes = Utils.createNewContainer(document, result,
//          "merchant-code-results");
//      
//      Iterator it = merchantCodeResults.iterator();
//      MerchantCodeResult mcResult;
//      Element eResult;
//      
//      while (it.hasNext()) {
//        mcResult = (MerchantCodeResult) it.next();
//        eResult = Utils.createNewContainer(document, codes, mcResult.getType());
//        
//        Utils.createNewElementAndSet(document, eResult, "valid", 
//            mcResult.isValid());
//  
//        Utils.createNewElementAndSet(document, eResult, "calculated-amount", 
//            mcResult.getCalculatedAmount());
//        
//        Utils.createNewElementAndSet(document, eResult, "code", 
//            mcResult.getCode());
//        
//        Utils.createNewElementAndSet(document, eResult, "message", 
//            mcResult.getMessage());
//      }
//    }
    addResult(shippingName, addressId, shippable, shippingRate, currency, merchantCodeResults);
    Element result = Utils.findElementOrContainer(document, results, "result");
    Element tax = Utils.createNewElementAndSet(document, result, "total-tax", 
    totalTaxAmount);
  
    tax.setAttribute("currency", currency);
  }

/**
 * Add a result without including a total-tax amount. It is important not to
 * send back data which is not required.
 * 
 * @param shippingName  The shipping name.
 * @param addressId The address id.
 * @param shippable The shippable flag.
 * @param shippingRate  The shipping rate.
 * @param currency  The currency code.
 * @param merchantCodeResults A collection of MerchantCodeResult objects.
 * 
 * @see MerchantCodeResult
 */
  public void addResult(String shippingName, String addressId, 
      boolean shippable, double shippingRate, String currency, 
      Collection merchantCodeResults) {
    
    Element result = Utils.createNewContainer(document, results, "result");
    
    result.setAttribute("shipping-name", shippingName);
    result.setAttribute("address-id", addressId);

    Utils.createNewElementAndSet(document, result, "shippable", shippable);

    Element shipping = Utils.createNewElementAndSet(document, result,
				"shipping-rate", shippingRate);
    
    shipping.setAttribute("currency", currency);

    Element codes = Utils.createNewContainer(document, result, 
        "merchant-code-results");

    if (merchantCodeResults != null) {
      Iterator it = merchantCodeResults.iterator();
      MerchantCodeResult mcResult;
      Element eResult;
  
      while (it.hasNext()) {
        mcResult = (MerchantCodeResult) it.next();  
        eResult = Utils.createNewContainer(document, codes, mcResult.getType());
  
        Utils.createNewElementAndSet(document, eResult, "valid", 
            mcResult.isValid());
        
        Utils.createNewElementAndSet(document, eResult, "calculated-amount", 
            mcResult.getCalculatedAmount());
  
        Utils.createNewElementAndSet(document, eResult, "code", 
            mcResult.getCode());
        
        Utils.createNewElementAndSet(document, eResult, "message", 
            mcResult.getMessage());
      }
    }
  }

  /**
   * Returns the document representing the CheckoutShoppingCartRequest
   *
   * @return The document representing the CheckoutShoppingCartRequest
   */
  protected Document getDocument() {
    return document;
  }
  
 /**
  * Return the XML String.
  * 
  * @return The XML String.
  */
  public String getXml() {
    return Utils.nodeToString(document);
  }

  /**
   * Return the nicely formatted XML String.
   * 
   * @return The nicely formatted XML String.
   */
  public String getXmlPretty() {
    return Utils.documentToStringPretty(document);
  }
}
