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

package com.google.checkout.util;

import com.google.checkout.CheckoutException;
import com.google.checkout.MerchantInfo;
import com.google.checkout.handlers.CheckoutHandlerException;
import com.google.checkout.handlers.MerchantCallbackHandler;
import com.google.checkout.handlers.MessageHandler;
import com.google.checkout.merchantcalculation.MerchantCalculationCallback;
import com.google.checkout.merchantcalculation.MerchantCalculationResults;
import java.util.HashMap;
import org.w3c.dom.Document;

/**
 *
 * @author Charles Dang
 */
public class CallbackXmlProcessor implements CheckoutXmlProcessor {
  private MerchantCallbackHandler handler;
  private MerchantInfo merchantInfo;
  private HashMap oldTypeCallbackHandlers;
  
  // TODO(cdang) where to assign the merchantInfo?
  
  public CallbackXmlProcessor(MerchantInfo merchantInfo, MerchantCallbackHandler 
    handler, HashMap oldTypeCallbackHandlers) {
    this.merchantInfo = merchantInfo;
    this.handler = handler;
    this.oldTypeCallbackHandlers = oldTypeCallbackHandlers;
  }
  
  /** 
   * @param xmlDocument The xml document of the callback to be processed.
   * @return A Document containing the results of processing the callback 
   * Document
   * @throws com.google.checkout.util.CheckoutXmlProcessorException if there was
   * an error processing the xml document of the callback
   */
  public Document process(Document xmlDocument) throws 
    CheckoutXmlProcessorException {
    MerchantCalculationResults results;
    Document resultDocument = null;
    
    try {
      MerchantCalculationCallback callback = new MerchantCalculationCallback(xmlDocument);
    
      if (oldTypeCallbackHandlers != null) {
        MessageHandler oldTypeHandler = 
          (MessageHandler)oldTypeCallbackHandlers.get(callback.getType());
        
        if (oldTypeHandler != null) {
          oldTypeHandler.process(merchantInfo, Utils.documentToString(xmlDocument));
        } 
      } else if (handler != null) {
        results = handler.handle(merchantInfo, callback);
        resultDocument = Utils.newDocumentFromString(results.getXml());
      }
    } catch (CheckoutHandlerException ex) {
      throw new CheckoutXmlProcessorException(ex);
    } catch (CheckoutException ex) {
      throw new CheckoutXmlProcessorException(ex);
    }
    
    return resultDocument;
  }
}
