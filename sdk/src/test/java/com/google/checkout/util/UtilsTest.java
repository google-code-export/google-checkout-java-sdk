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

import com.google.checkout.CheckoutException;

import junit.framework.TestCase;

import java.io.InputStream;
import java.util.Date;


/**
 * Tests for the Utils.java class
 * 
 * @author Charles Dang (cdang@google.com)
 */
public class UtilsTest extends TestCase {

  /**
   * Small test to verify indent.xsl exists under the src directory
   */
  public void testIndentXslExists() {
    InputStream inputStream = Utils.class.getResourceAsStream("indent.xsl");

    assertTrue(null != inputStream);
  }

  public void testDateParsing() throws CheckoutException {
    Date date = Utils.parseDate("2008-02-22T12:44:35.000Z");
    assertEquals("2008-02-22T12:44:35.000Z", Utils.getDateString(date));

    System.out.println(Utils.getDateString(new Date()));
  }
}
