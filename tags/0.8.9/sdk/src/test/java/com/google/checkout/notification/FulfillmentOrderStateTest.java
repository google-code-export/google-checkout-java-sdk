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

import junit.framework.TestCase;

public class FulfillmentOrderStateTest extends TestCase {
  public void testGetState() {
    assertEquals("DELIVERED", FulfillmentOrderState.getState("DELIVERED").toString());
    assertEquals("NEW", FulfillmentOrderState.getState("NEW").toString());
    assertEquals("PROCESSING", FulfillmentOrderState.getState("PROCESSING").toString());
    assertEquals("WILL_NOT_DELIVER", 
      FulfillmentOrderState.getState("WILL_NOT_DELIVER").toString());
  }
  
  public void testGetStateNullValue() {
    assertNull(FulfillmentOrderState.getState(null));
  }
}
