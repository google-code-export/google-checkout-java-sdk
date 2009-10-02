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

import com.google.checkout.util.Utils;
import junit.framework.TestCase;

/**
 *
 * @author cdang
 */
public class DigitalContentTest extends TestCase {
  public void testDigitalContentNodeNames() {
    DigitalContent digitalContent = new DigitalContent();
    digitalContent.setEmailDelivery(true);
    digitalContent.setDescription("description");
    digitalContent.setKey("0123456abcdef");
    digitalContent.setUrl("http://checkout.google.com");
    
    assertEquals(
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?><digital-content>" + 
      "<email-delivery>true</email-delivery><description>description" + 
      "</description><key>0123456abcdef</key><url>http://checkout.google.com" + 
      "</url></digital-content>", 
      Utils.nodeToString(digitalContent.getRootElement()));
  }
}
