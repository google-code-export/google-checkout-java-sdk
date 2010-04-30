/*******************************************************************************
 * Copyright (C) 2009 Google Inc.
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
package com.google.checkout.sdk.commands;

import com.google.checkout.sdk.testing.AbstractCommandTestCase;

/**
 * Test ApiContextImpl core behaviors. Though it does generate builders itself,
 * tests for them shouldn't go in this file: They are more appropriate in the
 * individual test cases for those builders.
 * 
*
 */
public class ApiContextTest extends AbstractCommandTestCase {

  public void testHttpAuth() throws Exception {
    String httpAuth = apiContext().getHttpAuth();
    assertEquals("Basic " + BASE64_ENCODED_ID_AND_KEY, httpAuth);
  }
  
  public void testIsValidAuthWithRealAuth() throws Exception {
    assertTrue(apiContext().isValidAuth("Basic " + BASE64_ENCODED_ID_AND_KEY));
  }
  
  public void testIsValidAuthWithInvalidAuth() throws Exception {
    assertFalse(apiContext().isValidAuth("Basic: foo"));
    assertFalse(apiContext().isValidAuth(null));
  }
}
