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

package com.google.checkout.util;

import junit.framework.TestCase;

public class StringUtilTest extends TestCase {

  public void testReplaceMultipleStringsStringString() {
    assertEquals("abcdef", StringUtil.replaceMultipleStrings("abc{0}", "def"));
  }

  public void testReplaceMultipleStringsStringStringString() {
    assertEquals("abcdefghi", StringUtil.replaceMultipleStrings("abc{0}{1}", "def", "ghi"));
  }

  public void testReplaceMultipleStringsStringStringArray() {
    String[] replaceStr = {"def", "ghi"};
    assertEquals("abcdefghi", StringUtil.replaceMultipleStrings("abc{0}{1}", replaceStr));
  }

  public void testReplaceXMLReservedChars() {
    assertEquals("abc&#x3c;def", StringUtil.replaceXMLReservedChars("abc<def"));
    assertEquals("abc&#x3c;d&#x3e;ef", StringUtil.replaceXMLReservedChars("abc<d>ef"));
    assertEquals("abc&#x26;def", StringUtil.replaceXMLReservedChars("abc&def"));
  }

  public void testRemoveChar() {
    assertEquals("abdef", StringUtil.removeChar("abcdef", 'c'));
  }
}
