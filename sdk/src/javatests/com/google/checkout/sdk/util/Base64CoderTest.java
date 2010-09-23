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
package com.google.checkout.sdk.util;

import com.google.checkout.sdk.util.Base64Coder;

import junit.framework.TestCase;

/**
 * Tests Base64 encoding utilities.
 *
 */
public class Base64CoderTest extends TestCase {
  private final String CURRENCIES = "$\t\u00A3\n\u00A5\u20AC";
  private final String SOME_TEXT = "Here are some smileys (:-D @@@@:-D {:-D *:-D";
  public void testBase64RoundTripString() throws Exception {
    assertEquals(CURRENCIES,
        Base64Coder.decode(Base64Coder.encode(CURRENCIES)));
    assertEquals(SOME_TEXT,
        Base64Coder.decode(Base64Coder.encode(SOME_TEXT)));
  }

  public void testBase64RoundTripBytes() throws Exception {
    assertEquals(CURRENCIES,
        new String(
            Base64Coder.decode(Base64Coder.encode(CURRENCIES.getBytes("utf-8"))),
            "utf-8"));
    assertEquals(SOME_TEXT,
        new String(
            Base64Coder.decode(Base64Coder.encode(SOME_TEXT.getBytes("utf-8"))),
            "utf-8"));
  }
}
