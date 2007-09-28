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
package com.google.checkout.sdk.nbmodule.common;

import junit.framework.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.Reader;
import java.io.StringReader;

/**
 * Unit tests for CheckoutFileWriter.
 *
 * @author David Rubel
 */
public class CheckoutFileWriterTest extends TestCase {
  
  public CheckoutFileWriterTest(String testName) {
    super(testName);
  }

  /**
   * Tests writeFileFromString().
   */
  public void testWriteFileFromString() throws Exception {
    // writeFileFromString is just a wrapper for writeFileFromReader
  }

  /**
   * Tests writeFileFromStream().
   */
  public void testWriteFileFromStream() throws Exception {
    // writeFileFromStream is just a wrapper for writeFileFromReader
  }

  /**
   * Tests writeFileFromFile().
   */
  public void testWriteFileFromFile() throws Exception {
    // writeFileFromFile is just a wrapper for writeFileFromReader
  }

  /**
   * Test of writeFileFromReader method, of class 
   * com.google.checkout.sdk.nbmodule.common.CheckoutFileWriter.
   */
  public void testWriteFileFromReader() throws Exception {
    System.out.println("writeFileFromReader");
    String text = "Test text/nTesting, 1... 2... 3...";
    
    Reader source = new StringReader(text);
    File dest = createTempFile("temp1.txt");
    
    CheckoutFileWriter.writeFileFromReader(source, dest);
    
    String result = CheckoutFileReader.readFileAsString(new FileInputStream(dest));
    
    assertEquals(text, result);
  }
  
  /*************************************************************************/
  /*                            UTILITY METHODS                            */
  /*************************************************************************/
  
  /**
   * Creates a temporary file in the present working directory.  This file will
   * be deleted when the program exits.
   *
   * @param name The name of the file
   * @return The temporary file
   */
  private File createTempFile(String name) {
    File file = new File(name);
    file.deleteOnExit();
    
    return file;
  }
  
}
