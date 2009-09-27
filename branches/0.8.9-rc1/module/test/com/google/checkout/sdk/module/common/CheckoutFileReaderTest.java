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
package com.google.checkout.sdk.module.common;

import junit.framework.TestCase;

/**
 * Unit tests for CheckoutFileReader.
 *
 * @author David Rubel
 */
public class CheckoutFileReaderTest extends TestCase {
  
  public CheckoutFileReaderTest(String testName) {
    super(testName);
  }

  /**
   * Tests that readFileAsString() reads all the text in a file.
   */
  public void testReadFileAsString() throws Exception {
    System.out.println("readFileAsString");
    
    // Get the test file and verify that the correct contents are 
    // read from the file
    String expResult = "Test text\nTesting, 1... 2... 3...";
    String result = 
        CheckoutFileReader.readFileAsString(
          getClass().getResourceAsStream("/resources/test.txt"));
    assertEquals(expResult, result);
  }
}
