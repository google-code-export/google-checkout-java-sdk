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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

/**
 * A collection of static methods which take different inputs and write them
 * to a newly created file.
 *
 * @author David Rubel
 */
public class CheckoutFileWriter {
  
  /**
   * Creates the specefied file, even if its parent directory doesn't exist.
   *
   * @param file File to create
   * @param dir True if this file is a directory
   */
  private static void createFile(File file, boolean dir) throws IOException {
    File parent = file.getParentFile();
    
    // Create the parent folder if necessary
    if (parent != null && !parent.exists()) {
      parent.mkdirs();
    }
    
    // Create this file if necessary
    if (!file.exists()) {
      if (dir) {
        file.mkdir();
      } else {
        file.createNewFile();
      }
    }
  }
  
  /**
   * Writes the contents of a string to a file, creating the file and parent
   * directories if necessary.
   *
   * @param source The string to read from
   * @param dest The file to be written to
   */
  public static void writeFileFromString(String source, File dest)
      throws IOException {
    StringReader reader = new StringReader(source);
    writeFileFromReader(reader, dest);
  }
  
  /**
   * Writes the contents of a stream to a file, creating the file and parent
   * directories if necessary.
   *
   * @param source The stream to read from
   * @param dest The file to be written to
   */
  public static void writeFileFromStream(InputStream source, File dest)
      throws IOException {
    InputStreamReader reader = new InputStreamReader(source);
    writeFileFromReader(reader, dest);
  }
  
  /**
   * Writes the contents of a file to a file, creating the file and parent
   * directories if necessary.
   *
   * @param source The file to read from
   * @param dest The file to be written to
   */
  public static void writeFileFromFile(File source, File dest) 
      throws IOException {
    FileReader reader = new FileReader(source);
    writeFileFromReader(reader, dest);
  }
  
  /**
   * Writes the contents of a reader to a file, creating the file and parent
   * directories if necessary.
   *
   * @param source The reader to read from
   * @param dest The file to be written to
   */
  public static void writeFileFromReader(Reader source, File dest) 
      throws IOException {
    // Create new file if necessary
    if (!dest.exists()) {
      createFile(dest, false);
    }
    
    // Open a writer to the file
    FileWriter writer = new FileWriter(dest);
    
    // Read from the input stream and write to the writer
    int ch;
    while ((ch = source.read()) != -1) {
      writer.write(ch);
    }
    
    // Close streams
    source.close();
    writer.close();
  }
}
