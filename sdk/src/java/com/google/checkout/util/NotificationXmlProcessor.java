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
import com.google.checkout.handlers.MessageHandler;
import com.google.checkout.handlers.NotificationAcknowledgment;
import com.google.checkout.handlers.NotificationHandler;
import com.google.checkout.notification.CheckoutNotification;
import com.google.checkout.notification.CheckoutParserException;
import com.google.checkout.notification.NotificationParser;
import java.util.HashMap;
import org.w3c.dom.Document;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class NotificationXmlProcessor implements CheckoutXmlProcessor {
  private NotificationParser parser;
  private NotificationHandler handler;
  private MerchantInfo merchantInfo;
  private HashMap oldTypeNotificationHandlers;
  
  // TODO(cdang) where to assign merchantInfo
  
  /**
   * Default constructor
   */
  public NotificationXmlProcessor(MerchantInfo merchantInfo, NotificationParser 
    parser, NotificationHandler handler, HashMap oldTypeNotificationHandlers) {
    this.merchantInfo = merchantInfo;
    this.parser = parser;
    this.handler = handler;
    this.oldTypeNotificationHandlers = oldTypeNotificationHandlers;
  }
  
  /**
   * Processes an xml document of an arbitrary notification type
   * 
   * @param xmlDocument The xml document representation of a notification to be 
   * processed
   * @return Returns an xml document containing the results after processing the
   * xml document of a notification
   */
  public Document process(Document xmlDocument) throws 
    CheckoutXmlProcessorException {
    Document resultDocument;
    
    try {
      CheckoutNotification notification = parser.parse(xmlDocument);

      if (oldTypeNotificationHandlers != null) {
        MessageHandler oldTypeHandler = 
        (MessageHandler)oldTypeNotificationHandlers.get(notification.getType());
      
        if (oldTypeHandler != null) {
          oldTypeHandler.process(merchantInfo, Utils.documentToString(xmlDocument));
        } 
      } else if (handler != null) {
        handler.handle(merchantInfo, notification);
      }
      
      resultDocument = 
        Utils.newDocumentFromString(NotificationAcknowledgment.getAckString());
    } catch (CheckoutHandlerException ex) {
      throw new CheckoutXmlProcessorException(ex);
    } catch (CheckoutParserException ex) {
      throw new CheckoutXmlProcessorException(ex);
    } catch (CheckoutException ex) {
      throw new CheckoutXmlProcessorException(ex);
    }
    
    return resultDocument;
  }
}
