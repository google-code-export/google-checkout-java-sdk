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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Charles Dang (cdang@google.com)
 */
public class BuyerMarketingPreferences {
  private Document document;
  private Element element;

  public BuyerMarketingPreferences(Document document, Element element) {
    this.document = document;
    this.element = element;
  }

  /**
   * Retrieves the value of the &lt;email-allowed&gt; element.
   * 
   * @return The marketing preferences flag.
   */
  public boolean isMarketingEmailAllowed() {
    Element buyerMarketingPreferences =
        Utils.findElementOrContainer(document, element,
            "buyer-marketing-preferences");
    return Utils.getElementBooleanValue(document, buyerMarketingPreferences,
        "email-allowed");
  }
}
