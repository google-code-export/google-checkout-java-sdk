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

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Test grab-bag utility methods.
 *
 */
public class UtilsTest extends TestCase {
  public void testSlurp() throws Exception {
    testSlurp("");
    testSlurp("abcABC123" + "$\t\u00A3\n\u00A5\u20AC");
  }

  public void testSlurpSlightlyShortString() throws Exception {
    StringBuilder sb;
    sb = new StringBuilder();
    for (int i = 0; i < 1023; i++) {
      sb.append("z");
    }
    testSlurp(sb.toString());
  }

  public void testSlurpLongString() throws Exception {
    StringBuilder sb;
    sb = new StringBuilder();
    for (int i = 0; i < 2048; i++) {
      sb.append("z");
    }
    testSlurp(sb.toString());
  }

  private void testSlurp(String toSlurp) throws Exception {
    InputStream testStream = new ByteArrayInputStream(toSlurp.getBytes("utf-8"));
    assertEquals(toSlurp, Utils.slurp(testStream));
  }
}
