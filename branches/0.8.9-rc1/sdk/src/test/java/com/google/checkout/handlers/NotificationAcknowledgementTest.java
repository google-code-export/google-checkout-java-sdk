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

package com.google.checkout.handlers;

import com.google.checkout.util.Constants;
import com.google.checkout.util.Utils;
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class NotificationAcknowledgementTest extends TestCase {
  public void testGetAckString() {
    
    Document document = Utils.newEmptyDocument();
    Element root = document.createElementNS(Constants.checkoutNamespace,
        "notification-acknowledgment");
    root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns",
        Constants.checkoutNamespace);
    document.appendChild(root);
    
    assertEquals(Utils.documentToStringPretty(document), 
      NotificationAcknowledgment.getAckString());
  }
}
