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

import java.io.IOException;
import junit.framework.TestCase;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileUtil;

/**
 * Unit tests for FileFinder.
 *
 * @author David Rubel
 */
public class FileFinderTest extends TestCase {
  
  public FileFinderTest(String testName) {
    super(testName);
  }

  /**
   * Test of findFile method, of class com.google.checkout.sdk.nbmodule.common.FileFinder.
   */
  public void testFindFile() {
    System.out.println("findFile");
    
    // Create mock file system
    FileObject root = null;
    FileObject file1 = null;
    FileObject file2 = null;
    try {
      FileSystem fileSystem = FileUtil.createMemoryFileSystem();
      root = fileSystem.getRoot();
      
      root.createFolder("folder1");
      FileObject folder2 = root.createFolder("folder2");
      FileObject subfolder1 = folder2.createFolder("subfolder1");
      
      file1 = root.createData("file1");
      file2 = subfolder1.createData("file2");
    } catch (IOException ex) {
      fail("Could not create mock FileSystem.");
    }
    
    if (root == null || file1 == null || file2 == null) {
      fail("Could not create mock FileSystem.");
    }
    
    // Verify that the files can be found
    assertEquals(file1, FileFinder.findFile("file1", root));
    assertEquals(file2, FileFinder.findFile("file2", root));
    
    // Verify that a non-existent file cannot be found
    assertEquals(null, FileFinder.findFile("file3", root));
    
  }
  
}
