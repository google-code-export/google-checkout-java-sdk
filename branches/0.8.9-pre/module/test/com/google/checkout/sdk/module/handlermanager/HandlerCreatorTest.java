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
package com.google.checkout.sdk.module.handlermanager;

import com.google.checkout.sdk.module.common.CheckoutFileReader;

import junit.framework.TestCase;
import java.io.IOException;

/**
 * Unit tests for HandlerCreator.
 *
 * @author David Rubel
 */
public class HandlerCreatorTest extends TestCase {
  
  public HandlerCreatorTest(String testName) {
    super(testName);
  }

  /**
   * Tests that createHandler() creates the correct handler based on an empty
   * file template.
   */
  public void testCreateEmptyHandler() {
    System.out.println("createHandler with empty class template");
  
    // Setup handler creation data
    HandlerCreationData handlerData = new HandlerCreationData();
    handlerData.setHandlerName("TempHandler");
    handlerData.setHandlerPackage("com.google.test");
    handlerData.setHandlerLocation("");
    handlerData.setHandlerClass(HandlerCreationData.NOTIFICATION);
    handlerData.setHandlerType("new-order-notification");
    handlerData.setHandlerImpl(HandlerCreationData.EMPTY_CLASS);
    
    // Create HandlerCreator instance
    HandlerCreator instance = new HandlerCreator();
    
    // Verify that the handler creation succeeded (non-null)
    String result = instance.createHandlerAsString(handlerData);
    assertNotNull(result);
    
    // Get the expected result
    String expResult = null;
    try {
      expResult = CheckoutFileReader.readFileAsString(
          getClass().getResourceAsStream("/resources/expected-temp-handler.txt"));
    } catch (IOException ex) {
      fail("Could not read expected-temp-handler.txt.");
    }
    
    // Verify the contents of the file
    assertEquals(result, expResult);
  }
}
