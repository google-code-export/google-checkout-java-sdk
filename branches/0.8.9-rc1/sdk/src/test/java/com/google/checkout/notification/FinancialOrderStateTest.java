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

/**
 * 
 * @author (Charles Dang) cdang@google.com
 */
public class FinancialOrderStateTest extends TestCase {
  public void testGetState() {
    assertEquals("CANCELLED", 
      FinancialOrderState.getState("CANCELLED").toString());
    assertEquals("CANCELLED_BY_GOOGLE", 
      FinancialOrderState.getState("CANCELLED_BY_GOOGLE").toString());
    assertEquals("CHARGEABLE", 
      FinancialOrderState.getState("CHARGEABLE").toString());
    assertEquals("CHARGED", FinancialOrderState.getState("CHARGED").toString());
    assertEquals("PAYMENT_DECLINED", 
      FinancialOrderState.getState("PAYMENT_DECLINED").toString());
    assertEquals("REVIEWING", 
      FinancialOrderState.getState("REVIEWING").toString());
  }
  
  public void testGetStateNullValue() {
    assertNull(FinancialOrderState.getState(null));
  }
}
